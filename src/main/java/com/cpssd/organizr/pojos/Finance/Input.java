package com.cpssd.organizr.pojos.Finance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Input {

    @SerializedName("weekStart")
    @Expose
    private Integer weekStart;
    @SerializedName("date")
    @Expose
    private Integer date;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("details")
    @Expose
    private Details details;

    public Integer getWeekStart() {
        return weekStart;
    }

    public void setWeekStart(Integer weekStart) {
        this.weekStart = weekStart;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

}