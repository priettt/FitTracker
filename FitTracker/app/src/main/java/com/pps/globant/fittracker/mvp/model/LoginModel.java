package com.pps.globant.fittracker.mvp.model;

import android.app.Activity;

import com.facebook.CallbackManager;
import com.pps.globant.fittracker.model.User;
import com.pps.globant.fittracker.utils.FacebookLoginProvider;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class LoginModel {
    private final Bus bus;
    private FacebookLoginProvider facebookLoginProvider;
    private User activeUser;

    public LoginModel(CallbackManager callbackManager, Bus bus) {
        this.bus = bus;
        activeUser = null;
        facebookLoginProvider = new FacebookLoginProvider(bus, callbackManager);
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

    @Subscribe
    public void onFbUserDataRecoveredEvent(FacebookLoginProvider.FbUserDataRecoveredEvent event) {
        this.activeUser = event.user;
        bus.post(new UserDataRecoveredEvent());//first time checking this on the construction the presenter is still unregister, so cheked it again after
    }

    @Subscribe
    public void onFetchingFbUserDataCancelEvent(FacebookLoginProvider.FetchingFbUserDataCancelEvent event) {
        bus.post(new ErrorOnUserDataRecoveryEvent("Canceled"));
    }


    @Subscribe
    public void onFetchingFbUserDataErrorEvent(FacebookLoginProvider.FetchingFbUserDataErrorEvent event) {
        bus.post(new ErrorOnUserDataRecoveryEvent(event.facebookException.toString()));
    }

    public void restoreState() {
        facebookLoginProvider.restoreState();
    }

    public static class UserDataRecoveredEvent {
    }

    public static class ErrorOnUserDataRecoveryEvent {
        public final String error;

        public ErrorOnUserDataRecoveryEvent(String error) {
            this.error = error;
        }
    }

}
