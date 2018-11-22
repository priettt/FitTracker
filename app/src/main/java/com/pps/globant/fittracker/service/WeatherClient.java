package com.pps.globant.fittracker.service;

import com.pps.globant.fittracker.model.weather.WeatherAPIResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherClient {

    @GET("/data/2.5/weather")
    Call<WeatherAPIResponse> getWeather(@Query("lat") double lat, @Query("lon") double lon, @Query("appid") String appid, @Query("units") String units);
}
