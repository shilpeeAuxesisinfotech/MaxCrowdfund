
package com.auxesis.maxcrowdfund.mvvm.ui.home.homeDetail.funddetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PitchSecurities {

    @SerializedName("heading")
    @Expose
    private String heading;
    @SerializedName("data")
    @Expose
    private String data;

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
