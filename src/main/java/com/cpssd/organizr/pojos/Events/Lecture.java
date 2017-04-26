package com.cpssd.organizr.pojos.Events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lecture {

    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("colour")
    @Expose
    private String colour;
    @SerializedName("timeMin")
    @Expose
    private String start;
    @SerializedName("timeMax")
    @Expose
    private String end;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStart() {
        if(!(start == null)) {
            return start.substring(11, 16);
        }
        else{
            return start;
        }
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        if(!(end == null)) {
            return end.substring(11, 16);
        }
        else{
            return end;
        }
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

}