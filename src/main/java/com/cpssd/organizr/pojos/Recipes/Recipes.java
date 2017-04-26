package com.cpssd.organizr.pojos.Recipes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Niall on 07/02/2017.
 */

public class Recipes {

    @SerializedName("results")
    @Expose
    private ArrayList<Recipe> recipes;

    public Recipes(ArrayList<Recipe> recipes) {
        super();
        this.recipes = recipes;
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) { this.recipes = recipes; }
}
