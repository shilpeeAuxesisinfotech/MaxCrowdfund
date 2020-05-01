
package com.auxesis.maxcrowdfund.mvvm.ui.investform.questmodel.question;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Option_____ {

    @SerializedName("i_can_have_my_loan_repaid")
    @Expose
    private String iCanHaveMyLoanRepaid;
    @SerializedName("i_can_compensate_for_this_from_other_income")
    @Expose
    private String iCanCompensateForThisFromOtherIncome;

    public String getICanHaveMyLoanRepaid() {
        return iCanHaveMyLoanRepaid;
    }

    public void setICanHaveMyLoanRepaid(String iCanHaveMyLoanRepaid) {
        this.iCanHaveMyLoanRepaid = iCanHaveMyLoanRepaid;
    }

    public String getICanCompensateForThisFromOtherIncome() {
        return iCanCompensateForThisFromOtherIncome;
    }

    public void setICanCompensateForThisFromOtherIncome(String iCanCompensateForThisFromOtherIncome) {
        this.iCanCompensateForThisFromOtherIncome = iCanCompensateForThisFromOtherIncome;
    }

}
