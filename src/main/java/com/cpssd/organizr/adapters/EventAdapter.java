package com.cpssd.organizr.adapters;

import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.cpssd.organizr.R;
import com.cpssd.organizr.pojos.Events.Event;

import java.util.ArrayList;


/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> implements ListAdapter {
    private static final String TAG = "CustomAdapter";

    private ArrayList<Event> mDataSet;

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


    public EventAdapter(ArrayList<Event> dataSet) {
        mDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.event_item, viewGroup, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");
        Event event = mDataSet.get(position);

        if(event.getSummary() != null) {
            viewHolder.getEvent_title().setText((event.getSummary().substring(0, 1).toUpperCase()) + (event.getSummary().substring(1)));
        }
        else{
            viewHolder.getEvent_title().setText("No title set.");

        }
        if(event.getDescription() != null) {
            viewHolder.getEvent_desc().setText((event.getDescription().substring(0, 1).toUpperCase()) + (event.getDescription().substring(1)));
        }
        else{
            viewHolder.getEvent_desc().setText("No description set.");
        }
        viewHolder.getEvent_loc().setText(event.getLocation());
        viewHolder.getEvent_date().setText(event.getStart().getDateTime().substring(0,10));
        viewHolder.getEvent_time().setText(event.getStart().getDateTime().substring(11, 16));

    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void remove(int position) {
        if(!(mDataSet.isEmpty())){
            mDataSet.remove(position);
            notifyItemRemoved(position);
        }
        else{
            notifyItemRemoved(position);
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView event_title;
        private final TextView event_desc;
        private final TextView event_loc;
        private final TextView event_date;
        private final TextView event_time;


        ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
            event_title = (TextView) v.findViewById(R.id.event_title);
            event_desc = (TextView) v.findViewById(R.id.event_description);
            event_loc = (TextView) v.findViewById(R.id.event_location);
            event_date = (TextView) v.findViewById(R.id.event_date);
            event_time = (TextView) v.findViewById(R.id.event_time);
        }
        TextView getEvent_title() {
            return event_title;
        }

        TextView getEvent_desc() {
            return event_desc;
        }

        TextView getEvent_loc() {
            return event_loc;
        }

        TextView getEvent_date() {
            return event_date;
        }

        TextView getEvent_time() { return  event_time; }
    }
}