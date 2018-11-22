package com.pps.globant.fittracker.mvp.model;

import android.location.Location;
import android.os.Looper;
import android.text.TextUtils;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.tasks.Task;
import com.pps.globant.fittracker.mvp.presenter.RunTrackerPresenter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;

public class RunTrackerModelTest {

    public static final Looper LOOPER = null;
    public static final float ANY_FLOAT = 5.5f;
    public static final int ZERO = 0;
    public static final float DELTA = 0.1f;
    public static final float DISTANCE = 5f;
    public static final String TOTAL_RUN = "totalRun";
    public static final String ONE_STEP_LENGTH = "ONE_STEP_LENGTH";
    public static final String STEPS_PER_CALORIE = "STEPS_PER_CALORIE";
    @Mock
    private FusedLocationProviderClient fusedLocationClient;
    @Mock
    private ArrayList<Location> locations;
    @Mock
    private RunTrackerPresenter presenter;
    @Mock
    private Task task;

    private RunTrackerModel model;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        model = new RunTrackerModel(fusedLocationClient, locations);
    }

    @Test
    public void startRecievingLocationUpdates() {
        model.startRecievingLocationUpdates(presenter);
        Mockito.verify(fusedLocationClient).requestLocationUpdates(Matchers.<LocationRequest>anyObject(), Matchers.eq(presenter), Matchers.eq(LOOPER));
        Mockito.verifyNoMoreInteractions(fusedLocationClient);
    }

    @Test
    public void reset() {
        Whitebox.setInternalState(model,
                TOTAL_RUN, ANY_FLOAT);
        model.reset();
        final float run = Whitebox.getInternalState(model,
                TOTAL_RUN);
        Assert.assertEquals(run, ZERO, DELTA);
    }

    @Test
    public void incDistance() {
        Whitebox.setInternalState(model,
                TOTAL_RUN, ZERO);
        model.incDistance(DISTANCE);
        final float run = Whitebox.getInternalState(model,
                TOTAL_RUN);
        Assert.assertEquals(run, DISTANCE, DELTA);
    }

    @Test
    public void stopRecievingLocationUpdates() {
        model.stopRecievingLocationUpdates(presenter);
        Mockito.verify(fusedLocationClient).removeLocationUpdates(presenter);
        Mockito.verifyNoMoreInteractions(fusedLocationClient);
    }

    @Test
    public void getDistance() {
        Whitebox.setInternalState(model,
                TOTAL_RUN, DISTANCE);
        final float run = model.getDistance();
        Assert.assertEquals(run, DISTANCE, DELTA);
    }

    @Test
    public void getSteps() {
        Whitebox.setInternalState(model,
                TOTAL_RUN, DISTANCE);
        final float one_step_length = Whitebox.getInternalState(RunTrackerModel.class, ONE_STEP_LENGTH);
        final float steps = model.getSteps();
        Assert.assertEquals(steps, DISTANCE/one_step_length, DELTA);
    }

    @Test
    public void getCalories() {
        Whitebox.setInternalState(model,
                TOTAL_RUN, DISTANCE);
        final float steps_per_calorie = Whitebox.getInternalState(RunTrackerModel.class, STEPS_PER_CALORIE);
        final float one_step_length = Whitebox.getInternalState(RunTrackerModel.class,ONE_STEP_LENGTH);
        final float calories = model.getCalories();
        Assert.assertEquals(calories, (DISTANCE/one_step_length)/steps_per_calorie, DELTA);
    }

    @Test
    public void getLocations() {
        ArrayList<Location> locationsRecovered= model.getLocations();
        Assert.assertArrayEquals(locationsRecovered.toArray(),locations.toArray());
    }

    @Test
    public void clearLocations() {
        model.clearLocations();
        Mockito.verify(locations).clear();
        Mockito.verifyNoMoreInteractions(locations);
    }
}
