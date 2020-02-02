//Source: Google/Developer.Android
//Title: Referencing complex data using room
//URL:https://developer.android.com/training/data-storage/room/referencing-data

package com.example.sharyar_cardiobook;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}