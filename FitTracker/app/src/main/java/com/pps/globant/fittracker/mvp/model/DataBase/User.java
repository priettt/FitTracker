package com.pps.globant.fittracker.mvp.model.DataBase;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = User.TABLE_NAME)
public class User {
    private static final String NAME = "name";
    static final String TABLE_NAME = "user";

    public User(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = NAME)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String firstName) {
        this.name = firstName;
    }

}