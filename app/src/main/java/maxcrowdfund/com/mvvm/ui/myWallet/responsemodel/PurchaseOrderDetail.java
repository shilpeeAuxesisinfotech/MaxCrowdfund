
package maxcrowdfund.com.mvvm.ui.myWallet.responsemodel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PurchaseOrderDetail {
    @SerializedName("date_title")
    @Expose
    private String dateTitle;
    @SerializedName("amount_title")
    @Expose
    private String amountTitle;
    @SerializedName("type_title")
    @Expose
    private String typeTitle;

    public String getDateTitle() {
        return dateTitle;
    }
    public void setDateTitle(String dateTitle) {
        this.dateTitle = dateTitle;
    }
    public String getAmountTitle() {
        return amountTitle;
    }
    public void setAmountTitle(String amountTitle) {
        this.amountTitle = amountTitle;
    }
    public String getTypeTitle() {
        return typeTitle;
    }
    public void setTypeTitle(String typeTitle) {
        this.typeTitle = typeTitle;
    }
}
