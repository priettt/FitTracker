package com.pps.globant.fittracker.service;

import com.pps.globant.fittracker.BuildConfig;
import com.pps.globant.fittracker.model.weather.WeatherAPIResponse;
import com.pps.globant.fittracker.mvp.events.GetWeatherFailureEvent;
import com.pps.globant.fittracker.mvp.events.GetWeatherSuccessEvent;
import com.squareup.otto.Bus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherService {
    private Bus bus;
    private double lon;
    private double lat;

    public WeatherService(Bus bus, double lon, double lat) {
        this.bus = bus;
        this.lon = lon;
        this.lat = lat;
    }

    public void getWeather() {
        WeatherClient client = WeatherGenerator.createService(WeatherClient.class);

        Call<WeatherAPIResponse> call = client.getWeather(lat, lon, BuildConfig.WEATHER_API_KEY,"metric");

        call.enqueue(new Callback<WeatherAPIResponse>() {
            @Override
            public void onResponse(Call<WeatherAPIResponse> call, Response<WeatherAPIResponse> response) {
                bus.post(new GetWeatherSuccessEvent(response.body()));
            }

            @Override
            public void onFailure(Call<WeatherAPIResponse> call, Throwable t) {
                bus.post(new GetWeatherFailureEvent(t));
            }
        });
    }
}
