package com.pps.globant.fittracker.mvp.presenter;

import com.pps.globant.fittracker.utils.BusProvider;

public class FirstAppScreenPresenter {

    private final AvatarsPresenter avatarsPresenter;
    private final RunTrackerPresenter runTrackerPresenter;

    public FirstAppScreenPresenter(RunTrackerPresenter runTrackerPresenter, AvatarsPresenter avatarsPresenter) {
        this.avatarsPresenter = avatarsPresenter;
        this.runTrackerPresenter = runTrackerPresenter;
    }

    public void register() {
        BusProvider.register(this, avatarsPresenter, runTrackerPresenter);
    }

    public void unregister() {
        BusProvider.unregister(this, avatarsPresenter, runTrackerPresenter);
    }

    public void AccesLocationPermissionGranted() {
        runTrackerPresenter.accesLocationPermisionGranted();
    }
}
