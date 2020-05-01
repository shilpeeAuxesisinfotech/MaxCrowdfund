
package com.auxesis.maxcrowdfund.mvvm.ui.investform.questmodel.question;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Option_ {

    @SerializedName("less_than_1_year")
    @Expose
    private String lessThan1Year;
    @SerializedName("between_1_and_3_years")
    @Expose
    private String between1And3Years;
    @SerializedName("more_than_3_year")
    @Expose
    private String moreThan3Year;

    public String getLessThan1Year() {
        return lessThan1Year;
    }

    public void setLessThan1Year(String lessThan1Year) {
        this.lessThan1Year = lessThan1Year;
    }

    public String getBetween1And3Years() {
        return between1And3Years;
    }

    public void setBetween1And3Years(String between1And3Years) {
        this.between1And3Years = between1And3Years;
    }

    public String getMoreThan3Year() {
        return moreThan3Year;
    }

    public void setMoreThan3Year(String moreThan3Year) {
        this.moreThan3Year = moreThan3Year;
    }

}
