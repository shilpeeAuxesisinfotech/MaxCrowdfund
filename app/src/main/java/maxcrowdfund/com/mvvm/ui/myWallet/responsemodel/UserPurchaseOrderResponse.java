
package maxcrowdfund.com.mvvm.ui.myWallet.responsemodel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserPurchaseOrderResponse {

    @SerializedName("user_login_status")
    @Expose
    private Integer userLoginStatus;
    @SerializedName("purchase_order_detail")
    @Expose
    private List<PurchaseOrderDetail> purchaseOrderDetail = null;

    public Integer getUserLoginStatus() {
        return userLoginStatus;
    }

    public void setUserLoginStatus(Integer userLoginStatus) {
        this.userLoginStatus = userLoginStatus;
    }

    public List<PurchaseOrderDetail> getPurchaseOrderDetail() {
        return purchaseOrderDetail;
    }

    public void setPurchaseOrderDetail(List<PurchaseOrderDetail> purchaseOrderDetail) {
        this.purchaseOrderDetail = purchaseOrderDetail;
    }

}
