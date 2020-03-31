
package com.auxesis.maxcrowdfund.mvvm.ui.dashborad.dashboardmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MpgPurchase {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("type")
    @Expose
    private String type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
