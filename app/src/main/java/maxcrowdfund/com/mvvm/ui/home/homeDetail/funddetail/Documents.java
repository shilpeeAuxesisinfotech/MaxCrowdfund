
package maxcrowdfund.com.mvvm.ui.home.homeDetail.funddetail;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Documents {

    @SerializedName("heading")
    @Expose
    private String heading;
    @SerializedName("data")
    @Expose
    private List<Datum_______> data = null;

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public List<Datum_______> getData() {
        return data;
    }

    public void setData(List<Datum_______> data) {
        this.data = data;
    }

}
