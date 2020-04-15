
package com.auxesis.maxcrowdfund.mvvm.ui.home.homeDetail.detailmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum_____ {

    @SerializedName("risk")
    @Expose
    private Risk_ risk;
    @SerializedName("measure")
    @Expose
    private Measure measure;
    @SerializedName("score")
    @Expose
    private Score score;

    public Risk_ getRisk() {
        return risk;
    }

    public void setRisk(Risk_ risk) {
        this.risk = risk;
    }

    public Measure getMeasure() {
        return measure;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

}
