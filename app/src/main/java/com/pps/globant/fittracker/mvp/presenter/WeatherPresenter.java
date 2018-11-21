package com.pps.globant.fittracker.mvp.presenter;

import android.app.Activity;
import android.content.IntentSender;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.pps.globant.fittracker.R;
import com.pps.globant.fittracker.mvp.events.GetWeatherFailureEvent;
import com.pps.globant.fittracker.mvp.events.GetWeatherSuccessEvent;
import com.pps.globant.fittracker.mvp.model.WeatherModel;
import com.pps.globant.fittracker.mvp.view.WeatherView;
import com.google.android.gms.tasks.Task;
import com.squareup.otto.Subscribe;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.pps.globant.fittracker.utils.Constants.REQUEST_CHECK_SETTINGS;
import static com.pps.globant.fittracker.utils.Constants.WEATHER_LOCATION_PERMISSION_REQUEST_CODE;

public class WeatherPresenter extends LocationCallback {

    private WeatherModel weatherModel;
    private WeatherView weatherView;
    private boolean locationPermissions;
    private boolean locationSettings;
    private boolean extended = false;

    public WeatherPresenter(WeatherModel weatherModel, WeatherView weatherView) {
        this.weatherModel = weatherModel;
        this.weatherView = weatherView;
    }

    @Subscribe
    public void onWeatherPermissionsButtonPressed(WeatherView.WeatherPermissionButtonPressedEvent event) {
        weatherView.toggleSpinner();
        retrieveLocation();
    }

    @Subscribe
    public void onWeatherRefreshButtonPressed(WeatherView.WeatherRefreshButtonPressedEvent event) {
        weatherModel.retrieveWeather();
    }

    @Subscribe
    public void onWeatherSeeMoreButtonPressed(WeatherView.WeatherSeeMoreButtonPressedEvent event) {
        if (extended) {
            extended = false;
            weatherView.hideExtended();
            return;
        }
        fillExtended();
    }

    private void fillExtended() {
        // The API returns wind direction in degrees, the array helps translating it into text,
        // doing a little trick with modulo and division.
        // Also, the API returns sunset and sunrise times in Epoch time, so it needs a conversion with
        // the Date and SimpleDateFormat class. As date works in milliseconds, a "*1000" is needed.
        String directions[] = {"N", "NE", "E", "SE", "S", "SW", "W", "NW", "N"};

        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.US);
        String sunrise = format.format(new Date(weatherModel.getWeather().getSys().getSunrise() * 1000L));
        String sunset = format.format(new Date(weatherModel.getWeather().getSys().getSunset() * 1000L));

        double rain = (weatherModel.getWeather().getRain() != null) ? weatherModel.getWeather().getRain().get3h() : 0;


        weatherView.showExtendedWeather(
                weatherModel.getWeather().getWind().getSpeed() * 3.6, // m/s to km/h
                directions[Math.round(((weatherModel.getWeather().getWind().getDeg() % 360) / 45))],
                weatherModel.getWeather().getMain().getHumidity(),
                weatherModel.getWeather().getMain().getPressure(),
                weatherModel.getWeather().getClouds().getAll(),
                rain,
                sunrise,
                sunset);
        extended = true;
    }

    private void fillBase() {
        //Changes to Uppercase the first letter from the description returned by the API
        String description = weatherModel.getWeather().getWeather().get(0).getDescription();
        String upperDesc = description.substring(0, 1).toUpperCase() + description.substring(1);
        weatherView.showWeather(
                weatherModel.getWeather().getName(),
                upperDesc,
                Math.round(weatherModel.getWeather().getMain().getTemp()),
                weatherModel.getWeather().getWeather().get(0).getIcon()
        );
    }

    private void retrieveLocation() {
        if (!locationPermissions)
            checkLocationPermissions();
        if (!locationSettings)
            checkSettings();
        if (locationPermissions && locationSettings)
            weatherModel.startLocationUpdates(this);
    }

    @Override
    public void onLocationResult(LocationResult locationResult) {
        if (locationResult == null) {
            return;
        }
        weatherModel.setLongitude(locationResult.getLastLocation().getLongitude());
        weatherModel.setLatitude(locationResult.getLastLocation().getLatitude());
        weatherModel.stopReceivingLocationUpdates(this);
        weatherModel.retrieveWeather();
    }

    @Subscribe
    public void onGetWeatherSuccessEvent(GetWeatherSuccessEvent event) {
        weatherModel.setWeather(event.getWeather());
        weatherView.toggleSpinner();
        fillBase();
        if (extended) fillExtended();
    }


    @Subscribe
    public void onGetWeatherFailureEvent(GetWeatherFailureEvent event) {
        if (weatherView.getContext() != null) {
            weatherView.toggleSpinner();
            Toast.makeText(weatherView.getContext(), weatherView.getContext().getResources().getString(R.string.weather_retrieve_error), Toast.LENGTH_LONG).show();
        }
    }

    private void checkLocationPermissions() {
        final Activity activity = weatherView.getActivity();
        if (activity != null) {
            if (weatherModel.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                locationPermissions = true;
                return;
            }
            // Permission to access the location is missing.
            weatherView.requestPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION, WEATHER_LOCATION_PERMISSION_REQUEST_CODE);
        }
        locationPermissions = false;
    }

    private void checkSettings() {
        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(weatherModel.getLocationRequest());

        final Activity activity = weatherView.getActivity();
        if (activity != null) {
            SettingsClient client = LocationServices.getSettingsClient(activity);
            Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
            task.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
                @Override
                public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                    locationSettings = true;
                    retrieveLocation();
                }
            });

            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if (e instanceof ResolvableApiException) {
                        // Location settings are not satisfied, but this can be fixed
                        // by showing the user a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            ResolvableApiException resolvable = (ResolvableApiException) e;
                            resolvable.startResolutionForResult(activity, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException sendEx) {
                            // Ignore the error.
                        }
                    }
                }
            });
        }
    }

    public void locationPermissionGranted() {
        locationPermissions = true;
        retrieveLocation();
    }

    public void settingsChecked() {
        checkSettings();
    }

}