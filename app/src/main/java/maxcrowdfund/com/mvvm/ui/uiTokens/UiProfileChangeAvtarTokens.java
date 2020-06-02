
package maxcrowdfund.com.mvvm.ui.uiTokens;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UiProfileChangeAvtarTokens {

    @SerializedName("user_profile_change_avtar_title")
    @Expose
    private String userProfileChangeAvtarTitle;
    @SerializedName("user_profile_change_avtar_button")
    @Expose
    private String userProfileChangeAvtarButton;

    public String getUserProfileChangeAvtarTitle() {
        return userProfileChangeAvtarTitle;
    }

    public void setUserProfileChangeAvtarTitle(String userProfileChangeAvtarTitle) {
        this.userProfileChangeAvtarTitle = userProfileChangeAvtarTitle;
    }

    public String getUserProfileChangeAvtarButton() {
        return userProfileChangeAvtarButton;
    }

    public void setUserProfileChangeAvtarButton(String userProfileChangeAvtarButton) {
        this.userProfileChangeAvtarButton = userProfileChangeAvtarButton;
    }

}
