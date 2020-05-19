
package maxcrowdfund.com.mvvm.ui.changePreference.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionSigning {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("options")
    @Expose
    private List<Option__> options = null;
    @SerializedName("value")
    @Expose
    private Object value;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Option__> getOptions() {
        return options;
    }

    public void setOptions(List<Option__> options) {
        this.options = options;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
