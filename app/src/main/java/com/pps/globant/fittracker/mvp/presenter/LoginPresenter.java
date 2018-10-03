package com.pps.globant.fittracker.mvp.presenter;

import com.squareup.otto.Subscribe;

import com.pps.globant.fittracker.mvp.model.LoginModel;
import com.pps.globant.fittracker.mvp.view.LoginView;
import com.pps.globant.fittracker.utils.BusProvider;
import com.pps.globant.fittracker.R;
import com.pps.globant.fittracker.utils.FacebookLoginProvider;

import android.app.Activity;
import android.util.Log;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;
import android.content.res.Resources;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;

import static com.pps.globant.fittracker.mvp.view.LoginView.*;
import static com.pps.globant.fittracker.mvp.model.LoginModel.*;

public class LoginPresenter {

    private final LoginModel model;
    private final LoginView view;
    private final Activity activity;

    //Google declarations-----------------------------------------------------------------------------------------------------------
    /*GOOGLE_SERVICE_CLIENT_ID is a key obtained from https://developers.google.com/identity/sign-in/android/start-integrating
    To get it, it requires an unique SHA1 key, so if you want to recompile the app in another pc, you'll need to create a new key.*/
    private static final String GOOGLE_SERVICE_CLIENT_ID = "268582315609-j419amnke1b8djg935oq1ncd08e78lam.apps.googleusercontent.com";
    private static final String GOOGLE_SIGN_IN_ERROR_TAG = "Sign In Error";
    private static final String GOOGLE_SIGNED_OUT_MESSAGE = "Signed out from google";
    private static final String GOOGLE_SIGN_IN_ERROR_MESSAGE = "handleSignInResult:error";
    private static final String EMPTY_STRING = "";
    private static final int RC_GET_TOKEN = 9002;

    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;
    //-------------------------------------------------------------------------------------------------------------------------------

    public LoginPresenter(LoginModel model, LoginView view) {
        this.model = model;
        this.view = view;
        this.activity = view.getActivity();

        if (activity != null) {

            // Configure sign-in to request the user's ID, email address, token and basic
            // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(GOOGLE_SERVICE_CLIENT_ID)
                    .requestEmail()
                    .build();

            // Build a GoogleSignInClient with the options specified by gso.
            mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);

            // Check for existing Google Sign In account, if the user is already signed in
            // the GoogleSignInAccount will be non-null.
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(activity);
            if (account != null) {
                model.setAccount(account);
                successfulGoogleSignIn();
            }
        }
    }

    @Subscribe
    public void onGoogleSignInButtonPressed(GoogleSignInButtonPressedEvent event) {
        //Starting the intent prompts the user to select a Google account to sign in with.
        //If you request other stuff beyond profile, email, token and openid,
        //the user is also prompted to grant access to the requested resources.
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        if (activity != null)
            activity.startActivityForResult(signInIntent, RC_GET_TOKEN);
    }

    @Subscribe
    public void onGoogleSignOutButtonPressed(GoogleSignOutButtonPressedEvent event) {
        if (activity != null) {
            mGoogleSignInClient.signOut()
                    .addOnCompleteListener(activity, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(activity, GOOGLE_SIGNED_OUT_MESSAGE, Toast.LENGTH_SHORT).show();
                            successfulGoogleSignOut();
                        }
                    });
        }
    }

    private void successfulGoogleSignIn() {
        //Once model posts that the login was successful, it sets the detail label with the name.
        view.setDetailLabel(model.getMail());
        view.hideGoogleSignInButton();
        view.showGoogleSignOutButton();
        view.setStatusLabel(R.string.signed_in);
    }

    private void successfulGoogleSignOut() {
        model.signOutGoogle();
        view.hideGoogleSignOutButton();
        view.showGoogleSignInButton();
        view.setStatusLabel(R.string.signed_out);
        view.setDetailLabel(EMPTY_STRING);
    }

    @Subscribe
    public void onGoogleSignIn(GoogleSignInEvent event) {
        //Handles the task of login in. If it's successful, posts in the bus a SuccessfulGoogleSignInEvent
        try {
            GoogleSignInAccount account = event.getCompletedTask().getResult(ApiException.class);
            model.setAccount(account);
            successfulGoogleSignIn();
        } catch (ApiException e) {
            Log.w(GOOGLE_SIGN_IN_ERROR_TAG, GOOGLE_SIGN_IN_ERROR_MESSAGE, e);
        }
    }


    public void setActivityResults(int requestCode, int resultCode, Intent data) {
        //Once the activity started in onGoogleSignInButtonPressed, model.signInGoogle is called.
        //This method is called by MainActivity, that is listening for the activity to get the result.
        if (requestCode == RC_GET_TOKEN) {
            model.signInGoogle(data);
        }
    }

    private void setUser() {
        Resources res = view.getActivity().getResources();
        view.setLabelButtonFb(R.string.fb_login_button_msg_Log_Out);
        view.setLabelFb(String.format(res.getString(R.string.loged_in_name), model.getUser().getName()));
    }

    public void setAndPopUpError(int error){
        view.setLabelFb(R.string.fb_login_error_message);
        view.popUp(error);
    }

    @Subscribe
    public void onLogOutCompleteEvent(FacebookLoginProvider.LogOutCompleteEvent event) {
        Resources res = view.getActivity().getResources();
        view.setLabelFb(R.string.not_loged_in);
        view.setLabelButtonFb(R.string.fb_login_button_msg_Continue_with_fb);
    }

    @Subscribe
    public void onFbButtonPressedEvent(LoginView.FbButtonPressedEvent event) {
        if (!model.isFbLogedIn()) {
            model.fbLogIn(view.getActivity());
        } else {
            model.fbLogOut();
        }
    }

    public void register() {
        BusProvider.register(this, this.model);
    }

    public void unregister() {
        BusProvider.unregister(this, this.model);
    }

    public void restoreState() {
        model.restoreState();
    }

    //FACEBOOK´S EVENTS
    @Subscribe
    public void onFbUserDataRecoveredEvent(FacebookLoginProvider.FbUserDataRecoveredEvent event) {
        model.setUser(event.user);
        setUser();
    }

    @Subscribe
    public void onFetchingFbUserDataCancelEvent(FacebookLoginProvider.FetchingFbUserDataCancelEvent event) {
        setAndPopUpError(R.string.fb_login_cancel_message);
    }

    @Subscribe
    public void onFetchingFbUserDataErrorEvent(FacebookLoginProvider.FetchingFbUserDataErrorEvent event) {
        setAndPopUpError(R.string.fb_login_error_message);
    }

}
