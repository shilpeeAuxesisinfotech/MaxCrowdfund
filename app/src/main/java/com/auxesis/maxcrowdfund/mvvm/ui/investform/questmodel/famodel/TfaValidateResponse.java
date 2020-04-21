
package com.auxesis.maxcrowdfund.mvvm.ui.investform.questmodel.famodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TfaValidateResponse {

    @SerializedName("user_login_status")
    @Expose
    private Integer userLoginStatus;
    @SerializedName("error_msg")
    @Expose
    private String errorMsg;
    @SerializedName("no_err_check")
    @Expose
    private Integer noErrCheck;

    public Integer getUserLoginStatus() {
        return userLoginStatus;
    }

    public void setUserLoginStatus(Integer userLoginStatus) {
        this.userLoginStatus = userLoginStatus;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Integer getNoErrCheck() {
        return noErrCheck;
    }

    public void setNoErrCheck(Integer noErrCheck) {
        this.noErrCheck = noErrCheck;
    }

}
