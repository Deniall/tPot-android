package com.cpssd.organizr.adapters;

import android.content.Context;
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

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> implements ListAdapter {
    private static final String TAG = "CustomAdapter";

    public ArrayList<Recipe> mDataSet;
    Context mContext;

    public RecipeAdapter(ArrayList<Recipe> mDataset, Context context) {
        mDataSet = mDataset;
        this.mContext = context;
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
                .inflate(R.layout.recipe_item, viewGroup, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");
        String baseuri = "https://spoonacular.com/recipeImages/";
        Uri image;
        if(mDataSet.get(position).getImage() != null){
            image = Uri.parse(baseuri+(mDataSet.get(position).getImage()));
        }
        else{
            image = Uri.parse("https://www.paragon.com.sg/assets/camaleon_cms/image-not-found-4a963b95bf081c3ea02923dceaeb3f8085e1a654fc54840aac61a57a60903fef.png");
        }
        int time = mDataSet.get(position).getReadyInMinutes();
        String title = mDataSet.get(position).getTitle();
        if(title.length() > 28){
            title = title.substring(0, 28);
        }
        viewHolder.getRecipe_title().setText(title);
        viewHolder.getRecipe_time().setText("Average cooking time: "+String.valueOf(time)+"mins");

        Glide.with(contextOfApplication).load(image)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new com.cpssd.organizr.other.CircleTransform(contextOfApplication))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.getRecipe_image());
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if(!mDataSet.isEmpty()) {
            return mDataSet.size();
        }
        else {
            return 0;
        }
    }

    public Recipe assignRecipe(int position) {
        return mDataSet.get(position);
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

                }
            });
            recipe_title = (TextView) v.findViewById(R.id.recipe_name);
            recipe_time = (TextView) v.findViewById(R.id.recipe_time);
            recipe_image = (ImageView) v.findViewById(R.id.recipe_image);
        }
        TextView getRecipe_title() {
            return recipe_title;
        }
        TextView getRecipe_time() { return  recipe_time; }
        ImageView getRecipe_image() { return recipe_image; }
    }
}
