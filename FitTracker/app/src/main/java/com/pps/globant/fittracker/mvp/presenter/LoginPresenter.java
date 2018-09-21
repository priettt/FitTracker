package com.pps.globant.fittracker.mvp.presenter;

import com.pps.globant.fittracker.model.FbUser;
import com.pps.globant.fittracker.mvp.model.LoginModel;
import com.pps.globant.fittracker.mvp.view.LoginView;
import com.pps.globant.fittracker.utils.FacebookLoginProvider;
import com.squareup.otto.Subscribe;

import butterknife.OnClick;

public class LoginPresenter {

    private final LoginModel model;
    private final LoginView view;

    public LoginPresenter(LoginModel model, LoginView view) {
        this.model = model;
        this.view = view;
        view.setLabel("Hi there");
        FacebookLoginProvider.registerCallback();
    }
    @Subscribe
    public void onLoginWithFbButtonPressed(LoginView.LoginWithFbButtonPressedEvent event){
        FbUser fbUser=FacebookLoginProvider.fetchData();
        if (fbUser!=null) {
            view.setLabelFb("name: " + fbUser.getName() + ", email: " + fbUser.getEmail());
        }
        else{
            view.setLabelFb("user nulo");
        }
    }

    public void fbLogOut() {
        FacebookLoginProvider.logOut();
    }
}
