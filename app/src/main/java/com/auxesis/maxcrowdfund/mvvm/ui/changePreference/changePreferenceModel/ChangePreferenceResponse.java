
package com.auxesis.maxcrowdfund.mvvm.ui.changePreference.changePreferenceModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePreferenceResponse {

    @SerializedName("preferences")
    @Expose
    private Preferences preferences;

    public Preferences getPreferences() {
        return preferences;
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

}
