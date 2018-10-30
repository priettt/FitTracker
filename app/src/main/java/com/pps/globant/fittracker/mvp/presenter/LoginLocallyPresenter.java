package com.pps.globant.fittracker.mvp.presenter;

import android.app.Activity;
import android.content.Intent;

import com.pps.globant.fittracker.FirstAppScreenActivity;
import com.pps.globant.fittracker.R;
import com.pps.globant.fittracker.SignInFormActivity;
import com.pps.globant.fittracker.mvp.model.DataBase.UsersRepository;
import com.pps.globant.fittracker.mvp.model.LoginLocallyModel;
import com.pps.globant.fittracker.mvp.view.LoginLocallyView;
import com.pps.globant.fittracker.utils.BusProvider;
import com.squareup.otto.Subscribe;

public class LoginLocallyPresenter {
    public static final String EXTRA_MESSAGE = "com.pps.globant.fittracker.USERID";
    private LoginLocallyView view;
    private LoginLocallyModel model;

    public LoginLocallyPresenter(LoginLocallyView view, LoginLocallyModel model) {
        this.view = view;
        this.model = model;
    }

    public void register() {
        BusProvider.register(this);
    }

    public void unregister() {
        BusProvider.unregister(this);
    }

    @Subscribe
    public void onLoginEvent(LoginLocallyView.LoginEvent event) {
        if (!validate(event)) {
            view.popUp(R.string.login_failed);
            return;
        }
        model.getUserFromDbByUsernameAndPassword(event.username, event.password);
    }

    private boolean validate(LoginLocallyView.LoginEvent event) {
        boolean valid = true;
        view.cleanErrors();
        if (!model.validateUserName(event.username)) {
            view.setUsernameError(R.string.username_error);
            valid = false;
        }
        if (!model.validatePassword(event.password)) {
            view.setPasswordError(R.string.password_error);
            valid = false;
        }
        return valid;
    }

    @Subscribe
    public void onFetchingUserFromDataBaseCompleted(UsersRepository.FetchingUserFromDataBaseCompleted event) {
        if (event.user == null || !event.user.isRegisterComplete()) {
            view.popUp(R.string.username_and_password_error);
        } else {
            Intent intent = new Intent(view.getActivity(), FirstAppScreenActivity.class);
            long userId = event.user.getId();
            intent.putExtra(EXTRA_MESSAGE, String.valueOf(userId));
            Activity activity = view.getActivity();
            if (activity == null) return;
            activity.startActivity(intent);
            activity.finish();
        }
    }

    @Subscribe
    public void onSignUpLinkPressedEvent(LoginLocallyView.signUpLinkPressedEvent event) {
        Activity activity = view.getActivity();
        if (activity == null) return;
        Intent intent = new Intent(activity, SignInFormActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

}
