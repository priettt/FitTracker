package com.pps.globant.fittracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.pps.globant.fittracker.utils.CONSTANTS;
import com.pps.globant.fittracker.utils.FacebookLoginProvider;

import com.pps.globant.fittracker.mvp.model.LoginModel;
import com.pps.globant.fittracker.mvp.presenter.LoginPresenter;
import com.pps.globant.fittracker.mvp.view.LoginView;
import com.pps.globant.fittracker.utils.BusProvider;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private LoginPresenter presenter;
    private static final int RC_GET_TOKEN = 9002;
    SharedPreferences spUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        spUser = getSharedPreferences(CONSTANTS.SP,MODE_PRIVATE);
        callbackManager = CallbackManager.Factory.create();
        presenter = new LoginPresenter(new LoginModel(new FacebookLoginProvider(BusProvider.getInstance(), callbackManager), BusProvider.getInstance()), new LoginView(this, BusProvider.getInstance()));
        presenter.register();
        presenter.restoreState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.isLoggedInInstagram(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unregister();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Calls a different method depending on which request is registered, Facebook or Google
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GET_TOKEN)
            presenter.setActivityResults(requestCode, resultCode, data);
        else
            callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.btn_insta_login)
    public void onIgLoginClick() {
        presenter.igButtonLoginClick(this);
    }

    @OnClick(R.id.btn_insta_logout)
    public void onIgLogoutClick(){
        presenter.igButtonLogoutClick(this, spUser);
    }


}
