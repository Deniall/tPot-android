package com.cpssd.organizr.adapters;

import android.database.DataSetObserver;
import android.net.Uri;
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
import com.cpssd.organizr.pojos.Recipes.Recipe;

import java.util.ArrayList;

import static com.cpssd.organizr.activity.LoginActivity.contextOfApplication;

/**
 * Created by Niall on 07/02/2017.
 */

public class OverviewFoodAdapter extends RecyclerView.Adapter<OverviewFoodAdapter.ViewHolder> implements ListAdapter {
    private static final String TAG = "CustomAdapter";

    private ArrayList<Recipe> mDataSet;

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


    public OverviewFoodAdapter(ArrayList<Recipe> dataSet) {
        mDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.overview_food_item, viewGroup, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");
        String title = mDataSet.get(position).getTitle();
        int time = mDataSet.get(position).getReadyInMinutes();
        Uri image = Uri.parse(mDataSet.get(position).getImage());
        viewHolder.getRecipe_title().setText(String.valueOf(title));
        viewHolder.getRecipe_time().setText("Average cooking time: "+String.valueOf(time)+"mins");
        String baseuri = "https://spoonacular.com/recipeImages/";
        Glide.with(contextOfApplication).load(baseuri+image)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new com.cpssd.organizr.other.CircleTransform(contextOfApplication))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.getRecipe_image());
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (mDataSet != null) {
            return mDataSet.size();
        }
        else {
            return 0;
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView recipe_title;
        private final TextView recipe_time;
        private final ImageView recipe_image;


        ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
            recipe_title = (TextView) v.findViewById(R.id.overview_food_item_name);
            recipe_time = (TextView) v.findViewById(R.id.overview_food_item_time);
            recipe_image = (ImageView) v.findViewById(R.id.overview_food_item_image);
        }
        TextView getRecipe_title() {
            return recipe_title;
        }
        TextView getRecipe_time() {
            return recipe_time;
        }
        ImageView getRecipe_image() {
            return recipe_image;
        }

    }
}
