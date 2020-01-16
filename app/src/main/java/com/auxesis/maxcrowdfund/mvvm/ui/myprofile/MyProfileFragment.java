package com.auxesis.maxcrowdfund.mvvm.ui.myprofile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.mvvm.activity.UploadImageActivity;
import com.auxesis.maxcrowdfund.constant.ProgressDialog;
import com.auxesis.maxcrowdfund.constant.Utils;
import com.auxesis.maxcrowdfund.custommvvm.profile.profileModel.ProfileResponse;
import com.auxesis.maxcrowdfund.mvvm.activity.HomeActivity;
import com.auxesis.maxcrowdfund.restapi.ApiClient;
import com.auxesis.maxcrowdfund.restapi.EndPointInterface;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

public class MyProfileFragment extends Fragment {
    private static final String TAG = "MyProfileFragment";
    private MyProfileViewModel myProfileViewModel;
    TextView tv_user_name, tvName, tv_name, tvSurName, tv_sur_name, tvEmail, tv_email, tvAddress, tv_Address, tvInvestorId, tv_investorId, tvAccountId, tv_accountId;
    Button btn_addChange, btn_change_email, btn_change_mobile, btn_change_pass, btn_change_default_bankA, btn_change_preference;
    ProgressDialog pd;
    CircleImageView iv_user_img;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // myProfileViewModel = ViewModelProviders.of(this).get(MyProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_my_profile, container, false);

        tv_user_name = root.findViewById(R.id.tv_user_name);
        tvName =  root.findViewById(R.id.tvName);
        tv_name =  root.findViewById(R.id.tv_name);
        tvSurName =  root.findViewById(R.id.tvSurName);
        tv_sur_name =  root.findViewById(R.id.tv_sur_name);
        tvEmail =  root.findViewById(R.id.tvEmail);
        tv_email =  root.findViewById(R.id.tv_email);
        tvAddress =  root.findViewById(R.id.tvAddress);
        tv_Address =  root.findViewById(R.id.tv_Address);
        tvInvestorId =  root.findViewById(R.id.tvInvestorId);
        tv_investorId =  root.findViewById(R.id.tv_investorId);
        tvAccountId =  root.findViewById(R.id.tvAccountId);
        tv_accountId =  root.findViewById(R.id.tv_accountId);
        iv_user_img =  root.findViewById(R.id.iv_user_img);

        btn_addChange =  root.findViewById(R.id.btn_addChange);
        btn_change_email =  root.findViewById(R.id.btn_change_email);
        btn_change_mobile =  root.findViewById(R.id.btn_change_mobile);
        btn_change_pass =  root.findViewById(R.id.btn_change_pass);
        btn_change_default_bankA =  root.findViewById(R.id.btn_change_default_bankA);
        btn_change_preference =  root.findViewById(R.id.btn_change_preference);

        btn_addChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UploadImageActivity.class));
            }
        });
        btn_change_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_nav_my_profile_to_changeEmailFragment);
            }
        });
        btn_change_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_nav_my_profile_to_changeMobileNumberFragment);
            }
        });
        btn_change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_nav_my_profile_to_changePasswordFragment);

              //  startActivity(new Intent(getActivity(), ChangePasswordActivity.class));
            }
        });
        btn_change_default_bankA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_nav_my_profile_to_changeBankAccountFragment);
            }
        });
        btn_change_preference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_nav_my_profile_to_changePreferenceFragment);
               // startActivity(new Intent(getActivity(), ChangePreferenceActivity.class));
            }
        });

        if (Utils.isInternetConnected(getActivity())) {
            getMyProfile();
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
        }
        return root;
    }

    private void getMyProfile() {
        try {
            pd = ProgressDialog.show(getActivity(), "Please Wait...");
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<ProfileResponse> call = git.UserProfile();
            call.enqueue(new Callback<ProfileResponse>() {
                @Override
                public void onResponse(@NonNull Call<ProfileResponse> call, @NonNull retrofit2.Response<ProfileResponse> response) {
                    Log.d(TAG, "onResponse: " + "><><" + new Gson().toJson(response.body()));
                    try {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
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
                                    Glide.with(getActivity()).load(profileResponse.getProfile().getData().getAvatar().getValue())
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


    public void onResume(){
        super.onResume();
        // Set title bar
        ((HomeActivity) getActivity()).setActionBarTitle(getString(R.string.menu_my_profile));
    }
}