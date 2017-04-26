package com.cpssd.organizr.pojos.Finance;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Balance {

    @SerializedName("20170306")
    @Expose
    private WeekBalance weekBalance;

    public WeekBalance getWeekBalance() {
        return weekBalance;
    }

    public void setWeekBalance(WeekBalance weekBalance) {
        this.weekBalance = weekBalance;
    }

}
