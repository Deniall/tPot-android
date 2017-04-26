package com.cpssd.organizr.pojos.Finance;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BalanceUpdate {

    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("input")
    @Expose
    private Input input;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Input getInput() {
        return input;
    }

    public void setInput(Input input) {
        this.input = input;
    }

}
