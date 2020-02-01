package com.example.sharyar_cardiobook;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    //Declare the variables required for use later

    public static final int NEW_CARDIORECORD_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_CARDIORECORD_ACTIVITY_REQUEST_CODE = 2;
    ListView cardioList;
    ArrayAdapter<CardioRecord> cardioRecordArrayAdapter;
    ArrayList<CardioRecord> cardioRecordArrayList;
    int selectedCardioRecordIdx;
    private CardioRecordModel mCardioModel;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_CARDIORECORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            CardioRecord record = (CardioRecord) data.getExtras().getSerializable("KEY");
            mCardioModel.insert(record);
        } else if (requestCode == UPDATE_CARDIORECORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            CardioRecord record = (CardioRecord) data.getExtras().getSerializable("KEY2");
            mCardioModel.update(record);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();


        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final CardioListAdapter adapter = new CardioListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCardioModel = new ViewModelProvider(this).get(CardioRecordModel.class);

        mCardioModel.getmAllCardioRecords().observe(this, new Observer<List<CardioRecord>>() {
            @Override
            public void onChanged(List<CardioRecord> cardioRecords) {
                adapter.setCardioRecords(cardioRecords);
            }
        });

        FloatingActionButton addRecord = findViewById(R.id.fab);
        addRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewRecordActivity.class);
                startActivityForResult(intent, NEW_CARDIORECORD_ACTIVITY_REQUEST_CODE);
            }
        });

        FloatingActionButton editRecord = findViewById(R.id.edit_fab);
        editRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditRecordActivity.class);
                intent.putExtra("KEY1", adapter.getCardioRecordAtPosition(selectedCardioRecordIdx));
                startActivityForResult(intent, UPDATE_CARDIORECORD_ACTIVITY_REQUEST_CODE);
            }
        });

        ItemClickSupport.addTo(recyclerView)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        selectedCardioRecordIdx = position;
                        for (int j = 0; j < recyclerView.getChildCount(); j++)
                            recyclerView.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);
                        v.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    }
                });

        FloatingActionButton deleteRecord = findViewById(R.id.deleteFab);
        deleteRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCardioModel.delete(adapter.getCardioRecordAtPosition(selectedCardioRecordIdx));
            }
        });


    }
}
