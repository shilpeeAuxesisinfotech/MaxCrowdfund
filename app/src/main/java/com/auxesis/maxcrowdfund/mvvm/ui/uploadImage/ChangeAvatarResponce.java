
package com.auxesis.maxcrowdfund.mvvm.ui.uploadImage;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangeAvatarResponce {
    @SerializedName("user_login_status")
    @Expose
    private Integer userLoginStatus;
    @SerializedName("result")
    @Expose
    private String result;

    public Integer getUserLoginStatus() {
        return userLoginStatus;
    }

    public void setUserLoginStatus(Integer userLoginStatus) {
        this.userLoginStatus = userLoginStatus;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
