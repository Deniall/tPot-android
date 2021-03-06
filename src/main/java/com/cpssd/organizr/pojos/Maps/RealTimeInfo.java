package com.cpssd.organizr.pojos.Maps;

/**
 * Created by Catha on 30/03/2017.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RealTimeInfo {

    @SerializedName("errorcode")
    @Expose
    private String errorcode;
    @SerializedName("errormessage")
    @Expose
    private String errormessage;
    @SerializedName("numberofresults")
    @Expose
    private Integer numberofresults;
    @SerializedName("stopid")
    @Expose
    private String stopid;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getErrormessage() {
        return errormessage;
    }

    public void setErrormessage(String errormessage) {
        this.errormessage = errormessage;
    }

    public Integer getNumberofresults() {
        return numberofresults;
    }

    public void setNumberofresults(Integer numberofresults) {
        this.numberofresults = numberofresults;
    }

    public String getStopid() {
        return stopid;
    }

    public void setStopid(String stopid) {
        this.stopid = stopid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Result getResults() {
        if(!results.isEmpty()) {
            return results.get(0);
        }
        else {
            return new Result();
        }
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

}