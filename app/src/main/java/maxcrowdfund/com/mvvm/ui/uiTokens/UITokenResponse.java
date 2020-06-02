
package maxcrowdfund.com.mvvm.ui.uiTokens;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UITokenResponse {

    @SerializedName("ui_login_tokens")
    @Expose
    private UiLoginTokens uiLoginTokens;
    @SerializedName("profile_page_title_tokens")
    @Expose
    private ProfilePageTitleTokens profilePageTitleTokens;
    @SerializedName("ui_profile_change_avtar_tokens")
    @Expose
    private UiProfileChangeAvtarTokens uiProfileChangeAvtarTokens;
    @SerializedName("ui_profile_change_email_tokens")
    @Expose
    private UiProfileChangeEmailTokens uiProfileChangeEmailTokens;
    @SerializedName("ui_profile_change_phone_tokens")
    @Expose
    private UiProfileChangePhoneTokens uiProfileChangePhoneTokens;
    @SerializedName("ui_profile_change_default_bank_account_tokens")
    @Expose
    private UiProfileChangeDefaultBankAccountTokens uiProfileChangeDefaultBankAccountTokens;
    @SerializedName("ui_profile_change_prefrence_tokens")
    @Expose
    private UiProfileChangePrefrenceTokens uiProfileChangePrefrenceTokens;

    public UiLoginTokens getUiLoginTokens() {
        return uiLoginTokens;
    }

    public void setUiLoginTokens(UiLoginTokens uiLoginTokens) {
        this.uiLoginTokens = uiLoginTokens;
    }

    public ProfilePageTitleTokens getProfilePageTitleTokens() {
        return profilePageTitleTokens;
    }

    public void setProfilePageTitleTokens(ProfilePageTitleTokens profilePageTitleTokens) {
        this.profilePageTitleTokens = profilePageTitleTokens;
    }

    public UiProfileChangeAvtarTokens getUiProfileChangeAvtarTokens() {
        return uiProfileChangeAvtarTokens;
    }

    public void setUiProfileChangeAvtarTokens(UiProfileChangeAvtarTokens uiProfileChangeAvtarTokens) {
        this.uiProfileChangeAvtarTokens = uiProfileChangeAvtarTokens;
    }

    public UiProfileChangeEmailTokens getUiProfileChangeEmailTokens() {
        return uiProfileChangeEmailTokens;
    }

    public void setUiProfileChangeEmailTokens(UiProfileChangeEmailTokens uiProfileChangeEmailTokens) {
        this.uiProfileChangeEmailTokens = uiProfileChangeEmailTokens;
    }

    public UiProfileChangePhoneTokens getUiProfileChangePhoneTokens() {
        return uiProfileChangePhoneTokens;
    }

    public void setUiProfileChangePhoneTokens(UiProfileChangePhoneTokens uiProfileChangePhoneTokens) {
        this.uiProfileChangePhoneTokens = uiProfileChangePhoneTokens;
    }

    public UiProfileChangeDefaultBankAccountTokens getUiProfileChangeDefaultBankAccountTokens() {
        return uiProfileChangeDefaultBankAccountTokens;
    }

    public void setUiProfileChangeDefaultBankAccountTokens(UiProfileChangeDefaultBankAccountTokens uiProfileChangeDefaultBankAccountTokens) {
        this.uiProfileChangeDefaultBankAccountTokens = uiProfileChangeDefaultBankAccountTokens;
    }

    public UiProfileChangePrefrenceTokens getUiProfileChangePrefrenceTokens() {
        return uiProfileChangePrefrenceTokens;
    }

    public void setUiProfileChangePrefrenceTokens(UiProfileChangePrefrenceTokens uiProfileChangePrefrenceTokens) {
        this.uiProfileChangePrefrenceTokens = uiProfileChangePrefrenceTokens;
    }

}
