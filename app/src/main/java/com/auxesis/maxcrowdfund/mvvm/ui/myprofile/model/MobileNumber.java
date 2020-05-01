
package com.auxesis.maxcrowdfund.mvvm.ui.myprofile.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MobileNumber {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("value")
    @Expose
    private Integer value;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

}
