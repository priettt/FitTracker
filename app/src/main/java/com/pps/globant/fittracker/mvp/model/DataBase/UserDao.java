package com.pps.globant.fittracker.mvp.model.DataBase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM User WHERE socialNetworkId LIKE :socialNetworkId LIMIT 1")
    List<User> findBySocialNetworkId(String socialNetworkId);

    @Query("SELECT * FROM User WHERE id LIKE :id ")
    List<User> findById(long id);

    @Query("SELECT * FROM User WHERE username LIKE :username ")
    List<User> findByUsername(String username);

    @Query("SELECT * FROM User WHERE username LIKE :username AND password LIKE :password")
    List<User> findByUsernameAndPassword(String username, String password);

    @Insert
    long insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

}