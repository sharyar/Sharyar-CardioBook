package com.example.sharyar_cardiobook;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Defined the Repository used by the application. The repository acts as the single unified source
 * of data for activities/controllers. In a completely developed app, the data maybe served to the
 * views via multiple sources and databases. The repository would ensure that all of that is
 * unified under one umbrella and make it easier to program the controllers and views.
 */
public class CardioRepository {
    private CardioDao mCardioDao;
    private LiveData<List<CardioRecord>> mAllCardioRecordsDESC;

    CardioRepository(Application application) {
        CardioRecordRoomDatabase db = CardioRecordRoomDatabase.getDatabase(application);
        mCardioDao = db.cardioDao();
        mAllCardioRecordsDESC = mCardioDao.getSortedRecordsDESC();
    }

    LiveData<List<CardioRecord>> getmAllCardioRecordsDESC() {
        return mAllCardioRecordsDESC;
    }

    void insert(CardioRecord record) {
        CardioRecordRoomDatabase.databaseWriterExecutor.execute(() -> {
            mCardioDao.insert(record);
        });
    }

    void delete(CardioRecord record) {
        CardioRecordRoomDatabase.databaseWriterExecutor.execute(() -> {
            mCardioDao.delete(record);
        });
    }

    void update(CardioRecord record) {
        CardioRecordRoomDatabase.databaseWriterExecutor.execute(() -> {
            mCardioDao.update(record);
        });
    }


}
