package com.pps.globant.fittracker.mvp.presenter;

import com.pps.globant.fittracker.utils.BusProvider;

public class FirstAppScreenPresenter {

    private final AvatarsPresenter avatarsPresenter;
    private final RunTrackerPresenter runTrackerPresenter;
    private final StepCounterPresenter stepCounterPresenter;

    public FirstAppScreenPresenter(RunTrackerPresenter runTrackerPresenter, AvatarsPresenter avatarsPresenter, StepCounterPresenter stepCounterPresenter) {
        this.avatarsPresenter = avatarsPresenter;
        this.runTrackerPresenter = runTrackerPresenter;
        this.stepCounterPresenter = stepCounterPresenter;
    }

    public void register() {
        BusProvider.register(this, avatarsPresenter, runTrackerPresenter, stepCounterPresenter);
    }

    public void unregister() {
        BusProvider.unregister(this, avatarsPresenter, runTrackerPresenter, stepCounterPresenter);
        stepCounterPresenter.unregisterSensor();
    }

    public void AccesLocationPermissionGranted() {
        runTrackerPresenter.accesLocationPermisionGranted();
    }
}

