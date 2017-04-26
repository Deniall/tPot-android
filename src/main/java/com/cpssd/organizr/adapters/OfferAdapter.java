package com.cpssd.organizr.adapters;

import android.database.DataSetObserver;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cpssd.organizr.R;
import com.cpssd.organizr.pojos.Events.Event;
import com.cpssd.organizr.pojos.Offers.Offer;

import java.util.ArrayList;

import static com.cpssd.organizr.activity.LoginActivity.contextOfApplication;


/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ViewHolder> implements ListAdapter {
    private static final String TAG = "OfferAdapter";

    private ArrayList<Offer> mDataSet;

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


    public OfferAdapter(ArrayList<Offer> dataSet) {
        mDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_offers_recycler_item, viewGroup, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");
        Offer offer = mDataSet.get(position);
        viewHolder.getOffer_title().setText(offer.getName());
        viewHolder.getOffer_price().setText(offer.getPrice());
        Glide.with(contextOfApplication).load(offer.getImg())
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new com.cpssd.organizr.other.CircleTransform(contextOfApplication))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.getOffer_image());

    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView offer_title;
        private final TextView offer_price;
        private final ImageView offer_image;


        ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
            offer_title = (TextView) v.findViewById(R.id.offers_item_title);
            offer_price = (TextView) v.findViewById(R.id.offers_item_price);
            offer_image = (ImageView) v.findViewById(R.id.offers_item_image);
        }
        TextView getOffer_title() {
            return offer_title;
        }

        TextView getOffer_price() {
            return offer_price;
        }

        ImageView getOffer_image() { return  offer_image; }
    }
}
