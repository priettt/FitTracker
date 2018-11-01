package com.pps.globant.fittracker.mvp.model;

import android.text.TextUtils;

import com.pps.globant.fittracker.mvp.model.DataBase.User;
import com.pps.globant.fittracker.mvp.model.DataBase.UsersRepository;
import com.squareup.otto.Bus;

public class LoginLocallyModel {

    private static final boolean FALSE = false;
    private final Bus bus;
    private final UsersRepository usersRepository;
    private User activeUser;

    public LoginLocallyModel(Bus bus, UsersRepository usersRepository) {
        this.bus = bus;
        this.usersRepository = usersRepository;
    }

    public User getUser() {
        return activeUser;
    }

    //ROOM DATABASE

    public void setUser(User user) {
        activeUser = user;
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

    public void getUserFromDbByUsername(String username) {
        usersRepository.getByUsername(username);
    }

    public void getUserFromDbByUsernameAndPassword(String username, String password) {
        usersRepository.getByUsernameAndPassword(username, password);
    }

    public boolean validateUserName(String username) {
        return (!TextUtils.isEmpty(username) && !(username.length() < 8));
    }

    public boolean validatePassword(String password) {
        return (!TextUtils.isEmpty(password) && !(password.length() < 4) && !(password.length() > 10));
    }

}
