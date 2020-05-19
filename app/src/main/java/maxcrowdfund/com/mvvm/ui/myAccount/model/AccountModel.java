package maxcrowdfund.com.mvvm.ui.myAccount.model;

public class AccountModel {
    private String tittle;
    private String type;
    private String value;

    public AccountModel(String tittle, String type, String value) {
        this.tittle = tittle;
        this.type = type;
        this.value = value;
    }

    public String getTittle() {
        return tittle;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
