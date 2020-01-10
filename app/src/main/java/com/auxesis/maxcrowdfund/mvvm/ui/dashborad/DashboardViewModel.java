package com.auxesis.maxcrowdfund.mvvm.ui.dashborad;

import android.app.Application;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class DashboardViewModel extends AndroidViewModel {
    private static final String TAG = "DashboardViewModel";
    Context context;

    public DashboardViewModel(@NonNull Application application) {
        super(application);
        this.context=application;
    }


}