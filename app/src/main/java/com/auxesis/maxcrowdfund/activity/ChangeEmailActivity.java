package com.auxesis.maxcrowdfund.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.custommvvm.DashboardDepositActivity;
import com.auxesis.maxcrowdfund.custommvvm.changeemail.ChangeEmailResponse;
import com.auxesis.maxcrowdfund.restapi.ApiClient;
import com.auxesis.maxcrowdfund.restapi.EndPointInterface;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.auxesis.maxcrowdfund.constant.Utils.getPreference;
import static com.auxesis.maxcrowdfund.constant.Utils.isInternetConnected;
import static com.auxesis.maxcrowdfund.constant.Utils.showToast;


public class ChangeEmailActivity extends AppCompatActivity {
    private static final String TAG = "ChangeEmailActivity";
    TextView tv_back_arrow, tvHeaderTitle;
    EditText edt_email;
    Button btn_submit;
    String error_msg = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);
        init();
    }

    private void init() {
        tv_back_arrow = findViewById(R.id.tv_back_arrow);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setText(R.string.change_email);
        tv_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        edt_email = findViewById(R.id.edt_email);
        btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetConnected(getApplicationContext())) {
                    if (Validation()) {
                        getChangeEmail();
                    } else {
                        showToast(ChangeEmailActivity.this, error_msg);
                    }
                } else {
                    showToast(ChangeEmailActivity.this, getResources().getString(R.string.oops_connect_your_internet));
                }
            }
        });
    }

    private void getChangeEmail() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", edt_email.getText().toString().trim());
        String XCSRF = getPreference(ChangeEmailActivity.this, "mCsrf_token");
        EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
        Call<ChangeEmailResponse> call = git.changeEmail("application/json", XCSRF, jsonObject);
        call.enqueue(new Callback<ChangeEmailResponse>() {
            @Override
            public void onResponse(Call<ChangeEmailResponse> call, Response<ChangeEmailResponse> response) {
                if (response!= null && response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + "><><" + new Gson().toJson(response.body()));
                    Toast.makeText(ChangeEmailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChangeEmailResponse> call, Throwable t) {
                Log.e("", "error " + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean Validation() {
        error_msg = "";
        if (TextUtils.isEmpty(edt_email.getText().toString().trim())) {
            error_msg = getString(R.string.enter_email);
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
