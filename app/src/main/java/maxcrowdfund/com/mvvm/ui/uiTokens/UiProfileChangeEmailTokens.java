
package maxcrowdfund.com.mvvm.ui.uiTokens;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UiProfileChangeEmailTokens {

    @SerializedName("user_profile_chenage_email_title")
    @Expose
    private String userProfileChenageEmailTitle;
    @SerializedName("user_profile_chenage_email_description")
    @Expose
    private String userProfileChenageEmailDescription;
    @SerializedName("user_profile_chenage_email_placeholder")
    @Expose
    private String userProfileChenageEmailPlaceholder;
    @SerializedName("user_profile_chenage_email_submit_button")
    @Expose
    private String userProfileChenageEmailSubmitButton;

    public String getUserProfileChenageEmailTitle() {
        return userProfileChenageEmailTitle;
    }

    public void setUserProfileChenageEmailTitle(String userProfileChenageEmailTitle) {
        this.userProfileChenageEmailTitle = userProfileChenageEmailTitle;
    }

    public String getUserProfileChenageEmailDescription() {
        return userProfileChenageEmailDescription;
    }

    public void setUserProfileChenageEmailDescription(String userProfileChenageEmailDescription) {
        this.userProfileChenageEmailDescription = userProfileChenageEmailDescription;
    }

    public String getUserProfileChenageEmailPlaceholder() {
        return userProfileChenageEmailPlaceholder;
    }

    public void setUserProfileChenageEmailPlaceholder(String userProfileChenageEmailPlaceholder) {
        this.userProfileChenageEmailPlaceholder = userProfileChenageEmailPlaceholder;
    }

    public String getUserProfileChenageEmailSubmitButton() {
        return userProfileChenageEmailSubmitButton;
    }

    public void setUserProfileChenageEmailSubmitButton(String userProfileChenageEmailSubmitButton) {
        this.userProfileChenageEmailSubmitButton = userProfileChenageEmailSubmitButton;
    }

}
