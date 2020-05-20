
package maxcrowdfund.com.mvvm.ui.dashborad.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountBalanceResponse {

    @SerializedName("user_login_status")
    @Expose
    private Integer userLoginStatus;
    @SerializedName("balance")
    @Expose
    private Balance balance;
    @SerializedName("portfolio")
    @Expose
    private Portfolio portfolio;
    @SerializedName("net_return")
    @Expose
    private NetReturn netReturn;

    public Integer getUserLoginStatus() {
        return userLoginStatus;
    }

    public void setUserLoginStatus(Integer userLoginStatus) {
        this.userLoginStatus = userLoginStatus;
    }

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public NetReturn getNetReturn() {
        return netReturn;
    }

    public void setNetReturn(NetReturn netReturn) {
        this.netReturn = netReturn;
    }

}
