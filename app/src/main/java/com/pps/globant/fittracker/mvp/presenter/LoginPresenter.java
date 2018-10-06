package com.pps.globant.fittracker.mvp.presenter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.pps.globant.fittracker.FirstAppScreenActivity;
import com.pps.globant.fittracker.LoginLocallyActivity;
import com.pps.globant.fittracker.MainActivity;
import com.pps.globant.fittracker.R;
import com.pps.globant.fittracker.SignInFormActivity;
import com.pps.globant.fittracker.mvp.model.DataBase.UsersRepository;
import com.pps.globant.fittracker.mvp.model.LoginModel;
import com.pps.globant.fittracker.mvp.view.LoginView;
import com.pps.globant.fittracker.utils.BusProvider;
import com.pps.globant.fittracker.utils.FacebookLoginProvider;
import com.squareup.otto.Subscribe;

import static com.pps.globant.fittracker.mvp.model.LoginModel.GoogleSignInEvent;
import static com.pps.globant.fittracker.mvp.view.LoginView.GoogleSignInButtonPressedEvent;
import static com.pps.globant.fittracker.mvp.view.LoginView.GoogleSignOutButtonPressedEvent;
import static com.pps.globant.fittracker.utils.CONSTANTS.SP_TOKEN;

public class LoginPresenter {

    //Google declarations-----------------------------------------------------------------------------------------------------------
    /*GOOGLE_SERVICE_CLIENT_ID is a key obtained from https://developers.google.com/identity/sign-in/android/start-integrating
    To get it, it requires an unique SHA1 key, so if you want to recompile the app in another pc, you'll need to create a new key.*/
    //francisco key private static final String GOOGLE_SERVICE_CLIENT_ID =
    // "268582315609-j419amnke1b8djg935oq1ncd08e78lam.apps.googleusercontent.com";
    //fermin key private static final String GOOGLE_SERVICE_CLIENT_ID = "146472501375-hu9vlk5qk9svo2m2b2gbt0kb0fnvfrvd
    // .apps.googleusercontent.com";
    //instagram presenter
    private final InstagramLoginPresenter igPresenter;
    public static final String GOOGLE_SERVICE_CLIENT_ID = "602409589834-6b03kf79u58uel71s3k840mjie1mrhm9.apps.googleusercontent.com";
    public static final String EXTRA_MESSAGE = "com.pps.globant.fittracker.USERID";
    private static final int RC_GET_TOKEN = 9002;
    private final LoginModel model;
    private final LoginView view;
    private GoogleSignInOptions gso;
    //-------------------------------------------------------------------------------------------------------------------------------
    private GoogleSignInClient mGoogleSignInClient;

    public LoginPresenter(LoginModel model, LoginView view, GoogleSignInOptions gso, GoogleSignInClient
            mGoogleSignInClient, InstagramLoginPresenter igPresenter) {
        this.model = model;
        this.view = view;
        this.gso = gso;
        this.mGoogleSignInClient = mGoogleSignInClient;
        this.igPresenter = igPresenter;

    }

    public void clearDatabase() {
        model.clearDatabase();
    }

    public void register() {
        BusProvider.register(this);
    }

    //GOOGLE

    public void unregister() {
        BusProvider.unregister(this);
    }

    @Subscribe
    public void onGoogleSignInButtonPressed(GoogleSignInButtonPressedEvent event) {
        //Starting the intent prompts the user to select a Google account to sign in with.
        //If you request other stuff beyond profile, email, token and openid,
        //the user is also prompted to grant access to the requested resources.
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        Activity activity = view.getActivity();
        if (activity != null)
            activity.startActivityForResult(signInIntent, RC_GET_TOKEN);
    }

    @Subscribe
    public void onGoogleSignOutButtonPressed(GoogleSignOutButtonPressedEvent event) {
        Activity activity = view.getActivity();
        if (activity != null) {
            mGoogleSignInClient.signOut()
                    .addOnCompleteListener(activity, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            successfulGoogleSignOut();
                        }
                    });
        }
    }

    private void successfulGoogleSignIn() {
        //TODO check if the google id is already into the database. If is, complete the login, otherwise save a new User into de Room database with the data provided from google account like facebook login does
        view.hideGoogleSignInButton();
        view.showGoogleSignOutButton();
        view.popUp(R.string.logged_into_google);
    }

    private void successfulGoogleSignOut() {
        model.googleSignOut();
        view.hideGoogleSignOutButton();
        view.showGoogleSignInButton();
        view.popUp(R.string.logged_out_from_google);

    }

    @Subscribe
    public void onGoogleSignIn(GoogleSignInEvent event) {
        //Handles the task of login in. If it's successful, posts in the bus a SuccessfulGoogleSignInEvent
        try {
            GoogleSignInAccount account = event.getCompletedTask().getResult(ApiException.class);
            model.setGoogleAccount(account);
            successfulGoogleSignIn();
        } catch (ApiException e) {
            view.popUp(R.string.google_login_error_message);
        }
    }

    public void setActivityResults(int requestCode, int resultCode, Intent data) {
        //Once the activity started in onGoogleSignInButtonPressed, model.googleSignIn is called.
        //This method is called by MainActivity, that is listening for the activity to get the result.
        if (requestCode == RC_GET_TOKEN) {
            model.googleSignIn(data);
        }
    }

    //FACEBOOK

    private void restoreGoogleState() {
        Activity activity = view.getActivity();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(activity);
        if (account != null) {
            model.setGoogleAccount(account);
            successfulGoogleSignIn();
        }
    }

    @Subscribe
    public void onLogOutFbCompleteEvent(FacebookLoginProvider.LogOutCompleteEvent event) {
        Activity activity = view.getActivity();
        if (activity == null) return;
        Resources res = activity.getResources();
        view.popUp(R.string.logged_out_from_facebook);
        view.setLabelButtonFb(R.string.fb_login_button_msg_Continue_with_fb);
    }

    @Subscribe
    public void onFbButtonPressedEvent(LoginView.FbButtonPressedEvent event) {
        if (!model.isFbLogedIn()) {
            Activity activity = view.getActivity();
            if (activity == null) return;
            model.fbLogIn(activity);
        } else {
            model.fbLogOut();
        }
    }

    public void restoreFbState() {
        model.restoreState();
    }

    @Subscribe
    public void onFbUserDataRecoveredEvent(FacebookLoginProvider.FbUserDataRecoveredEvent event) {
        model.setUser(event.user);
        view.popUp(R.string.logged_into_facebook);
        view.setLabelButtonFb(R.string.fb_login_button_msg_Log_Out);
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

    /***** instagram methods *****/
    public void igButtonLoginClick() {
        view.showLoadingLogin();
        igPresenter.igButtonLoginClick();
    }

    @Subscribe
    public void onInformationReady(InstagramLoginPresenter.InformationReady event) {
        if (event.logeado) {
            view.disableLogin();
            view.showLoginToast(event.name);
        } else {
            view.enableLogin();
        }
        view.setIgTextView(event.name);
    }

    public void isLoggedInInstagram(SharedPreferences spUser) {
        igPresenter.isLoggedIn(spUser);
    }

    public void igButtonLogoutClick(MainActivity activity, SharedPreferences spUser) {
        view.enableLogin();
        view.setIgTextView(activity.getResources().getString(R.string.instagram_not_login));
        view.showLogoutToast();
        CookieSyncManager.createInstance(activity.getApplicationContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        spUser.edit().putString(SP_TOKEN, null).apply();
    }

    /*****end instagram*****/


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

    //LOCAL SIGNUP

    @Subscribe
    public void onSignUpLinkPressedEvent(LoginView.signUpLinkPressedEvent event) {
        Activity activity = view.getActivity();
        if (activity == null) return;
        Intent intent = new Intent(activity, SignInFormActivity.class);
        activity.startActivity(intent);
    }

    //LOCAL LOGIN

    @Subscribe
    public void onManualLogginButtonPressedEvent(LoginView.ManualLogginButtonPressedEvent event) {
        Activity activity = view.getActivity();
        if (activity == null) {
            return;
        }
        Intent intent = new Intent(activity, LoginLocallyActivity.class);
        activity.startActivity(intent);
    }
}
