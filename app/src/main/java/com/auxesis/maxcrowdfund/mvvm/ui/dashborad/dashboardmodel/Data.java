
package com.auxesis.maxcrowdfund.mvvm.ui.dashborad.dashboardmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("total_balance")
    @Expose
    private TotalBalance totalBalance;
    @SerializedName("deposited")
    @Expose
    private Deposited deposited;
    @SerializedName("withdrawn")
    @Expose
    private Withdrawn withdrawn;
    @SerializedName("invested")
    @Expose
    private Invested invested;
    @SerializedName("pending")
    @Expose
    private Pending pending;
    @SerializedName("fees")
    @Expose
    private Fees fees;
    @SerializedName("repaid")
    @Expose
    private Repaid repaid;
    @SerializedName("interest_paid")
    @Expose
    private InterestPaid interestPaid;
    @SerializedName("reserved")
    @Expose
    private Reserved reserved;
    @SerializedName("written_off")
    @Expose
    private WrittenOff writtenOff;
    @SerializedName("mpg_purchase")
    @Expose
    private MpgPurchase mpgPurchase;
    @SerializedName("mpgs_purchase")
    @Expose
    private MpgsPurchase mpgsPurchase;

    public TotalBalance getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(TotalBalance totalBalance) {
        this.totalBalance = totalBalance;
    }

    public Deposited getDeposited() {
        return deposited;
    }

    public void setDeposited(Deposited deposited) {
        this.deposited = deposited;
    }

    public Withdrawn getWithdrawn() {
        return withdrawn;
    }

    public void setWithdrawn(Withdrawn withdrawn) {
        this.withdrawn = withdrawn;
    }

    public Invested getInvested() {
        return invested;
    }

    public void setInvested(Invested invested) {
        this.invested = invested;
    }

    public Pending getPending() {
        return pending;
    }

    public void setPending(Pending pending) {
        this.pending = pending;
    }

    public Fees getFees() {
        return fees;
    }

    public void setFees(Fees fees) {
        this.fees = fees;
    }

    public Repaid getRepaid() {
        return repaid;
    }

    public void setRepaid(Repaid repaid) {
        this.repaid = repaid;
    }

    public InterestPaid getInterestPaid() {
        return interestPaid;
    }

    public void setInterestPaid(InterestPaid interestPaid) {
        this.interestPaid = interestPaid;
    }

    public Reserved getReserved() {
        return reserved;
    }

    public void setReserved(Reserved reserved) {
        this.reserved = reserved;
    }

    public WrittenOff getWrittenOff() {
        return writtenOff;
    }

    public void setWrittenOff(WrittenOff writtenOff) {
        this.writtenOff = writtenOff;
    }

    public MpgPurchase getMpgPurchase() {
        return mpgPurchase;
    }

    public void setMpgPurchase(MpgPurchase mpgPurchase) {
        this.mpgPurchase = mpgPurchase;
    }

    public MpgsPurchase getMpgsPurchase() {
        return mpgsPurchase;
    }

    public void setMpgsPurchase(MpgsPurchase mpgsPurchase) {
        this.mpgsPurchase = mpgsPurchase;
    }

}
