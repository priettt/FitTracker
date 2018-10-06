package com.pps.globant.fittracker.service;

import com.pps.globant.fittracker.model.avatars.jsonAvatarClasses.AvatarsData;
import com.pps.globant.fittracker.mvp.events.GetAvatarsSuccessEvent;
import com.squareup.otto.Bus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AvatarService {
    private Bus bus;

    public AvatarService(Bus bus) {
        this.bus = bus;
    }

    public void getAvatarsList() {
        AvatarsClient client = ServiceGenerator.createService(AvatarsClient.class);

        Call<AvatarsData> call = client.avatars();

        call.enqueue(new Callback<AvatarsData>() {
            @Override
            public void onResponse(Call<AvatarsData> call, Response<AvatarsData> response) {
                bus.post(new GetAvatarsSuccessEvent(response.body()));
            }

            @Override
            public void onFailure(Call<AvatarsData> call, Throwable t) {
                //Handle failure
            }
        });
    }


}
