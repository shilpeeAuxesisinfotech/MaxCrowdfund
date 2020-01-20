
package com.auxesis.maxcrowdfund.mvvm.ui.changePreference.changePreferenceModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("language")
    @Expose
    private Language language;
    @SerializedName("active_account")
    @Expose
    private ActiveAccount activeAccount;
    @SerializedName("transaction_signing")
    @Expose
    private TransactionSigning transactionSigning;
    @SerializedName("joint_account")
    @Expose
    private JointAccount jointAccount;
    @SerializedName("notification_preferences")
    @Expose
    private NotificationPreferences notificationPreferences;

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public ActiveAccount getActiveAccount() {
        return activeAccount;
    }

    public void setActiveAccount(ActiveAccount activeAccount) {
        this.activeAccount = activeAccount;
    }

    public TransactionSigning getTransactionSigning() {
        return transactionSigning;
    }

    public void setTransactionSigning(TransactionSigning transactionSigning) {
        this.transactionSigning = transactionSigning;
    }

    public JointAccount getJointAccount() {
        return jointAccount;
    }

    public void setJointAccount(JointAccount jointAccount) {
        this.jointAccount = jointAccount;
    }

    public NotificationPreferences getNotificationPreferences() {
        return notificationPreferences;
    }

    public void setNotificationPreferences(NotificationPreferences notificationPreferences) {
        this.notificationPreferences = notificationPreferences;
    }

}
