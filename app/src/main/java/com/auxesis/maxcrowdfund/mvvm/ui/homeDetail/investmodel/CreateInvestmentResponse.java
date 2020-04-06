
package com.auxesis.maxcrowdfund.mvvm.ui.homeDetail.investmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateInvestmentResponse {

    @SerializedName("user_login_status")
    @Expose
    private Integer userLoginStatus;
    @SerializedName("deposit_amount")
    @Expose
    private Integer depositAmount;
    @SerializedName("deposit_check")
    @Expose
    private Integer depositCheck;
    @SerializedName("investment_success")
    @Expose
    private String investmentSuccess;
    @SerializedName("invest_create_check")
    @Expose
    private Integer investCreateCheck;

    public Integer getUserLoginStatus() {
        return userLoginStatus;
    }

    public void setUserLoginStatus(Integer userLoginStatus) {
        this.userLoginStatus = userLoginStatus;
    }

    public Integer getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(Integer depositAmount) {
        this.depositAmount = depositAmount;
    }

    public Integer getDepositCheck() {
        return depositCheck;
    }

    public void setDepositCheck(Integer depositCheck) {
        this.depositCheck = depositCheck;
    }

    public String getInvestmentSuccess() {
        return investmentSuccess;
    }

    public void setInvestmentSuccess(String investmentSuccess) {
        this.investmentSuccess = investmentSuccess;
    }

    public Integer getInvestCreateCheck() {
        return investCreateCheck;
    }

    public void setInvestCreateCheck(Integer investCreateCheck) {
        this.investCreateCheck = investCreateCheck;
    }

}
