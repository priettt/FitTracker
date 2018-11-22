package com.pps.globant.fittracker.mvp.view;

import android.app.Activity;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pps.globant.fittracker.R;
import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WeatherView extends CardViewView {
    private Bus bus;


    @BindView(R.id.button_weather_permission)
    Button weatherPermissionButton;
    @BindView(R.id.weather_layout_permission)
    LinearLayout weatherLayoutPermission;
    @BindView(R.id.weather_layout_base)
    ConstraintLayout weatherLayoutBase;
    @BindView(R.id.weather_layout_expanded)
    ConstraintLayout weatherLayoutExpanded;
    @BindView(R.id.weather_text_location)
    TextView weatherTextLocation;
    @BindView(R.id.weather_image)
    ImageView weatherImage;
    @BindView(R.id.weather_text_temp_value)
    TextView weatherTextTempNumber;
    @BindView(R.id.weather_text_description)
    TextView weatherTextDescription;
    @BindView(R.id.weather_text_wind_speed_value)
    TextView weatherTextWindSpeedValue;
    @BindView(R.id.weather_text_wind_direction_value)
    TextView weatherTextWindDirectionValue;
    @BindView(R.id.weather_text_cloudiness_value)
    TextView weatherTextCloudinessValue;
    @BindView(R.id.weather_text_humidity_value)
    TextView weatherTextHumidityValue;
    @BindView(R.id.weather_text_pressure_value)
    TextView weatherTextPressureValue;
    @BindView(R.id.weather_text_sunrise_value)
    TextView weatherTextSunriseValue;
    @BindView(R.id.weather_text_sunset_value)
    TextView weatherTextSunsetValue;
    @BindView(R.id.weather_text_rain_value)
    TextView weatherTextRainValue;
    @BindView(R.id.button_weather_see_more)
    Button buttonSeeMore;
    @BindView(R.id.weather_progress)
    ProgressBar weatherProgress;

    public WeatherView(CardView cardView, Activity activity, Bus bus) {
        super(cardView, activity);
        ButterKnife.bind(this, activity);
        this.bus = bus;
    }

    public void requestPermission(Activity activity, String permission, int requestCode) {
        ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
    }

    @OnClick(R.id.button_weather_permission)
    public void weatherPermissionButtonPressed() {
        bus.post(new WeatherPermissionButtonPressedEvent());
    }

    @OnClick(R.id.button_weather_refresh)
    public void weatherRefreshButtonPressed() {
        bus.post(new WeatherRefreshButtonPressedEvent());
    }

    @OnClick(R.id.button_weather_see_more)
    public void weatherSeeMoreButtonPressed() {
        bus.post(new WeatherSeeMoreButtonPressedEvent());
    }

    public void showExtendedWeather(double speed, String direction, int humidity, double pressure, int cloud, double rain, String sunrise, String sunset) {
        if (getContext() != null) {
            weatherTextWindSpeedValue.setText(String.format(getContext().getResources().getString(R.string.wind_speed_value), speed));
            weatherTextHumidityValue.setText(String.format(getContext().getResources().getString(R.string.humidity_value), humidity));
            weatherTextPressureValue.setText(String.format(getContext().getResources().getString(R.string.pressure_value), pressure));
            weatherTextCloudinessValue.setText(String.format(getContext().getResources().getString(R.string.cloudiness_value), cloud));
            weatherTextRainValue.setText(String.format(getContext().getResources().getString(R.string.rain_value), rain));
            weatherTextSunriseValue.setText(sunrise);
            weatherTextSunsetValue.setText(sunset);
            weatherTextWindDirectionValue.setText(direction);
            buttonSeeMore.setText(getContext().getResources().getString(R.string.cards_see_less));
            weatherLayoutExpanded.setVisibility(View.VISIBLE);
        }
    }

    public void hideExtended() {
        if (getContext() != null) {
            buttonSeeMore.setText(getContext().getResources().getString(R.string.cards_see_more));
            weatherLayoutExpanded.setVisibility(View.GONE);
        }
    }

    public void toggleSpinnerOn(){
        weatherLayoutPermission.setAlpha(0.4f);
        weatherProgress.setVisibility(View.VISIBLE);
        weatherPermissionButton.setEnabled(false);
    }

    public void toggleSpinnerOff(){
        weatherLayoutPermission.setAlpha(1f);
        weatherProgress.setVisibility(View.GONE);
        weatherPermissionButton.setEnabled(true);
    }

    public static class WeatherPermissionButtonPressedEvent {
        //Nothing to do, class made to pass it through the bus
    }

    public static class WeatherSeeMoreButtonPressedEvent {
        //Nothing to do, class made to pass it through the bus
    }

    public static class WeatherRefreshButtonPressedEvent {
        //Nothing to do, class made to pass it through the bus
    }


    public void showWeather(String location, String description, int temp, String icon) {
        weatherTextLocation.setText(location);
        weatherTextDescription.setText(description);
        if (getContext() != null)
            weatherTextTempNumber.setText(String.format(getContext().getResources().getString(R.string.degrees), temp));
        Picasso.get()
                .load(String.format(getContext().getResources().getString(R.string.icon_url), icon))
                .into(weatherImage);
        weatherLayoutPermission.setVisibility(View.GONE);
        weatherLayoutBase.setVisibility(View.VISIBLE);
    }
}
