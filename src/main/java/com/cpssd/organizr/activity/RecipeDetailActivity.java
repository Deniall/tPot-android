package com.cpssd.organizr.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cpssd.organizr.R;
import com.cpssd.organizr.adapters.IngredientAdapter;
import com.cpssd.organizr.pojos.Recipes.ExtendedIngredient;
import com.cpssd.organizr.pojos.Recipes.RecipeDetail;

import java.util.ArrayList;
import java.util.List;

import static com.cpssd.organizr.activity.LoginActivity.contextOfApplication;
import static com.cpssd.organizr.activity.LoginActivity.getContextOfApplication;

/**
 * Created by Niall on 19/02/2017.
 */

public class RecipeDetailActivity extends AppCompatActivity {

    private static final String TAG = getContextOfApplication().getClass().getSimpleName();
    public String image;
    RecipeDetail recipeDetail;
    String baseuri = "https://spoonacular.com/recipeImages/";
    private RecyclerView mRecyclerView;
    private IngredientAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    protected RelativeLayout detailLoadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_detail_ingredients);
        detailLoadingBar = (RelativeLayout) findViewById(R.id.detailLoading);
        Intent intent = getIntent();
        String id = intent.getStringExtra("recipeId");
        String image = intent.getStringExtra("recipeImage");
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        Log.d(getLocalClassName(), id);
        new getDetail().execute(id, image);

    }


    class getDetail extends AsyncTask<String, Void, RecipeDetail> {

        @Override
        protected void onPreExecute() {
            detailLoadingBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected RecipeDetail doInBackground(String... strings) {
            String id = strings[0];
            image = strings[1];
            recipeDetail = new NetworkController().getRecipeDetail(id);
            return recipeDetail;
        }

        @Override
        protected void onPostExecute(RecipeDetail recipeDetail) {
            TextView titleTextView = (TextView) findViewById(R.id.activity_detail_text);
            TextView instructionsTextView = (TextView) findViewById(R.id.activity_detail_instructions);
            instructionsTextView.setMovementMethod(new ScrollingMovementMethod());
            ImageView imageView = (ImageView) findViewById(R.id.activity_detail_image);
            ImageView vegetarian = (ImageView) findViewById(R.id.activity_detail_vegetarian);
            vegetarian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(RecipeDetailActivity.this, "Vegetarian", Toast.LENGTH_SHORT).show();
                }
            });
            ImageView glutenFree = (ImageView) findViewById(R.id.activity_detail_gluten);
            glutenFree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(RecipeDetailActivity.this, "Gluten-Free", Toast.LENGTH_SHORT).show();
                }
            });
            ImageView dairyFree = (ImageView) findViewById(R.id.activity_detail_dairy);
            dairyFree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(RecipeDetailActivity.this, "Dairy-Free", Toast.LENGTH_SHORT).show();
                }
            });
            if(recipeDetail.getVegetarian()){ vegetarian.setVisibility(View.VISIBLE);}
            if(recipeDetail.getGlutenFree()){ glutenFree.setVisibility(View.VISIBLE);}
            if(recipeDetail.getDairyFree()){ dairyFree.setVisibility(View.VISIBLE);}
            titleTextView.setText(recipeDetail.getTitle().substring(0,1).toUpperCase()+recipeDetail.getTitle().substring(1));
            if(recipeDetail.getInstructions().substring(0,12) == "Instructions") {
                instructionsTextView.setText(recipeDetail.getInstructions().substring(12));
            }
            else{
                instructionsTextView.setText(recipeDetail.getInstructions());
            }
            Glide.with(contextOfApplication).load(baseuri+image)
                    .crossFade()
                    .thumbnail(0.5f)
                    .bitmapTransform(new com.cpssd.organizr.other.CircleTransform(contextOfApplication))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
            ArrayList<ExtendedIngredient> ingredients = new ArrayList<>();
            List<ExtendedIngredient> ingredientList = recipeDetail.getExtendedIngredients();

            for(ExtendedIngredient ingredient : ingredientList){
                ingredients.add(ingredient);
            }
            Log.d(TAG, ingredients.get(0).getName());
            mAdapter = new IngredientAdapter(ingredients);
            mRecyclerView.setAdapter(mAdapter);
            detailLoadingBar.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);

        }
    }

}
