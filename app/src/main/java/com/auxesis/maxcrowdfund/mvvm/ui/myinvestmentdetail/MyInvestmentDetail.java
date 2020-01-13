package com.auxesis.maxcrowdfund.mvvm.ui.myinvestmentdetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyInvestmentDetail extends ViewModel {

    private MutableLiveData<String> mText;

    public MyInvestmentDetail() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Shilpee");
    }

    public LiveData<String> getText() {
        return mText;
    }
}