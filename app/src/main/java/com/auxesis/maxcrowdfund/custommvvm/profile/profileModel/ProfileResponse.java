
package com.auxesis.maxcrowdfund.custommvvm.profile.profileModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileResponse {

    @SerializedName("profile")
    @Expose
    private Profile profile;

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }


}
