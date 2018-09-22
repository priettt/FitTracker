package com.pps.globant.fittracker.mvp.presenter;

import android.util.Log;
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
    @Subscribe
    public void onFetchingFbUserDataCompletedEvent(FacebookLoginProvider.FetchingFbUserDataCompletedEvent event){
        view.setLabelFb("name: "+event.fbUser.getName());
    }

    @Subscribe
    public void onCantRetrieveAllTheFieldsRequestedsEvent(FacebookLoginProvider.CantRetrieveAllTheFieldsRequestedsEvent event){
        view.setLabelFb("error");
        Log.e("Facebook", "error al obtener los campos nombre y email del objeto JSON recuperado");
    }

    @Subscribe
    public void onFetchingFbUserDataErrorEvent(FacebookLoginProvider.FetchingFbUserDataErrorEvent event){
        view.setLabelFb("error");
        Log.e("Facebook", "error al conectar a facebook. Excepcion :"+ event.facebookException.toString());
    }

    @Subscribe
    public void onLogOutCompleteEvent(FacebookLoginProvider.LogOutCompleteEvent event){
        view.setLabelFb("not logged in");
    }

    public void fbLogOut() {
        model.fbLogOut();
    }
}
