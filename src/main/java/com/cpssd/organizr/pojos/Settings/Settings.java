package com.cpssd.organizr.pojos.Settings;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Settings {

    @SerializedName("account")
    @Expose
    private Account account;
    @SerializedName("calendars")
    @Expose
    private List<Calendar> calendars = null;
    @SerializedName("cuisine")
    @Expose
    private Cuisine cuisine;
    @SerializedName("course")
    @Expose
    private Course course;
    @SerializedName("societies")
    @Expose
    private List<String> societies = null;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Calendar> getCalendars() {
        return calendars;
    }

    public void setCalendars(List<Calendar> calendars) {
        this.calendars = calendars;
    }

    public Cuisine getCuisine() {
        return cuisine;
    }

    public void setCuisine(Cuisine cuisine) {
        this.cuisine = cuisine;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<String> getSocieties() {
        return societies;
    }

    public void setSocieties(List<String> societies) {
        this.societies = societies;
    }

}