
package maxcrowdfund.com.mvvm.ui.BankTransferDetail.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BankTransferDetailResponse {

    @SerializedName("user_login_status")
    @Expose
    private Integer userLoginStatus;
    @SerializedName("bank_transfer_detail")
    @Expose
    private BankTransferDetail bankTransferDetail;

    public Integer getUserLoginStatus() {
        return userLoginStatus;
    }

    public void setUserLoginStatus(Integer userLoginStatus) {
        this.userLoginStatus = userLoginStatus;
    }

    public BankTransferDetail getBankTransferDetail() {
        return bankTransferDetail;
    }

    public void setBankTransferDetail(BankTransferDetail bankTransferDetail) {
        this.bankTransferDetail = bankTransferDetail;
    }

}
