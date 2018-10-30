package com.pps.globant.fittracker.mvp.view;

import android.app.Activity;
import android.support.annotation.StringRes;
import android.widget.Button;
import android.widget.Toast;

import com.pps.globant.fittracker.R;
import com.squareup.otto.Bus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginView extends ActivityView {

    private final Bus bus;

    @BindView(R.id.google_sign_in_button)
    Button googleSignInButton;

    @BindView(R.id.facebook_sign_in_button)
    Button facebookSignInButton;

    @BindView(R.id.instagram_sign_in_button)
    Button instagramSignInButton;

    @BindView(R.id.manual_sign_in_button)
    Button manualSignInButton;
    @BindView(R.id.manual_register_button)
    Button manualRegisterButton;

    public LoginView(Activity activity, Bus bus) {
        super(activity);
        this.bus = bus;
        ButterKnife.bind(this, activity);
    }

    @OnClick(R.id.google_sign_in_button)
    public void googleSignInButtonPressed() {
        bus.post(new GoogleSignInButtonPressedEvent());
    }

    @OnClick(R.id.facebook_sign_in_button)
    public void facebookSignInButtonPressed() {
        bus.post(new FacebookSignInButtonPressedEvent());
    }

    @OnClick(R.id.instagram_sign_in_button)
    public void instagramSignInButtonPressed() {
        bus.post(new InstagramSignInButtonPressedEvent());
    }

    @OnClick(R.id.manual_sign_in_button)
    public void manualSignInButtonPressed() {
        bus.post(new ManualSignInButtonPressedEvent());
    }

    @OnClick(R.id.manual_register_button)
    public void manualRegisterButtonPressed() {
        bus.post(new ManualRegisterButtonPressedEvent());
    }

    public void popUp(@StringRes int msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void popUp(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public static class GoogleSignInButtonPressedEvent {
        //Nothing to do, class made to pass it through the bus
    }

    public static class FacebookSignInButtonPressedEvent {
        //Nothing to do, class made to pass it through the bus
    }

    public static class InstagramSignInButtonPressedEvent {
        //Nothing to do, class made to pass it through the bus
    }

    public static class ManualSignInButtonPressedEvent {
        //Nothing to do, class made to pass it through the bus
    }

    public static class ManualRegisterButtonPressedEvent {
        //Nothing to do, class made to pass it through the bus
    }

}
