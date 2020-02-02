package com.example.sharyar_cardiobook;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {CardioRecord.class}, version = 2, exportSchema = false)
@TypeConverters({DateConverter.class}) //Uses converters. See DateConverter class file for reference
public abstract class CardioRecordRoomDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriterExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static volatile CardioRecordRoomDatabase INSTANCE;
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            databaseWriterExecutor.execute(() -> {
                CardioDao dao = INSTANCE.cardioDao();

            });
        }
    };

    static CardioRecordRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CardioRecordRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CardioRecordRoomDatabase.class, "cardio_database")
                            .addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract CardioDao cardioDao();
}
