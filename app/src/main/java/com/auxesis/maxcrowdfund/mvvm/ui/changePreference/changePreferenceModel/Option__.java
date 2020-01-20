
package com.auxesis.maxcrowdfund.mvvm.ui.changePreference.changePreferenceModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Option__ {

    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("val")
    @Expose
    private String val;

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
