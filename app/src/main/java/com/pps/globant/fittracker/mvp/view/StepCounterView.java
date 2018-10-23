package com.pps.globant.fittracker.mvp.view;

import android.app.Activity;
import android.support.annotation.StringRes;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pps.globant.fittracker.R;
import com.squareup.otto.Bus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StepCounterView extends CardViewView {

    private final Bus bus;

    @BindView(R.id.layout_count_steps)
    ConstraintLayout layoutCountSteps;
    @BindView(R.id.layout_counting_steps)
    ConstraintLayout layoutCountingSteps;
    @BindView(R.id.text_current_steps)
    TextView currentSteps;

    public StepCounterView(CardView card, Activity activity, Bus bus) {
        super(card, activity);
        ButterKnife.bind(this, activity);
        this.bus = bus;
    }

    @OnClick(R.id.button_count_steps)
    public void onCountStepsPressed(){
        bus.post(new CountStepsPressedEvent());
    }

    @OnClick(R.id.button_stop_steps_count)
    public void onStopStepsCountPressed(){
        bus.post(new StopStepsCountPressedEvent());
    }

    public void stopCountingSteps(){
        layoutCountingSteps.setVisibility(View.GONE);
        layoutCountSteps.setVisibility(View.VISIBLE);
    }
    public void startCountingSteps(){
        layoutCountSteps.setVisibility(View.GONE);
        layoutCountingSteps.setVisibility(View.VISIBLE);
    }

    public void setSteps(int steps) {
        currentSteps.setText(String.valueOf(steps));
    }

    public void popUp(@StringRes int mensaje) {
        Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();
    }


    public static class CountStepsPressedEvent {
    }

    public static class StopStepsCountPressedEvent {
    }
}
