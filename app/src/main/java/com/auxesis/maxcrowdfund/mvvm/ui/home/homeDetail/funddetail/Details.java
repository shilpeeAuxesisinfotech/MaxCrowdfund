
package com.auxesis.maxcrowdfund.mvvm.ui.home.homeDetail.funddetail;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Details {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("interest_pa")
    @Expose
    private String interestPa;
    @SerializedName("risk_class")
    @Expose
    private String riskClass;
    @SerializedName("risk_class_colour_code")
    @Expose
    private String riskClassColourCode;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("currency_symbol")
    @Expose
    private String currencySymbol;
    @SerializedName("filled")
    @Expose
    private Integer filled;
    @SerializedName("no_of_investors")
    @Expose
    private Integer noOfInvestors;
    @SerializedName("amount_left")
    @Expose
    private Integer amountLeft;
    @SerializedName("months")
    @Expose
    private String months;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("invest_button_check")
    @Expose
    private Integer investButtonCheck;
    @SerializedName("error_msg")
    @Expose
    private String errorMsg;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("location_flag_img")
    @Expose
    private String locationFlagImg;
    @SerializedName("main_img")
    @Expose
    private String mainImg;
    @SerializedName("other_photos_videos")
    @Expose
    private List<OtherPhotosVideo> otherPhotosVideos = null;
    @SerializedName("summary")
    @Expose
    private Summary summary;
    @SerializedName("loan_terms")
    @Expose
    private LoanTerms loanTerms;
    @SerializedName("collateral")
    @Expose
    private Collateral collateral;
    @SerializedName("fundraiser")
    @Expose
    private Fundraiser fundraiser;
    @SerializedName("investment_plan")
    @Expose
    private InvestmentPlan investmentPlan;
    @SerializedName("risk")
    @Expose
    private Risk risk;
    @SerializedName("documents")
    @Expose
    private Documents documents;
    @SerializedName("last_investment")
    @Expose
    private LastInvestment lastInvestment;
    @SerializedName("invested_total_amount_on_loan")
    @Expose
    private Integer investedTotalAmountOnLoan;
    @SerializedName("payment_conditions")
    @Expose
    private PaymentConditions paymentConditions;
    @SerializedName("additional_terms_condition")
    @Expose
    private AdditionalTermsCondition additionalTermsCondition;
    @SerializedName("pitch_securities")
    @Expose
    private PitchSecurities pitchSecurities;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInterestPa() {
        return interestPa;
    }

    public void setInterestPa(String interestPa) {
        this.interestPa = interestPa;
    }

    public String getRiskClass() {
        return riskClass;
    }

    public void setRiskClass(String riskClass) {
        this.riskClass = riskClass;
    }

    public String getRiskClassColourCode() {
        return riskClassColourCode;
    }

    public void setRiskClassColourCode(String riskClassColourCode) {
        this.riskClassColourCode = riskClassColourCode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    public Integer getFilled() {
        return filled;
    }

    public void setFilled(Integer filled) {
        this.filled = filled;
    }

    public Integer getNoOfInvestors() {
        return noOfInvestors;
    }

    public void setNoOfInvestors(Integer noOfInvestors) {
        this.noOfInvestors = noOfInvestors;
    }

    public Integer getAmountLeft() {
        return amountLeft;
    }

    public void setAmountLeft(Integer amountLeft) {
        this.amountLeft = amountLeft;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getInvestButtonCheck() {
        return investButtonCheck;
    }

    public void setInvestButtonCheck(Integer investButtonCheck) {
        this.investButtonCheck = investButtonCheck;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationFlagImg() {
        return locationFlagImg;
    }

    public void setLocationFlagImg(String locationFlagImg) {
        this.locationFlagImg = locationFlagImg;
    }

    public String getMainImg() {
        return mainImg;
    }

    public void setMainImg(String mainImg) {
        this.mainImg = mainImg;
    }

    public List<OtherPhotosVideo> getOtherPhotosVideos() {
        return otherPhotosVideos;
    }

    public void setOtherPhotosVideos(List<OtherPhotosVideo> otherPhotosVideos) {
        this.otherPhotosVideos = otherPhotosVideos;
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public LoanTerms getLoanTerms() {
        return loanTerms;
    }

    public void setLoanTerms(LoanTerms loanTerms) {
        this.loanTerms = loanTerms;
    }

    public Collateral getCollateral() {
        return collateral;
    }

    public void setCollateral(Collateral collateral) {
        this.collateral = collateral;
    }

    public Fundraiser getFundraiser() {
        return fundraiser;
    }

    public void setFundraiser(Fundraiser fundraiser) {
        this.fundraiser = fundraiser;
    }

    public InvestmentPlan getInvestmentPlan() {
        return investmentPlan;
    }

    public void setInvestmentPlan(InvestmentPlan investmentPlan) {
        this.investmentPlan = investmentPlan;
    }

    public Risk getRisk() {
        return risk;
    }

    public void setRisk(Risk risk) {
        this.risk = risk;
    }

    public Documents getDocuments() {
        return documents;
    }

    public void setDocuments(Documents documents) {
        this.documents = documents;
    }

    public LastInvestment getLastInvestment() {
        return lastInvestment;
    }

    public void setLastInvestment(LastInvestment lastInvestment) {
        this.lastInvestment = lastInvestment;
    }

    public Integer getInvestedTotalAmountOnLoan() {
        return investedTotalAmountOnLoan;
    }

    public void setInvestedTotalAmountOnLoan(Integer investedTotalAmountOnLoan) {
        this.investedTotalAmountOnLoan = investedTotalAmountOnLoan;
    }

    public PaymentConditions getPaymentConditions() {
        return paymentConditions;
    }

    public void setPaymentConditions(PaymentConditions paymentConditions) {
        this.paymentConditions = paymentConditions;
    }

    public AdditionalTermsCondition getAdditionalTermsCondition() {
        return additionalTermsCondition;
    }

    public void setAdditionalTermsCondition(AdditionalTermsCondition additionalTermsCondition) {
        this.additionalTermsCondition = additionalTermsCondition;
    }

    public PitchSecurities getPitchSecurities() {
        return pitchSecurities;
    }

    public void setPitchSecurities(PitchSecurities pitchSecurities) {
        this.pitchSecurities = pitchSecurities;
    }

}
