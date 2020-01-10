
package com.auxesis.maxcrowdfund.custommvvm.dashboardDetail.DashboardDetailModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardDetailModelResponce {

    @SerializedName("active_account")
    @Expose
    private ActiveAccount activeAccount;

    public ActiveAccount getActiveAccount() {
        return activeAccount;
    }

    public void setActiveAccount(ActiveAccount activeAccount) {
        this.activeAccount = activeAccount;
    }

}
