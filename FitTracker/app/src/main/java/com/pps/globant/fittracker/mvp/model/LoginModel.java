package com.pps.globant.fittracker.mvp.model;

import com.pps.globant.fittracker.utils.FacebookLoginProvider;
import com.squareup.otto.Bus;

public class LoginModel {
    private final Bus bus;

    public LoginModel(Bus bus) {
        this.bus = bus;
    }

    public void registerFbCallbacks(){
    FacebookLoginProvider.registerCallback(bus);
    }

    public void fbLogOut() {
        FacebookLoginProvider.logOut();
    }
}
