package com.auxesis.maxcrowdfund.mvvm.ui.contactinformation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ContactInformationViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ContactInformationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Contact Information");
    }

    public LiveData<String> getText() {
        return mText;
    }
}