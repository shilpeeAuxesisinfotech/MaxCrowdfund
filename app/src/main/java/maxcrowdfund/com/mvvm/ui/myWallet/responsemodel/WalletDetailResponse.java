
package maxcrowdfund.com.mvvm.ui.myWallet.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletDetailResponse {

    @SerializedName("user_login_status")
    @Expose
    private Integer userLoginStatus;
    @SerializedName("wallet_detail")
    @Expose
    private WalletDetail walletDetail;

    public Integer getUserLoginStatus() {
        return userLoginStatus;
    }

    public void setUserLoginStatus(Integer userLoginStatus) {
        this.userLoginStatus = userLoginStatus;
    }

    public WalletDetail getWalletDetail() {
        return walletDetail;
    }

    public void setWalletDetail(WalletDetail walletDetail) {
        this.walletDetail = walletDetail;
    }

}
