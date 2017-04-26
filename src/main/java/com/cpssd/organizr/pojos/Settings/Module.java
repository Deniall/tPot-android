package com.cpssd.organizr.pojos.Settings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Module {

    @SerializedName("module")
    @Expose
    private String module;
    @SerializedName("value")
    @Expose
    private Boolean value;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

}
