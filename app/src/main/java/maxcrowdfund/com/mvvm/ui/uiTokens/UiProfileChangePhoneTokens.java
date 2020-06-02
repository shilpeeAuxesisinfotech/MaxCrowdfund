
package maxcrowdfund.com.mvvm.ui.uiTokens;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UiProfileChangePhoneTokens {

    @SerializedName("user_profile_chanage_password_title")
    @Expose
    private String userProfileChanagePasswordTitle;
    @SerializedName("user_profile_chanage_password_placeholder_old_password")
    @Expose
    private String userProfileChanagePasswordPlaceholderOldPassword;
    @SerializedName("user_profile_chanage_password_placeholder_new_password")
    @Expose
    private String userProfileChanagePasswordPlaceholderNewPassword;
    @SerializedName("user_profile_chanage_password_placeholder_confirm_password")
    @Expose
    private String userProfileChanagePasswordPlaceholderConfirmPassword;
    @SerializedName("user_profile_chanage_password_button_submit")
    @Expose
    private String userProfileChanagePasswordButtonSubmit;

    public String getUserProfileChanagePasswordTitle() {
        return userProfileChanagePasswordTitle;
    }

    public void setUserProfileChanagePasswordTitle(String userProfileChanagePasswordTitle) {
        this.userProfileChanagePasswordTitle = userProfileChanagePasswordTitle;
    }

    public String getUserProfileChanagePasswordPlaceholderOldPassword() {
        return userProfileChanagePasswordPlaceholderOldPassword;
    }

    public void setUserProfileChanagePasswordPlaceholderOldPassword(String userProfileChanagePasswordPlaceholderOldPassword) {
        this.userProfileChanagePasswordPlaceholderOldPassword = userProfileChanagePasswordPlaceholderOldPassword;
    }

    public String getUserProfileChanagePasswordPlaceholderNewPassword() {
        return userProfileChanagePasswordPlaceholderNewPassword;
    }

    public void setUserProfileChanagePasswordPlaceholderNewPassword(String userProfileChanagePasswordPlaceholderNewPassword) {
        this.userProfileChanagePasswordPlaceholderNewPassword = userProfileChanagePasswordPlaceholderNewPassword;
    }

    public String getUserProfileChanagePasswordPlaceholderConfirmPassword() {
        return userProfileChanagePasswordPlaceholderConfirmPassword;
    }

    public void setUserProfileChanagePasswordPlaceholderConfirmPassword(String userProfileChanagePasswordPlaceholderConfirmPassword) {
        this.userProfileChanagePasswordPlaceholderConfirmPassword = userProfileChanagePasswordPlaceholderConfirmPassword;
    }

    public String getUserProfileChanagePasswordButtonSubmit() {
        return userProfileChanagePasswordButtonSubmit;
    }

    public void setUserProfileChanagePasswordButtonSubmit(String userProfileChanagePasswordButtonSubmit) {
        this.userProfileChanagePasswordButtonSubmit = userProfileChanagePasswordButtonSubmit;
    }

}
