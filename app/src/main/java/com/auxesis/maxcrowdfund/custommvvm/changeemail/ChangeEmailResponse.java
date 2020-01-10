
package com.auxesis.maxcrowdfund.custommvvm.changeemail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangeEmailResponse {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("message")
    @Expose
    private String message;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
