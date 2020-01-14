package com.auxesis.maxcrowdfund.mvvm.ui.myprofile;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.constant.ProgressDialog;
import com.auxesis.maxcrowdfund.custommvvm.profile.profileModel.ProfileResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.changebankaccount.changebankaccountmodel.ChangeBankAccountResponse;
import com.auxesis.maxcrowdfund.restapi.ApiClient;
import com.auxesis.maxcrowdfund.restapi.EndPointInterface;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;

import static com.auxesis.maxcrowdfund.constant.Utils.isInternetConnected;

public class MyProfileViewModel extends ViewModel {
    private static final String TAG = "MyProfileViewModel";
    MutableLiveData<ProfileResponse> myProfile;
    Context context;
    ProgressDialog pd;



   /* public MyProfileViewModel(Context ctx) {
        this.context = ctx;
    }*/

    public MutableLiveData<ProfileResponse> getMyProfile() {
        if (myProfile == null) {
            myProfile = new MutableLiveData<>();
            if (isInternetConnected(context)) {
                myProfile();
            } else {
                Toast.makeText(context, context.getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
            }
        }
        return myProfile;
    }

    private void myProfile() {
        try {
            pd = ProgressDialog.show(context, "Please Wait...");
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<ProfileResponse> call = git.UserProfile();
            call.enqueue(new Callback<ProfileResponse>() {
                @Override
                public void onResponse(@NonNull Call<ProfileResponse> call, @NonNull retrofit2.Response<ProfileResponse> response) {
                    try {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        if (response != null && response.isSuccessful()) {
                            Log.d(TAG, "onResponse: " + "><><" + new Gson().toJson(response.body()));
                            myProfile.setValue(response.body());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<ProfileResponse> call, @NonNull Throwable t) {
                    Log.e("response", "error " + t.getMessage());
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}