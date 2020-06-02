
package maxcrowdfund.com.mvvm.ui.myWallet.responsemodel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserTransactionResponse {

    @SerializedName("user_login_status")
    @Expose
    private Integer userLoginStatus;
    @SerializedName("user_transaction_detail")
    @Expose
    private List<UserTransactionDetail> userTransactionDetail = null;

    public Integer getUserLoginStatus() {
        return userLoginStatus;
    }

    public void setUserLoginStatus(Integer userLoginStatus) {
        this.userLoginStatus = userLoginStatus;
    }

    public List<UserTransactionDetail> getUserTransactionDetail() {
        return userTransactionDetail;
    }
    public void setUserTransactionDetail(List<UserTransactionDetail> userTransactionDetail) {
        this.userTransactionDetail = userTransactionDetail;
    }
}
