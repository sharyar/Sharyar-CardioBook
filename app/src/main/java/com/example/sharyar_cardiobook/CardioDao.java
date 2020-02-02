package com.example.sharyar_cardiobook;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CardioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
        //Replaces current record if same object added twice
    void insert(CardioRecord cardioRecord);

    @Delete
    void delete(CardioRecord cardioRecord);

    @Update
    void update(CardioRecord cardioRecord);

    @Query("DELETE FROM cardio_record")
    void deleteAll();

    @Query("SELECT * from cardio_record ORDER BY UniqueId ASC")
    LiveData<List<CardioRecord>> getSortedRecordsDESC();

    @Query("SELECT * from cardio_record ORDER BY recordDate ASC")
    LiveData<List<CardioRecord>> getSortedRecordsASC();
}
