
package maxcrowdfund.com.mvvm.ui.uiTokens;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UiProfileChangeDefaultBankAccountTokens {

    @SerializedName("user_profile_chanage_default_bank_account_title")
    @Expose
    private String userProfileChanageDefaultBankAccountTitle;
    @SerializedName("user_profile_chanage_default_bank_account_label_default")
    @Expose
    private String userProfileChanageDefaultBankAccountLabelDefault;
    @SerializedName("user_profile_chanage_default_bank_account_label_bank_account_number")
    @Expose
    private String userProfileChanageDefaultBankAccountLabelBankAccountNumber;
    @SerializedName("user_profile_chanage_default_bank_account_button_save_changes")
    @Expose
    private String userProfileChanageDefaultBankAccountButtonSaveChanges;

    public String getUserProfileChanageDefaultBankAccountTitle() {
        return userProfileChanageDefaultBankAccountTitle;
    }

    public void setUserProfileChanageDefaultBankAccountTitle(String userProfileChanageDefaultBankAccountTitle) {
        this.userProfileChanageDefaultBankAccountTitle = userProfileChanageDefaultBankAccountTitle;
    }

    public String getUserProfileChanageDefaultBankAccountLabelDefault() {
        return userProfileChanageDefaultBankAccountLabelDefault;
    }

    public void setUserProfileChanageDefaultBankAccountLabelDefault(String userProfileChanageDefaultBankAccountLabelDefault) {
        this.userProfileChanageDefaultBankAccountLabelDefault = userProfileChanageDefaultBankAccountLabelDefault;
    }

    public String getUserProfileChanageDefaultBankAccountLabelBankAccountNumber() {
        return userProfileChanageDefaultBankAccountLabelBankAccountNumber;
    }

    public void setUserProfileChanageDefaultBankAccountLabelBankAccountNumber(String userProfileChanageDefaultBankAccountLabelBankAccountNumber) {
        this.userProfileChanageDefaultBankAccountLabelBankAccountNumber = userProfileChanageDefaultBankAccountLabelBankAccountNumber;
    }

    public String getUserProfileChanageDefaultBankAccountButtonSaveChanges() {
        return userProfileChanageDefaultBankAccountButtonSaveChanges;
    }

    public void setUserProfileChanageDefaultBankAccountButtonSaveChanges(String userProfileChanageDefaultBankAccountButtonSaveChanges) {
        this.userProfileChanageDefaultBankAccountButtonSaveChanges = userProfileChanageDefaultBankAccountButtonSaveChanges;
    }

}
