
package com.auxesis.maxcrowdfund.mvvm.ui.home.homeDetail.funddetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FundraiserDetailResponse {

    @SerializedName("user_login_status")
    @Expose
    private Integer userLoginStatus;
    @SerializedName("details")
    @Expose
    private Details details;

    public Integer getUserLoginStatus() {
        return userLoginStatus;
    }

    public void setUserLoginStatus(Integer userLoginStatus) {
        this.userLoginStatus = userLoginStatus;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

}
