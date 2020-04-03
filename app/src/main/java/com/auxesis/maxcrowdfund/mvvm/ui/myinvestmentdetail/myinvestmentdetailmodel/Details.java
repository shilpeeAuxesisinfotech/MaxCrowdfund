
package com.auxesis.maxcrowdfund.mvvm.ui.myinvestmentdetail.myinvestmentdetailmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Details {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("mainheading")
    @Expose
    private Mainheading mainheading;
    @SerializedName("loan_terms")
    @Expose
    private LoanTerms loanTerms;
    @SerializedName("invested")
    @Expose
    private Invested invested;
    @SerializedName("change_cancel_allowed")
    @Expose
    private Integer changeCancelAllowed;
    @SerializedName("documents")
    @Expose
    private Documents documents;
    @SerializedName("repayment_schedule")
    @Expose
    private RepaymentSchedule repaymentSchedule;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Mainheading getMainheading() {
        return mainheading;
    }

    public void setMainheading(Mainheading mainheading) {
        this.mainheading = mainheading;
    }

    public LoanTerms getLoanTerms() {
        return loanTerms;
    }

    public void setLoanTerms(LoanTerms loanTerms) {
        this.loanTerms = loanTerms;
    }

    public Invested getInvested() {
        return invested;
    }

    public void setInvested(Invested invested) {
        this.invested = invested;
    }

    public Integer getChangeCancelAllowed() {
        return changeCancelAllowed;
    }

    public void setChangeCancelAllowed(Integer changeCancelAllowed) {
        this.changeCancelAllowed = changeCancelAllowed;
    }

    public Documents getDocuments() {
        return documents;
    }

    public void setDocuments(Documents documents) {
        this.documents = documents;
    }

    public RepaymentSchedule getRepaymentSchedule() {
        return repaymentSchedule;
    }

    public void setRepaymentSchedule(RepaymentSchedule repaymentSchedule) {
        this.repaymentSchedule = repaymentSchedule;
    }

}
