
package com.auxesis.maxcrowdfund.mvvm.ui.changeMobileNumber.changeMobileModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdatePhoneNumberResponse {
    @SerializedName("result")
    @Expose
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
