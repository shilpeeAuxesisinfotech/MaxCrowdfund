package maxcrowdfund.com.mvvm.ui.changeMobileNumber;

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

import maxcrowdfund.com.R;
import maxcrowdfund.com.constant.MaxCrowdFund;
import maxcrowdfund.com.constant.ProgressDialog;
import maxcrowdfund.com.mvvm.activity.HomeActivity;
import maxcrowdfund.com.mvvm.activity.LoginActivity;
import maxcrowdfund.com.mvvm.ui.changeMobileNumber.changeMobileModel.SendOTPResponse;
import maxcrowdfund.com.mvvm.ui.changeMobileNumber.changeMobileModel.UpdatePhoneNumberResponse;
import maxcrowdfund.com.restapi.ApiClient;
import maxcrowdfund.com.restapi.EndPointInterface;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hbb20.CountryCodePicker;
import maxcrowdfund.com.constant.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    String mOTP = "";
    String countryName = "";
    Activity mActivity;
    private String mMobileNumber = "";
    private String mAddressCountryCode = "";
    String XCSRF ="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_change_mobile_number, container, false);
        mActivity = getActivity();
         XCSRF = Utils.getPreference(getActivity(), "mCsrf_token");
        mMobileNumber = Utils.getPreference(mActivity, "mobile");
        mAddressCountryCode = Utils.getPreference(mActivity, "countryCode");

        cardViewSend = root.findViewById(R.id.cardViewSend);
        cardViewVerify = root.findViewById(R.id.cardViewVerify);
        cardViewSend.setVisibility(View.VISIBLE);
        cardViewVerify.setVisibility(View.GONE);
        btn_change = root.findViewById(R.id.btn_change);
        btn_resend = root.findViewById(R.id.btn_resend);
        btn_verify = root.findViewById(R.id.btn_verify);
        tv_mobile = root.findViewById(R.id.tv_mobile);
        edt_mobile = root.findViewById(R.id.edt_mobile);
        edt_verifyCode = root.findViewById(R.id.edt_verifyCode);
        /*For Country code*/
        ccp_countryCode = root.findViewById(R.id.ccp_countryCode);
        if (mMobileNumber != null && mAddressCountryCode != null) {
            if (mMobileNumber.equals("0")) {
                ccp_countryCode.setCountryForNameCode(mAddressCountryCode);
                countryCode = ccp_countryCode.getSelectedCountryCodeWithPlus();
                countryName = ccp_countryCode.getSelectedCountryNameCode();
                edt_mobile.setText("");
            } else {
                ccp_countryCode.setCountryForNameCode(mAddressCountryCode);
                countryCode = ccp_countryCode.getSelectedCountryCodeWithPlus();
                countryName = ccp_countryCode.getSelectedCountryNameCode();
                edt_mobile.setText(mMobileNumber);
            }
        }
        ccp_countryCode.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                countryCode = ccp_countryCode.getSelectedCountryCodeWithPlus();
                Log.d(TAG, "onCountrySelected: " + ">>>>>>>>>>" + countryCode);
                Log.d(TAG, "onCountrySelected: " + ">>>>>>>>>>" + ccp_countryCode.getSelectedCountryNameCode());
            }
        });
        //For Send Otp
        btn_send_verify = root.findViewById(R.id.btn_send_verify);
        btn_send_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isInternetConnected(getActivity())) {
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
                if (Utils.isInternetConnected(getActivity())) {
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
                if (Utils.isInternetConnected(getActivity())) {
                    if (Utils.getPreference(getActivity(), "enteredPhone") != null && !Utils.getPreference(getActivity(), "enteredPhone").isEmpty()) {
                        Log.d(TAG, "onClick: " + "Resend---" + Utils.getPreference(getActivity(), "enteredPhone"));
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
        EndPointInterface git = ApiClient.getClient1(getActivity()).create(EndPointInterface.class);
        Call<SendOTPResponse> call = git.getSendOTP("application/json", XCSRF, jsonObject);
        call.enqueue(new Callback<SendOTPResponse>() {
            @Override
            public void onResponse(Call<SendOTPResponse> call, Response<SendOTPResponse> response) {
                try {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    if (response.code() == 200) {
                        if (response != null && response.isSuccessful()) {
                            Log.d(TAG, "onResponse: " + "><send><" + new Gson().toJson(response.body()));
                            if (response.body().getUserLoginStatus() == 1) {
                                if (response.body().getMessage() != null) {
                                    if (response.body().getOtp() != null) {
                                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                        Utils.setPreference(getActivity(), "countryCode", countryCode);
                                        Utils.setPreference(getActivity(), "enteredPhone", enteredPhone);
                                        mOTP = response.body().getOtp().toString();
                                        cardViewSend.setVisibility(View.GONE);
                                        cardViewVerify.setVisibility(View.VISIBLE);
                                        edt_mobile.setText("");
                                        tv_mobile.setText("+" + Utils.getPreference(getActivity(), "countryCode") + Utils.getPreference(getActivity(), "enteredPhone"));
                                    } else {
                                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                Utils.setPreference(getActivity(), "user_id", "");
                                Utils.setPreference(getActivity(), "mLogout_token", "");
                                MaxCrowdFund.getInstance().getClearCookies(getActivity(), "cookies", "");
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
        jsonObject.addProperty("tfa_type", Utils.getPreference(getActivity(), "enteredPhone"));
        String XCSRF = Utils.getPreference(getActivity(), "mCsrf_token");
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
                            if (response.body().getMessage() != null) {
                                if (response.body().getOtp() != null) {
                                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    // Toast.makeText(getActivity(), "OTP Resend Successfully...", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Utils.setPreference(getActivity(), "user_id", "");
                            Utils.setPreference(getActivity(), "mLogout_token", "");
                            MaxCrowdFund.getInstance().getClearCookies(getActivity(), "cookies", "");
                            Toast.makeText(getActivity(), getResources().getString(R.string.session_expire), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                            mActivity.finish();
                        }
                    } else {
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
                                Utils.setPreference(getActivity(), "countryCode", "");
                                Utils.setPreference(getActivity(), "enteredPhone", "");
                                edt_verifyCode.setText("");
                            }
                        } else {
                            Utils.setPreference(getActivity(), "user_id", "");
                            Utils.setPreference(getActivity(), "mLogout_token", "");
                            MaxCrowdFund.getInstance().getClearCookies(getActivity(), "cookies", "");
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
        ((HomeActivity) getActivity()).setActionBarTitle(getString(R.string.change_mobile_no));
    }
}
