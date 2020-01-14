
package com.auxesis.maxcrowdfund.mvvm.ui.changebankaccount.changebankaccountmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("account")
    @Expose
    private String account;
    @SerializedName("active")
    @Expose
    private Integer active;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

}
