
package com.auxesis.maxcrowdfund.mvvm.ui.home.homeDetail.funddetail;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("data")
    @Expose
    private List<Datum____> data = null;
    @SerializedName("additional")
    @Expose
    private Additional additional;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<Datum____> getData() {
        return data;
    }

    public void setData(List<Datum____> data) {
        this.data = data;
    }

    public Additional getAdditional() {
        return additional;
    }

    public void setAdditional(Additional additional) {
        this.additional = additional;
    }

}
