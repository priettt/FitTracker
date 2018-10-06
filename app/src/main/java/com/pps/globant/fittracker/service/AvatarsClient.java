package com.pps.globant.fittracker.service;

import com.pps.globant.fittracker.model.avatars.jsonAvatarClasses.AvatarsData;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AvatarsClient {

    @GET("/v1/public/characters")
    Call<AvatarsData> avatars ();

}

