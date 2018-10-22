package com.pps.globant.fittracker.mvp.view;

import android.app.Activity;
import android.support.annotation.StringRes;
import android.widget.EditText;
import android.widget.Toast;

import com.pps.globant.fittracker.R;
import com.squareup.otto.Bus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginLocallyView extends ActivityView {

    private final Bus bus;

    @BindView(R.id.edit_username_login)
    EditText username;

    @BindView(R.id.edit_password_login)
    EditText password;

    public LoginLocallyView(Activity activity, Bus bus) {
        super(activity);
        this.bus = bus;
        ButterKnife.bind(this, activity);
    }

    public void popUp(@StringRes int msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.button_login_locally)
    public void loginButtonPressed() {
        bus.post(new LoginEvent(username.getText().toString(), password.getText().toString()));
    }

    @OnClick(R.id.link_signup_locally)
    public void signUpLinkPressed() {
        bus.post(new LoginLocallyView.signUpLinkPressedEvent());
    }

    public void cleanErrors() {
        username.setError(null);
        password.setError(null);
    }

    public void setUsernameError(@StringRes int error) {
        username.setError(getContext().getResources().getString(error));
    }

    public void setPasswordError(@StringRes int error) {
        password.setError(getContext().getResources().getString(error));
    }

    public static class signUpLinkPressedEvent {
    }

    public class LoginEvent {
        public String username;
        public String password;

        public LoginEvent(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

}
