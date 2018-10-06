package com.pps.globant.fittracker.mvp.model.DataBase.dbUtils;

import android.arch.persistence.room.TypeConverter;


import com.pps.globant.fittracker.model.avatars.Thumbnail;

import java.util.Date;

public class Converters {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }


    @TypeConverter
    public static Thumbnail stringToThumbail(String string) {
        if (string == null) return null;
        Thumbnail thumbnail = new Thumbnail();
        String[] thumbnailStirng = string.split("===");
        thumbnail.setPath(thumbnailStirng[0]);
        thumbnail.setExtension(thumbnailStirng[1]);
        return thumbnail;
    }

    @TypeConverter
    public static String ThumbnailTostring(Thumbnail thumbnail) {
        return thumbnail == null ? null : String.format("%1$s===%2$s", thumbnail.getPath(), thumbnail.getExtension());
    }

}