package com.auxesis.maxcrowdfund.mvvm.ui.changePassword;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
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
import com.auxesis.maxcrowdfund.constant.Utils;
import com.auxesis.maxcrowdfund.mvvm.activity.HomeActivity;
import com.auxesis.maxcrowdfund.mvvm.activity.LoginActivity;
import com.auxesis.maxcrowdfund.mvvm.ui.changePassword.changePassModel.ChangePasswordResponse;
import com.auxesis.maxcrowdfund.restapi.ApiClient;
import com.auxesis.maxcrowdfund.restapi.EndPointInterface;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Callback;
import static com.auxesis.maxcrowdfund.constant.Utils.getPreference;
import static com.auxesis.maxcrowdfund.constant.Utils.setPreference;

public class ChangePasswordFragment extends Fragment {
    private static final String TAG = "ChangePasswordFragment";
    EditText edtOldPass, edtNPass, edtConfirmPass;
    TextView tvErrorMessage;
    Button btnSubmit;
    ProgressDialog pd;
    String error_msg = "";
    Activity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_change_password, container, false);
        mActivity = getActivity();
        tvErrorMessage = root.findViewById(R.id.tvErrorMessage);
        edtOldPass = root.findViewById(R.id.edtOldPass);
        edtNPass = root.findViewById(R.id.edtNPass);
        edtConfirmPass = root.findViewById(R.id.edtConfirmPass);
        btnSubmit = root.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isInternetConnected(getActivity())) {
                    if (getValidation()) {
                        if (edtNPass.getText().toString().trim().equals(edtConfirmPass.getText().toString().trim())) {
                            if (edtNPass.getText().toString().trim().length()>=8) {
                                getChangePass();
                            }else {
                                Toast.makeText(getActivity(), getResources().getString(R.string.eight_digit), Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.confirm_password), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), error_msg, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_LONG).show();
                }
            }
        });
        return root;
    }

    private void getChangePass() {
        try {
            pd = ProgressDialog.show(getActivity(), "Please Wait...");
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("old_pass", edtOldPass.getText().toString().trim());
            jsonObject.addProperty("new_pass", edtNPass.getText().toString().trim());
            String XCSRF = getPreference(getActivity(), "mCsrf_token");
            EndPointInterface git = ApiClient.getClient1(getActivity()).create(EndPointInterface.class);
            Call<ChangePasswordResponse> call = git.getChangePassword("application/json",XCSRF, jsonObject);
            call.enqueue(new Callback<ChangePasswordResponse>() {
                @Override
                public void onResponse(@NonNull Call<ChangePasswordResponse> call, @NonNull retrofit2.Response<ChangePasswordResponse> response) {
                    Log.d(TAG, "onResponse:" + "><><" + new Gson().toJson(response.body()));
                    try {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        if (response.code()==200) {
                            if (response != null && response.isSuccessful()) {
                                if (response.body().getUserLoginStatus() == 1) {
                                    if (response.body().getResult()==1) {
                                        tvErrorMessage.setVisibility(View.GONE);
                                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                        edtOldPass.setText("");
                                        edtNPass.setText("");
                                        edtConfirmPass.setText("");
                                        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                                        navController.navigateUp();
                                    }else {
                                        tvErrorMessage.setVisibility(View.VISIBLE);
                                        tvErrorMessage.setText(response.body().getMessage());
                                        //Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    setPreference(getActivity(), "user_id", "");
                                    setPreference(getActivity(), "mLogout_token", "");
                                    MaxCrowdFund.getClearCookies(getActivity(), "cookies", "");
                                    Toast.makeText(getActivity(), getResources().getString(R.string.session_expire), Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                                    startActivity(intent);
                                    mActivity.finish();
                                }
                            }
                        }else {
                            if (pd != null && pd.isShowing()) {
                                pd.dismiss();
                            }
                            Toast.makeText(getActivity(), getResources().getString(R.string.something_went), Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<ChangePasswordResponse> call, @NonNull Throwable t) {
                    Log.e("response", "error " + t.getMessage());
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private boolean getValidation() {
        error_msg = "";
        if (TextUtils.isEmpty(edtOldPass.getText().toString().trim())) {
            error_msg = getString(R.string.enter_old_password);
            return false;
        } else if (TextUtils.isEmpty(edtNPass.getText().toString().trim())) {
            error_msg = getString(R.string.enter_new_password);
            return false;
        } else if (TextUtils.isEmpty(edtConfirmPass.getText().toString().trim())) {
            error_msg = getString(R.string.enter_confirm_password);
            return false;
        } else {
            return true;
        }
    }

    public void onResume() {
        super.onResume();
        ((HomeActivity) getActivity()).setActionBarTitle(getString(R.string.change_password));
    }
}
