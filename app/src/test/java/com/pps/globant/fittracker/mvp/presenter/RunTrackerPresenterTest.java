package com.pps.globant.fittracker.mvp.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.location.LocationResult;
import com.pps.globant.fittracker.MapsActivity;
import com.pps.globant.fittracker.mvp.model.RunTrackerModel;
import com.pps.globant.fittracker.mvp.view.RunTrackerView;
import com.pps.globant.fittracker.utils.Constants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ContextCompat.class, LocationResult.class, ActivityCompat.class, RunTrackerPresenter.class})
public class RunTrackerPresenterTest {
    private static final Integer ENOUGH_POINTS = 5;
    private static final Integer NOT_ENOUGH_POINTS = 2;
    private static final Float NOT_ENOUGH_ACCURACY = 50f;
    private static final Float ENOUGH_ACCURACY = 5f;
    private static final Float DISTANCE = 1f;
    private static final Float CALORIES = 2f;
    private static final Float STEPS = 3f;
    private static ArrayList<Location> EMPTY_LIST = new ArrayList<>();
    @Mock
    private RunTrackerModel model;
    @Mock
    private RunTrackerView view;
    @Mock
    private RunTrackerView.StartRunPressedEvent startRunPressedEvent;
    @Mock
    private Activity activity;
    @Mock
    private RunTrackerView.ShowMapPressedEvent showMapPressedEvent;
    @Mock
    private ArrayList<Location> locations;
    @Mock
    private RunTrackerView.CloseRunPressedEvent closeRunPressedEvent;
    @Mock
    private RunTrackerView.StopRunPressedEvent stopRunPressedEvent;
    @Mock
    private Location location;
    @Mock
    private Location location2;
    @Mock
    private Location location3;

    private RunTrackerPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new RunTrackerPresenter(model, view);
        Mockito.when(view.getActivity()).thenReturn(activity);
    }

    @Test
    public void onStartRunPressedEventWithPermissions() {
        PowerMockito.mockStatic(ContextCompat.class);
        PowerMockito.when(ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)).thenReturn(PackageManager.PERMISSION_GRANTED);
        presenter.onStartRunPressedEvent(startRunPressedEvent);
        Mockito.verify(view).getActivity();
        PowerMockito.verifyStatic();
        ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        startRun();
    }

    @Test
    public void onStartRunPressedEventWithoutPermissions() {
        PowerMockito.mockStatic(ContextCompat.class);
        PowerMockito.mockStatic(ActivityCompat.class);
        PowerMockito.when(ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)).thenReturn(PackageManager.PERMISSION_DENIED);
        presenter.onStartRunPressedEvent(startRunPressedEvent);
        Mockito.verify(view).getActivity();
        PowerMockito.verifyStatic();
        ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        PowerMockito.verifyStatic();
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                RunTrackerPresenter.MY_PERMISSIONS_ACCES_FINE_LOCATION);
        Mockito.verifyNoMoreInteractions(view);
    }


    @Test
    public void onStopRunPressedEvent() {
        presenter.onStopRunPressedEvent(stopRunPressedEvent);
        Mockito.verify(model).stopRecievingLocationUpdates(presenter);
        Mockito.verify(view).stopRun();
    }

    @Test
    public void onShowMapPressedEventWithEnoughPoints() throws Exception {
        final Intent intent = PowerMockito.mock(Intent.class);
        PowerMockito.whenNew(Intent.class).withArguments(activity, MapsActivity.class).thenReturn(intent);
        Mockito.when(model.getLocations()).thenReturn(locations);
        Mockito.when(locations.size()).thenReturn(ENOUGH_POINTS);
        presenter.onShowMapPressedEvent(showMapPressedEvent);
        Mockito.verify(view).getActivity();
        Mockito.verify(model, Mockito.times(2)).getLocations();
        Mockito.verify(locations).size();
        Mockito.verify(intent).putParcelableArrayListExtra(Constants.PATH_VECTOR, locations);
        Mockito.verify(activity).startActivity(intent);
        Mockito.verifyNoMoreInteractions(activity);
        Mockito.verifyNoMoreInteractions(model);
        Mockito.verifyNoMoreInteractions(intent);
        Mockito.verifyNoMoreInteractions(view);
    }


    @Test
    public void onCloseRunPressedEvent() {
        presenter.onCloseRunPressedEvent(closeRunPressedEvent);
        Mockito.verify(model).clearLocations();
        Mockito.verify(view).closeRun();
        Mockito.verifyNoMoreInteractions(view);
        Mockito.verifyNoMoreInteractions(model);
    }

    @Test
    public void accesLocationPermisionGranted() {
        presenter.accesLocationPermisionGranted();
        startRun();
    }

    @Test
    public void onLocationResultNull() {
        LocationResult locationResult = null;
        presenter.onLocationResult(locationResult);
        Mockito.verifyNoMoreInteractions(model);
        Mockito.verifyNoMoreInteractions(view);
    }

    public void startRun() {
        Mockito.verify(view).startRun();
        Mockito.verify(model).reset();
        Mockito.verify(model).startRecievingLocationUpdates(presenter);
    }
}
