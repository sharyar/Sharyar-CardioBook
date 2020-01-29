package com.example.sharyar_cardiobook;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CardioRecordModel extends AndroidViewModel {

    private CardioRepository mRepository;

    private LiveData<List<CardioRecord>> mAllCardioRecords;

    public CardioRecordModel(Application application) {
        super(application);
        mRepository = new CardioRepository(application);
        mAllCardioRecords = mRepository.getmAllCardioRecordsDESC();
    }

    LiveData<List<CardioRecord>> getmAllCardioRecords() {
        return mAllCardioRecords;
    }

    public void insert(CardioRecord record) {
        mRepository.insert(record);
    }

    public void delete(CardioRecord record) {
        mRepository.delete(record);
    }

    public void update(CardioRecord record) {
        mRepository.update(record);
    }

}
