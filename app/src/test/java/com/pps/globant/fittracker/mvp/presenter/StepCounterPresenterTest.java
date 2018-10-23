package com.pps.globant.fittracker.mvp.presenter;

import android.app.Activity;

import com.pps.globant.fittracker.R;
import com.pps.globant.fittracker.mvp.model.StepCounterModel;
import com.pps.globant.fittracker.mvp.presenter.StepCounterPresenter;
import com.pps.globant.fittracker.mvp.view.StepCounterView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Random;

import static org.mockito.Mockito.verifyNoMoreInteractions;

public class StepCounterPresenterTest {
    private float valor;

    StepCounterPresenter presenter;

    @Mock
    StepCounterModel model;
    @Mock
    StepCounterView view;
    @Mock
    Activity activity;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        valor = generateStepsCount();
        Mockito.when(model.getSteps()).thenReturn(Math.round(valor));
        Mockito.when(view.getActivity()).thenReturn(activity);
        Mockito.when(model.sensorAvailable()).thenReturn(true);
        Mockito.when(model.isSensorRegister()).thenReturn(true);
        Mockito.doNothing().when(view).popUp(R.string.sensor_unavailable);
        presenter = new StepCounterPresenter(model, view);
    }

    @Test
    public void onStartCountingStepsPressed() throws Exception {
        presenter.onStartCountingStepsPressed(new StepCounterView.CountStepsPressedEvent());
        Mockito.verify(model).sensorAvailable();
        Mockito.verify(view).startCountingSteps();
        Mockito.verify(model).registerSensorListener(presenter);
        verifyNoMoreInteractions(view);
        verifyNoMoreInteractions(model);
    }

    @Test
    public void onStartCountingStepsPressedWithoutASensor() throws Exception {
        Mockito.when(model.sensorAvailable()).thenReturn(false);

        presenter.onStartCountingStepsPressed(new StepCounterView.CountStepsPressedEvent());
        Mockito.verify(view).popUp(R.string.sensor_unavailable);
        Mockito.verify(model).sensorAvailable();
        verifyNoMoreInteractions(view);
        verifyNoMoreInteractions(model);
    }

    @Test
    public void onStopCountingStepsPressed() throws Exception {
        presenter.onStopCountingStepsPressed(new StepCounterView.StopStepsCountPressedEvent());
        Mockito.verify(model).isSensorRegister();
        Mockito.verify(model).unRegisterSensorListener(presenter);
        Mockito.verify(view).stopCountingSteps();
        verifyNoMoreInteractions(view);
        verifyNoMoreInteractions(model);
    }

    @Test
    public void updateSteps() throws Exception {
        presenter.updateSteps(valor);
        Mockito.verify(model).setSteps(valor);
        Mockito.verify(view).setSteps(Math.round(valor));
        Mockito.verify(model).getSteps();
        verifyNoMoreInteractions(view);
        verifyNoMoreInteractions(model);
    }

    @Test
    public void unregisterSensor() {
        presenter.unregisterSensor();
        Mockito.verify(model).isSensorRegister();
        Mockito.verify(model).unRegisterSensorListener(presenter);
        verifyNoMoreInteractions(model);
    }

    private float generateStepsCount() {
        float minVal = 1.0f;
        float maxVal = 9999.0f;
        Random rand = new Random();
        return rand.nextFloat() * (maxVal - minVal) + minVal;
    }

}
