package com.pps.globant.fittracker.mvp.presenter;

import com.pps.globant.fittracker.adapters.ExerciseAdapter;
import com.pps.globant.fittracker.mvp.events.GetExerciseSuccessEvent;
import com.pps.globant.fittracker.mvp.model.ExerciseModel;
import com.pps.globant.fittracker.mvp.view.ExerciseView;
import com.pps.globant.fittracker.utils.BusProvider;
import com.squareup.otto.Subscribe;

public class ExercisePresenter {
    private ExerciseModel model;
    private ExerciseView view;

    public ExercisePresenter(ExerciseModel model,ExerciseView view) {
        this.model = model;
        this.view = view;
        init();
    }

    private void init() {
        model.getExerciseList();
    }

    @Subscribe
    public void onExercisesSuccesEvent(GetExerciseSuccessEvent event){
        view.setAdapter(new ExerciseAdapter(event.getExerciseList()));
    }
}
