package com.example.sharyar_cardiobook;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    // Declare the variables required for use later
    /*
    Activity request codes are used to direct edit vs new record addition. This way, I don't have
    to create two different activities for updating vs editing current records.
     */
    public static final int NEW_CARDIORECORD_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_CARDIORECORD_ACTIVITY_REQUEST_CODE = 2;

    // This variable is used to keep a track of currently selected record within the app.
    int selectedCardioRecordIdx;

    private CardioRecordModel mCardioModel;


    /**
     * This method is used to review the result from the NewRecordActivity. It checks what
     * requestCode was used to initialize the activity. Based on that, it adds a new record to the
     * database or updates a current record. It also checks if the resultCode is OK or Not. In case
     * it is not, the activity just cancels and does not do anything to the database.
     * It uses a getSerializable method to get the new CardioRecord/updated CardioRecord from the
     * other intent.
     *
     * @param requestCode Used to figure out if CardioRecord needs to be added or updated.
     * @param resultCode  Used to ensure that activity completed correctly.
     * @param data        CardioRecord (updated or new)
     */
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

    /**
     * This method is executed upon creation of this activity.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //All of this starts and sets up the adapter to connect with the views and ViewModel.
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final CardioListAdapter adapter = new CardioListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCardioModel = new ViewModelProvider(this).get(CardioRecordModel.class);

        /*
        The edit & delete record buttons are hidden by default. They only become available once a
        user selects a record.
         */
        FloatingActionButton editRecord = findViewById(R.id.edit_fab);
        editRecord.hide();
        FloatingActionButton deleteRecord = findViewById(R.id.deleteFab);
        deleteRecord.hide();

        /*
        We setup an observer here to ensure our adapter is notified when our model is updated with
        new data.
         */
        mCardioModel.getmAllCardioRecords().observe(this, new Observer<List<CardioRecord>>() {
            @Override
            public void onChanged(List<CardioRecord> cardioRecords) {
                adapter.setCardioRecords(cardioRecords);
            }
        });

        /*
        Add record button starts a NewRecordActivity. Once the activity completes, it adds a
        new record.
         */
        FloatingActionButton addRecord = findViewById(R.id.fab);
        addRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewRecordActivity.class);
                startActivityForResult(intent, NEW_CARDIORECORD_ACTIVITY_REQUEST_CODE);
            }
        });

        /*
        Edit Record button starts a NewRecordActivity but starts it with a UPDATE request code. This
        ensures that the a currently selected cardioRecord is edited. This listener also passes
        the selected CardioRecord to the new intent so it can be updated or edited.
         */
        editRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewRecordActivity.class);
                intent.putExtra("KEY1", adapter.getCardioRecordAtPosition(selectedCardioRecordIdx));
                startActivityForResult(intent, UPDATE_CARDIORECORD_ACTIVITY_REQUEST_CODE);
            }
        });

        //Sets the delete button so it can delete the selected record.
        deleteRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCardioModel.delete(adapter.getCardioRecordAtPosition(selectedCardioRecordIdx));
            }
        });

        /*
        Uses ItemClickSupport class. Please see class file for references.
        Allows user to select a record. It also un-hides the edit and delete button.
         */
        ItemClickSupport.addTo(recyclerView)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        selectedCardioRecordIdx = position;
                        for (int j = 0; j < recyclerView.getChildCount(); j++)
                            recyclerView.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);
                        v.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        editRecord.show();
                        deleteRecord.show();
                    }
                });
    }
}
