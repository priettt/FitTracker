package com.pps.globant.fittracker;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;

import com.google.android.gms.location.LocationServices;
import com.pps.globant.fittracker.mvp.model.AvatarsModel;
import com.pps.globant.fittracker.mvp.model.DataBase.UserRoomDataBase;
import com.pps.globant.fittracker.mvp.model.DataBase.UsersRepository;
import com.pps.globant.fittracker.mvp.model.RunTrackerModel;
import com.pps.globant.fittracker.mvp.model.StepCounterModel;
import com.pps.globant.fittracker.mvp.model.WeatherModel;
import com.pps.globant.fittracker.mvp.presenter.AvatarsPresenter;
import com.pps.globant.fittracker.mvp.presenter.FirstAppScreenPresenter;
import com.pps.globant.fittracker.mvp.presenter.RunTrackerPresenter;
import com.pps.globant.fittracker.mvp.presenter.StepCounterPresenter;
import com.pps.globant.fittracker.mvp.presenter.WeatherPresenter;
import com.pps.globant.fittracker.mvp.view.AvatarsView;
import com.pps.globant.fittracker.mvp.view.RunTrackerView;
import com.pps.globant.fittracker.mvp.view.StepCounterView;
import com.pps.globant.fittracker.mvp.view.WeatherView;
import com.pps.globant.fittracker.utils.BusProvider;
import com.pps.globant.fittracker.utils.Constants;
import com.pps.globant.fittracker.utils.ServiceUtils;
import com.squareup.otto.Bus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pps.globant.fittracker.utils.Constants.WEATHER_LOCATION_PERMISSION_REQUEST_CODE;


public class FirstAppScreenActivity extends AppCompatActivity {

    @BindView(R.id.card_run_tracker)
    CardView runTrackerCard;
    @BindView(R.id.card_avatars)
    CardView avatarsCard;
    @BindView(R.id.card_step_counter)
    CardView stepCounterCard;
    @BindView(R.id.card_weather)
    CardView weatherCard;

    FirstAppScreenPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_app_screen);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String userId = intent.getStringExtra(Constants.EXTRA_MESSAGE);
        Bus bus = BusProvider.getInstance();
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        presenter = new FirstAppScreenPresenter(
                new RunTrackerPresenter(
                        new RunTrackerModel(
                                LocationServices.getFusedLocationProviderClient(this),
                                new ArrayList<Location>()),
                        new RunTrackerView(runTrackerCard, this, bus)),
                new AvatarsPresenter(
                        new AvatarsModel(
                                ServiceUtils.getAvatarService(), bus,
                                new UsersRepository(UserRoomDataBase.getDatabase(this).userDao(), bus)),
                        new AvatarsView(avatarsCard, this, bus), userId),
                new StepCounterPresenter(
                        new StepCounterModel(sensorManager, getPackageManager().hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER)),
                        new StepCounterView(stepCounterCard, this, bus)),
                new WeatherPresenter(
                        new WeatherModel(
                                LocationServices.getFusedLocationProviderClient(this), bus),
                        new WeatherView(weatherCard, this, bus))
        );

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

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RunTrackerPresenter.MY_PERMISSIONS_ACCES_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    presenter.AccesLocationPermissionGranted();
                }
            }
            case WEATHER_LOCATION_PERMISSION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    presenter.AccessLocationPermissionGranted(WEATHER_LOCATION_PERMISSION_REQUEST_CODE);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1)
            presenter.weatherCheckSettings();
    }
}