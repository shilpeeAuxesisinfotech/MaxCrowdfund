
package com.auxesis.maxcrowdfund.mvvm.ui.home.oppmodel;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InvestmentOppRes {
    @SerializedName("user_login_status")
    @Expose
    private Integer userLoginStatus;
    @SerializedName("total_raised")
    @Expose
    private String totalRaised;
    @SerializedName("active_investors")
    @Expose
    private Integer activeInvestors;
    @SerializedName("average_return")
    @Expose
    private String averageReturn;
    @SerializedName("defaults")
    @Expose
    private String defaults;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("fundraiser_type")
    @Expose
    private String fundraiserType;

    public Integer getUserLoginStatus() {
        return userLoginStatus;
    }

    public void setUserLoginStatus(Integer userLoginStatus) {
        this.userLoginStatus = userLoginStatus;
    }

    public String getTotalRaised() {
        return totalRaised;
    }

    public void setTotalRaised(String totalRaised) {
        this.totalRaised = totalRaised;
    }

    public Integer getActiveInvestors() {
        return activeInvestors;
    }

    public void setActiveInvestors(Integer activeInvestors) {
        this.activeInvestors = activeInvestors;
    }

    public String getAverageReturn() {
        return averageReturn;
    }

    public void setAverageReturn(String averageReturn) {
        this.averageReturn = averageReturn;
    }

    public String getDefaults() {
        return defaults;
    }

    public void setDefaults(String defaults) {
        this.defaults = defaults;
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
