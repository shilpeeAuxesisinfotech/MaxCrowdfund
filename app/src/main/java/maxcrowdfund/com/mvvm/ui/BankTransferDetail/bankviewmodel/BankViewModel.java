package maxcrowdfund.com.mvvm.ui.BankTransferDetail.bankviewmodel;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import maxcrowdfund.com.R;
import maxcrowdfund.com.constant.MaxCrowdFund;
import maxcrowdfund.com.constant.ProgressDialog;
import maxcrowdfund.com.mvvm.activity.LoginActivity;
import maxcrowdfund.com.mvvm.ui.BankTransferDetail.model.BankTransferDetailResponse;
import maxcrowdfund.com.restapi.ApiClient;
import maxcrowdfund.com.restapi.EndPointInterface;
import com.google.gson.Gson;
import maxcrowdfund.com.constant.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BankViewModel extends AndroidViewModel {
    private static final String TAG = "BankViewModel";
    Context mContext;
    ProgressDialog pd;
    MutableLiveData<BankTransferDetailResponse> responseMutableLiveData;
    public BankViewModel(@NonNull Application application) {
        super(application);
        mContext = application;
    }

    public MutableLiveData<BankTransferDetailResponse> getResponseMutableLiveData() {
        if (responseMutableLiveData == null) {
            responseMutableLiveData = new MutableLiveData<>();
            getBankResponse();
        }
        return responseMutableLiveData;
    }

    private void getBankResponse() {
        pd = ProgressDialog.show(mContext, "Please Wait...");
        EndPointInterface git = ApiClient.getClient1(mContext).create(EndPointInterface.class);
      String XCSRF = Utils.getPreference(mContext, "mCsrf_token");
        Call<BankTransferDetailResponse> call = git.getBankTransferDetail("application/json", XCSRF);
        call.enqueue(new Callback<BankTransferDetailResponse>() {
            @Override
            public void onResponse(Call<BankTransferDetailResponse> call, Response<BankTransferDetailResponse> response) {
                Log.d(TAG, "onResponse: " + "><getBankTranfer><" + new Gson().toJson(response.body()));
                try {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    if (response.code() == 200) {
                        if (response != null && response.isSuccessful()) {
                            if (response.body().getUserLoginStatus() == 1) {
                                if (response.body().getBankTransferDetail() != null) {
                                    if (response.body().getBankTransferDetail().getData() != null) {

                                    }
                                } else {
                                    Toast.makeText(mContext, mContext.getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Utils.setPreference(mContext, "user_id", "");
                                Utils.setPreference(mContext, "mLogout_token", "");
                                MaxCrowdFund.getInstance().getClearCookies(mContext, "cookies", "");
                                Toast.makeText(mContext, mContext.getString(R.string.session_expire), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(mContext, LoginActivity.class);
                                mContext.startActivity(intent);
                            }
                        } else {
                            Toast.makeText(mContext, mContext.getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, mContext.getString(R.string.something_went), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BankTransferDetailResponse> call, Throwable t) {
                Log.e("response", "error " + t.getMessage());
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
            }
        });
    }
}
