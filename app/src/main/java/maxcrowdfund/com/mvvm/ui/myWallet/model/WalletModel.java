package maxcrowdfund.com.mvvm.ui.myWallet.model;

public class WalletModel {
    private String tittle;
    private String value;

    public WalletModel(String tittle, String value) {
        this.tittle = tittle;
        this.value = value;
    }
    public String getTittle() {
        return tittle;
    }
    public String getValue() {
        return value;
    }
}
