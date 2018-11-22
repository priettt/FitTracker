package com.pps.globant.fittracker.service;

import com.pps.globant.fittracker.model.fitness.Exercise;
import com.pps.globant.fittracker.model.fitness.ExerciseList;
import com.pps.globant.fittracker.mvp.events.GetExerciseSuccessEvent;
import com.squareup.otto.Bus;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExerciseService {
    private Bus bus;

    public ExerciseService(Bus bus) {
        this.bus = bus;
    }

    public void getExerciseList(){
        ExerciseClient client = ExerciseServiceGenerator.createService(ExerciseClient.class);

        Call<ExerciseList> call = client.getData();

        call.enqueue(new Callback<ExerciseList>() {
            @Override
            public void onResponse(Call<ExerciseList> call, Response<ExerciseList> response) {
                //Collections.shuffle(response.body().getExercises());
// add elements to al, including duplicates
                Set<Exercise> hs = new HashSet<>();
                hs.addAll(response.body().getExercises());
                response.body().getExercises().clear();
                response.body().getExercises().addAll(hs);

                bus.post(new GetExerciseSuccessEvent(response.body()));
            }

            @Override
            public void onFailure(Call<ExerciseList> call, Throwable t) {
            //Handle failure
            }
        });
    }
}
