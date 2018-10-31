package com.pps.globant.fittracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.pps.globant.fittracker.mvp.model.DataBase.UserRoomDataBase;
import com.pps.globant.fittracker.mvp.model.DataBase.UsersRepository;
import com.pps.globant.fittracker.mvp.model.InstagramLoginModel;
import com.pps.globant.fittracker.mvp.model.LoginModel;
import com.pps.globant.fittracker.mvp.presenter.InstagramLoginPresenter;
import com.pps.globant.fittracker.mvp.presenter.LoginPresenter;
import com.pps.globant.fittracker.mvp.view.InstagramLoginView;
import com.pps.globant.fittracker.mvp.view.LoginView;
import com.pps.globant.fittracker.utils.BusProvider;
import com.pps.globant.fittracker.utils.FacebookLoginProvider;
import com.squareup.otto.Bus;

import butterknife.ButterKnife;

import static com.pps.globant.fittracker.utils.Constants.GOOGLE_SERVICE_CLIENT_ID;
import static com.pps.globant.fittracker.utils.Constants.RC_GET_TOKEN;
import static com.pps.globant.fittracker.utils.Constants.SP;


public class MainActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private LoginPresenter presenter;
    private SharedPreferences spUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bus bus = BusProvider.getInstance();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // Configure sign-in to request the user's ID, email address, token and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(GOOGLE_SERVICE_CLIENT_ID)
                .requestEmail()
                .requestProfile()
                .build();

        callbackManager = CallbackManager.Factory.create();
        spUser = getSharedPreferences(SP, MODE_PRIVATE);
        presenter = new LoginPresenter(new LoginModel(new FacebookLoginProvider(bus, callbackManager),
                bus, new UsersRepository(UserRoomDataBase.getDatabase(this).userDao(), bus)),
                new LoginView(this, bus), GoogleSignIn.getClient(this, gso),
                new InstagramLoginPresenter(BusProvider.getInstance(), new InstagramLoginModel(BusProvider.getInstance(),
                        spUser), new InstagramLoginView(this, bus)));

        // next line is for debuggin purpose only, it resets the entire database every time the app start. comment it
        // for a persistence behaviour
        // presenter.clearDatabase();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unregister();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.register();
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

}
