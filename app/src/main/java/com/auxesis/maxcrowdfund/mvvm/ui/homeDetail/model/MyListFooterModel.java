package com.auxesis.maxcrowdfund.mvvm.ui.homeDetail.model;

public class MyListFooterModel {
    int total_raised;
    int active_investors;
    int average_return;

    public MyListFooterModel() {
    }

    public int getTotal_raised() {
        return total_raised;
    }

    public void setTotal_raised(int total_raised) {
        this.total_raised = total_raised;
    }

    public int getActive_investors() {
        return active_investors;
    }

    public void setActive_investors(int active_investors) {
        this.active_investors = active_investors;
    }

    public int getAverage_return() {
        return average_return;
    }

    public void setAverage_return(int average_return) {
        this.average_return = average_return;
    }
}
