
package maxcrowdfund.com.mvvm.ui.uiTokens;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UiProfileChangePrefrenceTokens {

    @SerializedName("user_profile_chanage_prefrence_title")
    @Expose
    private String userProfileChanagePrefrenceTitle;
    @SerializedName("user_profile_chanage_prefrence_button_update_prefrence")
    @Expose
    private String userProfileChanagePrefrenceButtonUpdatePrefrence;

    public String getUserProfileChanagePrefrenceTitle() {
        return userProfileChanagePrefrenceTitle;
    }

    public void setUserProfileChanagePrefrenceTitle(String userProfileChanagePrefrenceTitle) {
        this.userProfileChanagePrefrenceTitle = userProfileChanagePrefrenceTitle;
    }

    public String getUserProfileChanagePrefrenceButtonUpdatePrefrence() {
        return userProfileChanagePrefrenceButtonUpdatePrefrence;
    }

    public void setUserProfileChanagePrefrenceButtonUpdatePrefrence(String userProfileChanagePrefrenceButtonUpdatePrefrence) {
        this.userProfileChanagePrefrenceButtonUpdatePrefrence = userProfileChanagePrefrenceButtonUpdatePrefrence;
    }

}
