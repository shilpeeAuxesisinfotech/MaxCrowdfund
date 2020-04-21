package com.auxesis.maxcrowdfund.mvvm.ui.home.oppmodel;

import static com.auxesis.maxcrowdfund.constant.APIUrl.investment_status;

public class InvestmentOppModel {
    String id;
    String mTitle;
    String interest_pa;
    String risk_class;
    String amount;
    String currency;
    String currency_symbol;
    int filled;
    int no_of_investors;
    String amount_left;
    String months;
    String type;
    String location;
    String location_flag_img;
    String main_img;
    String total_raised;
    int active_investors;
    String average_return;
    String fundraiser_type;
    String investment_status;
    String mDefaults;
    boolean isTypeData;

    public boolean isTypeData() {
        return isTypeData;
    }

    public void setTypeData(boolean typeData) {
        isTypeData = typeData;
    }

    public String getmDefaults() {
        return mDefaults;
    }

    public void setmDefaults(String mDefaults) {
        this.mDefaults = mDefaults;
    }

    public String getFundraiser_type() {
        return fundraiser_type;
    }

    public void setFundraiser_type(String fundraiser_type) {
        this.fundraiser_type = fundraiser_type;
    }

    public int mViewHolderType;

    public int getmViewHolderType() {
        return mViewHolderType;
    }

    public void setmViewHolderType(int mViewHolderType) {
        this.mViewHolderType = mViewHolderType;
    }

    public String getInvestment_status() {
        return investment_status;
    }

    public void setInvestment_status(String investment_status) {
        this.investment_status = investment_status;
    }

    public InvestmentOppModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getInterest_pa() {
        return interest_pa;
    }

    public void setInterest_pa(String interest_pa) {
        this.interest_pa = interest_pa;
    }

    public String getRisk_class() {
        return risk_class;
    }

    public void setRisk_class(String risk_class) {
        this.risk_class = risk_class;
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

    public String getCurrency_symbol() {
        return currency_symbol;
    }

    public void setCurrency_symbol(String currency_symbol) {
        this.currency_symbol = currency_symbol;
    }

    public int getFilled() {
        return filled;
    }

    public void setFilled(int filled) {
        this.filled = filled;
    }

    public int getNo_of_investors() {
        return no_of_investors;
    }

    public void setNo_of_investors(int no_of_investors) {
        this.no_of_investors = no_of_investors;
    }

    public String getAmount_left() {
        return amount_left;
    }

    public void setAmount_left(String amount_left) {
        this.amount_left = amount_left;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation_flag_img() {
        return location_flag_img;
    }

    public void setLocation_flag_img(String location_flag_img) {
        this.location_flag_img = location_flag_img;
    }

    public String getMain_img() {
        return main_img;
    }

    public void setMain_img(String main_img) {
        this.main_img = main_img;
    }

    public String getTotal_raised() {
        return total_raised;
    }

    public void setTotal_raised(String total_raised) {
        this.total_raised = total_raised;
    }

    public int getActive_investors() {
        return active_investors;
    }

    public void setActive_investors(int active_investors) {
        this.active_investors = active_investors;
    }

    public String getAverage_return() {
        return average_return;
    }

    public void setAverage_return(String average_return) {
        this.average_return = average_return;
    }
}
