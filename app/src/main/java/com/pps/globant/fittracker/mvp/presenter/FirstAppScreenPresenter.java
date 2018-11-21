package com.pps.globant.fittracker.mvp.presenter;

import com.pps.globant.fittracker.utils.BusProvider;

import static com.pps.globant.fittracker.utils.Constants.WEATHER_LOCATION_PERMISSION_REQUEST_CODE;

public class FirstAppScreenPresenter {

    private final AvatarsPresenter avatarsPresenter;
    private final RunTrackerPresenter runTrackerPresenter;
    private final StepCounterPresenter stepCounterPresenter;
    private final WeatherPresenter weatherPresenter;

    public FirstAppScreenPresenter(RunTrackerPresenter runTrackerPresenter, AvatarsPresenter avatarsPresenter, StepCounterPresenter stepCounterPresenter, WeatherPresenter weatherPresenter) {
        this.avatarsPresenter = avatarsPresenter;
        this.runTrackerPresenter = runTrackerPresenter;
        this.stepCounterPresenter = stepCounterPresenter;
        this.weatherPresenter = weatherPresenter;
    }

    public void register() {
        BusProvider.register(this, avatarsPresenter, runTrackerPresenter, stepCounterPresenter,weatherPresenter);
    }

    public void unregister() {
        BusProvider.unregister(this, avatarsPresenter, runTrackerPresenter, stepCounterPresenter,weatherPresenter);
        stepCounterPresenter.unregisterSensor();
    }

    public void AccesLocationPermissionGranted() {
        runTrackerPresenter.accesLocationPermisionGranted();
    }

    public void weatherCheckSettings(){
        weatherPresenter.settingsChecked();
    }

    public void AccessLocationPermissionGranted(int requestCode) {
        switch(requestCode){
            case WEATHER_LOCATION_PERMISSION_REQUEST_CODE:{
                weatherPresenter.locationPermissionGranted();
            }
        }
    }
}

