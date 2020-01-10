package com.auxesis.maxcrowdfund.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.auxesis.maxcrowdfund.custommvvm.profile.profileModel.ProfileResponse;
import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.constant.ProgressDialog;
import com.auxesis.maxcrowdfund.constant.Utils;
import com.auxesis.maxcrowdfund.restapi.ApiClient;
import com.auxesis.maxcrowdfund.restapi.EndPointInterface;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

import static com.auxesis.maxcrowdfund.constant.Utils.showToast;

public class MyProfileActivity extends AppCompatActivity {
    private static final String TAG = "MyProfileActivity";
    TextView tv_back_arrow, tvHeaderTitle, tv_user_name, tvName, tv_name, tvSurName, tv_sur_name, tvEmail, tv_email, tvAddress, tv_Address,
            tvInvestorId, tv_investorId, tvAccountId, tv_accountId;
    Button btn_addChange, btn_change_email, btn_change_mobile, btn_change_pass, btn_change_default_bankA, btn_change_preference;
    ProgressDialog pd;
    CircleImageView iv_user_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        init();
    }

    private void init() {
        tv_back_arrow = findViewById(R.id.tv_back_arrow);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setText(R.string.menu_my_profile);
        tv_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tv_user_name = findViewById(R.id.tv_user_name);
        tvName = findViewById(R.id.tvName);
        tv_name = findViewById(R.id.tv_name);
        tvSurName = findViewById(R.id.tvSurName);
        tv_sur_name = findViewById(R.id.tv_sur_name);
        tvEmail = findViewById(R.id.tvEmail);
        tv_email = findViewById(R.id.tv_email);
        tvAddress = findViewById(R.id.tvAddress);
        tv_Address = findViewById(R.id.tv_Address);
        tvInvestorId = findViewById(R.id.tvInvestorId);
        tv_investorId = findViewById(R.id.tv_investorId);
        tvAccountId = findViewById(R.id.tvAccountId);
        tv_accountId = findViewById(R.id.tv_accountId);
        iv_user_img = findViewById(R.id.iv_user_img);

        btn_addChange = findViewById(R.id.btn_addChange);
        btn_change_email = findViewById(R.id.btn_change_email);
        btn_change_mobile = findViewById(R.id.btn_change_mobile);
        btn_change_pass = findViewById(R.id.btn_change_pass);
        btn_change_default_bankA = findViewById(R.id.btn_change_default_bankA);
        btn_change_preference = findViewById(R.id.btn_change_preference);

        btn_addChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyProfileActivity.this, UploadImageActivity.class));
            }
        });
        btn_change_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyProfileActivity.this, ChangeEmailActivity.class));
            }
        });
        btn_change_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyProfileActivity.this, ChangeMobileNumberActivity.class));
            }
        });
        btn_change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyProfileActivity.this, ChangePasswordActivity.class));
            }
        });
        btn_change_default_bankA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyProfileActivity.this, ChangeBankAccountActivity.class));
            }
        });
        btn_change_preference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyProfileActivity.this, ChangePreferenceActivity.class));
            }
        });

        if (Utils.isInternetConnected(getApplicationContext())) {
            getMyData();
        } else {
            showToast(MyProfileActivity.this, getResources().getString(R.string.oops_connect_your_internet));
        }
    }

    private void getMyData() {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<ProfileResponse> call = git.UserProfile();
            call.enqueue(new Callback<ProfileResponse>() {
                @Override
                public void onResponse(@NonNull Call<ProfileResponse> call, @NonNull retrofit2.Response<ProfileResponse> response) {
                    Log.d(TAG, "onResponse: " + "><><" + new Gson().toJson(response.body()));
                    try {
                        ProfileResponse profileResponse = response.body();
                        if (profileResponse != null && profileResponse.getProfile() != null) {
                            tv_user_name.setText(profileResponse.getProfile().getData().getName().getValue());
                            tvName.setText(profileResponse.getProfile().getData().getGivenname().getTitle());
                            tv_name.setText(profileResponse.getProfile().getData().getGivenname().getValue());
                            tvSurName.setText(profileResponse.getProfile().getData().getSurname().getTitle());
                            tv_sur_name.setText(profileResponse.getProfile().getData().getSurname().getValue());
                            tvEmail.setText(profileResponse.getProfile().getData().getEmail().getTitle());
                            tv_email.setText(profileResponse.getProfile().getData().getEmail().getValue());
                            tvAddress.setText(profileResponse.getProfile().getData().getAddress().getTitle());
                            tv_Address.setText(profileResponse.getProfile().getData().getAddress().getValue().replaceAll("<br>",""));
                            tvInvestorId.setText(profileResponse.getProfile().getData().getInvestorid().getTitle());
                            tv_investorId.setText(profileResponse.getProfile().getData().getInvestorid().getValue());
                            tvAccountId.setText(profileResponse.getProfile().getData().getAccountid().getTitle());
                            tv_accountId.setText(profileResponse.getProfile().getData().getAccountid().getValue());

                            try {
                                if (profileResponse.getProfile().getData().getAvatar().getValue() != null && !profileResponse.getProfile().getData().getAvatar().getValue().isEmpty() && !profileResponse.getProfile().getData().getAvatar().getValue().equals("null")) {
                                    Glide.with(MyProfileActivity.this).load(profileResponse.getProfile().getData().getAvatar().getValue())
                                            .thumbnail(0.5f)
                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                            .into(iv_user_img);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<ProfileResponse> call, @NonNull Throwable t) {
                    Log.e("response", "error " + t.getMessage());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
