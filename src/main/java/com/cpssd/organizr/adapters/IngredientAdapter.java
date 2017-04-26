package com.cpssd.organizr.adapters;

import android.content.Context;
import android.database.DataSetObserver;
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
import com.cpssd.organizr.pojos.Recipes.ExtendedIngredient;

import java.util.ArrayList;

import static com.cpssd.organizr.activity.LoginActivity.contextOfApplication;

/**
 * Created by Niall on 07/02/2017.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> implements ListAdapter {
    private static final String TAG = "CustomAdapter";

    public ArrayList<ExtendedIngredient> mDataSet;
    Context mContext;

    public IngredientAdapter(ArrayList<ExtendedIngredient> mDataset) {
        mDataSet = mDataset;
    }

    public void showPopup(View view) {
        View popupView = LayoutInflater.from(mContext).inflate(R.layout.recipes_popup_layout, null);

        // Blah Blah remaining stuff...
    }

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

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_detail_item, viewGroup, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        String title = mDataSet.get(position).getName();
        if (title.length() > 28) {
            title = title.substring(0, 28);
        }
        float amount = mDataSet.get(position).getAmount();
        String unit = mDataSet.get(position).getUnitShort();
        String image_url = mDataSet.get(position).getImage();
        String ingredient = title.substring(0,1).toUpperCase()+title.substring(1)+", "+amount+" "+unit;
        Glide.with(contextOfApplication).load(image_url)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new com.cpssd.organizr.other.CircleTransform(contextOfApplication))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.getIngredient_image());
        Log.d("TESTINGREDIENT", title);
        viewHolder.getIngredient_title().setText(ingredient);

    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView ingredient_title;
        private final ImageView ingredient_image;

        ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            ingredient_title = (TextView) v.findViewById(R.id.activity_detail_ingredient_item_name);
            ingredient_image = (ImageView) v.findViewById(R.id.activity_detail_ingredient_image);
        }
        TextView getIngredient_title() {
            return ingredient_title;
        }
        ImageView getIngredient_image() {return ingredient_image;}
    }
}
