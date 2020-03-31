package com.auxesis.maxcrowdfund.mvvm.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.constant.APIUrl;
import com.auxesis.maxcrowdfund.constant.MaxCrowdFund;
import com.auxesis.maxcrowdfund.constant.ProgressDialog;
import com.auxesis.maxcrowdfund.constant.Utils;
import com.auxesis.maxcrowdfund.mvvm.ui.login.LoginResponse;
import com.auxesis.maxcrowdfund.restapi.ApiClient;
import com.auxesis.maxcrowdfund.restapi.EndPointInterface;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

import static com.auxesis.maxcrowdfund.constant.Utils.getPreference;
import static com.auxesis.maxcrowdfund.constant.Utils.hideKeyboard;
import static com.auxesis.maxcrowdfund.constant.Utils.isInternetConnected;
import static com.auxesis.maxcrowdfund.constant.Utils.setPreference;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    EditText edt_email, edt_pssword;
    Button btn_login;
    TextView tv_max_crowd;
    boolean doubleBackToExitPressedOnce = false;
    String error_msg = "";
    ProgressDialog pd;
    CheckBox checkBoxRMe;
    boolean isRememberMe = false;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Get the application context
        mContext = getApplicationContext();
        hideKeyboard(LoginActivity.this);
        init();
    }

    private void init() {
        edt_email = findViewById(R.id.edt_email);
        edt_pssword = findViewById(R.id.edt_pssword);
        tv_max_crowd = findViewById(R.id.tv_max_crowd);
        btn_login = findViewById(R.id.btn_login);
        checkBoxRMe = findViewById(R.id.checkBoxRMe);

        checkBoxRMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBoxRMe.isChecked()) {
                    isRememberMe = checkBoxRMe.isChecked();
                } else {
                    isRememberMe = checkBoxRMe.isChecked();
                }
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetConnected(getApplicationContext())) {
                    if (Validation()) {
                        getLoginAPI();
                    } else {
                        Toast.makeText(LoginActivity.this, error_msg, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
                }
            }
        });

        tv_max_crowd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://maxcrowdfund.com"));
                startActivity(intent);
            }
        });
    }

    private void getLoginAPI() {
        try {
            pd = ProgressDialog.show(LoginActivity.this, "Please Wait...");
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", edt_email.getText().toString().trim());
            jsonObject.addProperty("pass", edt_pssword.getText().toString().trim());
            EndPointInterface git = ApiClient.getClient1(LoginActivity.this).create(EndPointInterface.class);
            Call<LoginResponse> call = git.getLoginUser("application/json", jsonObject);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(@NonNull Call<LoginResponse> call, @NonNull retrofit2.Response<LoginResponse> response) {
                    Log.d(TAG, "onResponse:" + "><><" + new Gson().toJson(response.body()));
                    try {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        if (response != null && response.isSuccessful()) {
                            if (response.body().getMessage().equals("Succesfully Logged In")) {
                                String mSattus = response.body().getStatus();
                                if (mSattus.equals("200")) {
                                    String name = response.body().getCurrentUser().getName();
                                    String uid = response.body().getCurrentUser().getUid();
                                    String csrf_token = response.body().getCurrentUser().getCsrfToken();
                                    String logout_token = response.body().getCurrentUser().getLogoutToken();
                                    setPreference(LoginActivity.this, "isRememberMe", String.valueOf(isRememberMe));
                                    setPreference(LoginActivity.this, "user_id", uid);
                                    setPreference(LoginActivity.this, "mName", name);
                                    setPreference(LoginActivity.this, "mCsrf_token", csrf_token);
                                    setPreference(LoginActivity.this, "mLogout_token", logout_token);
                                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                    overridePendingTransition(R.anim.enter, R.anim.exit);
                                    edt_email.setText("");
                                    edt_pssword.setText("");
                                    finish();
                                }
                            }
                        } else {
                            MaxCrowdFund.getClearCookies(LoginActivity.this, "cookies", "");
                            Toast.makeText(LoginActivity.this, "This route can only be accessed by anonymous users.", Toast.LENGTH_SHORT).show();
                           // Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                    Log.e("response", "error " + t.getMessage());
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean Validation() {
        error_msg = "";
        if (TextUtils.isEmpty(edt_email.getText().toString().trim())) {
            error_msg = getString(R.string.enter_email);
            return false;
        } else if (TextUtils.isEmpty(edt_pssword.getText().toString().trim())) {
            error_msg = getString(R.string.enter_password);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}

