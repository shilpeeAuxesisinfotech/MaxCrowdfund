
package maxcrowdfund.com.mvvm.ui.investform.questmodel.questionlistmodel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InvestAmountKeyUpResponse {

    @SerializedName("user_login_status")
    @Expose
    private Integer userLoginStatus;
    @SerializedName("invest_create_check")
    @Expose
    private Integer investCreateCheck;
    @SerializedName("total_charge_mpg_token")
    @Expose
    private Double totalChargeMpgToken;
    @SerializedName("required_extra_euro_amount_of_mpg")
    @Expose
    private Double requiredExtraEuroAmountOfMpg;
    @SerializedName("sum_of_euro_mpg_and_amount")
    @Expose
    private Double sumOfEuroMpgAndAmount;
    @SerializedName("error_msg")
    @Expose
    private String errorMsg;
    @SerializedName("token_required")
    @Expose
    private Integer tokenRequired;

    public Integer getUserLoginStatus() {
        return userLoginStatus;
    }

    public void setUserLoginStatus(Integer userLoginStatus) {
        this.userLoginStatus = userLoginStatus;
    }

    public Integer getInvestCreateCheck() {
        return investCreateCheck;
    }

    public void setInvestCreateCheck(Integer investCreateCheck) {
        this.investCreateCheck = investCreateCheck;
    }

    public Double getTotalChargeMpgToken() {
        return totalChargeMpgToken;
    }

    public void setTotalChargeMpgToken(Double totalChargeMpgToken) {
        this.totalChargeMpgToken = totalChargeMpgToken;
    }

    public Double getRequiredExtraEuroAmountOfMpg() {
        return requiredExtraEuroAmountOfMpg;
    }

    public void setRequiredExtraEuroAmountOfMpg(Double requiredExtraEuroAmountOfMpg) {
        this.requiredExtraEuroAmountOfMpg = requiredExtraEuroAmountOfMpg;
    }

    public Double getSumOfEuroMpgAndAmount() {
        return sumOfEuroMpgAndAmount;
    }

    public void setSumOfEuroMpgAndAmount(Double sumOfEuroMpgAndAmount) {
        this.sumOfEuroMpgAndAmount = sumOfEuroMpgAndAmount;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Integer getTokenRequired() {
        return tokenRequired;
    }

    public void setTokenRequired(Integer tokenRequired) {
        this.tokenRequired = tokenRequired;
    }

}
