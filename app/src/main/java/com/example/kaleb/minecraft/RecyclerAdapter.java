package com.example.kaleb.minecraft;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kaleb on 12/18/2016.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MessageViewHolder> {
    private ArrayList<String> mDataset;

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public MessageViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.textView);
            Typeface tf = Typeface.createFromAsset(v.getContext().getAssets(), "fonts/font-mine.ttf");
            mTextView.setTypeface(tf);
            mTextView.setTextSize((float)18.00);
        }
    }

    public RecyclerAdapter(ArrayList<String> myDataset) {
        this.mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerAdapter.MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.messages, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return  new MessageViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset.get(position));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void updateSocketInfo(String message) {
        mDataset.add(message);
        notifyDataSetChanged();
    }
}
