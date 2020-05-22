package maxcrowdfund.com.mvvm.ui.customModels;

public class CustomSpinnerModel {
    private String key;
    private String val;

    public CustomSpinnerModel() {
    }

    public CustomSpinnerModel(String key, String val) {
        this.key = key;
        this.val = val;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }


}
