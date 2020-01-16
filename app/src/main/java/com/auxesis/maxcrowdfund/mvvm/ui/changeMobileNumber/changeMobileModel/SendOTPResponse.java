
package com.auxesis.maxcrowdfund.mvvm.ui.changeMobileNumber.changeMobileModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendOTPResponse {

    @SerializedName("otp")
    @Expose
    private String otp;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

}
