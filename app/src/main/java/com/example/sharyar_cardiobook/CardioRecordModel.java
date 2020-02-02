package com.example.sharyar_cardiobook;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * The CardioRecord model is a ViewModel class that caches and holds the data required by the UI.
 * It communicates with controller(s)/view(s) and the Repository. It persists beyond Activity/Fragment
 * creation/destruction.
 */

public class CardioRecordModel extends AndroidViewModel {

    // Create a repository
    private CardioRepository mRepository;

    /*
    Live data wrapped list of CardioRecords. The live data wrapper allows the views to observe
    changes in the CardioRecord database and update themselves upon data change.
     */
    private LiveData<List<CardioRecord>> mAllCardioRecords;

    /**
     * Constructor for CardioRecordModel. It uses the application context to persist beyond the
     * fragments and activities that rely on it. It also uses the application
     *
     * @param application
     */
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
