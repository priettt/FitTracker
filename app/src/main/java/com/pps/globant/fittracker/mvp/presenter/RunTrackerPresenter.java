package com.pps.globant.fittracker.mvp.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.pps.globant.fittracker.MapsActivity;
import com.pps.globant.fittracker.R;
import com.pps.globant.fittracker.mvp.model.RunTrackerModel;
import com.pps.globant.fittracker.mvp.view.RunTrackerView;
import com.pps.globant.fittracker.utils.Constants;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;


public class RunTrackerPresenter extends LocationCallback {
    public static final int MY_PERMISSIONS_ACCES_FINE_LOCATION = 99;
    public static final int ONE = 1;
    private static final float MIN_ACCURACY = 10;
    private static final int MIN_POINTS = 3;
    private final RunTrackerModel model;
    private final RunTrackerView view;

    public RunTrackerPresenter(RunTrackerModel model, RunTrackerView view) {
        this.model = model;
        this.view = view;
    }

    @Subscribe
    public void onStartRunPressedEvent(RunTrackerView.StartRunPressedEvent event) {
        final Activity activity = view.getActivity();
        if (activity == null) return;
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_ACCES_FINE_LOCATION);
        } else {
            startRun();
        }
    }

    @Subscribe
    public void onStopRunPressedEvent(RunTrackerView.StopRunPressedEvent event) {
        model.stopRecievingLocationUpdates(this);
        view.stopRun();
    }

    @Subscribe
    public void onShowMapPressedEvent(RunTrackerView.ShowMapPressedEvent event) {
        final Activity activity = view.getActivity();
        if (activity == null) {
            return;
        }
        if (model.getLocations().size()<MIN_POINTS) {
            view.popUp(R.string.no_point_registered_yet);
            return;
        }
        Intent intent = new Intent(activity, MapsActivity.class);
        intent.putParcelableArrayListExtra(Constants.PATH_VECTOR, model.getLocations());
        activity.startActivity(intent);
    }

    @Subscribe
    public void onCloseRunPressedEvent(RunTrackerView.CloseRunPressedEvent event) {
        model.clearLocations();
        view.closeRun();
    }

    private void startRun() {
        view.startRun();
        model.reset();
        model.startRecievingLocationUpdates(this);
    }

    public void accesLocationPermisionGranted() {
        startRun();
    }

    @Override
    public void onLocationResult(LocationResult locationResult) {
        if (locationResult == null) {
            return;
        }
        for (Location location : locationResult.getLocations()) {
            if (location.getAccuracy() < MIN_ACCURACY) {
                ArrayList<Location> locations = model.getLocations();
                if (!locations.isEmpty())
                    model.incDistance(locations.get(locations.size() - ONE).distanceTo(location));
                locations.add(location);
            }
        }
        view.setActualDistance(model.getDistance());
        view.setActualSteps(model.getSteps());
        view.setActualCalories(model.getCalories());
    }
}

