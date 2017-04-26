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
import com.cpssd.organizr.pojos.Maps.NearbyStop;

import java.util.ArrayList;


/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class MapAdapter extends RecyclerView.Adapter<MapAdapter.ViewHolder> implements ListAdapter {
    private static final String TAG = "CustomAdapter";

    private ArrayList<NearbyStop> mDataSet;

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


    public MapAdapter(ArrayList<NearbyStop> dataSet) {
        mDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.maps_recycler_item, viewGroup, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");
        NearbyStop nearbyStop = mDataSet.get(position);
        viewHolder.getStop_id().setText("Stop "+nearbyStop.getRealTimeInfo().getStopid());
        viewHolder.getStop_duetime().setText("Due in "+nearbyStop.getRealTimeInfo().getResults().getDuetime()+" mins");
        viewHolder.getStop_from_to().setText("From "+nearbyStop.getRealTimeInfo().getResults().getOrigin()+" to "+nearbyStop.getRealTimeInfo().getResults().getDestination());
        viewHolder.getStop_name().setText(nearbyStop.getName());
        viewHolder.getStop_route().setText("Route: "+nearbyStop.getRealTimeInfo().getResults().getRoute());
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView stop_id;
        private final TextView stop_duetime;
        private final TextView stop_from_to;
        private final TextView stop_name;
        private final TextView stop_route;


        ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
            stop_id = (TextView) v.findViewById(R.id.stop_id);
            stop_duetime = (TextView) v.findViewById(R.id.stop_due_time);
            stop_from_to = (TextView) v.findViewById(R.id.stop_from_to);
            stop_name = (TextView) v.findViewById(R.id.stop_name);
            stop_route = (TextView) v.findViewById(R.id.stop_route);
        }

        TextView getStop_id () {
            return stop_id;
        }

        TextView getStop_duetime() {
            return stop_duetime;
        }

        TextView getStop_from_to() {
            return stop_from_to;
        }

        TextView getStop_name() {
            return stop_name;
        }

        TextView getStop_route() { return  stop_route; }
    }
}