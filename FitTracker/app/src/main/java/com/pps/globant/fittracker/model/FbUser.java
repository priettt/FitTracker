package com.pps.globant.fittracker.model;

public class FbUser {
    private final String name;
    private final String email;

    public FbUser(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {

        return name;
    }
}
