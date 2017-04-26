package com.cpssd.organizr.pojos.Settings;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Course {

    @SerializedName("college")
    @Expose
    private String college;
    @SerializedName("course")
    @Expose
    private String course;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("modules")
    @Expose
    private List<Module> modules = null;

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

}
