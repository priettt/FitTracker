package com.pps.globant.fittracker.mvp.events;


import com.pps.globant.fittracker.model.weather.WeatherAPIResponse;

public class GetWeatherSuccessEvent {
    private WeatherAPIResponse weatherAPIResponse;

    public GetWeatherSuccessEvent(WeatherAPIResponse weatherAPIResponse) {
        this.weatherAPIResponse = weatherAPIResponse;
    }

    public WeatherAPIResponse getWeather() {
        return weatherAPIResponse;
    }
}
