
package com.auxesis.maxcrowdfund.mvvm.ui.changePreference.changePreferenceModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JointAccount {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("request_url")
    @Expose
    private String requestUrl;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("button-display-title")
    @Expose
    private String buttonDisplayTitle;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getButtonDisplayTitle() {
        return buttonDisplayTitle;
    }

    public void setButtonDisplayTitle(String buttonDisplayTitle) {
        this.buttonDisplayTitle = buttonDisplayTitle;
    }

}
