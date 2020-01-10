
package com.auxesis.maxcrowdfund.custommvvm.dashboardDetail.DashboardDetailModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActiveAccount {

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
