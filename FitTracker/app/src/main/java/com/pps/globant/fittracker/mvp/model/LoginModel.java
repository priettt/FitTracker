package com.pps.globant.fittracker.mvp.model;

import com.facebook.CallbackManager;
import com.pps.globant.fittracker.utils.FacebookLoginProvider;
import com.squareup.otto.Bus;

public class LoginModel {
    private final Bus bus;
    private final CallbackManager callbackManager;

    public LoginModel(CallbackManager callbackManager, Bus bus) {
        this.bus = bus;
        this.callbackManager = callbackManager;
    }

    public void registerFbCallbacks() {
        FacebookLoginProvider.registerCallback(bus, callbackManager);
    }

    public void fbLogOut() {
        FacebookLoginProvider.logOut();
    }
}
