package com.pps.globant.fittracker.mvp.presenter;

import com.pps.globant.fittracker.mvp.model.LoginModel;
import com.pps.globant.fittracker.mvp.view.LoginView;

public class LoginPresenter {

    private final LoginModel model;
    private final LoginView view;

    public LoginPresenter(LoginModel model, LoginView view) {
        this.model = model;
        this.view = view;
        view.setLabel("Hi there");
    }

}
