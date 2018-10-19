package com.pps.globant.fittracker.mvp.model;

import android.annotation.SuppressLint;
import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.pps.globant.fittracker.mvp.presenter.RunTrackerPresenter;

import java.util.ArrayList;

public class RunTrackerModel {
    private static final float TOTAL_RUN_ZERO = 0f;
    private static final int FASTEST_INTERVAL = 5000;
    private static final int INTERVAL = 10000;
    private static final float ONE_STEP_LENGTH = 1.1f;
    private static final float STEPS_PER_CALORIE = 20f;
    private FusedLocationProviderClient fusedLocationClient;
    private float totalRun;


    private final ArrayList<Location> locations;


    public RunTrackerModel(FusedLocationProviderClient fusedLocationClient, ArrayList<Location>locations) {
        this.fusedLocationClient = fusedLocationClient;
        this.locations = locations;
    }

    @SuppressLint("MissingPermission")//already checked from the presenter
    public void startRecievingLocationUpdates(RunTrackerPresenter runTrackerPresenter) {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        fusedLocationClient.requestLocationUpdates(mLocationRequest,runTrackerPresenter,null);
    }

    public void reset() {
        totalRun= TOTAL_RUN_ZERO;
    }

    public void incDistance(float distance) {
        totalRun+=distance;
    }

    public void stopRecievingLocationUpdates(RunTrackerPresenter runTrackerPresenter) {
        fusedLocationClient.removeLocationUpdates(runTrackerPresenter);
    }

    public float getDistance() {
        return totalRun;
    }

    public float getSteps() {
        return totalRun/ONE_STEP_LENGTH;
    }

    public float getCalories() {
        return getSteps()/STEPS_PER_CALORIE;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void clearLocations() {
        locations.clear();
    }
}
