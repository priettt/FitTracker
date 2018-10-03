package com.pps.globant.fittracker.mvp.model;

import com.squareup.otto.Bus;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;

import android.app.Activity;

import com.pps.globant.fittracker.model.User;
import com.pps.globant.fittracker.utils.FacebookLoginProvider;

public class LoginModel {

    private Bus bus;
    private FacebookLoginProvider facebookLoginProvider;
    private User activeUser;

    private GoogleSignInAccount account; //Contains all the information of the account.

    public LoginModel(FacebookLoginProvider facebookLoginProvider, Bus bus) {
        this.facebookLoginProvider = facebookLoginProvider;
        this.bus = bus;
    }

    public void signInGoogle(Intent data) {
        //Creates the task to SignIn from the intent, and posts GoogleSignInEvent
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        bus.post(new GoogleSignInEvent(task));

    }

    public void signOutGoogle() {
        account = null;
    }

    public static class GoogleSignInEvent {
        @NonNull
        Task<GoogleSignInAccount> completedTask;

        public GoogleSignInEvent(@NonNull Task<GoogleSignInAccount> completedTask) {
            this.completedTask = completedTask;
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

    public void fbLogOut() {
        facebookLoginProvider.logOut();
    }

    public void fbLogIn(Activity activity) {
        facebookLoginProvider.logIn(activity);
    }

    public boolean isFbLogedIn() {
        return facebookLoginProvider.isLoginTokenActive();
    }

    public User getUser() {
        return activeUser;
    }

    public void restoreState() {
        facebookLoginProvider.restoreState();
    }

    public void setUser(User user) {
        activeUser = user;
    }
}
