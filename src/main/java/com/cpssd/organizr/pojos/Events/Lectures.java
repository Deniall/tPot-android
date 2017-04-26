package com.cpssd.organizr.pojos.Events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Niall on 4/18/2017.
 */

public class Lectures {
    @SerializedName("college")
    @Expose
    private List<Lecture> lectures = null;

    public List<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }

}
