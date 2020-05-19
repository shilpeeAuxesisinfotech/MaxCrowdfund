
package maxcrowdfund.com.mvvm.ui.investform.questmodel.question;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Option______ {

    @SerializedName("the_entrepreneur_can_no_longer_meet")
    @Expose
    private String theEntrepreneurCanNoLongerMeet;
    @SerializedName("the_value_of_the_real_estate")
    @Expose
    private String theValueOfTheRealEstate;
    @SerializedName("both_the_above_answers_are_correct")
    @Expose
    private String bothTheAboveAnswersAreCorrect;

    public String getTheEntrepreneurCanNoLongerMeet() {
        return theEntrepreneurCanNoLongerMeet;
    }

    public void setTheEntrepreneurCanNoLongerMeet(String theEntrepreneurCanNoLongerMeet) {
        this.theEntrepreneurCanNoLongerMeet = theEntrepreneurCanNoLongerMeet;
    }

    public String getTheValueOfTheRealEstate() {
        return theValueOfTheRealEstate;
    }

    public void setTheValueOfTheRealEstate(String theValueOfTheRealEstate) {
        this.theValueOfTheRealEstate = theValueOfTheRealEstate;
    }

    public String getBothTheAboveAnswersAreCorrect() {
        return bothTheAboveAnswersAreCorrect;
    }

    public void setBothTheAboveAnswersAreCorrect(String bothTheAboveAnswersAreCorrect) {
        this.bothTheAboveAnswersAreCorrect = bothTheAboveAnswersAreCorrect;
    }

}
