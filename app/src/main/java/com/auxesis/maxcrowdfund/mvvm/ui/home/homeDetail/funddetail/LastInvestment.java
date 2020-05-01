
package com.auxesis.maxcrowdfund.mvvm.ui.home.homeDetail.funddetail;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LastInvestment {

    @SerializedName("heading")
    @Expose
    private String heading;
    @SerializedName("data")
    @Expose
    private List<Datum________> data = null;

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public List<Datum________> getData() {
        return data;
    }

    public void setData(List<Datum________> data) {
        this.data = data;
    }

}
