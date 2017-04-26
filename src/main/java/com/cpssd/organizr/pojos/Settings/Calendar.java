package com.cpssd.organizr.pojos.Settings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Calendar {

    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("value")
    @Expose
    private Boolean value;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

}
