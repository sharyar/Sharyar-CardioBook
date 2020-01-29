package com.example.sharyar_cardiobook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class NewRecordActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    private EditText mDate;
    private EditText mSystolic;
    private EditText mDiastolic;
    private EditText mHeartRate;
    private EditText mComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_record);
        mDate = findViewById(R.id.date);
        mSystolic = findViewById(R.id.systolic);
        mDiastolic = findViewById(R.id.diastolic);
        mHeartRate = findViewById(R.id.heartrate);
        mComment = findViewById(R.id.comment);

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    CardioRecord cardioRecord = new CardioRecord(
                            formatter.parse(mDate.getText().toString()),
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
        });
    }
}
