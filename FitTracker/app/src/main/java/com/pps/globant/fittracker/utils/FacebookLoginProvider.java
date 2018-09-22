package com.pps.globant.fittracker.utils;
import android.content.Context;
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

    public static void registerCallback(Context applicationContext, final Bus bus){
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
                                        setResponse(object,bus);
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name");
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

    private static void setResponse(JSONObject jsonObject, Bus bus) {
        String name=null;
        try {
            name=jsonObject.getString("name");
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
        public FbUser fbUser;
        public FetchingFbUserDataCompletedEvent(FbUser fbUser) {
            this.fbUser=fbUser;
        }
    }

    public static class FetchingFbUserDataErrorEvent {
        public FacebookException facebookException;
        public FetchingFbUserDataErrorEvent(FacebookException exception) {
            facebookException=exception;
        }
    }

    public static class LogOutCompleteEvent {
        //Nothing to do
    }
}
