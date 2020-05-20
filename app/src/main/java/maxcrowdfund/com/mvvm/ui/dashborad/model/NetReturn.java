
package maxcrowdfund.com.mvvm.ui.dashborad.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NetReturn {

    @SerializedName("heading")
    @Expose
    private String heading;
    @SerializedName("data")
    @Expose
    private Data__ data;

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public Data__ getData() {
        return data;
    }

    public void setData(Data__ data) {
        this.data = data;
    }

}
