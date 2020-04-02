
package com.auxesis.maxcrowdfund.mvvm.ui.home.homemodel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InvestmentOppResponse {

    @SerializedName("total_raised")
    @Expose
    private Integer totalRaised;
    @SerializedName("active_investors")
    @Expose
    private Integer activeInvestors;
    @SerializedName("average_return")
    @Expose
    private Integer averageReturn;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("fundraiser_type")
    @Expose
    private String fundraiserType;

    public Integer getTotalRaised() {
        return totalRaised;
    }

    public void setTotalRaised(Integer totalRaised) {
        this.totalRaised = totalRaised;
    }

    public Integer getActiveInvestors() {
        return activeInvestors;
    }

    public void setActiveInvestors(Integer activeInvestors) {
        this.activeInvestors = activeInvestors;
    }

    public Integer getAverageReturn() {
        return averageReturn;
    }

    public void setAverageReturn(Integer averageReturn) {
        this.averageReturn = averageReturn;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public String getFundraiserType() {
        return fundraiserType;
    }

    public void setFundraiserType(String fundraiserType) {
        this.fundraiserType = fundraiserType;
    }

}
