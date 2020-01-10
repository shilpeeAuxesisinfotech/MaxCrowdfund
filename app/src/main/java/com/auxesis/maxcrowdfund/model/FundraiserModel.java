package com.auxesis.maxcrowdfund.model;

public class FundraiserModel {
    String mfundraiserTitle;
    String mfundraiserValue;

    public FundraiserModel(String mfundraiserTitle, String mfundraiserValue) {
        this.mfundraiserTitle = mfundraiserTitle;
        this.mfundraiserValue = mfundraiserValue;
    }

    public String getMfundraiserTitle() {
        return mfundraiserTitle;
    }

    public String getMfundraiserValue() {
        return mfundraiserValue;
    }
}
