package com.cpssd.organizr.pojos.Maps;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NearbyStop {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("realTimeInfo")
    @Expose
    private RealTimeInfo realTimeInfo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public RealTimeInfo getRealTimeInfo() {
        return realTimeInfo;
    }

    public void setRealTimeInfo(RealTimeInfo realTimeInfo) {
        this.realTimeInfo = realTimeInfo;
    }

}