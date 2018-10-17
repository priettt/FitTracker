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
import com.pps.globant.fittracker.mvp.model.DataBase.User;
import com.squareup.otto.Bus;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class FacebookLoginProvider {
    private static final String FIELDS = "fields";

    private static final String ID = "id";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String EMAIL = "email";
    private static final String BIRTHDAY = "birthday";
    private static final String REQUESTED_FIELDS = String.format("%1$s,%2$s,%3$s,%4$s,%5$s", ID, FIRST_NAME, LAST_NAME, EMAIL, BIRTHDAY);
    private static final String FB_ID_PREFIX = "FB";
    private static final List<String> LOGIN_PERMISSIONS = Collections.unmodifiableList(new ArrayList<String>() {{
        add("public_profile");
        add("email");
        add("user_birthday");
    }});
    private static final String DATE_PATTERN = "mm/dd/yyyy";

    private final Bus bus;
    private CallbackManager callbackManager;
    private boolean callbackRegistered;

    public FacebookLoginProvider(Bus bus, CallbackManager callbackManager) {
        this.bus = bus;
        this.callbackManager = callbackManager;
        callbackRegistered = false;
    }

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
        final AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return (accessToken != null && !accessToken.isExpired());
    }

    public void restoreState() {
        if (isLoginTokenActive()) {
            getUserProfile(AccessToken.getCurrentAccessToken());
        }
    }

    private void getUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            String firstName = object.getString(FIRST_NAME);
                            String lastName = object.getString(LAST_NAME);
                            String socialNetworkId = String.format("%1$s-%2$s", FB_ID_PREFIX, object.getString(ID));
                            String email = object.getString(EMAIL);
                            Date birthday;
                            try {
                                birthday = new SimpleDateFormat(DATE_PATTERN).parse(object.getString(BIRTHDAY));
                            } catch (ParseException e) {
                                //according to the fb graph api, if the returned string is different from that format, the user had configured his account to doesn't say some part of his birthday
                                birthday = null;
                            }
                            bus.post(new FbUserDataRecoveredEvent(User.getUser(firstName, lastName, email, birthday, socialNetworkId)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString(FIELDS, REQUESTED_FIELDS);
        request.setParameters(parameters);
        request.executeAsync();
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
}