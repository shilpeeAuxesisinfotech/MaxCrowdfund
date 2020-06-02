package maxcrowdfund.com.mvvm.ui.myprofile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import maxcrowdfund.com.R;
import maxcrowdfund.com.constant.MaxCrowdFund;
import maxcrowdfund.com.constant.ProgressDialog;
import maxcrowdfund.com.constant.Utils;
import maxcrowdfund.com.databinding.FragmentMyProfileBinding;
import maxcrowdfund.com.mvvm.activity.HomeActivity;
import maxcrowdfund.com.mvvm.activity.LoginActivity;
import maxcrowdfund.com.mvvm.ui.myprofile.model.ProfileResponse;
import maxcrowdfund.com.restapi.ApiClient;
import maxcrowdfund.com.restapi.EndPointInterface;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import retrofit2.Call;
import retrofit2.Callback;

public class MyProfileFragment extends Fragment {
    ProgressDialog pd;
    Activity mActivity;
    String mMobile = "";
    String mCountryCode = "";
    FragmentMyProfileBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyProfileBinding.inflate(inflater);
        mActivity = getActivity();
        binding.btnAddChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_nav_my_profile_to_uploadImgFragment);
            }
        });
        binding.btnChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_nav_my_profile_to_changeEmailFragment);
            }
        });
        binding.btnChangeMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMobile != null) {
                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                    Utils.setPreference(mActivity, "mobile", mMobile);
                    Utils.setPreference(mActivity, "countryCode", mCountryCode);
                    navController.navigate(R.id.action_nav_my_profile_to_changeMobileNumberFragment);
                }
            }
        });
        binding.btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_nav_my_profile_to_changePasswordFragment);
            }
        });
        binding.btnChangeDefaultBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_nav_my_profile_to_changeBankAccountFragment);
            }
        });
        binding.btnChangePreference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(mActivity, R.id.nav_host_fragment);
                navController.navigate(R.id.action_nav_my_profile_to_changePreferenceFragment);
            }
        });
        if (Utils.isInternetConnected(mActivity)) {
            getMyProfile();
        } else {
            Toast.makeText(mActivity, getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
        }
        return binding.getRoot();
    }
    private void getMyProfile() {
        try {
            pd = ProgressDialog.show(mActivity, "Please Wait...");
            String XCSRF = Utils.getPreference(mActivity, "mCsrf_token");
            EndPointInterface git = ApiClient.getClient1(mActivity).create(EndPointInterface.class);
            Call<ProfileResponse> call = git.UserProfile("application/json", XCSRF);
            call.enqueue(new Callback<ProfileResponse>() {
                @Override
                public void onResponse(@NonNull Call<ProfileResponse> call, @NonNull retrofit2.Response<ProfileResponse> response) {
                    Log.d("onResponse: ", "><><" + new Gson().toJson(response.body()));
                    try {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        if (response.code() == 200) {
                            if (response != null && response.isSuccessful()) {
                                if (response.body().getUserLoginStatus() == 1) {
                                    ProfileResponse profileResponse = response.body();
                                    if (profileResponse != null && profileResponse.getProfile() != null) {

                                        binding.tvTitleName.setText(profileResponse.getProfile().getData().getName().getValue());
                                        binding.tvUserNameTitle.setText(profileResponse.getProfile().getData().getGivenname().getTitle());
                                        binding.tvUserNameValue.setText(profileResponse.getProfile().getData().getGivenname().getValue());
                                        binding.tvSurNameTittle.setText(profileResponse.getProfile().getData().getSurname().getTitle());
                                        binding.tvSurNameValue.setText(profileResponse.getProfile().getData().getSurname().getValue());
                                        binding.tvEmailTitle.setText(profileResponse.getProfile().getData().getEmail().getTitle());
                                        binding.tvEmailValue.setText(profileResponse.getProfile().getData().getEmail().getValue());
                                        binding.tvAddressTitle.setText(profileResponse.getProfile().getData().getAddress().getTitle());
                                        binding.tvAddressValue.setText(profileResponse.getProfile().getData().getAddress().getValue().replaceAll("<br>", ""));
                                        binding.tvInvestorIdTitle.setText(profileResponse.getProfile().getData().getInvestorid().getTitle());
                                        binding.tvInvestorIdValue.setText(profileResponse.getProfile().getData().getInvestorid().getValue());
                                        binding.tvAccountIdTitle.setText(profileResponse.getProfile().getData().getAccountid().getTitle());
                                        binding.tvAccountIdValue.setText(profileResponse.getProfile().getData().getAccountid().getValue());
                                        mMobile = String.valueOf(profileResponse.getProfile().getData().getMobileNumber().getValue());
                                        mCountryCode = profileResponse.getProfile().getData().getAddressCountryCode().getValue();
                                        try {
                                            if (profileResponse.getProfile().getData().getAvatar().getValue() != null && !profileResponse.getProfile().getData().getAvatar().getValue().isEmpty() && !profileResponse.getProfile().getData().getAvatar().getValue().equals("null")) {
                                                Glide.with(getActivity()).load(profileResponse.getProfile().getData().getAvatar().getValue())
                                                        .thumbnail(0.5f)
                                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                        .into(binding.ivUserImg);
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
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
                                Toast.makeText(mActivity, getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (pd != null && pd.isShowing()) {
                                pd.dismiss();
                            }
                            Toast.makeText(mActivity, getResources().getString(R.string.something_went), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<ProfileResponse> call, @NonNull Throwable t) {
                    Log.e("response", "error " + t.getMessage());
                    Toast.makeText(mActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void onResume() {
        super.onResume();
        ((HomeActivity) getActivity()).setActionBarTitle(Utils.profilePageTitle);
    }
}