package maxcrowdfund.com.mvvm.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import maxcrowdfund.com.R;
import maxcrowdfund.com.constant.MaxCrowdFund;
import maxcrowdfund.com.constant.ProgressDialog;
import maxcrowdfund.com.databinding.ActivityLoginBinding;
import maxcrowdfund.com.mvvm.ui.login.LoginResponse;
import maxcrowdfund.com.restapi.ApiClient;
import maxcrowdfund.com.restapi.EndPointInterface;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import maxcrowdfund.com.constant.Utils;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    boolean doubleBackToExitPressedOnce = false;
    private String email = null;
    private String password = null;
    ProgressDialog pd;
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Utils.hideKeyboard(LoginActivity.this);
        init();
    }

    private void init() {
        binding.tvDescription.setText(Utils.user_login_description);
        binding.edtEmail.setHint(Utils.user_login_email_placeholder);
        binding.edtPassword.setHint(Utils.user_login_password_placehplder);
        binding.btnLogin.setText(Utils.user_login_btn_login);
        binding.tvPleaseRegister.setText(Utils.don_t_have_one_yet_please_register);
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isInternetConnected(getApplicationContext())) {
                    email = binding.edtEmail.getText().toString().trim();
                    password = binding.edtPassword.getText().toString().trim();
                    if (email != null &&!email.isEmpty()) {
                        if (password!=null &&!password.isEmpty()) {
                            getLoginAPI();
                        }else {
                            Toast.makeText(LoginActivity.this, Utils.enter_password, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, Utils.enter_email, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, Utils.oops_connect_your_internet, Toast.LENGTH_SHORT).show();
                }
            }
        });

       binding.tvPleaseRegister.setOnClickListener(new View.OnClickListener() {
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
            jsonObject.addProperty("name", email);
            jsonObject.addProperty("pass", password);
            EndPointInterface git = ApiClient.getClient1(LoginActivity.this).create(EndPointInterface.class);
            Call<LoginResponse> call = git.getLoginUser("json", "application/json", jsonObject);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(@NonNull Call<LoginResponse> call, @NonNull retrofit2.Response<LoginResponse> response) {
                    Log.d(TAG, "onResponse:" + "><Login><" + new Gson().toJson(response.body()));
                    try {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        if (response.code() == 200) {
                            if (response != null && response.isSuccessful()) {
                                String mSattus = response.body().getStatus();
                                if (mSattus.equals("200")) {
                                    String name = response.body().getCurrentUser().getName();
                                    String uid = response.body().getCurrentUser().getUid();
                                    String csrf_token = response.body().getCurrentUser().getCsrfToken();
                                    String logout_token = response.body().getCurrentUser().getLogoutToken();
                                    Utils.setPreference(LoginActivity.this, "user_id", uid);
                                    Utils.setPreference(LoginActivity.this, "mName", name);
                                    Utils.setPreference(LoginActivity.this, "mCsrf_token", csrf_token);
                                    Utils.setPreference(LoginActivity.this, "mLogout_token", logout_token);
                                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                    overridePendingTransition(R.anim.enter, R.anim.exit);
                                    binding.edtEmail.setText("");
                                    binding.edtPassword.setText("");
                                    email = "";
                                    password = "";
                                    finish();
                                }
                            } else {
                                Log.d(TAG, "onResponse: " + ">>>>>>>>" + response.code());
                                Log.d(TAG, "onResponse: " + ">>>>>b>>>" + response.body().getMessage());
                                String mError = response.errorBody().string();
                                Toast.makeText(LoginActivity.this, mError, Toast.LENGTH_SHORT).show();
                                // Toast.makeText(LoginActivity.this, "This route can only be accessed by anonymous users.", Toast.LENGTH_SHORT).show();
                                MaxCrowdFund.getInstance().getClearCookies(LoginActivity.this, "cookies", "");
                            }
                        } else {
                            String mError = response.errorBody().string();
                            Log.d(TAG, "onResponse: " + ">>>>parse>>>>" + mError);
                            Toast.makeText(LoginActivity.this, mError, Toast.LENGTH_SHORT).show();
                            MaxCrowdFund.getInstance().getClearCookies(LoginActivity.this, "cookies", "");
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

    @Override
    public void onBackPressed() {
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

