package com.auxesis.maxcrowdfund.mvvm.ui.myinvestments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyInvestmentsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyInvestmentsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}