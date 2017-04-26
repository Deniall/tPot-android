package com.cpssd.organizr.pojos.Events;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Calendars {

    @SerializedName("eventData")
    @Expose
    private List<Event> eventData = null;

    public List<Event> getEventData() {
        return eventData;
    }

    public void setEventData(List<Event> eventData) {
        this.eventData = eventData;
    }

}
