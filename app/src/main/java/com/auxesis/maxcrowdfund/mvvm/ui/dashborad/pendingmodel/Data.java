
package com.auxesis.maxcrowdfund.mvvm.ui.dashborad.pendingmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("no_arrears")
    @Expose
    private NoArrears noArrears;
    @SerializedName("arrears4589")
    @Expose
    private Arrears4589 arrears4589;
    @SerializedName("arrears90179")
    @Expose
    private Arrears90179 arrears90179;
    @SerializedName("arrears180364")
    @Expose
    private Arrears180364 arrears180364;
    @SerializedName("arrears365")
    @Expose
    private Arrears365 arrears365;
    @SerializedName("reserved")
    @Expose
    private Reserved reserved;

    public NoArrears getNoArrears() {
        return noArrears;
    }

    public void setNoArrears(NoArrears noArrears) {
        this.noArrears = noArrears;
    }

    public Arrears4589 getArrears4589() {
        return arrears4589;
    }

    public void setArrears4589(Arrears4589 arrears4589) {
        this.arrears4589 = arrears4589;
    }

    public Arrears90179 getArrears90179() {
        return arrears90179;
    }

    public void setArrears90179(Arrears90179 arrears90179) {
        this.arrears90179 = arrears90179;
    }

    public Arrears180364 getArrears180364() {
        return arrears180364;
    }

    public void setArrears180364(Arrears180364 arrears180364) {
        this.arrears180364 = arrears180364;
    }

    public Arrears365 getArrears365() {
        return arrears365;
    }

    public void setArrears365(Arrears365 arrears365) {
        this.arrears365 = arrears365;
    }

    public Reserved getReserved() {
        return reserved;
    }

    public void setReserved(Reserved reserved) {
        this.reserved = reserved;
    }

}
