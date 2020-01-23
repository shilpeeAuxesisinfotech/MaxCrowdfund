package com.auxesis.maxcrowdfund.mvvm.ui.changePassword;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.constant.ProgressDialog;
import com.auxesis.maxcrowdfund.constant.Utils;
import com.auxesis.maxcrowdfund.mvvm.activity.HomeActivity;
import com.auxesis.maxcrowdfund.mvvm.ui.changePassword.changePassModel.ChangePasswordResponse;
import com.auxesis.maxcrowdfund.restapi.ApiClient;
import com.auxesis.maxcrowdfund.restapi.EndPointInterface;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Callback;

public class ChangePasswordFragment extends Fragment {
    private static final String TAG = "ChangePasswordFragment";
    EditText edtOldPass, edtNPass, edtConfirmPass;
    Button btnSubmit;
    ProgressDialog pd;
    String error_msg = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_change_password, container, false);

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
                            getChangePass();
                        }else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.confirm_password), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), error_msg, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
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
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<ChangePasswordResponse> call = git.getChangePassword("application/json", jsonObject);
            call.enqueue(new Callback<ChangePasswordResponse>() {
                @Override
                public void onResponse(@NonNull Call<ChangePasswordResponse> call, @NonNull retrofit2.Response<ChangePasswordResponse> response) {
                    Log.d(TAG, "onResponse: " + "><><" + new Gson().toJson(response.body()));
                    try {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        if (response != null && response.isSuccessful()) {
                            if (response.body().getResult().equals("success")) {
                                Toast.makeText(getActivity(), getResources().getString(R.string.update_password), Toast.LENGTH_SHORT).show();
                                edtOldPass.setText("");
                                edtNPass.setText("");
                                edtConfirmPass.setText("");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ChangePasswordResponse> call, @NonNull Throwable t) {
                    Log.e("response", "error " + t.getMessage());
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
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
        // Set title bar
        ((HomeActivity) getActivity()).setActionBarTitle(getString(R.string.change_password));
    }
}
