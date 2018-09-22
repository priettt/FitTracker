package com.pps.globant.fittracker.mvp.model;

import android.content.Context;

import com.facebook.FacebookSdk;
import com.pps.globant.fittracker.utils.FacebookLoginProvider;
import com.squareup.otto.Bus;

public class LoginModel {
    private final Bus bus;
    public LoginModel(Bus bus, Context applicationContext) {
        this.bus = bus;
        FacebookSdk.sdkInitialize(applicationContext);
        FacebookLoginProvider.registerCallback(applicationContext,bus);
    }

    public void fbLogOut() {
        FacebookLoginProvider.logOut();
    }
}
