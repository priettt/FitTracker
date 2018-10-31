package com.pps.globant.fittracker.mvp.model;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class StepCounterModel {
    public static final boolean FALSE = false;
    public static final boolean TRUE = true;
    private final SensorManager sensorManager;
    private int steps;
    private boolean isSensorRegister;
    private boolean isSensorAvailable;

    public StepCounterModel(SensorManager sensorManager,boolean isSensorAvailable) {
        this.sensorManager = sensorManager;
        isSensorRegister = FALSE;
        this.isSensorAvailable=isSensorAvailable;
    }

    public void registerSensorListener(SensorEventListener listener) {
        final Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (sensor != null) {
            isSensorRegister = TRUE;
            sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void unRegisterSensorListener(SensorEventListener listener) {
        isSensorRegister = FALSE;
        sensorManager.unregisterListener(listener);
    }

    public void setSteps(float value) {
        steps = Math.round(value);
    }

    public int getSteps() {
        return steps;
    }

    public boolean isSensorRegister() {
        return isSensorRegister;
    }

    public boolean sensorAvailable() {
        return isSensorAvailable;
    }
}
