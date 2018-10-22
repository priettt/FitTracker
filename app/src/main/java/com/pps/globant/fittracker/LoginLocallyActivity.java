package com.pps.globant.fittracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.pps.globant.fittracker.mvp.model.DataBase.UserRoomDataBase;
import com.pps.globant.fittracker.mvp.model.DataBase.UsersRepository;
import com.pps.globant.fittracker.mvp.model.LoginLocallyModel;
import com.pps.globant.fittracker.mvp.presenter.LoginLocallyPresenter;
import com.pps.globant.fittracker.mvp.view.LoginLocallyView;
import com.pps.globant.fittracker.utils.BusProvider;
import com.squareup.otto.Bus;

public class LoginLocallyActivity extends AppCompatActivity {

    private LoginLocallyPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggin_locally);
        Bus bus = BusProvider.getInstance();
        presenter = new LoginLocallyPresenter(new LoginLocallyView(this, bus), new LoginLocallyModel(bus,
                new UsersRepository(UserRoomDataBase.getDatabase(this).userDao(), bus)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unregister();
    }
}
