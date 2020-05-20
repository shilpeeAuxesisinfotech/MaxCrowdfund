
package maxcrowdfund.com.mvvm.ui.dashborad.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data_ {

    @SerializedName("portfolio")
    @Expose
    private Portfolio_ portfolio;
    @SerializedName("no_arrear")
    @Expose
    private NoArrear noArrear;
    @SerializedName("arrears_45_89_days")
    @Expose
    private Arrears4589Days arrears4589Days;
    @SerializedName("arrears_90_179_days")
    @Expose
    private Arrears90179Days arrears90179Days;
    @SerializedName("arrears_190_364_days")
    @Expose
    private Arrears190364Days arrears190364Days;
    @SerializedName("arrears_365_days")
    @Expose
    private Arrears365Days arrears365Days;
    @SerializedName("total_receivable")
    @Expose
    private TotalReceivable totalReceivable;
    @SerializedName("reserved")
    @Expose
    private Reserved_ reserved;
    @SerializedName("portfolio_value")
    @Expose
    private PortfolioValue portfolioValue;

    public Portfolio_ getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio_ portfolio) {
        this.portfolio = portfolio;
    }

    public NoArrear getNoArrear() {
        return noArrear;
    }

    public void setNoArrear(NoArrear noArrear) {
        this.noArrear = noArrear;
    }

    public Arrears4589Days getArrears4589Days() {
        return arrears4589Days;
    }

    public void setArrears4589Days(Arrears4589Days arrears4589Days) {
        this.arrears4589Days = arrears4589Days;
    }

    public Arrears90179Days getArrears90179Days() {
        return arrears90179Days;
    }

    public void setArrears90179Days(Arrears90179Days arrears90179Days) {
        this.arrears90179Days = arrears90179Days;
    }

    public Arrears190364Days getArrears190364Days() {
        return arrears190364Days;
    }

    public void setArrears190364Days(Arrears190364Days arrears190364Days) {
        this.arrears190364Days = arrears190364Days;
    }

    public Arrears365Days getArrears365Days() {
        return arrears365Days;
    }

    public void setArrears365Days(Arrears365Days arrears365Days) {
        this.arrears365Days = arrears365Days;
    }

    public TotalReceivable getTotalReceivable() {
        return totalReceivable;
    }

    public void setTotalReceivable(TotalReceivable totalReceivable) {
        this.totalReceivable = totalReceivable;
    }

    public Reserved_ getReserved() {
        return reserved;
    }

    public void setReserved(Reserved_ reserved) {
        this.reserved = reserved;
    }

    public PortfolioValue getPortfolioValue() {
        return portfolioValue;
    }

    public void setPortfolioValue(PortfolioValue portfolioValue) {
        this.portfolioValue = portfolioValue;
    }

}
