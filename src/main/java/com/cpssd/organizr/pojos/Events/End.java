package com.cpssd.organizr.pojos.Events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class End {

    @SerializedName("dateTime")
    @Expose
    private String dateTime;

    public End(String dateTimeEnd) {
        setDateTime(dateTimeEnd);
        return;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

}
