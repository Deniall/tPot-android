package com.cpssd.organizr.pojos.Maps;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllStops {

    private List<NearbyStop> nearbyStops;

    public List<NearbyStop> getNearbyStops() {
        return nearbyStops;
    }

}