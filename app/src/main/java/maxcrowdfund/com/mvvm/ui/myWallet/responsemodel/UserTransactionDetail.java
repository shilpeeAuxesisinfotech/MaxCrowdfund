
package maxcrowdfund.com.mvvm.ui.myWallet.responsemodel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserTransactionDetail {

    @SerializedName("date_title")
    @Expose
    private String dateTitle;
    @SerializedName("description_title")
    @Expose
    private String descriptionTitle;
    @SerializedName("amount_title")
    @Expose
    private String amountTitle;

    public String getDateTitle() {
        return dateTitle;
    }

    public void setDateTitle(String dateTitle) {
        this.dateTitle = dateTitle;
    }

    public String getDescriptionTitle() {
        return descriptionTitle;
    }

    public void setDescriptionTitle(String descriptionTitle) {
        this.descriptionTitle = descriptionTitle;
    }

    public String getAmountTitle() {
        return amountTitle;
    }

    public void setAmountTitle(String amountTitle) {
        this.amountTitle = amountTitle;
    }
}
