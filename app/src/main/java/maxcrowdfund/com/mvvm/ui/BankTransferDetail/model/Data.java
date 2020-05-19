
package maxcrowdfund.com.mvvm.ui.BankTransferDetail.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("short_message")
    @Expose
    private ShortMessage shortMessage;
    @SerializedName("account_number")
    @Expose
    private AccountNumber accountNumber;
    @SerializedName("benificiary_name")
    @Expose
    private BenificiaryName benificiaryName;
    @SerializedName("bicswift")
    @Expose
    private Bicswift bicswift;
    @SerializedName("reference")
    @Expose
    private Reference reference;
    @SerializedName("long_message")
    @Expose
    private LongMessage longMessage;
    @SerializedName("back_url")
    @Expose
    private BackUrl backUrl;

    public ShortMessage getShortMessage() {
        return shortMessage;
    }

    public void setShortMessage(ShortMessage shortMessage) {
        this.shortMessage = shortMessage;
    }

    public AccountNumber getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(AccountNumber accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BenificiaryName getBenificiaryName() {
        return benificiaryName;
    }

    public void setBenificiaryName(BenificiaryName benificiaryName) {
        this.benificiaryName = benificiaryName;
    }

    public Bicswift getBicswift() {
        return bicswift;
    }

    public void setBicswift(Bicswift bicswift) {
        this.bicswift = bicswift;
    }

    public Reference getReference() {
        return reference;
    }

    public void setReference(Reference reference) {
        this.reference = reference;
    }

    public LongMessage getLongMessage() {
        return longMessage;
    }

    public void setLongMessage(LongMessage longMessage) {
        this.longMessage = longMessage;
    }

    public BackUrl getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(BackUrl backUrl) {
        this.backUrl = backUrl;
    }

}
