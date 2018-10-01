package com.pps.globant.fittracker.mvp.presenter;

import android.content.res.Resources;

import com.pps.globant.fittracker.R;
import com.pps.globant.fittracker.mvp.model.LoginModel;
import com.pps.globant.fittracker.mvp.view.LoginView;
import com.pps.globant.fittracker.utils.FacebookLoginProvider;
import com.squareup.otto.Subscribe;

public class LoginPresenter {


    private final LoginModel model;
    private final LoginView view;

    public LoginPresenter(LoginModel model, LoginView view) {
        this.model = model;
        this.view = view;
    }

    public LoginModel getModel() {
        return model;
    }

    public void restoreState() {
        model.restoreState();
    }

    private void setUser() {
        Resources res = view.getActivity().getResources();
        view.setLabelButtonFb(res.getString(R.string.fb_login_button_msg_Log_Out));
        view.setLabelFb(String.format(res.getString(R.string.loged_in_name), model.getUser().getName()));
    }

    @Subscribe
    public void onUserDataRecoveredEvent(LoginModel.UserDataRecoveredEvent event) {
        setUser();
    }

    @Subscribe
    public void onErrorOnUserDataRecoveryEvent(LoginModel.ErrorOnUserDataRecoveryEvent event) {
        Resources res = view.getActivity().getResources();
        view.setLabelFb(res.getString(R.string.fb_login_error_message));
        view.popUp(event.error);
    }

    @Subscribe
    public void onLogOutCompleteEvent(FacebookLoginProvider.LogOutCompleteEvent event) {
        Resources res = view.getActivity().getResources();
        view.setLabelFb(res.getString(R.string.not_loged_in));
        view.setLabelButtonFb(res.getString(R.string.fb_login_button_msg_Continue_with_fb));
    }

    @Subscribe
    public void onFbButtonPressedEvent(LoginView.FbButtonPressedEvent event) {
        if (!model.isFbLogedIn()) {
            model.fbLogIn(view.getActivity());
        } else {
            model.fbLogOut();
        }
    }

}
