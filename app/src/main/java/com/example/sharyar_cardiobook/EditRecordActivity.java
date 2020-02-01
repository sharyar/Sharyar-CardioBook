package com.example.sharyar_cardiobook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EditRecordActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";
    CardioRecord editRecord;
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
        Intent parentIntent = getIntent();
        CardioRecord recordToEdit = (CardioRecord) parentIntent.getSerializableExtra("KEY1");

        Button button = findViewById(R.id.button);

        //Set text fields with current values of record to be edited.
        mSystolic.setText(String.valueOf(recordToEdit.getSystolic()));
        mDiastolic.setText(String.valueOf(recordToEdit.getDiastolic()));
        mHeartRate.setText(String.valueOf(recordToEdit.getHeartRate()));
        mComment.setText(recordToEdit.getComment());

        //Update text of button to "Update Record"
        button.setText("Update Record");


        //Set button action
        button.setOnClickListener(new View.OnClickListener() {
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
                        editRecord.setRecordDate(formatter.parse(mDate.getText().toString()));
                        editRecord.setSystolic(Integer.parseInt(mSystolic.getText().toString()));
                        editRecord.setDiastolic(Integer.parseInt(mDiastolic.getText().toString()));
                        editRecord.setHeartRate(Integer.parseInt(mHeartRate.getText().toString()));
                        editRecord.setComment(mComment.getText().toString());

                        Intent replyIntent = new Intent();
                        replyIntent.putExtra("KEY2", editRecord);
                        setResult(RESULT_OK, replyIntent);
                        finish();

                    } catch (ParseException e) {
                    }
                }
            }
        });
    }

}
