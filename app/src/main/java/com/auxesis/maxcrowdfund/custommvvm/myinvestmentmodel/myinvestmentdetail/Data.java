
package com.auxesis.maxcrowdfund.custommvvm.myinvestmentmodel.myinvestmentdetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("period")
    @Expose
    private Integer period;
    @SerializedName("amount")
    @Expose
    private String amount;

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
