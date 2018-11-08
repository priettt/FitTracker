package com.pps.globant.fittracker.mvp.model;

import com.pps.globant.fittracker.service.ExerciseService;
import com.squareup.otto.Bus;

public class ExerciseModel {
    private ExerciseService service;
    private Bus bus;

    public ExerciseModel(ExerciseService service, Bus bus) {
        this.service = service;
        this.bus = bus;
    }
    public void getExerciseList(){service.getExerciseList();}
}
