
package com.auxesis.maxcrowdfund.mvvm.ui.investform.questmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InvestFormPreloadResponse {

    @SerializedName("user_login_status")
    @Expose
    private Integer userLoginStatus;
    @SerializedName("warning_show")
    @Expose
    private Integer warningShow;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("warning_msg")
    @Expose
    private String warningMsg;

    public Integer getUserLoginStatus() {
        return userLoginStatus;
    }

    public void setUserLoginStatus(Integer userLoginStatus) {
        this.userLoginStatus = userLoginStatus;
    }

    public Integer getWarningShow() {
        return warningShow;
    }

    public void setWarningShow(Integer warningShow) {
        this.warningShow = warningShow;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getWarningMsg() {
        return warningMsg;
    }

    public void setWarningMsg(String warningMsg) {
        this.warningMsg = warningMsg;
    }

}
