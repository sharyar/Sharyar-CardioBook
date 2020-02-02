package com.example.sharyar_cardiobook;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;


/*
Please note that most of the database system used in this program is based upon information from:

Author: Codelabs by Google
Title: Android Room with a View - Java
Url: https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#0

There are some parts of the application where I also used the official reference documentation for
Anrdoid from their main site: https://developer.android.com/docs to understand the funcationality
as well as implement functionality that I needed.
 */

@Entity(tableName = "cardio_record")
public class CardioRecord implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "UniqueId")
    private int id = 0;

    // Declare variables required


    private Date recordDate;
    private int systolic;
    private int diastolic;
    private int heartRate;
    private String comment;


    //getter and setter methods

    public CardioRecord(Date date, int systolic, int diastolic, int heartRate, String comment) {
        this.recordDate = date;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.heartRate = heartRate;
        this.comment = comment;
    }

    public CardioRecord() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getRecordDate() {
        return this.recordDate;
    }

    public void setRecordDate(Date date) {
        this.recordDate = date;
    }

    public int getSystolic() {
        return this.systolic;
    }

    public void setSystolic(int systolic) {
        this.systolic = systolic;
    }

    public int getDiastolic() {
        return this.diastolic;
    }

    public void setDiastolic(int diastolic) {
        this.diastolic = diastolic;
    }

    public int getHeartRate() {
        return this.heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }


    //Default constructor

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    @Override
    @NonNull
    public String toString() {
        String stringRepresentation;
        stringRepresentation = String.format("Systolic: %d - Diastolic: %d - " +
                        "Heart Rate: %d - Comment: %s", systolic, diastolic,
                heartRate, comment);

        return stringRepresentation;
    }

}
