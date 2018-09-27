package com.pps.globant.fittracker.mvp.model;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.squareup.otto.Bus;

public class LoginModel {

    private Bus bus;

    private GoogleSignInAccount account; //Contains all the information of the account.
    private static final String GoogleSignInErrorTAG = "LoginPresenter";


    public LoginModel(Bus bus) {
        this.bus = bus;
    }

    public void signInGoogle(Intent data) {
        //Creates the task to SignIn from the intent, and calls private method handleSignInResult
        //to get the results
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        handleSignInResult(task);
    }

    private void handleSignInResult(@NonNull Task<GoogleSignInAccount> completedTask) {
        //Handles the task of login in. If it's successful, posts in the bus a SuccessfulGoogleSignInEvent
        try {
            account = completedTask.getResult(ApiException.class);
            bus.post(new SuccessfulGoogleSignInEvent());
        } catch (ApiException e) {
            bus.post(new UnsuccessfulGoogleSignInEvent());
            Log.w(GoogleSignInErrorTAG, "handleSignInResult:error", e);
        }
    }

    public void signOutGoogle() {
        account = null;
    }


    public static class SuccessfulGoogleSignInEvent {
        //Nothing to do, class made to pass it through the bus
    }

    public static class UnsuccessfulGoogleSignInEvent {
        //Nothing to do, class made to pass it through the bus
    }

    public String getMail() {
        return account.getEmail();
    }

}
