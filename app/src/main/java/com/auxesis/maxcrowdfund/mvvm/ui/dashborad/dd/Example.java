
package com.auxesis.maxcrowdfund.mvvm.ui.dashborad.dd;

import com.auxesis.maxcrowdfund.mvvm.ui.dashborad.dashboardmodel.Balance;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example {

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
