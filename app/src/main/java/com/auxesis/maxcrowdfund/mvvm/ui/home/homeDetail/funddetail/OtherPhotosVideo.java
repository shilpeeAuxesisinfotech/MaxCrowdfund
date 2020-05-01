
package com.auxesis.maxcrowdfund.mvvm.ui.home.homeDetail.funddetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtherPhotosVideo {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("url")
    @Expose
    private String url;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
