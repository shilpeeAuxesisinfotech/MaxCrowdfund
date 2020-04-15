
package com.auxesis.maxcrowdfund.mvvm.ui.home.homeDetail.detailmodel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InvestmentPlan {

    @SerializedName("heading")
    @Expose
    private String heading;
    @SerializedName("data")
    @Expose
    private List<Datum____> data = null;

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public List<Datum____> getData() {
        return data;
    }

    public void setData(List<Datum____> data) {
        this.data = data;
    }

}