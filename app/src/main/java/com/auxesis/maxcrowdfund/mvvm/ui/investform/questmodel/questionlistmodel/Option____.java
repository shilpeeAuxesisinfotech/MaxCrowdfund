
package com.auxesis.maxcrowdfund.mvvm.ui.investform.questmodel.questionlistmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Option____ {

    @SerializedName("that_is_no_problem_for_me")
    @Expose
    private String thatIsNoProblemForMe;
    @SerializedName("that_can_be_difficult_for_a_while")
    @Expose
    private String thatCanBeDifficultForAWhile;
    @SerializedName("then_i_have_a_problem")
    @Expose
    private String thenIHaveAProblem;

    public String getThatIsNoProblemForMe() {
        return thatIsNoProblemForMe;
    }

    public void setThatIsNoProblemForMe(String thatIsNoProblemForMe) {
        this.thatIsNoProblemForMe = thatIsNoProblemForMe;
    }

    public String getThatCanBeDifficultForAWhile() {
        return thatCanBeDifficultForAWhile;
    }

    public void setThatCanBeDifficultForAWhile(String thatCanBeDifficultForAWhile) {
        this.thatCanBeDifficultForAWhile = thatCanBeDifficultForAWhile;
    }

    public String getThenIHaveAProblem() {
        return thenIHaveAProblem;
    }

    public void setThenIHaveAProblem(String thenIHaveAProblem) {
        this.thenIHaveAProblem = thenIHaveAProblem;
    }

}
