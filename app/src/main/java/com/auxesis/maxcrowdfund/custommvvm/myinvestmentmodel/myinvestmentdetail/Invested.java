
package com.auxesis.maxcrowdfund.custommvvm.myinvestmentmodel.myinvestmentdetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Invested {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("data")
    @Expose
    private String data;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
