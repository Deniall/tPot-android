package com.cpssd.organizr.pojos.Settings;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cuisine {

    @SerializedName("diet")
    @Expose
    private Diet diet;
    @SerializedName("intolerances")
    @Expose
    private Intolerances intolerances;

    public Diet getDiet() {
        return diet;
    }

    public void setDiet(Diet diet) {
        this.diet = diet;
    }

    public Intolerances getIntolerances() {
        return intolerances;
    }

    public void setIntolerances(Intolerances intolerances) {
        this.intolerances = intolerances;
    }

}

