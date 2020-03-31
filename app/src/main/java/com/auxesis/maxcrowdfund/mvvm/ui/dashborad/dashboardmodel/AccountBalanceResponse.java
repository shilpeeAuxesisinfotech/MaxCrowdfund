
package com.auxesis.maxcrowdfund.mvvm.ui.dashborad.dashboardmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountBalanceResponse {

    @SerializedName("balance")
    @Expose
    private Balance balance;

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

}
