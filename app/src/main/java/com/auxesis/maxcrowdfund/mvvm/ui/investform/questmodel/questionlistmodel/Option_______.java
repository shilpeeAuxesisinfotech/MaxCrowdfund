
package com.auxesis.maxcrowdfund.mvvm.ui.investform.questmodel.questionlistmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Option_______ {

    @SerializedName("more_than_25%")
    @Expose
    private String moreThan25;
    @SerializedName("between_10%_and_25%")
    @Expose
    private String between10And25;
    @SerializedName("up_to_10%")
    @Expose
    private String upTo10;

    public String getMoreThan25() {
        return moreThan25;
    }

    public void setMoreThan25(String moreThan25) {
        this.moreThan25 = moreThan25;
    }

    public String getBetween10And25() {
        return between10And25;
    }

    public void setBetween10And25(String between10And25) {
        this.between10And25 = between10And25;
    }

    public String getUpTo10() {
        return upTo10;
    }

    public void setUpTo10(String upTo10) {
        this.upTo10 = upTo10;
    }

}
