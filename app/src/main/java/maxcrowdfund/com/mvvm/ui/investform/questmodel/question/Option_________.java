
package maxcrowdfund.com.mvvm.ui.investform.questmodel.question;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Option_________ {

    @SerializedName("i_m_not_allowed_to_change_or_cancel_my_investment")
    @Expose
    private String iMNotAllowedToChangeOrCancelMyInvestment;
    @SerializedName("i_can_change_or_cancel_within_24_hours")
    @Expose
    private String iCanChangeOrCancelWithin24Hours;
    @SerializedName("i_can_change_or_cancel_while_the_loan_is_not_final")
    @Expose
    private String iCanChangeOrCancelWhileTheLoanIsNotFinal;

    public String getIMNotAllowedToChangeOrCancelMyInvestment() {
        return iMNotAllowedToChangeOrCancelMyInvestment;
    }

    public void setIMNotAllowedToChangeOrCancelMyInvestment(String iMNotAllowedToChangeOrCancelMyInvestment) {
        this.iMNotAllowedToChangeOrCancelMyInvestment = iMNotAllowedToChangeOrCancelMyInvestment;
    }

    public String getICanChangeOrCancelWithin24Hours() {
        return iCanChangeOrCancelWithin24Hours;
    }

    public void setICanChangeOrCancelWithin24Hours(String iCanChangeOrCancelWithin24Hours) {
        this.iCanChangeOrCancelWithin24Hours = iCanChangeOrCancelWithin24Hours;
    }

    public String getICanChangeOrCancelWhileTheLoanIsNotFinal() {
        return iCanChangeOrCancelWhileTheLoanIsNotFinal;
    }

    public void setICanChangeOrCancelWhileTheLoanIsNotFinal(String iCanChangeOrCancelWhileTheLoanIsNotFinal) {
        this.iCanChangeOrCancelWhileTheLoanIsNotFinal = iCanChangeOrCancelWhileTheLoanIsNotFinal;
    }

}
