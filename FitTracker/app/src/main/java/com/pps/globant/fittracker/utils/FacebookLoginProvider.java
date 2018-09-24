package com.pps.globant.fittracker.utils;

import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.pps.globant.fittracker.model.FbUser;
import com.squareup.otto.Bus;

import org.json.JSONException;
import org.json.JSONObject;

public class FacebookLoginProvider {
    private static boolean callbackRegistered = false;

    private static final String FIELDS="fields";
    private static final String NAME="name";
    private static final String ID="id";
    private static final String REQUESTED_FIELDS=String.format("%1$s,%2$s",ID,NAME);

    public static void registerCallback(final Bus bus) {
        if (!isCallbackRegistered()) {
            LoginManager.getInstance().registerCallback(CallBackManagerProviderForFb.getCallbackManager(),
                    new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
                                @Override
                                protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
                                                                           AccessToken currentAccessToken) {
                                    if (currentAccessToken == null) {
                                        bus.post(new LogOutCompleteEvent());
                                        this.stopTracking();
                                    }
                                }
                            };
                            accessTokenTracker.startTracking();
                            GraphRequest request = GraphRequest.newMeRequest(
                                    loginResult.getAccessToken(),
                                    new GraphRequest.GraphJSONObjectCallback() {
                                        @Override
                                        public void onCompleted(JSONObject object, GraphResponse response) {
                                            setResponse(object, bus);
                                        }
                                    });
                            Bundle parameters = new Bundle();
                            parameters.putString(FIELDS, REQUESTED_FIELDS);
                            request.setParameters(parameters);
                            request.executeAsync();
                        }

                        @Override
                        public void onCancel() {
                        }

                        @Override
                        public void onError(FacebookException exception) {
                            bus.post(new FetchingFbUserDataErrorEvent(exception));
                        }
                    });
        }
        callbackRegistered = true;
    }

    private static void setResponse(JSONObject jsonObject, Bus bus) {
        String name;
        try {
            name = jsonObject.getString(NAME);
        } catch (JSONException e) {
            bus.post(new CantRetrieveAllTheFieldsRequestedsEvent());
            return;
        }
        bus.post(new FetchingFbUserDataCompletedEvent(new FbUser(name)));
    }

    public static void logOut() {
        LoginManager.getInstance().logOut();
    }

    public static class CantRetrieveAllTheFieldsRequestedsEvent {

    }

    public static class FetchingFbUserDataCompletedEvent {
        public final FbUser fbUser;

        public FetchingFbUserDataCompletedEvent(FbUser fbUser) {
            this.fbUser = fbUser;
        }
    }

    public static class FetchingFbUserDataErrorEvent {
        public final FacebookException facebookException;
        public FetchingFbUserDataErrorEvent(FacebookException exception) {
            facebookException = exception;
        }
    }

    public static class LogOutCompleteEvent {
        //Nothing to do
    }

    private static boolean isCallbackRegistered() {
        return callbackRegistered;
    }
}