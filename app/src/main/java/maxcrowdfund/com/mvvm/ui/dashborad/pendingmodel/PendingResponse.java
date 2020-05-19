
package maxcrowdfund.com.mvvm.ui.dashborad.pendingmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PendingResponse {

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
