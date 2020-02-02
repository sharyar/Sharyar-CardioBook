package com.example.sharyar_cardiobook;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class NewRecordActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";
    CardioRecord editRecord;
    private EditText mDate;
    private EditText mTime;
    private EditText mSystolic;
    private EditText mDiastolic;
    private EditText mHeartRate;
    private EditText mComment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_record);


        /*
        Based on Jenokv.com Tech & Media Labs Tutorial
        Tutorial Title: Parsing & Formatting Dates in Java
        http://tutorials.jenkov.com/java-date-time/parsing-formatting-dates.html
         */
        DateFormat inputDateformatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        DateFormat outputDateFormatter = new SimpleDateFormat("MM/dd/yyyy");
        DateFormat outputTimeFormatter = new SimpleDateFormat("HH:mm");

        mTime = findViewById(R.id.timeRecorded);
        mDate = findViewById(R.id.date);
        mSystolic = findViewById(R.id.systolic);
        mDiastolic = findViewById(R.id.diastolic);
        mHeartRate = findViewById(R.id.heartrate);
        mComment = findViewById(R.id.comment);

        Button submitButton = findViewById(R.id.submit);
        Button setTimeButton = findViewById(R.id.setTime);
        Button setDateButton = findViewById(R.id.setDate);
        Intent parentIntent = getIntent();

        editRecord = (CardioRecord) parentIntent.getSerializableExtra("KEY1");


        //Set the time button to display the dialog for entering time.
        setTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "TimePicker");
            }
        });


        //Also opens the date picker dialog if user double clicks the time EditText field twice
        mTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "TimePicker");
            }
        });

        setDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "DatePicker");
            }
        });

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "DatePicker");
            }
        });

        /*
        This if statement checks if the editRecord is null. This is the case when a user clicks
        the add record button. It ensures that when the button at the end of the form is pressed,
        the record is added or updated correctly.
         */

        if (editRecord == null) {
            NewRecordActivity.this.setTitle("Add New Record");
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDate.getText().toString().isEmpty() || mSystolic.getText().toString().isEmpty()
                            || mDiastolic.getText().toString().isEmpty() || mHeartRate.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "All fields except comment are " +
                                "required!", Toast.LENGTH_LONG).show();
                    } else {
                        try {
                            CardioRecord cardioRecord = new CardioRecord(
                                    inputDateformatter.parse((mDate.getText().toString()) + " "
                                            + mTime.getText().toString()),
                                    Integer.parseInt(mSystolic.getText().toString()),
                                    Integer.parseInt(mDiastolic.getText().toString()),
                                    Integer.parseInt(mHeartRate.getText().toString()),
                                    mComment.getText().toString());

                            Intent replyIntent = new Intent();
                            replyIntent.putExtra("KEY", cardioRecord);
                            setResult(RESULT_OK, replyIntent);
                            finish();

                        } catch (ParseException e) {
                        }
                    }

                }
            });

        } else {

            NewRecordActivity.this.setTitle("Edit Record");

            //Set text fields with current values of record to be edited.
            mSystolic.setText(String.valueOf(editRecord.getSystolic()));
            mDiastolic.setText(String.valueOf(editRecord.getDiastolic()));
            mHeartRate.setText(String.valueOf(editRecord.getHeartRate()));
            mComment.setText(editRecord.getComment());
            mTime.setText(outputTimeFormatter.format(editRecord.getRecordDate()));
            mDate.setText(outputDateFormatter.format(editRecord.getRecordDate()));

            //Update text of button to "Update Record"
            submitButton.setText("Update Record");

            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //This if statement ensures that all the required information is provided in the form
                    if (mDate.getText().toString().isEmpty() || mSystolic.getText().toString().isEmpty()
                            || mDiastolic.getText().toString().isEmpty() ||
                            mHeartRate.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "All fields except comment are " +
                                "required!", Toast.LENGTH_LONG).show();
                    } else {
                        //Use try and catch statements for parsing of data.
                        try {
                            /*
                            The datetime string is used to combine the info from the time & date
                            fields
                             */
                            String dateTime = mDate.getText().toString() + " " + mTime.getText().toString();
                            editRecord.setRecordDate(inputDateformatter.parse(dateTime));
                            editRecord.setSystolic(Integer.parseInt(mSystolic.getText().toString()));
                            editRecord.setDiastolic(Integer.parseInt(mDiastolic.getText().toString()));
                            editRecord.setHeartRate(Integer.parseInt(mHeartRate.getText().toString()));
                            editRecord.setComment(mComment.getText().toString());

                            Intent replyIntent = new Intent();
                            replyIntent.putExtra("KEY2", editRecord);
                            setResult(RESULT_OK, replyIntent);
                            finish();

                        } catch (ParseException e) {
                            Toast.makeText(getApplicationContext(), "Ensure that you have" +
                                            "added the date & time in the correct format."
                                    , Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });

        }
    }
}
