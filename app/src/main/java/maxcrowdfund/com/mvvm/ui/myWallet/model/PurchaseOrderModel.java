package maxcrowdfund.com.mvvm.ui.myWallet.model;

public class PurchaseOrderModel {
    private String date;
    private String type;
    private String amount;
    private boolean isDelete;

    public PurchaseOrderModel(String date, String type, String amount) {
        this.date = date;
        this.type = type;
        this.amount = amount;
    }

    public PurchaseOrderModel(String date, String type, String amount, boolean isDelete) {
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.isDelete = isDelete;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getAmount() {
        return amount;
    }
}
