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

/**
 * This abstract class defines the database. It also sets up multithreading to ensure that any
 * database tasks such as updating records, adding records or querying is not done on the UI
 * thread. This ensures responsiveness of the app.
 */
@Database(entities = {CardioRecord.class}, version = 2, exportSchema = false)
@TypeConverters({DateConverter.class}) //Uses converters. See DateConverter class file for reference
public abstract class CardioRecordRoomDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriterExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    /*
    Declaring the database volatile ensures that changes to the database are merged together when
    there is activity across multiple threads.
     */
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

    /*
    This ensures that there is only one copy of Database within the application. It defines the
    database as a singleton.
     */
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
