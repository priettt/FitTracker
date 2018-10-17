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
    @BindView(R.id.button_google_sign_in)
    SignInButton googleSignInButton;
    @BindView(R.id.button_google_sign_out)
    Button googleSignOutButton;

    //GOOGLE
    @BindView(R.id.button_fb)
    Button buttonFb;

    @BindView(R.id.btn_insta_login) Button btn_insta_login;
    @BindView(R.id.txt_view_instagram) TextView igLabel;
    @BindView(R.id.btn_insta_logout) Button btn_insta_logout;

    public LoginView(Activity activity, Bus bus) {
        super(activity);
        this.bus = bus;
        ButterKnife.bind(this, activity);
    }

    public void popUp(@StringRes int mensaje) {
        Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();
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

    @OnClick(R.id.button_google_sign_in)
    public void signInButtonPressed() {
        bus.post(new GoogleSignInButtonPressedEvent());
    }

    @OnClick(R.id.button_google_sign_out)
    public void signOutButtonPressed() {
        bus.post(new GoogleSignOutButtonPressedEvent());
    }

    @OnClick(R.id.button_fb)
    public void fbButtonPressed() {
        bus.post(new FbButtonPressedEvent());
    }

    //FACEBOOK

    public void setLabelButtonFb(@StringRes int label) {
        this.buttonFb.setText(label);
    }

    @OnClick(R.id.button_manual_loggin)
    public void manualLogginButtonPressed() {
        bus.post(new ManualLogginButtonPressedEvent());
    }

    @OnClick(R.id.link_signup)
    public void signUpLinkPressed() {
        bus.post(new signUpLinkPressedEvent());
    }

    public static class GoogleSignInButtonPressedEvent {
        //Nothing to do, class made to pass it through the bus
    }

    //LOCAL LOGGIN AND SIGNUP

    public static class GoogleSignOutButtonPressedEvent {
        //Nothing to do, class made to pass it through the bus
    }

    public static class signUpLinkPressedEvent {
    }

    public class FbButtonPressedEvent {
    }

    /********instagram*******/

    public void setIgTextView(String name){
        igLabel.setText(name);
    }

    public void showLoginToast(String name){
        Toast.makeText(this.getContext(), String.format("%s%s", getContext().getResources().getString(R.string.toast_instagram_login), name), Toast.LENGTH_SHORT).show();
    }

    public void showLogoutToast(){
        Toast.makeText(this.getContext(), R.string.toast_instagram_logout, Toast.LENGTH_SHORT).show();
    }

    public void disableLogin(){
        btn_insta_login.setVisibility(View.INVISIBLE);
        btn_insta_login.setEnabled(false);
        btn_insta_logout.setVisibility(View.VISIBLE);
        btn_insta_logout.setEnabled(true);

    }
    public void enableLogin(){
        btn_insta_login.setVisibility(View.VISIBLE);
        btn_insta_login.setEnabled(true);
        btn_insta_logout.setVisibility(View.INVISIBLE);
        btn_insta_logout.setEnabled(false);
    }

    public void showLoadingLogin() {
        Toast.makeText(this.getContext(), R.string.toast_instagram_loading, Toast.LENGTH_LONG).show();
    }

    /********end instagram********/

    public class ManualLogginButtonPressedEvent {
    }
}
