
package com.auxesis.maxcrowdfund.mvvm.ui.investform.questmodel.question;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Option________ {

    @SerializedName("i_invest_the_entire_amount_in_one_loan")
    @Expose
    private String iInvestTheEntireAmountInOneLoan;
    @SerializedName("i_spread_the_amount_over_several_loans")
    @Expose
    private String iSpreadTheAmountOverSeveralLoans;

    public String getIInvestTheEntireAmountInOneLoan() {
        return iInvestTheEntireAmountInOneLoan;
    }

    public void setIInvestTheEntireAmountInOneLoan(String iInvestTheEntireAmountInOneLoan) {
        this.iInvestTheEntireAmountInOneLoan = iInvestTheEntireAmountInOneLoan;
    }

    public String getISpreadTheAmountOverSeveralLoans() {
        return iSpreadTheAmountOverSeveralLoans;
    }

    public void setISpreadTheAmountOverSeveralLoans(String iSpreadTheAmountOverSeveralLoans) {
        this.iSpreadTheAmountOverSeveralLoans = iSpreadTheAmountOverSeveralLoans;
    }

}
