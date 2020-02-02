package com.example.sharyar_cardiobook;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * The DAO or Data Access Object is a mapping of SQL queries to functions so we can access our
 * Database programtically without needing a SQL helper. It communicates with SQLite database.
 */

@Dao
public interface CardioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
        // Replaces current record if same object added twice
    void insert(CardioRecord cardioRecord);

    @Delete
    void delete(CardioRecord cardioRecord);

    @Update
    void update(CardioRecord cardioRecord);

    @Query("SELECT * from cardio_record ORDER BY UniqueId ASC")
    LiveData<List<CardioRecord>> getSortedRecordsDESC();

    // Built this extra query to switch sorting method. Currently not implemented in the app.
    @Query("SELECT * from cardio_record ORDER BY recordDate ASC")
    LiveData<List<CardioRecord>> getSortedRecordsASC();
}
