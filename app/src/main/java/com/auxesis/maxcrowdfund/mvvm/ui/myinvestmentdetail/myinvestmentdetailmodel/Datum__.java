
package com.auxesis.maxcrowdfund.mvvm.ui.myinvestmentdetail.myinvestmentdetailmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum__ {

    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
