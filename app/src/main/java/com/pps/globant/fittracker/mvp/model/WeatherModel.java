package com.pps.globant.fittracker.mvp.model;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.pps.globant.fittracker.model.weather.Weather;
import com.pps.globant.fittracker.model.weather.WeatherAPIResponse;
import com.pps.globant.fittracker.mvp.presenter.WeatherPresenter;
import com.pps.globant.fittracker.service.WeatherService;
import com.squareup.otto.Bus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.pps.globant.fittracker.utils.Constants.FASTEST_INTERVAL;
import static com.pps.globant.fittracker.utils.Constants.UPDATE_INTERVAL;

public class WeatherModel {

    private LocationRequest mLocationRequest = new LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(UPDATE_INTERVAL)
            .setFastestInterval(FASTEST_INTERVAL);
    private FusedLocationProviderClient fusedLocationClient;
    private SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.US);

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

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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

    public String getIcon() {
        return weather.getWeather().get(0).getIcon();
    }

    public int getTemperature() {
        return Math.round(weather.getMain().getTemp());
    }

    public String getDescription() {
        //Returns the description with the first letter in Uppercase
        String lowerDesc = weather.getWeather().get(0).getDescription();
        return lowerDesc.substring(0, 1).toUpperCase() + lowerDesc.substring(1);
    }

    public String getName() {
        return weather.getName();
    }

    public double getWindSpeed() {
        return weather.getWind().getSpeed() * 3.6; // m/s to km/h
    }

    public String getWindDirection() {
        // The API returns wind direction in degrees, the array helps translating it into text,
        // doing a little trick with modulo and division.
        String directions[] = {"N", "NE", "E", "SE", "S", "SW", "W", "NW", "N"};
        return directions[Math.round(((weather.getWind().getDeg() % 360) / 45))];
    }

    public int getHumidity() {
        return weather.getMain().getHumidity();
    }

    public double getPressure() {
        return weather.getMain().getPressure();
    }

    public int getClouds() {
        return weather.getClouds().getAll();
    }

    public double getRain() {
        return (weather.getRain() != null) ? weather.getRain().get3h() : 0;
    }

    //The API returns sunset and sunrise times in Epoch time, so it needs a conversion with
    // the Date and SimpleDateFormat class. As date works in milliseconds, a "*1000" is needed.

    public String getSunrise() {
        return format.format(new Date(weather.getSys().getSunrise() * 1000L));
    }

    public String getSunset() {
        return format.format(new Date(weather.getSys().getSunset() * 1000L));
    }
}
