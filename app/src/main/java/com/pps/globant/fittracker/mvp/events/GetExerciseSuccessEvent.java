package com.pps.globant.fittracker.mvp.events;

import com.pps.globant.fittracker.model.fitness.Exercise;
import com.pps.globant.fittracker.model.fitness.ExerciseList;

import java.util.ArrayList;
import java.util.List;

public class GetExerciseSuccessEvent {
    ExerciseList exerciseList;

    public GetExerciseSuccessEvent(ExerciseList exerciseList) {
        this.exerciseList = exerciseList;
    }

    public List<Exercise> getExerciseList(){
        return new ArrayList<>(exerciseList.getExercises());
    }
}
