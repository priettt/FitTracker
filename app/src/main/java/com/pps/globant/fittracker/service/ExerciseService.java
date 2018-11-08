package com.pps.globant.fittracker.service;

import com.pps.globant.fittracker.model.fitness.ExerciseList;
import com.pps.globant.fittracker.mvp.events.GetExerciseSuccessEvent;
import com.squareup.otto.Bus;

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
                bus.post(new GetExerciseSuccessEvent(response.body()));
            }

            @Override
            public void onFailure(Call<ExerciseList> call, Throwable t) {
            //Handle failure
            }
        });
    }
}
