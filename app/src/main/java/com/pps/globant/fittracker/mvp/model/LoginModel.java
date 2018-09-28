package com.pps.globant.fittracker.mvp.model;

import com.squareup.otto.Bus;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import com.facebook.CallbackManager;
import com.pps.globant.fittracker.utils.FacebookLoginProvider;

public class LoginModel {

    private Bus bus;

    private final CallbackManager callbackManager;

    private GoogleSignInAccount account; //Contains all the information of the account.

    public LoginModel(CallbackManager callbackManager, Bus bus) {
        this.bus = bus;
        this.callbackManager = callbackManager;
    }

    public void signInGoogle(Intent data) {
        //Creates the task to SignIn from the intent, and posts GoogleSignInEvent
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        bus.post(new GoogleSignInEvent(task));

    }

    public void signOutGoogle() {
        account = null;
    }

    public static class GoogleSignInEvent{
        @NonNull Task<GoogleSignInAccount> completedTask;
        public GoogleSignInEvent(@NonNull Task<GoogleSignInAccount> completedTask) {
            this.completedTask=completedTask;
        }

        @NonNull
        public Task<GoogleSignInAccount> getCompletedTask() {
            return completedTask;
        }
    }

    public String getMail() {
        return account.getEmail();
    }

    public void setAccount(GoogleSignInAccount account) {
        this.account = account;
    }

    public void registerFbCallbacks() {
        FacebookLoginProvider.registerCallback(bus, callbackManager);
    }

    public void fbLogOut() {
        FacebookLoginProvider.logOut();
    }
}
