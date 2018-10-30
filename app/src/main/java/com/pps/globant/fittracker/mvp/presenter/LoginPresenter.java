package com.pps.globant.fittracker.mvp.presenter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.pps.globant.fittracker.FirstAppScreenActivity;
import com.pps.globant.fittracker.LoginLocallyActivity;
import com.pps.globant.fittracker.R;
import com.pps.globant.fittracker.SignInFormActivity;
import com.pps.globant.fittracker.mvp.model.DataBase.User;
import com.pps.globant.fittracker.mvp.model.DataBase.UsersRepository;
import com.pps.globant.fittracker.mvp.model.LoginModel;
import com.pps.globant.fittracker.mvp.view.LoginView;
import com.pps.globant.fittracker.utils.BusProvider;
import com.pps.globant.fittracker.utils.FacebookLoginProvider;

import com.squareup.otto.Subscribe;

import static com.pps.globant.fittracker.utils.Constants.EXTRA_MESSAGE;
import static com.pps.globant.fittracker.utils.Constants.RC_GET_TOKEN;

public class LoginPresenter {

    private final LoginModel model;
    private final LoginView view;
    private final InstagramLoginPresenter igPresenter;
    private GoogleSignInClient mGoogleSignInClient;

    public LoginPresenter(LoginModel model, LoginView view, GoogleSignInClient mGoogleSignInClient,
                          InstagramLoginPresenter igPresenter) {
        this.model = model;
        this.view = view;
        this.mGoogleSignInClient = mGoogleSignInClient;
        this.igPresenter = igPresenter;
    }

    public void clearDatabase() {
        model.clearDatabase();
    }

    public void register() {
        BusProvider.register(this);
    }

    public void unregister() {
        BusProvider.unregister(this);
    }

    @Subscribe
    public void onGoogleSignInButtonPressed(LoginView.GoogleSignInButtonPressedEvent event) {
        //Starting the intent prompts the user to select a Google account to sign in with.
        //If you request other stuff besides profile, email, token and openid,
        //the user is also prompted to grant access to the requested resources.

        if (view.getActivity() != null) {
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(view.getActivity());
            // Check for existing Google Sign In account, if the user is already signed in
            // the GoogleSignInAccount will be non-null.
            if (account != null) {
                successfulGoogleSignIn(account);
                return;
            }
        }
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        Activity activity = view.getActivity();
        if (activity != null)
            activity.startActivityForResult(signInIntent, RC_GET_TOKEN);
    }

    private void successfulGoogleSignIn(GoogleSignInAccount account) {
        view.popUp(R.string.logged_into_google);
        model.setUser(User.getUser(account.getGivenName(), account.getFamilyName(), account.getEmail(), null, account.getIdToken()));
        model.getUserFromDbBySocialNetworkId();
    }

    @Subscribe
    public void onGoogleSignInEvent(LoginModel.GoogleSignInEvent event) {
        //Handles the task of login in. If it's successful, posts in the bus a SuccessfulGoogleSignInEvent
        if (event.getAccount() != null)
            successfulGoogleSignIn(event.getAccount());
        else
            view.popUp(R.string.google_login_error_message);
    }

    public void setActivityResults(int requestCode, int resultCode, Intent data) {
        //Once the activity started in onGoogleSignInButtonPressed, model.googleSignIn is called.
        //This method is called by MainActivity, that is listening for the activity to get the result.
        if (requestCode == RC_GET_TOKEN) {
            model.googleSignIn(data);
        }
    }

    @Subscribe
    public void onFacebookSignInButtonPressed(LoginView.FacebookSignInButtonPressedEvent event) {
        Activity activity = view.getActivity();
        if (activity == null) return;
        model.fbLogIn(activity);
    }

    @Subscribe
    public void onFbUserDataRecoveredEvent(FacebookLoginProvider.FbUserDataRecoveredEvent event) {
        model.setUser(event.user);
        view.popUp(R.string.logged_into_facebook);
        model.getUserFromDbBySocialNetworkId();
    }

    @Subscribe
    public void onFetchingFbUserDataCancelEvent(FacebookLoginProvider.FetchingFbUserDataCancelEvent event) {
        view.popUp(R.string.fb_login_cancel_message);
    }

    //ROOM DATABASE

    @Subscribe
    public void onFetchingFbUserDataErrorEvent(FacebookLoginProvider.FetchingFbUserDataErrorEvent event) {
        view.popUp(R.string.fb_login_error_message);
    }

    @Subscribe
    public void onFetchingUserFromDataBaseCompleted(UsersRepository.FetchingUserFromDataBaseCompleted event) {
        if (event.user == null) {
            model.insertUserToDB();
        } else {
            model.setUser(event.user);
            view.popUp(R.string.db_text_msg_user_exists);
            if (event.user.isRegisterComplete()) loginSocial();
            else signUpSocial();
        }
    }

    @Subscribe
    public void onInsertUserIntoDataBaseCompleted(UsersRepository.InsertUserIntoDataBaseCompleted event) {
        view.popUp(R.string.db_text_msg_adding_user);
        model.getUser().setId(event.id);
        signUpSocial();
    }

    private void loginSocial() {
        Activity activity = view.getActivity();
        if (activity == null) {
            return;
        }
        Intent intent = new Intent(activity, FirstAppScreenActivity.class);
        long userId = model.getUser().getId();
        intent.putExtra(EXTRA_MESSAGE, userId);
        activity.startActivity(intent);
    }

    @Subscribe
    public void onInformationReady(InstagramLoginPresenter.InformationReady event) {
        view.popUp(R.string.toast_instagram_login + event.name);
    }

    @Subscribe
    public void onIgUserDataRecoveredEvent(InstagramLoginPresenter.IgUserDataRecoveredEvent event) {
        model.setUser(event.user);
        model.getUserFromDbBySocialNetworkId();
    }

    @Subscribe
    public void onInstagramSignInButtonPressed(LoginView.InstagramSignInButtonPressedEvent event) {
        view.popUp(R.string.toast_instagram_loading);
        igPresenter.igButtonLoginClick();
    }

    private void signUpSocial() {
        Activity activity = view.getActivity();
        if (activity == null) {
            return;
        }
        Intent intent = new Intent(activity, SignInFormActivity.class);
        long userId = model.getUser().getId();
        intent.putExtra(EXTRA_MESSAGE, String.valueOf(userId));
        activity.startActivity(intent);
    }

    @Subscribe
    public void onDeletingUserFromDataBaseCompleted(UsersRepository.DeletingUserFromDataBaseCompleted event) {
        view.popUp(R.string.db_text_msg_user_deleted);
    }

    @Subscribe
    public void onManualRegisterButtonPressedEvent(LoginView.ManualRegisterButtonPressedEvent event) {
        Activity activity = view.getActivity();
        if (activity == null) return;
        Intent intent = new Intent(activity, SignInFormActivity.class);
        activity.startActivity(intent);
    }

    @Subscribe
    public void onManualSignInButtonPressedEvent(LoginView.ManualSignInButtonPressedEvent event) {
        Activity activity = view.getActivity();
        if (activity == null) return;
        Intent intent = new Intent(activity, LoginLocallyActivity.class);
        activity.startActivity(intent);
    }

    public void checkLoggedIn(SharedPreferences spUser) {
        igPresenter.checkLoggedIn(spUser);
    }
}