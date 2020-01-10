
package com.auxesis.maxcrowdfund.custommvvm.myinvestmentmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("loanid")
    @Expose
    private Integer loanid;
    @SerializedName("loan")
    @Expose
    private String loan;
    @SerializedName("invested")
    @Expose
    private Integer invested;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("currency_symbol")
    @Expose
    private String currencySymbol;

    public Integer getLoanid() {
        return loanid;
    }

    public void setLoanid(Integer loanid) {
        this.loanid = loanid;
    }

    public String getLoan() {
        return loan;
    }

    public void setLoan(String loan) {
        this.loan = loan;
    }

    public Integer getInvested() {
        return invested;
    }

    public void setInvested(Integer invested) {
        this.invested = invested;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

}
