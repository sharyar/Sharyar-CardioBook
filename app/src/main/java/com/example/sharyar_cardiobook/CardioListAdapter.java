package com.example.sharyar_cardiobook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardioListAdapter extends RecyclerView.Adapter<CardioListAdapter.CardioViewHolder> {


    private final LayoutInflater mInflater;
    private List<CardioRecord> mCardioRecords;

    CardioListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public CardioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new CardioViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CardioViewHolder holder, int position) {
        if (mCardioRecords != null) {
            CardioRecord current = mCardioRecords.get(position);
            holder.cardioItemView.setText(current.toString());
        } else {
            holder.cardioItemView.setText("No records");
        }
    }

    void setCardioRecords(List<CardioRecord> records) {
        mCardioRecords = records;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mCardioRecords != null)
            return mCardioRecords.size();
        else return 0;
    }

    class CardioViewHolder extends RecyclerView.ViewHolder {
        private final TextView cardioItemView;

        private CardioViewHolder(View itemView) {
            super(itemView);
            cardioItemView = itemView.findViewById(R.id.textView);
        }
    }


}


