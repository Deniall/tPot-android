package com.cpssd.organizr.pojos.Offers;

/**
 * Created by Catha on 30/03/2017.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpecialOffers {

    @SerializedName("tesco")
    @Expose
    private List<Offer> tesco = null;
    @SerializedName("supervalu")
    @Expose
    private List<Offer> supervalu = null;

    public List<Offer> getTesco() {
        return tesco;
    }

    public void setTesco(List<Offer> tesco) {
        this.tesco = tesco;
    }

    public List<Offer> getSupervalu() {
        return supervalu;
    }

    public void setSupervalu(List<Offer> supervalu) {
        this.supervalu = supervalu;
    }

}
