package com.cpssd.organizr.pojos.Settings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Diet {

    @SerializedName("vegan")
    @Expose
    private Boolean vegan;
    @SerializedName("vegetarian")
    @Expose
    private Boolean vegetarian;
    @SerializedName("pescetarian")
    @Expose
    private Boolean pescetarian;
    @SerializedName("lactoVegetarian")
    @Expose
    private Boolean lactoVegetarian;
    @SerializedName("ovoVegetarian")
    @Expose
    private Boolean ovoVegetarian;

    public Boolean getVegan() {
        return vegan;
    }

    public void setVegan(Boolean vegan) {
        this.vegan = vegan;
    }

    public Boolean getVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(Boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public Boolean getPescetarian() {
        return pescetarian;
    }

    public void setPescetarian(Boolean pescetarian) {
        this.pescetarian = pescetarian;
    }

    public Boolean getLactoVegetarian() {
        return lactoVegetarian;
    }

    public void setLactoVegetarian(Boolean lactoVegetarian) {
        this.lactoVegetarian = lactoVegetarian;
    }

    public Boolean getOvoVegetarian() {
        return ovoVegetarian;
    }

    public void setOvoVegetarian(Boolean ovoVegetarian) {
        this.ovoVegetarian = ovoVegetarian;
    }

}

