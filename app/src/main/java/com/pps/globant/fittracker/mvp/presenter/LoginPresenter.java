package com.pps.globant.fittracker.mvp.presenter;


import com.pps.globant.fittracker.mvp.model.LoginModel;
import com.pps.globant.fittracker.mvp.view.LoginView;
import com.squareup.otto.Subscribe;

import com.pps.globant.fittracker.R;
import com.pps.globant.fittracker.utils.FacebookLoginProvider;
import android.content.res.Resources;
import android.util.Log;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import static com.pps.globant.fittracker.mvp.view.LoginView.*;
import static com.pps.globant.fittracker.mvp.model.LoginModel.*;

public class LoginPresenter {

    private final LoginModel model;
    private final LoginView view;

    //Google declarations-----------------------------------------------------------------------------------------------------------
    /*googleServiceClientId is a key obtained from https://developers.google.com/identity/sign-in/android/start-integrating
    To get it, it requires an unique SHA1 key, so if you want to recompile the app in another pc, you'll need to create a new key.*/
    private static final String googleServiceClientId = "268582315609-j419amnke1b8djg935oq1ncd08e78lam.apps.googleusercontent.com";
    private static final int RC_GET_TOKEN = 9002;
    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;
    //-------------------------------------------------------------------------------------------------------------------------------

    public LoginPresenter(LoginModel model, LoginView view) {
        this.model = model;
        this.view = view;

        // Configure sign-in to request the user's ID, email address, token and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(googleServiceClientId)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(view.getActivity(), gso);
    }

    @Subscribe
    public void onGoogleSignInButtonPressed(GoogleSignInButtonPressedEvent event) {
        //Starting the intent prompts the user to select a Google account to sign in with.
        //If you request other stuff beyond profile, email, token and openid,
        //the user is also prompted to grant access to the requested resources.
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        view.getActivity().startActivityForResult(signInIntent, RC_GET_TOKEN);
    }

    @Subscribe
    public void onGoogleSignOutButtonPressed(GoogleSignOutButtonPressedEvent event) {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(view.getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(view.getActivity(), "Signed out from google", Toast.LENGTH_SHORT).show();
                    }
                });
        model.signOutGoogle();
        view.hideGoogleSignOutButton();
        view.showGoogleSignInButton();
        view.setStatusLabel("Signed out");
        view.setDetailLabel("");
    }

    @Subscribe
    public void onSuccessfulGoogleSignIn(SuccessfulGoogleSignInEvent event) {
        //Once model posts that the login was successful, it sets the detail label with the name.
        view.setDetailLabel(model.getMail());
        view.hideGoogleSignInButton();
        view.showGoogleSignOutButton();
        view.setStatusLabel("Signed in");
    }

    public void setActivityResults(int requestCode, int resultCode, Intent data) {
        //Once the activity started in onGoogleSignInButtonPressed, model.signInGoogle is called.
        //This method is called by MainActivity, that is listening for the activity to get the result.
        if (requestCode == RC_GET_TOKEN) {
            model.signInGoogle(data);
        }
    }

    @Subscribe
    public void onFetchingFbUserDataCompletedEvent(FacebookLoginProvider.FetchingFbUserDataCompletedEvent event) {
        Resources res = view.getActivity().getResources();
        view.setLabelFb(String.format(res.getString(R.string.loged_in_name), event.fbUser.getName()));
    }

    @Subscribe
    public void onCantRetrieveAllTheFieldsRequestedsEvent(FacebookLoginProvider.CantRetrieveAllTheFieldsRequestedsEvent event) {
        Resources res = view.getActivity().getResources();
        view.setLabelFb(res.getString(R.string.fb_login_error_message));
        Log.e(res.getString(R.string.facebook), res.getString(R.string.fb_login_error_logPrintErrorCantRetrieveAllTheFields));
    }

    @Subscribe
    public void onFetchingFbUserDataErrorEvent(FacebookLoginProvider.FetchingFbUserDataErrorEvent event) {
        Resources res = view.getActivity().getResources();
        view.setLabelFb(res.getString(R.string.fb_login_error_message));
        Log.e(res.getString(R.string.facebook), String.format(res.getString(R.string.fb_login_error_logPrintErrorCantFetchData), event.facebookException.toString()));
    }

    @Subscribe
    public void onLogOutCompleteEvent(FacebookLoginProvider.LogOutCompleteEvent event) {
        Resources res = view.getActivity().getResources();
        view.setLabelFb(res.getString(R.string.not_loged_in));
    }

    @Subscribe
    public void onFbButtonPressedEvent(LoginView.FbButtonPressedEvent event) {
        model.registerFbCallbacks();
    }

    public void fbLogOut() {
        model.fbLogOut();
    }

}
