package com.cpssd.organizr.adapters;

import android.database.DataSetObserver;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.cpssd.organizr.R;
import com.cpssd.organizr.pojos.Events.Lecture;


import java.util.ArrayList;


/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class LectureAdapter extends RecyclerView.Adapter<LectureAdapter.ViewHolder> implements ListAdapter {
    private static final String TAG = "LectureAdapter";

    private ArrayList<Lecture> mDataSet;

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int i) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }


    public LectureAdapter(ArrayList<Lecture> dataSet) {
        mDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.lecture_item, viewGroup, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");
        Lecture lecture = mDataSet.get(position);
        viewHolder.getLecture_title().setText(lecture.getSummary().substring(0,5));
        viewHolder.getLecture_desc().setText(lecture.getDescription());
        viewHolder.getLecture_loc().setText(lecture.getLocation());
        viewHolder.getBackground().setBackgroundColor(Color.parseColor(lecture.getColour()));
        viewHolder.getLecture_time().setText(lecture.getStart());

    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void remove(int position) {
        mDataSet.remove(position);
        notifyItemRemoved(position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView lecture_title;
        private final TextView lecture_desc;
        private final TextView lecture_loc;
        private final TextView lecture_time;
        private final FrameLayout background;


        ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });

            lecture_title = (TextView) v.findViewById(R.id.lecture_title);
            lecture_desc = (TextView) v.findViewById(R.id.lecture_description);
            lecture_loc = (TextView) v.findViewById(R.id.lecture_location);
            lecture_time = (TextView) v.findViewById(R.id.lecture_time);
            background = (FrameLayout) v.findViewById(R.id.background);
        }
        TextView getLecture_title() {
            return lecture_title;
        }

        TextView getLecture_desc() {
            return lecture_desc;
        }

        TextView getLecture_loc() {
            return lecture_loc;
        }

        TextView getLecture_time() { return  lecture_time; }

        FrameLayout getBackground() { return  background; }
    }
}