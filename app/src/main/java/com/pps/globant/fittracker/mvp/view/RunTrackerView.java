package com.pps.globant.fittracker.mvp.view;

import android.app.Activity;
import android.support.annotation.StringRes;
import android.support.constraint.ConstraintLayout;
import android.support.transition.AutoTransition;
import android.support.transition.TransitionManager;
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

public class RunTrackerView extends CardViewView {

    public static final float CERO = 0f;
    public static final String TWO_DECIMALS_STRING = "%.2g%n";
    @BindView(R.id.layout_cards_container)
    ConstraintLayout layoutCardsContainer;
    @BindView(R.id.layout_start_running)
    ConstraintLayout layoutStartRunning;
    @BindView(R.id.text_total_distance)
    TextView totalDistance;
    @BindView(R.id.text_total_steps)
    TextView totalSteps;
    @BindView(R.id.text_total_calories)
    TextView totalCalories;
    @BindView(R.id.layout_run_in_progress)
    ConstraintLayout layoutRunInProgress;
    @BindView(R.id.button_close_run)
    Button closeRun;
    @BindView(R.id.button_show_on_map)
    Button showOnMap;
    @BindView(R.id.button_stop_running)
    Button stopRunning;

    private final Bus bus;

    public RunTrackerView(CardView card, Activity activity, Bus bus) {
        super(card, activity);
        ButterKnife.bind(this, activity);
        this.bus = bus;
    }

    @OnClick(R.id.button_run)
    public void startRunPressed() {
        bus.post(new StartRunPressedEvent());
    }

    @OnClick(R.id.button_show_on_map)
    public void showMapPressed() {
        bus.post(new ShowMapPressedEvent());
    }

    @OnClick(R.id.button_close_run)
    public void closeRunPressed() {
        bus.post(new CloseRunPressedEvent());
    }

    @OnClick(R.id.button_stop_running)
    public void stopRunPressed() {
        bus.post(new StopRunPressedEvent());
    }

    public void startRun() {
        TransitionManager.beginDelayedTransition(layoutCardsContainer, new AutoTransition());
        layoutStartRunning.setVisibility(View.GONE);
        layoutRunInProgress.setVisibility(View.VISIBLE);
        stopRunning.setVisibility(View.VISIBLE);
        closeRun.setVisibility(View.GONE);
        showOnMap.setVisibility(View.GONE);
        setActualDistance(CERO);
        setActualCalories(CERO);
        setActualSteps(CERO);
    }

    public void popUp(@StringRes int msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void closeRun() {
        TransitionManager.beginDelayedTransition(layoutCardsContainer, new AutoTransition());
        layoutRunInProgress.setVisibility(View.GONE);
        layoutStartRunning.setVisibility(View.VISIBLE);
    }

    public void setActualDistance(float distance) {
        totalDistance.setText(String.valueOf(Math.round(distance)));
    }

    public void setActualSteps(float steps) {
        totalSteps.setText(String.valueOf(Math.round(steps)));
    }

    public void setActualCalories(float calories) {
        totalCalories.setText(String.valueOf(Math.round(calories)));
    }

    public void stopRun() {
        TransitionManager.beginDelayedTransition(layoutCardsContainer, new AutoTransition());
        stopRunning.setVisibility(View.GONE);
        closeRun.setVisibility(View.VISIBLE);
        showOnMap.setVisibility(View.VISIBLE);
    }

    public class StartRunPressedEvent {
    }

    public class StopRunPressedEvent {
    }

    public class CloseRunPressedEvent {
    }

    public class ShowMapPressedEvent {
    }

}
