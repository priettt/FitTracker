package com.pps.globant.fittracker.utils;

import android.app.Activity;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.pps.globant.fittracker.model.User;
import com.squareup.otto.Bus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

public class FacebookLoginProvider {
    private static final String FIELDS = "fields";
    private static final String NAME = "name";
    private static final List<String> LOGIN_PERMISSIONS = Collections.singletonList("public_profile");
    private final Bus bus;
    private CallbackManager callbackManager;
    private boolean callbackRegistered;


    private void registerCallback() {
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        getUserProfile(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        bus.post(new FetchingFbUserDataCancelEvent());
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        bus.post(new FetchingFbUserDataErrorEvent(exception));
                    }
                });
        callbackRegistered = true;
    }

    public FacebookLoginProvider(Bus bus, CallbackManager callbackManager) {
        this.bus = bus;
        this.callbackManager = callbackManager;
        callbackRegistered = false;
    }

    public void logOut() {
        LoginManager.getInstance().logOut();
        bus.post(new LogOutCompleteEvent());
    }

    public void logIn(Activity activity) {
        if (!callbackRegistered) {
            registerCallback();
        }
        LoginManager.getInstance().logInWithReadPermissions(activity, LOGIN_PERMISSIONS);
    }

    public boolean isLoginTokenActive() {
        return (AccessToken.getCurrentAccessToken() != null && !AccessToken.getCurrentAccessToken().isExpired());
    }

    public void restoreState() {
        if (isLoginTokenActive()) {
            getUserProfile(AccessToken.getCurrentAccessToken());
        }
    }

    public class FetchingFbUserDataErrorEvent {
        public final FacebookException facebookException;

        public FetchingFbUserDataErrorEvent(FacebookException exception) {
            facebookException = exception;
        }
    }

    public class LogOutCompleteEvent {
    }

    public class FetchingFbUserDataCancelEvent {
    }

    public class FbUserDataRecoveredEvent {
        public final User user;

        public FbUserDataRecoveredEvent(User user) {
            this.user = user;
        }
    }

    private void getUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            String name = object.getString(NAME);
                            bus.post(new FbUserDataRecoveredEvent(new User(name)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString(FIELDS, NAME);
        request.setParameters(parameters);
        request.executeAsync();
    }
}