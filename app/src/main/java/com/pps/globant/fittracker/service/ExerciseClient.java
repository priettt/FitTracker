package com.pps.globant.fittracker.service;

import retrofit2.Call;
import retrofit2.http.GET;
import com.pps.globant.fittracker.model.fitness.ExerciseList;

public interface ExerciseClient {
    @GET("/api/v2/exerciseimage/")
    Call<ExerciseList> getData();
}
