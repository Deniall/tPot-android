package com.cpssd.organizr.pojos.Finance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeekBalance {

    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("income")
    @Expose
    private Integer income;
    @SerializedName("expenditure")
    @Expose
    private Integer expenditure;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getIncome() {
        return income;
    }

    public void setIncome(Integer income) {
        this.income = income;
    }

    public Integer getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(Integer expenditure) {
        this.expenditure = expenditure;
    }

}
