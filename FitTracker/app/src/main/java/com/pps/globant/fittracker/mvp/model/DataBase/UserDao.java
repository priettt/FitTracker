package com.pps.globant.fittracker.mvp.model.DataBase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM User WHERE name LIKE :name LIMIT 1")
        List<User> findByName(String name);

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);
}