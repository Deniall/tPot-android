package com.cpssd.organizr.pojos.Events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Start {

    @SerializedName("dateTime")
    @Expose
    private String dateTime;

    public Start(String dateTimeStart) {
        setDateTime(dateTimeStart);
        return;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

}