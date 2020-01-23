
package com.auxesis.maxcrowdfund.custommvvm.myinvestmentmodel.myinvestmentdetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyInvestmentDetailResponse {

    @SerializedName("details")
    @Expose
    private Details details;

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }


}
