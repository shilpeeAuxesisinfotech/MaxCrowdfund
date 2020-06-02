package maxcrowdfund.com.mvvm.ui.commonmodel;

public class CommonModel {
    String tittle;
    String value;
    String type;

    public CommonModel(String tittle, String value) {
        this.tittle = tittle;
        this.value = value;
    }
    public CommonModel(String tittle, String value, String type) {
        this.tittle = tittle;
        this.value = value;
        this.type = type;
    }
    public String getTittle() {
        return tittle;
    }
    public String getValue() {
        return value;
    }
    public String getType() {
        return type;
    }

}
