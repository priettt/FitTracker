package com.pps.globant.fittracker.mvp.model.DataBase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


@Database(entities = {User.class}, version = 1,exportSchema = false)
public abstract class UserRoomDataBase extends RoomDatabase {
    private static final String DB_NAME="base de datos room";

    public abstract UserDao userDao();
    private static UserRoomDataBase INSTANCE;

    public static UserRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserRoomDataBase.class, DB_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}