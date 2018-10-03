package com.pps.globant.fittracker.mvp.view;

import android.app.Activity;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;

import com.pps.globant.fittracker.R;
import com.squareup.otto.Bus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginView extends ActivityView {

    private final Bus bus;

    @BindView(R.id.status)
    TextView statusLabel;
    @BindView(R.id.detail)
    TextView detailLabel;
    @BindView(R.id.sign_in_button)
    SignInButton googleSignInButton;
    @BindView(R.id.sign_out_button)
    Button googleSignOutButton;

    @BindView(R.id.text_fb_label)
    TextView textFbLabel;
    @BindView(R.id.buttton_fb)
    Button buttonFb;


    public LoginView(Activity activity, Bus bus) {
        super(activity);
        this.bus = bus;
        ButterKnife.bind(this, activity);
    }

    public void setDetailLabel(String label) {
        detailLabel.setText(label);
    }

    public void setStatusLabel(@StringRes int label) {
        statusLabel.setText(label);
    }

    public void hideGoogleSignInButton() {
        googleSignInButton.setVisibility(View.GONE);
    }

    public void hideGoogleSignOutButton() {
        googleSignOutButton.setVisibility(View.GONE);
    }

    public void showGoogleSignInButton() {
        googleSignInButton.setVisibility(View.VISIBLE);
    }

    public void showGoogleSignOutButton() {
        googleSignOutButton.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.sign_in_button)
    public void signInButtonPressed() {
        bus.post(new GoogleSignInButtonPressedEvent());
    }

    @OnClick(R.id.sign_out_button)
    public void signOutButtonPressed() {
        bus.post(new GoogleSignOutButtonPressedEvent());
    }

    public static class GoogleSignInButtonPressedEvent {
        //Nothing to do, class made to pass it through the bus
    }

    public static class GoogleSignOutButtonPressedEvent {
        //Nothing to do, class made to pass it through the bus
    }

    public void setLabelFb(String label) {
        textFbLabel.setText(label);
    }

    public void setLabelFb(@StringRes int labelId) {
        textFbLabel.setText(labelId);
    }

    @OnClick(R.id.buttton_fb)
    public void fbButtonPressed() {
        bus.post(new FbButtonPressedEvent());
    }

    public void setLabelButtonFb(@StringRes int label) {
        this.buttonFb.setText(label);
    }

    public void popUp(@StringRes int error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    public class FbButtonPressedEvent {
    }

}
