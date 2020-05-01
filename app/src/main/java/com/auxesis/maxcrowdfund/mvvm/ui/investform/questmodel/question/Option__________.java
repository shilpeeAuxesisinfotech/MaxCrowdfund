
package com.auxesis.maxcrowdfund.mvvm.ui.investform.questmodel.question;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Option__________ {

    @SerializedName("no_maximum_is_applicable")
    @Expose
    private String noMaximumIsApplicable;
    @SerializedName("i_cannot_invest_more_than_8000_eur")
    @Expose
    private String iCannotInvestMoreThan8000Eur;
    @SerializedName("i_cannot_invest_more_than_40000_eur")
    @Expose
    private String iCannotInvestMoreThan40000Eur;

    public String getNoMaximumIsApplicable() {
        return noMaximumIsApplicable;
    }

    public void setNoMaximumIsApplicable(String noMaximumIsApplicable) {
        this.noMaximumIsApplicable = noMaximumIsApplicable;
    }

    public String getICannotInvestMoreThan8000Eur() {
        return iCannotInvestMoreThan8000Eur;
    }

    public void setICannotInvestMoreThan8000Eur(String iCannotInvestMoreThan8000Eur) {
        this.iCannotInvestMoreThan8000Eur = iCannotInvestMoreThan8000Eur;
    }

    public String getICannotInvestMoreThan40000Eur() {
        return iCannotInvestMoreThan40000Eur;
    }

    public void setICannotInvestMoreThan40000Eur(String iCannotInvestMoreThan40000Eur) {
        this.iCannotInvestMoreThan40000Eur = iCannotInvestMoreThan40000Eur;
    }

}
