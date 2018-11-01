package com.pps.globant.fittracker.mvp.model;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

@RunWith(PowerMockRunner.class)
@PrepareForTest({StepCounterModel.class})

public class StepCounterModelTest {

    public static final int STEPS_NUMBER = 10;
    public static final String STEPS = "steps";
    public static final String IS_SENSOR_REGISTER = "isSensorRegister";
    public static final String IS_SENSOR_AVAILABLE = "isSensorAvailable";
    @Mock
    private SensorManager sensorManager;
    @Mock
    private Sensor mySensor;
    private Sensor mySensorNull;
    @Mock
    SensorEventListener listener;
    private StepCounterModel model;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        model = new StepCounterModel(sensorManager, Boolean.TRUE);
        Mockito.when(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)).thenReturn(mySensor);
    }

    @Test
    public void registerSensorListener() {
        model.registerSensorListener(listener);
        Mockito.verify(sensorManager).getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        Mockito.verify(sensorManager).registerListener(listener, mySensor, SensorManager.SENSOR_DELAY_NORMAL);
        Mockito.verifyNoMoreInteractions(sensorManager);
    }

    @Test
    public void registerSensorListenerWithoutSensor() {
        Mockito.when(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)).thenReturn(mySensorNull);
        model.registerSensorListener(listener);
        Mockito.verify(sensorManager).getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        Mockito.verifyNoMoreInteractions(sensorManager);
        Mockito.when(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)).thenReturn(mySensor);
    }

    @Test
    public void unRegisterSensorListener() {
        model.unRegisterSensorListener(listener);
        Mockito.verify(sensorManager).unregisterListener(listener);
        Mockito.verifyNoMoreInteractions(sensorManager);
    }

    @Test
    public void getSteps() {
        Whitebox.setInternalState(model, STEPS, STEPS_NUMBER);
        Assert.assertEquals(model.getSteps(), STEPS_NUMBER);
    }

    @Test
    public void setSteps() {
        model.setSteps(STEPS_NUMBER);
        Assert.assertEquals(Whitebox.getInternalState(model, STEPS), STEPS_NUMBER);
    }

    @Test
    public void isSensorRegister() {
        Whitebox.setInternalState(model, IS_SENSOR_REGISTER, Boolean.TRUE);
        Assert.assertEquals(model.isSensorRegister(), Boolean.TRUE);
    }

    @Test
    public void sensorAvailable() {

        Whitebox.setInternalState(model, IS_SENSOR_AVAILABLE, Boolean.TRUE);
        Assert.assertEquals(model.sensorAvailable(), Boolean.TRUE);
    }

}
