package com.auxesis.maxcrowdfund.mvvm.ui.changeMobileNumber;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.constant.MaxCrowdFund;
import com.auxesis.maxcrowdfund.constant.ProgressDialog;
import com.auxesis.maxcrowdfund.mvvm.activity.HomeActivity;
import com.auxesis.maxcrowdfund.mvvm.activity.LoginActivity;
import com.auxesis.maxcrowdfund.mvvm.ui.changeMobileNumber.changeMobileModel.SendOTPResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.changeMobileNumber.changeMobileModel.UpdatePhoneNumberResponse;
import com.auxesis.maxcrowdfund.restapi.ApiClient;
import com.auxesis.maxcrowdfund.restapi.EndPointInterface;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hbb20.CountryCodePicker;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.auxesis.maxcrowdfund.constant.Utils.getPreference;
import static com.auxesis.maxcrowdfund.constant.Utils.isInternetConnected;
import static com.auxesis.maxcrowdfund.constant.Utils.setPreference;

public class ChangeMobileNumberFragment extends Fragment {
    private static final String TAG = "ChangeMobileNumberFragm";
    Button btn_send_verify, btn_change, btn_resend, btn_verify;
    String error_msg = "";
    EditText edt_mobile, edt_verifyCode;
    ProgressDialog pd;
    CardView cardViewSend, cardViewVerify;
    String enteredPhone = "";
    TextView tv_mobile;
    CountryCodePicker ccp_countryCode;
    String countryCode = "";
    String code = "";
    String reSendcode = "";
    String mOTP = "";
    Activity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_change_mobile_number, container, false);
        mActivity = getActivity();
        cardViewSend = root.findViewById(R.id.cardViewSend);
        cardViewVerify = root.findViewById(R.id.cardViewVerify);
        cardViewSend.setVisibility(View.VISIBLE);
        cardViewVerify.setVisibility(View.GONE);
        btn_change = root.findViewById(R.id.btn_change);
        btn_resend = root.findViewById(R.id.btn_resend);
        btn_verify = root.findViewById(R.id.btn_verify);
        tv_mobile = root.findViewById(R.id.tv_mobile);
        edt_verifyCode = root.findViewById(R.id.edt_verifyCode);
        /*For Country code*/
        ccp_countryCode = root.findViewById(R.id.ccp_countryCode);
        //countryCode = ccp_countryCode.getDefaultCountryCode();
        countryCode = ccp_countryCode.getSelectedCountryCodeWithPlus();
        ccp_countryCode.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                countryCode = ccp_countryCode.getSelectedCountryCodeWithPlus();
                Log.d(TAG, "onCountrySelected: " + ">>>>>>>>>>" + countryCode);
            }
        });
        edt_mobile = root.findViewById(R.id.edt_mobile);
        //For Send Otp
        btn_send_verify = root.findViewById(R.id.btn_send_verify);
        btn_send_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetConnected(getActivity())) {
                    if (Validation()) {
                        getSendOTP();
                    } else {
                        Toast.makeText(getActivity(), error_msg, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
                }
            }
        });

        //For Verify OTP
        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetConnected(getActivity())) {
                    if (edt_verifyCode.getText().toString().trim() != null && !edt_verifyCode.getText().toString().trim().isEmpty()) {
                        if (edt_verifyCode.getText().toString().trim().equals(mOTP)) {
                            getVerifyMobile();
                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.enter_valid), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.enter_code_to_verify), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardViewSend.setVisibility(View.VISIBLE);
                cardViewVerify.setVisibility(View.GONE);
                edt_mobile.setText("");
            }
        });

        btn_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetConnected(getActivity())) {
                    if (getPreference(getActivity(), "enteredPhone") != null && !getPreference(getActivity(), "enteredPhone").isEmpty()) {
                        Log.d(TAG, "onClick: " + "Resend---" + getPreference(getActivity(), "enteredPhone"));
                        getResendOTP();
                    }
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }

    /*For Send OTP*/
    private void getSendOTP() {
        pd = ProgressDialog.show(getActivity(), "Please Wait...");
        JsonObject jsonObject = new JsonObject();
        enteredPhone = edt_mobile.getText().toString().trim();
        code = (countryCode + enteredPhone);
        jsonObject.addProperty("tfa_type", code);
        Log.d(">>>>number>", code);
        String XCSRF = getPreference(getActivity(), "mCsrf_token");
        EndPointInterface git = ApiClient.getClient1(getActivity()).create(EndPointInterface.class);
        Call<SendOTPResponse> call = git.getSendOTP("application/json", XCSRF, jsonObject);
        call.enqueue(new Callback<SendOTPResponse>() {
            @Override
            public void onResponse(Call<SendOTPResponse> call, Response<SendOTPResponse> response) {
                try {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    if (response.code()==200) {
                        if (response != null && response.isSuccessful()) {
                            Log.d(TAG, "onResponse: " + "><send><" + new Gson().toJson(response.body()));
                            if (response.body().getUserLoginStatus() == 1) {
                                if (response.body().getMessage() != null) {
                                    if (response.body().getOtp() != null) {
                                        //Toast.makeText(getActivity(), "OTP Send Successfully..", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                        setPreference(getActivity(), "countryCode", countryCode);
                                        setPreference(getActivity(), "enteredPhone", enteredPhone);
                                        mOTP = response.body().getOtp().toString();
                                        cardViewSend.setVisibility(View.GONE);
                                        cardViewVerify.setVisibility(View.VISIBLE);
                                        edt_mobile.setText("");
                                        tv_mobile.setText("+" + getPreference(getActivity(), "countryCode") + getPreference(getActivity(), "enteredPhone"));
                                    }else {
                                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                setPreference(getActivity(), "user_id", "");
                                setPreference(getActivity(), "mLogout_token", "");
                                MaxCrowdFund.getClearCookies(getActivity(), "cookies", "");
                                Toast.makeText(getActivity(), getResources().getString(R.string.session_expire), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                                mActivity.finish();
                            }
                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        Toast.makeText(getActivity(), getResources().getString(R.string.something_went), Toast.LENGTH_SHORT).show();
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SendOTPResponse> call, Throwable t) {
                Log.e("", "error " + t.getMessage());
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                edt_mobile.setText("");
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*For Resend OTP*/
    private void getResendOTP() {
        pd = ProgressDialog.show(getActivity(), "Please Wait...");
        JsonObject jsonObject = new JsonObject();
       // jsonObject.addProperty("phone", getPreference(getActivity(), "enteredPhone"));
        jsonObject.addProperty("tfa_type", getPreference(getActivity(), "enteredPhone"));
        String XCSRF = getPreference(getActivity(), "mCsrf_token");
        EndPointInterface git = ApiClient.getClient1(getActivity()).create(EndPointInterface.class);
        Call<SendOTPResponse> call = git.getSendOTP("application/json", XCSRF, jsonObject);
        call.enqueue(new Callback<SendOTPResponse>() {
            @Override
            public void onResponse(Call<SendOTPResponse> call, Response<SendOTPResponse> response) {
                try {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    if (response != null && response.isSuccessful()) {
                        if (response.body().getUserLoginStatus() == 1) {
                            if (response.body().getMessage()!=null) {
                                if (response.body().getOtp() != null) {
                                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                   // Toast.makeText(getActivity(), "OTP Resend Successfully...", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            setPreference(getActivity(), "user_id", "");
                            setPreference(getActivity(), "mLogout_token", "");
                            MaxCrowdFund.getClearCookies(getActivity(), "cookies", "");
                            Toast.makeText(getActivity(), getResources().getString(R.string.session_expire), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                            mActivity.finish();
                        }
                    }else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SendOTPResponse> call, Throwable t) {
                Log.e("", "error " + t.getMessage());
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*For verify otp*/
    private void getVerifyMobile() {
        pd = ProgressDialog.show(getActivity(), "Please Wait...");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("phone", edt_verifyCode.getText().toString().trim());
        String XCSRF = getPreference(getActivity(), "mCsrf_token");
        EndPointInterface git = ApiClient.getClient1(getActivity()).create(EndPointInterface.class);
        Call<UpdatePhoneNumberResponse> call = git.getUpdatePhone("application/json", XCSRF, jsonObject);
        call.enqueue(new Callback<UpdatePhoneNumberResponse>() {
            @Override
            public void onResponse(Call<UpdatePhoneNumberResponse> call, Response<UpdatePhoneNumberResponse> response) {
                try {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    if (response != null && response.isSuccessful()) {
                        Log.d(TAG, "onResponse: " + "><><" + new Gson().toJson(response.body()));
                        if (response.body().getUserLoginStatus() == 1) {
                            if (response.body().getResult() != null) {
                                Toast.makeText(getActivity(), response.body().getResult(), Toast.LENGTH_SHORT).show();
                                cardViewSend.setVisibility(View.VISIBLE);
                                cardViewVerify.setVisibility(View.GONE);
                                setPreference(getActivity(), "countryCode", "");
                                setPreference(getActivity(), "enteredPhone", "");
                                edt_verifyCode.setText("");
                            }
                        } else {
                            setPreference(getActivity(), "user_id", "");
                            setPreference(getActivity(), "mLogout_token", "");
                            MaxCrowdFund.getClearCookies(getActivity(), "cookies", "");
                            Toast.makeText(getActivity(), getResources().getString(R.string.session_expire), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                            mActivity.finish();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<UpdatePhoneNumberResponse> call, Throwable t) {
                Log.e("", "error " + t.getMessage());
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                edt_verifyCode.setText("");
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean Validation() {
        error_msg = "";
        if (TextUtils.isEmpty(edt_mobile.getText().toString().trim())) {
            error_msg = getString(R.string.enter_mobile_number);
            return false;
        } else {
            return true;
        }
    }

    public void onResume() {
        super.onResume();
        // Set title bar
        ((HomeActivity) getActivity()).setActionBarTitle(getString(R.string.change_mobile_no));
    }
}
