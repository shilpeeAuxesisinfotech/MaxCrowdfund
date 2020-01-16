
package com.auxesis.maxcrowdfund.mvvm.ui.home.homemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("interest_pa")
    @Expose
    private String interestPa;
    @SerializedName("risk_class")
    @Expose
    private String riskClass;
    @SerializedName("amount")
    @Expose
    private Integer amount;
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
    private Integer months;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("location_flag_img")
    @Expose
    private String locationFlagImg;
    @SerializedName("main_img")
    @Expose
    private String mainImg;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
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

    public Integer getMonths() {
        return months;
    }

    public void setMonths(Integer months) {
        this.months = months;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

}
