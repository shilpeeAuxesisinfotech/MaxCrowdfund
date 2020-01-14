
package com.auxesis.maxcrowdfund.mvvm.ui.changebankaccount.changebankaccountmodel;

import com.auxesis.maxcrowdfund.mvvm.ui.changebankaccount.changebankaccountmodel.BankAccounts;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangeBankAccountResponse {

    @SerializedName("bank_accounts")
    @Expose
    private BankAccounts bankAccounts;

    public BankAccounts getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(BankAccounts bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

}
