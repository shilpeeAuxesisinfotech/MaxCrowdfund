
package com.auxesis.maxcrowdfund.mvvm.ui.dashborad.dashboarddeposit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardDepositResponse {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("trustly_url")
    @Expose
    private String trustlyUrl;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTrustlyUrl() {
        return trustlyUrl;
    }

    public void setTrustlyUrl(String trustlyUrl) {
        this.trustlyUrl = trustlyUrl;
    }

}
