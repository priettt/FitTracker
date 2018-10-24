package com.pps.globant.fittracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.pps.globant.fittracker.mvp.model.DataBase.UserRoomDataBase;
import com.pps.globant.fittracker.mvp.model.DataBase.UsersRepository;
import com.pps.globant.fittracker.mvp.model.SignUpModel;
import com.pps.globant.fittracker.mvp.presenter.SignUpPresenter;
import com.pps.globant.fittracker.mvp.view.SignUpView;
import com.pps.globant.fittracker.utils.BusProvider;
import com.squareup.otto.Bus;

import butterknife.ButterKnife;

import static com.pps.globant.fittracker.utils.Constants.EXTRA_MESSAGE;

public class SignInFormActivity extends FragmentActivity {

    private SignUpPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_form);
        ButterKnife.bind(this);
        Bus bus = BusProvider.getInstance();
        Intent intent = getIntent();
        String userId = intent.getStringExtra(EXTRA_MESSAGE);
        presenter = new SignUpPresenter(new SignUpView(this, bus), new SignUpModel(bus, new UsersRepository
                (UserRoomDataBase.getDatabase(this).userDao(), bus)), userId);

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
