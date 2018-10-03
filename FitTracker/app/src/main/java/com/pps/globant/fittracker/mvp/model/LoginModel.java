package com.pps.globant.fittracker.mvp.model;

import android.app.Activity;

import com.pps.globant.fittracker.mvp.model.DataBase.User;
import com.pps.globant.fittracker.mvp.model.DataBase.UsersRepository;
import com.pps.globant.fittracker.utils.FacebookLoginProvider;

public class LoginModel {
    private FacebookLoginProvider facebookLoginProvider;
    private User activeUser;
    private UsersRepository usersRepository;

    public LoginModel(FacebookLoginProvider facebookLoginProvider, UsersRepository usersRepository) {
        this.facebookLoginProvider = facebookLoginProvider;
        this.usersRepository = usersRepository;
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

    public User getUser() {
        return activeUser;
    }

    public void restoreState() {
        facebookLoginProvider.restoreState();
    }

    public void setUser(User user) {
        activeUser=user;
    }

    public void getUserFromDB() {
        usersRepository.get(activeUser.getName());
    }

    public void insertUserToDB() {
        usersRepository.insert(activeUser);
    }

    public void deleteUser() {
        usersRepository.delete(activeUser);
    }
}
