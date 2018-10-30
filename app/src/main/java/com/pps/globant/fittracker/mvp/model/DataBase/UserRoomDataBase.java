package com.pps.globant.fittracker.mvp.model.DataBase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.pps.globant.fittracker.mvp.model.DataBase.dbUtils.Converters;

@Database(entities = {User.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})

public abstract class UserRoomDataBase extends RoomDatabase {

    private static final String DB_NAME = "Base de datos room";
    private static UserRoomDataBase INSTANCE;

    public static UserRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {

            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    UserRoomDataBase.class, DB_NAME)
                    .build();
        }
        return INSTANCE;
    }

    //for debuging purposes only
    public static void emptyDatabase() {
        if (INSTANCE == null) {
            return;
        }
        INSTANCE.clearAllTables();
    }

    public abstract UserDao userDao();

}