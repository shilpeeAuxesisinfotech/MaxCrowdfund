
package com.auxesis.maxcrowdfund.custommvvm.dashboardDetail.DashboardDetailModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardSignatureResponce {

    @SerializedName("signature")
    @Expose
    private String signature;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

}
