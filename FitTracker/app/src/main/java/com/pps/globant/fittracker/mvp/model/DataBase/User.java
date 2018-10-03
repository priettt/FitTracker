package com.pps.globant.fittracker.mvp.model.DataBase;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity
public class User {

    public User(int id, String email, String firstName, String lastName, Date dateOfBirth) {
        this.name = name;
    }

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String firstName) {
        this.name = firstName;
    }

}