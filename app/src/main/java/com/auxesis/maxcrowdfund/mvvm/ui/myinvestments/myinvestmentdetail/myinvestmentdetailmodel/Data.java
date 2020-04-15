
package com.auxesis.maxcrowdfund.mvvm.ui.myinvestments.myinvestmentdetail.myinvestmentdetailmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("period")
    @Expose
    private String period;
    @SerializedName("amount")
    @Expose
    private String amount;

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
