package com.pps.globant.fittracker.mvp.presenter;

import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;

import com.pps.globant.fittracker.R;
import com.pps.globant.fittracker.mvp.model.LoginModel;
import com.pps.globant.fittracker.mvp.view.LoginView;
import com.pps.globant.fittracker.utils.CallBackManagerProviderForFb;
import com.pps.globant.fittracker.utils.FacebookLoginProvider;
import com.squareup.otto.Subscribe;

import java.util.Objects;

public class LoginPresenter {

    private final LoginModel model;
    private final LoginView view;

    public LoginPresenter(LoginModel model, LoginView view) {
        this.model = model;
        this.view = view;
    }

    @Subscribe
    public void onFetchingFbUserDataCompletedEvent(FacebookLoginProvider.FetchingFbUserDataCompletedEvent event) {
        Resources res= Objects.requireNonNull(view.getActivity()).getResources();
        view.setLabelFb(String.format(res.getString(R.string.loged_in_name),event.fbUser.getName()));
    }

    @Subscribe
    public void onCantRetrieveAllTheFieldsRequestedsEvent(FacebookLoginProvider.CantRetrieveAllTheFieldsRequestedsEvent event) {
        Resources res= Objects.requireNonNull(view.getActivity()).getResources();
        view.setLabelFb(res.getString(R.string.fb_login_error_message));
        Log.e(res.getString(R.string.facebook),res.getString(R.string.fb_login_error_logPrintErrorCantRetrieveAllTheFields));
    }

    @Subscribe
    public void onFetchingFbUserDataErrorEvent(FacebookLoginProvider.FetchingFbUserDataErrorEvent event) {
        Resources res= Objects.requireNonNull(view.getActivity()).getResources();
        view.setLabelFb(res.getString(R.string.fb_login_error_message));
        Log.e(res.getString(R.string.facebook),String.format(res.getString(R.string.fb_login_error_logPrintErrorCantFetchData),event.facebookException.toString()));
    }

    @Subscribe
    public void onLogOutCompleteEvent(FacebookLoginProvider.LogOutCompleteEvent event) {
        Resources res= Objects.requireNonNull(view.getActivity()).getResources();
        view.setLabelFb(res.getString(R.string.not_loged_in));
    }

    @Subscribe
    public void onFbButtonPressedEvent(LoginView.FbButtonPressedEvent event) {
        model.registerFbCallbacks();
    }

    public void fbLogOut() {
        model.fbLogOut();
    }

    public void redirectActivityResults(int requestCode, int resultCode, Intent data) {
        CallBackManagerProviderForFb.getCallbackManager().onActivityResult(requestCode, resultCode, data);
    }
}
