package com.example.sharyar_cardiobook;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CardioRepository {
    private CardioDao mCardioDao;
    private LiveData<List<CardioRecord>> mAllCardioRecordsDESC;
    private LiveData<List<CardioRecord>> mAllCardioRecordsASC;


    CardioRepository(Application application) {
        CardioRecordRoomDatabase db = CardioRecordRoomDatabase.getDatabase(application);
        mCardioDao = db.cardioDao();
        mAllCardioRecordsDESC = mCardioDao.getSortedRecordsDESC();
        mAllCardioRecordsASC = mCardioDao.getSortedRecordsASC();
    }

    LiveData<List<CardioRecord>> getmAllCardioRecordsDESC() {
        return mAllCardioRecordsDESC;
    }

    LiveData<List<CardioRecord>> getmAllCardRecordsASC() {
        return mAllCardioRecordsASC;
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
