package com.pps.globant.fittracker.mvp.presenter;

import com.pps.globant.fittracker.mvp.model.WeatherModel;
import com.pps.globant.fittracker.mvp.view.WeatherView;

public class WeatherPresenter {
    private WeatherModel weatherModel;
    private WeatherView weatherView;

    public WeatherPresenter(WeatherModel weatherModel, WeatherView weatherView) {
        this.weatherModel = weatherModel;
        this.weatherView = weatherView;
    }
}
