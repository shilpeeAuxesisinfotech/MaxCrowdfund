package com.auxesis.maxcrowdfund.mvvm.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.constant.MaxCrowdFund;
import com.auxesis.maxcrowdfund.constant.ProgressDialog;
import com.auxesis.maxcrowdfund.mvvm.ui.homeDetail.investmodel.CreateInvestmentResponse;
import com.auxesis.maxcrowdfund.restapi.ApiClient;
import com.auxesis.maxcrowdfund.restapi.EndPointInterface;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.auxesis.maxcrowdfund.constant.Utils.getPreference;
import static com.auxesis.maxcrowdfund.constant.Utils.isInternetConnected;
import static com.auxesis.maxcrowdfund.constant.Utils.setPreference;

public class InvestActivity extends AppCompatActivity {
    private static final String TAG = "InvestActivity";
    TextView tv_back_arrow, tvHeaderTitle;
    EditText edtAccount;
    Button btnContinue;
    String error_msg = "";
    ProgressDialog pd;
    Activity mActivity;
    String mLoanId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invest);
        mActivity =InvestActivity.this;
        initUI();
    }

    private void initUI() {
        tv_back_arrow = findViewById(R.id.tv_back_arrow);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setText(R.string.max_property_group);
        tv_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        edtAccount = findViewById(R.id.edtAccount);
        btnContinue = findViewById(R.id.btnContinue);
        Intent intent = getIntent();
        if(intent!=null){
            mLoanId= intent.getStringExtra("loan_id");
        }
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetConnected(getApplicationContext())) {
                    if (Validation()) {
                        getCreateInvest();
                    } else {
                        Toast.makeText(mActivity, error_msg, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mActivity, getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getCreateInvest() {
        pd = ProgressDialog.show(mActivity, "Please Wait...");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("amount", edtAccount.getText().toString().trim());
        if (mLoanId!=null && !mLoanId.isEmpty()){
            jsonObject.addProperty("loan_id", mLoanId);
        }
        Log.d(TAG, "getCreateInvest: "+jsonObject);
        String XCSRF = getPreference(mActivity, "mCsrf_token");
        EndPointInterface git = ApiClient.getClient1(mActivity).create(EndPointInterface.class);
        Call<CreateInvestmentResponse> call = git.getCreateInvestment("application/json", XCSRF, jsonObject);
        call.enqueue(new Callback<CreateInvestmentResponse>() {
            @Override
            public void onResponse(Call<CreateInvestmentResponse> call, Response<CreateInvestmentResponse> response) {
                try {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    if (response!=null) {
                        if (response != null && response.isSuccessful()) {
                            Log.d(TAG, "onResponse:" + "><><" + new Gson().toJson(response.body()));
                            if (response.body().getUserLoginStatus() == 1) {
                               // Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }else {
                                setPreference(mActivity, "user_id", "");
                                setPreference(mActivity, "mLogout_token", "");
                                MaxCrowdFund.getClearCookies(mActivity, "cookies", "");
                                Toast.makeText(mActivity, getResources().getString(R.string.session_expire), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(mActivity, LoginActivity.class);
                                startActivity(intent);
                                mActivity.finish();
                            }
                        }
                    }else {
                        Toast.makeText(mActivity, getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<CreateInvestmentResponse> call, Throwable t) {
                Log.e("error", "error " + t.getMessage());
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                Toast.makeText(mActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean Validation() {
        error_msg = "";
        if (TextUtils.isEmpty(edtAccount.getText().toString().trim())) {
            error_msg = getString(R.string.enter_amount);
            return false;
        } else {
            return true;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
