package com.pps.globant.fittracker.mvp.view;

import android.app.Activity;
import android.support.annotation.StringRes;
import android.view.View;
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
    @BindView(R.id.google_sign_out_button)
    Button googleSignOutButton;

    @BindView(R.id.facebook_sign_in_button)
    Button facebookSignInButton;
    @BindView(R.id.facebook_sign_out_button)
    Button facebookSignOutButton;

    @BindView(R.id.instagram_sign_in_button)
    Button instagramSignInButton;
    @BindView(R.id.instagram_sign_out_button)
    Button instagramSignOutButton;

    public LoginView(Activity activity, Bus bus) {
        super(activity);
        this.bus = bus;
        ButterKnife.bind(this, activity);
    }

    public void toggleGoogleVisibility() {
        if (googleSignInButton.getVisibility() == View.GONE) {
            googleSignOutButton.setVisibility(View.GONE);
            googleSignInButton.setVisibility(View.VISIBLE);
        } else {
            googleSignInButton.setVisibility(View.GONE);
            googleSignOutButton.setVisibility(View.VISIBLE);
        }
    }

    public void toggleFacebookVisibility() {
        if (facebookSignInButton.getVisibility() == View.GONE) {
            facebookSignOutButton.setVisibility(View.GONE);
            facebookSignInButton.setVisibility(View.VISIBLE);
        } else {
            facebookSignInButton.setVisibility(View.GONE);
            facebookSignOutButton.setVisibility(View.VISIBLE);
        }
    }

    public void toggleInstagramVisibility() {
        if (instagramSignInButton.getVisibility() == View.GONE) {
            instagramSignOutButton.setVisibility(View.GONE);
            instagramSignInButton.setVisibility(View.VISIBLE);
        } else {
            instagramSignInButton.setVisibility(View.GONE);
            instagramSignOutButton.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.google_sign_in_button)
    public void signInButtonPressed() {
        bus.post(new GoogleSignInButtonPressedEvent());
    }

    @OnClick(R.id.google_sign_out_button)
    public void signOutButtonPressed() {
        bus.post(new GoogleSignOutButtonPressedEvent());
    }

    public static class GoogleSignInButtonPressedEvent {
        //Nothing to do, class made to pass it through the bus
    }

    public static class GoogleSignOutButtonPressedEvent {
        //Nothing to do, class made to pass it through the bus
    }

    @OnClick(R.id.facebook_sign_in_button)
    public void FacebookSignInButtonPressed() {
        bus.post(new FacebookSignInButtonPressed());
    }

    @OnClick(R.id.facebook_sign_out_button)
    public void FacebookSignOutButtonPressed() {
        bus.post(new FacebookSignOutButtonPressed());
    }

    public void popUp(@StringRes int error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    public static class FacebookSignInButtonPressed {
        //Nothing to do, class made to pass it through the bus
    }

    public static class FacebookSignOutButtonPressed {
        //Nothing to do, class made to pass it through the bus
    }

}
