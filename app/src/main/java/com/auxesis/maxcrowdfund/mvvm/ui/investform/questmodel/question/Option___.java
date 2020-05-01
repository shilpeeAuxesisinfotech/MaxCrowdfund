
package com.auxesis.maxcrowdfund.mvvm.ui.investform.questmodel.question;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Option___ {

    @SerializedName("this_has_no_consequences")
    @Expose
    private String thisHasNoConsequences;
    @SerializedName("i_can_lose_my_investment")
    @Expose
    private String iCanLoseMyInvestment;

    public String getThisHasNoConsequences() {
        return thisHasNoConsequences;
    }

    public void setThisHasNoConsequences(String thisHasNoConsequences) {
        this.thisHasNoConsequences = thisHasNoConsequences;
    }

    public String getICanLoseMyInvestment() {
        return iCanLoseMyInvestment;
    }

    public void setICanLoseMyInvestment(String iCanLoseMyInvestment) {
        this.iCanLoseMyInvestment = iCanLoseMyInvestment;
    }

}
