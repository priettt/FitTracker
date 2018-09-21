package com.pps.globant.fittracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.pps.globant.fittracker.mvp.model.LoginModel;
import com.pps.globant.fittracker.mvp.presenter.LoginPresenter;
import com.pps.globant.fittracker.mvp.view.LoginView;
import com.pps.globant.fittracker.utils.BusProvider;
import com.pps.globant.fittracker.utils.CallBackManagerProviderForFb;

import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//todo move to oncreate from presenter maybe in another class delegated for this purpose
/*        @Override
        public void onCreate() {
            super.onCreate();
            FacebookSdk.sdkInitialize(getApplicationContext());
            AppEventsLogger.activateApp(this);
        }*/
//todo move
        FacebookSdk.sdkInitialize(getApplicationContext());
        //todo try to delete the next line and see whats happening
        AppEventsLogger.activateApp(this);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter = new LoginPresenter(new LoginModel(), new LoginView(this, BusProvider.getInstance()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.register(presenter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.unregister(presenter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        CallBackManagerProviderForFb.getCallbackManager().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.fbLogOut();
    }

}
