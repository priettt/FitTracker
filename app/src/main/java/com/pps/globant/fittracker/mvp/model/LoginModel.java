package com.pps.globant.fittracker.mvp.model;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.pps.globant.fittracker.mvp.model.DataBase.User;
import com.pps.globant.fittracker.mvp.model.DataBase.UsersRepository;
import com.pps.globant.fittracker.utils.FacebookLoginProvider;
import com.squareup.otto.Bus;

public class LoginModel {

    private final Bus bus;
    private User activeUser;
    private GoogleSignInAccount account; //Contains all the information of the account.
    private final FacebookLoginProvider facebookLoginProvider;
    private final UsersRepository usersRepository;

    //GOOGLE

    public LoginModel(FacebookLoginProvider facebookLoginProvider, Bus bus, UsersRepository usersRepository) {
        this.facebookLoginProvider = facebookLoginProvider;
        this.bus = bus;
        this.usersRepository = usersRepository;
    }

    public User getUser() {
        return activeUser;
    }

    public void setUser(User user) {
        activeUser = user;
    }

    public void googleSignIn(Intent data) {
        //Creates the task to SignIn from the intent, and posts GoogleSignInEvent
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        bus.post(new GoogleSignInEvent(task));

    }

    public void googleSignOut() {
        account = null;
    }

    public String getGoogleMail() {
        return account.getEmail();
    }

    //GOOGLE EVENTS'S CLASSES

    public void setGoogleAccount(GoogleSignInAccount account) {
        this.account = account;
    }

    //FACEBOOK

    public void clearDatabase() {
        usersRepository.clearDatabase();
    }

    public void fbLogOut() {
        facebookLoginProvider.logOut();
    }

    public void fbLogIn(Activity activity) {
        facebookLoginProvider.logIn(activity);
    }

    public boolean isFbLogedIn() {
        return facebookLoginProvider.isLoginTokenActive();
    }

    public void restoreState() {
        facebookLoginProvider.restoreState();
    }

    //ROOM DATABASE

    public void getUserFromDbById() {
        usersRepository.getById(activeUser.getId());
    }

    public void getUserFromDbBySocialNetworkId() {
        usersRepository.getBySocialNetworkId(activeUser.getSocialNetworkId());
    }

    public void updateUserToDB() {
        usersRepository.update(activeUser);
    }

    public void insertUserToDB() {
        usersRepository.insert(activeUser);
    }

    public void deleteUserFromDb() {
        usersRepository.delete(activeUser);
    }

    public static class GoogleSignInEvent {
        @NonNull
        Task<GoogleSignInAccount> completedTask;

        public GoogleSignInEvent(@NonNull Task<GoogleSignInAccount> completedTask) {
            this.completedTask = completedTask;
        }

        @NonNull
        public Task<GoogleSignInAccount> getCompletedTask() {
            return completedTask;
        }
    }

}
