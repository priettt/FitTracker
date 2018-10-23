package com.pps.globant.fittracker.mvp.presenter;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import com.pps.globant.fittracker.R;
import com.pps.globant.fittracker.mvp.model.StepCounterModel;
import com.pps.globant.fittracker.mvp.view.StepCounterView;
import com.squareup.otto.Subscribe;

public class StepCounterPresenter implements SensorEventListener {

    private final StepCounterView view;
    private final StepCounterModel model;

    public StepCounterPresenter(StepCounterModel model, StepCounterView view) {
        this.model = model;
        this.view = view;
    }

    @Subscribe
    public void onStartCountingStepsPressed(StepCounterView.CountStepsPressedEvent event) {
        if (model.sensorAvailable()) {
            view.startCountingSteps();
            model.registerSensorListener(this);
        } else view.popUp(R.string.sensor_unavailable);
    }

    @Subscribe
    public void onStopCountingStepsPressed(StepCounterView.StopStepsCountPressedEvent event) {
        view.stopCountingSteps();
        unregisterSensor();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        updateSteps(event.values[0]);
    }

    public void updateSteps(float value) {
        model.setSteps(value);
        view.setSteps(model.getSteps());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //not needed
    }

    public void unregisterSensor() {
        if (model.isSensorRegister()) model.unRegisterSensorListener(this);
    }
}
