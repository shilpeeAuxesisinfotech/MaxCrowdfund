package maxcrowdfund.com.mvvm.ui.dashborad.netreturnmodel;

public class NetReturnModel {
    private String tittle;
    private String type;
    private String value;

    public NetReturnModel(String tittle, String type, String value) {
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
