package com.pps.globant.fittracker.mvp.model;

import android.app.Activity;

import com.pps.globant.fittracker.model.User;
import com.pps.globant.fittracker.utils.FacebookLoginProvider;

public class LoginModel {
    private FacebookLoginProvider facebookLoginProvider;
    private User activeUser;

    public LoginModel(FacebookLoginProvider facebookLoginProvider) {
        activeUser = null;
        this.facebookLoginProvider = facebookLoginProvider;
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
        this.activeUser=user;
    }

}
