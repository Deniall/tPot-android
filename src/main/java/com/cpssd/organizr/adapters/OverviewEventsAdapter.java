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
public class OverviewEventsAdapter extends RecyclerView.Adapter<OverviewEventsAdapter.ViewHolder> implements ListAdapter {
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


    public OverviewEventsAdapter(ArrayList<Event> dataSet) {
        mDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.overview_events_item, viewGroup, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");
        Event event = mDataSet.get(position);
        viewHolder.getEvent_title().setText(event.getDescription());
        viewHolder.getEvent_loc().setText(event.getLocation());
        viewHolder.getEvent_time().setText(event.getStart().getDateTime().substring(11, 16));

    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView event_title;
        private final TextView event_loc;
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
            event_title = (TextView) v.findViewById(R.id.overview_events_item_title);
            event_loc = (TextView) v.findViewById(R.id.overview_events_item_location);
            event_time = (TextView) v.findViewById(R.id.overview_events_item_time);
        }
        TextView getEvent_title() {
            return event_title;
        }

        TextView getEvent_loc() {
            return event_loc;
        }

        TextView getEvent_time() { return  event_time; }
    }
}
