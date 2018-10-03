package com.pps.globant.fittracker.mvp.presenter;

import android.content.res.Resources;

import com.pps.globant.fittracker.R;
import com.pps.globant.fittracker.mvp.model.DataBase.UsersRepository;
import com.pps.globant.fittracker.mvp.model.LoginModel;
import com.pps.globant.fittracker.mvp.view.LoginView;
import com.pps.globant.fittracker.utils.BusProvider;
import com.pps.globant.fittracker.utils.FacebookLoginProvider;
import com.squareup.otto.Subscribe;

public class LoginPresenter {


    private final LoginModel model;
    private final LoginView view;

    public LoginPresenter(LoginModel model, LoginView view) {
        this.model = model;
        this.view = view;
    }

    public void restoreState() {
        model.restoreState();
    }

    private void setUser() {
        Resources res = view.getActivity().getResources();
        view.setLabelButtonFb(R.string.fb_login_button_msg_Log_Out);
        view.setLabelFb(String.format(res.getString(R.string.loged_in_name), model.getUser().getName()));
        view.setDbOptionVisible();
    }

    public void setAndPopUpError(int error) {
        view.setLabelFb(R.string.fb_login_error_message);
        view.popUp(error);
    }

    public void register() {
        BusProvider.register(this, this.model);
    }

    public void unregister() {
        BusProvider.unregister(this, this.model);
    }

    @Subscribe
    public void onLogOutCompleteEvent(FacebookLoginProvider.LogOutCompleteEvent event) {
        Resources res = view.getActivity().getResources();
        view.setLabelFb(R.string.not_loged_in);
        view.setLabelButtonFb(R.string.fb_login_button_msg_Continue_with_fb);
        view.setDbOptionInvisible();
    }
    @Subscribe
    public void onFbButtonPressedEvent(LoginView.FbButtonPressedEvent event) {
        if (!model.isFbLogedIn()) {
            model.fbLogIn(view.getActivity());
        } else {
            model.fbLogOut();
        }
    }


    @Subscribe
    public void onDbButtonPressedEvent(LoginView.DbButtonPressedEvent event) {
        model.getUserFromDB();
    }

    @Subscribe
    public void onDbDeleteButtonPressedEvent(LoginView.DbButtonDeletePressedEvent event) {
        model.deleteUser();
    }


    //DB'S EVENTS

    @Subscribe
    public void onFetchingUserFromDataBaseCompleted(UsersRepository.FetchingUserFromDataBaseCompleted event) {
        if (event.user == null) {
            view.popUp(R.string.db_text_msg_adding_user);
            model.insertUserToDB();
        } else {
            view.popUp(R.string.db_text_msg_user_exists);
        }
        view.setDbDeleteOptionVisible();
    }

    @Subscribe
    public void onDeletingUserFromDataBaseCompleted(UsersRepository.DeletingUserFromDataBaseCompleted event) {
        view.popUp(R.string.db_text_msg_user_deleted);
        view.setDbDeleteOptionInvisible();
    }


    //FACEBOOK´S EVENTS
    @Subscribe
    public void onFbUserDataRecoveredEvent(FacebookLoginProvider.FbUserDataRecoveredEvent event) {
        model.setUser(event.user);
        setUser();
    }

    @Subscribe
    public void onFetchingFbUserDataCancelEvent(FacebookLoginProvider.FetchingFbUserDataCancelEvent event) {
        setAndPopUpError(R.string.fb_login_cancel_message);
    }

    @Subscribe
    public void onFetchingFbUserDataErrorEvent(FacebookLoginProvider.FetchingFbUserDataErrorEvent event) {
        setAndPopUpError(R.string.fb_login_error_message);
    }


}
