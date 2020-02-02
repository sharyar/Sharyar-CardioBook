package com.example.sharyar_cardiobook;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;


/**
 * This Adapter interfaces between the repository and mainActivity. It controls most of the
 * UI seen on the landing page of the app.
 */
public class CardioListAdapter extends RecyclerView.Adapter<CardioListAdapter.CardioViewHolder> {

    //Declare variables required for the app to run
    private final LayoutInflater mInflater; //This layout inflater is used to inflate the recylerView_item layout
    private List<CardioRecord> mCardioRecords; //this array list is use to populate the recyclerView

    /**
     * Constructor for the class. Relies on context passed as a parameter to the method.
     */
    CardioListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    /**
     * Defines the CardioViewHolder method, onCreateViewholder. The method inflates the recylerview_item k
     * view defined via xml and sets it as itemView. This view is then used as a parameter to create the
     * caredioViewHolder which is called upon implicitly by the application.
     */
    @Override
    public CardioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new CardioViewHolder(itemView);
    }

    /**
     * The onBindViewHolder method gets called by bindViewHolder. It takes the array list of our
     * records and display their string representation in the itemview within the holder that was
     * passed as a parameter to the method. In this case, it is the cardioItemView which is linked
     * to the textview within RecyclerView_item layout. We also use some conditional logic
     * here to mark any records that are outside the normal range of systolic and diastolic.
     */
    DateFormat outputDateFormatter = new SimpleDateFormat("MMM d, YYYY");

    @Override
    public void onBindViewHolder(CardioViewHolder holder, int position) {
        if (mCardioRecords != null) {
            CardioRecord current = mCardioRecords.get(position);
            holder.cardioItemView.setText(current.toString());
            holder.cardioItemView2.setText(outputDateFormatter.format(current.getRecordDate()));

            if (current.getSystolic() < 90 || current.getSystolic() > 140 ||
                    current.getDiastolic() < 60 || current.getDiastolic() > 90) {
                holder.cardioBackView.setBackgroundColor(Color.RED);
            }
            else {
                holder.cardioBackView.setBackgroundColor(Color.TRANSPARENT);

            }
        } else {
            holder.cardioItemView.setText("No records");
            holder.cardioItemView2.setText("");
        }
    }


    /**
     * This method is a setter for mCardioRecords arrayList. It also notifies views that
     * the data set has been changed each time this method is called. This ensures that we have the
     * correct data being displayed if the source data list is changed.
     */
    void setCardioRecords(List<CardioRecord> records) {
        mCardioRecords = records;
        notifyDataSetChanged();
    }

    /**
     * This method overrides the getItemCount method to ensure that a null value is not returned
     * if mCardioRecords has not been assigned and the getItemCount() method is called.
     *
     * @return 0 or size of arrayList mCardioRecords.
     */
    @Override
    public int getItemCount() {
        if (mCardioRecords != null)
            return mCardioRecords.size();
        else return 0;
    }

    /**
     * This method returns the cardioRecord at the position provided as the parameter.
     * This is used in the edit & delete record listeners to pass the correct CardioRecord
     * object to the activities associated with the listeners.
     *
     * @param position index of the record in ArrayList mCardioRecords
     * @return CardioRecord at index: position.
     */
    public CardioRecord getCardioRecordAtPosition(int position) {
        return mCardioRecords.get(position);
    }

    /**
     * Defines a subclass of RecylerView.Viewholer. The new class(CardioViewHolder) has a TextView
     * object called cardioItemView which is used later to display the cardioRecords.toString()
     * representations.
     */
    class CardioViewHolder extends RecyclerView.ViewHolder {
        private final TextView cardioItemView;
        private final TextView cardioItemView2;
        private final LinearLayout cardioBackView;

        /**
         * Defines constructor for CardioViewHolder. The constructor calls the superclass constructor
         * and sets the cardioItemView as textView in the layout. This allows us to link the layout
         * with variable.
         */

        private CardioViewHolder(View itemView) {
            super(itemView);
            cardioItemView = itemView.findViewById(R.id.textView);
            cardioItemView2 = itemView.findViewById(R.id.textView2);
            cardioBackView = itemView.findViewById(R.id.linearContainerCardioView);
        }


    }


}


