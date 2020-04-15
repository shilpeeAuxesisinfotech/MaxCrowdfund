
package com.auxesis.maxcrowdfund.mvvm.ui.investform.questmodel.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SurveyFormDataInsertResponse {

    @SerializedName("user_login_status")
    @Expose
    private Integer userLoginStatus;
    @SerializedName("data_insert")
    @Expose
    private Integer dataInsert;

    public Integer getUserLoginStatus() {
        return userLoginStatus;
    }

    public void setUserLoginStatus(Integer userLoginStatus) {
        this.userLoginStatus = userLoginStatus;
    }

    public Integer getDataInsert() {
        return dataInsert;
    }

    public void setDataInsert(Integer dataInsert) {
        this.dataInsert = dataInsert;
    }

}