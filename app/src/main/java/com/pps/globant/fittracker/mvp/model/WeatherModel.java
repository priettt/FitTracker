package com.pps.globant.fittracker.mvp.model;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.pps.globant.fittracker.model.weather.Weather;
import com.pps.globant.fittracker.model.weather.WeatherAPIResponse;
import com.pps.globant.fittracker.mvp.presenter.WeatherPresenter;
import com.pps.globant.fittracker.service.WeatherService;
import com.squareup.otto.Bus;

import static com.pps.globant.fittracker.utils.Constants.FASTEST_INTERVAL;
import static com.pps.globant.fittracker.utils.Constants.UPDATE_INTERVAL;

public class WeatherModel {

    private LocationRequest mLocationRequest = new LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(UPDATE_INTERVAL)
            .setFastestInterval(FASTEST_INTERVAL);
    private FusedLocationProviderClient fusedLocationClient;

    private double longitude;
    private double latitude;
    private Bus bus;
    private WeatherAPIResponse weather;

    public WeatherModel(FusedLocationProviderClient fusedLocationClient, Bus bus) {
        this.fusedLocationClient = fusedLocationClient;
        this.bus = bus;
    }

    public boolean checkSelfPermission(Activity activity, String permission) {
        return (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED);
    }

    @SuppressLint("MissingPermission")
    public void startLocationUpdates(WeatherPresenter weatherPresenter) {
        fusedLocationClient.requestLocationUpdates(mLocationRequest, weatherPresenter, null);
    }

    public void stopReceivingLocationUpdates(WeatherPresenter weatherPresenter) {
        fusedLocationClient.removeLocationUpdates(weatherPresenter);
    }

    public LocationRequest getLocationRequest() {
        return mLocationRequest;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void retrieveWeather() {
        WeatherService weatherService = new WeatherService(bus, longitude, latitude);
        weatherService.getWeather();
    }

    public void setWeather(WeatherAPIResponse weather) {
        this.weather = weather;
    }

    public WeatherAPIResponse getWeather() {
        return weather;
    }
}
