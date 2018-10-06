package com.pps.globant.fittracker.mvp.model;

import com.pps.globant.fittracker.mvp.model.DataBase.User;
import com.pps.globant.fittracker.mvp.model.DataBase.UsersRepository;
import com.squareup.otto.Bus;

public class SignUpModel {
    private Bus bus;
    private User activeUser;
    private UsersRepository usersRepository;

    public SignUpModel(Bus bus, UsersRepository usersRepository) {
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

    public void getUserFromDbById(int id) {
        usersRepository.getById(id);
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

    public boolean validateFirstName(String firstName) {
        return (firstName.isEmpty() || firstName.length() < 3);
    }

    public boolean validateLastName(String lastName) {
        return (lastName.isEmpty() || lastName.length() < 3);
    }

    public boolean validateEmail(String email) {
        return (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public boolean validateUserName(String username) {
        return (username.isEmpty() || username.length() < 8);
    }

    public boolean validatePassword(String password) {
        return (password.isEmpty() || password.length() < 4 || password.length() > 10);
    }

    public boolean validateReEnterPassword(String password, String reEnterPassword) {
        return (reEnterPassword.isEmpty() || reEnterPassword.equals(password));
    }

    public boolean validateBirthday(String birthday) {
        return (birthday.isEmpty());
    }
}
