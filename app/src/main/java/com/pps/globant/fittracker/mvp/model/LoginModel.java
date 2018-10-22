package com.pps.globant.fittracker.mvp.model;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.pps.globant.fittracker.mvp.model.DataBase.User;
import com.pps.globant.fittracker.mvp.model.DataBase.UsersRepository;
import com.pps.globant.fittracker.utils.FacebookLoginProvider;
import com.squareup.otto.Bus;

public class LoginModel {

    private final Bus bus;
    private User activeUser;
    private final FacebookLoginProvider facebookLoginProvider;
    private final UsersRepository usersRepository;

    public LoginModel(FacebookLoginProvider facebookLoginProvider, Bus bus, UsersRepository usersRepository) {
        this.facebookLoginProvider = facebookLoginProvider;
        this.bus = bus;
        this.usersRepository = usersRepository;
    }

    public void googleSignIn(Intent data) {
        //Creates the task to SignIn from the intent, and posts GoogleSignInEvent
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        task.addOnCompleteListener(new OnCompleteListener<GoogleSignInAccount>() {
            @Override
            public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                bus.post(new GoogleSignInEvent(task.getResult()));
            }
        });
    }

    public static class GoogleSignInEvent {
        GoogleSignInAccount account;

        public GoogleSignInEvent(GoogleSignInAccount account) {
            this.account = account;
        }

        public GoogleSignInAccount getAccount() {
            return account;
        }
    }

    public void fbLogIn(Activity activity) {
        facebookLoginProvider.logIn(activity);
    }

    public void clearDatabase() {
        usersRepository.clearDatabase();
    }

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

    public User getUser() {
        return activeUser;
    }

    public void setUser(User user) {
        activeUser = user;
    }
}
