package com.pps.globant.fittracker.utils;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import com.pps.globant.fittracker.model.FbUser;

import org.json.JSONException;
import org.json.JSONObject;

public class FacebookLoginProvider {
    private static FbUser fbUser=null;

    public static void registerCallback(){
        LoginManager.getInstance().registerCallback(CallBackManagerProviderForFb.getCallbackManager(),
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        setFbUser(object);
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
                    }
                });
    }

    private static void setFbUser(JSONObject jsonObject) {
        String name=null;
        String email=null;
        try {
            name=jsonObject.getString("name");
            email=jsonObject.getString("email");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (name!=null && email!=null) {
            fbUser = new FbUser(name,email);
        }
    }

    public static FbUser fetchData (){
        return fbUser;
    }

    public static void logOut() {
        LoginManager.getInstance().logOut();
    }
}
