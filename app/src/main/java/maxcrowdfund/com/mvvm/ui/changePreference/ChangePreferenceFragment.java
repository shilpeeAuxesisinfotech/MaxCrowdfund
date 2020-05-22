package maxcrowdfund.com.mvvm.ui.changePreference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import maxcrowdfund.com.R;
import maxcrowdfund.com.constant.MaxCrowdFund;
import maxcrowdfund.com.constant.ProgressDialog;
import maxcrowdfund.com.constant.Utils;
import maxcrowdfund.com.databinding.FragmentChangePreferenceBinding;
import maxcrowdfund.com.mvvm.activity.HomeActivity;
import maxcrowdfund.com.mvvm.activity.LoginActivity;
import maxcrowdfund.com.mvvm.ui.changePreference.adapter.LanguagePreferenceAdapter;
import maxcrowdfund.com.mvvm.ui.changePreference.model.ChangePreferenceResponse;
import maxcrowdfund.com.mvvm.ui.changePreference.model.Option;
import maxcrowdfund.com.mvvm.ui.changePreference.model.UpdatePreferenceResponse;
import maxcrowdfund.com.mvvm.ui.customAdapter.CustomAdapter;
import maxcrowdfund.com.mvvm.ui.customModels.CustomSpinnerModel;
import maxcrowdfund.com.restapi.ApiClient;
import maxcrowdfund.com.restapi.EndPointInterface;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePreferenceFragment extends Fragment {
    private static final String TAG = "ChangePreferenceFragmen";
    ProgressDialog pd;
    LanguagePreferenceAdapter languagePreferenceAdapter;
    CustomAdapter activeAccountAdapter;
    CustomAdapter signingAdapter;
    private int accountLogin = 0;
    private String mLanguage = "";
    private String mActive_account = "";
    private String mTrans_signing = "";
    private int mAccount_login = 0;
    private int mInvestment_opportunities = 0;
    private int mInvestment_updates = 0;
    private int mNewsletter = 0;
    private Activity mActivity;
    private String mSelected = "";
    List<Option> languageArraySelected = new ArrayList<>();
    List<Option> languageArray = new ArrayList<>();
    FragmentChangePreferenceBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChangePreferenceBinding.inflate(inflater, container, false);
        mActivity = getActivity();
        if (Utils.isInternetConnected(getActivity())) {
            getChangePreference();
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
        }

        binding.spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Option option = languagePreferenceAdapter.getItem(position);
                //selected_product_id = addProductModel.getProduct_id();
                Log.d(TAG, "onItemSelected: " + option.getKey());
                Log.d(TAG, "onItemSelected: " + option.getVal());
                mLanguage = option.getKey();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //For Active Account
        binding.spinnerActiveAccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CustomSpinnerModel optionActive = activeAccountAdapter.getItem(position);
                if (position == 0) {
                    mActive_account = "";
                } else {
                    mActive_account = optionActive.getKey();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //For Transation Siging
        binding.spinnerTranSigning.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CustomSpinnerModel option = signingAdapter.getItem(position);
                if (position == 0) {
                    mTrans_signing = "";
                } else {
                    mTrans_signing = option.getKey();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        // for Login Prefernec
        binding.radioGroupLogin.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (checkedId == R.id.loginOn) {
                    mAccount_login = 1;
                    Log.d(TAG, "onCheckedChanged: " + ">>>>>>>>>>>>>if----------" + mAccount_login);
                    binding.loginOn.setBackgroundResource(R.drawable.radio_button_bg_on);
                    binding.loginOff.setBackgroundResource(R.drawable.radio_button_bg_off);
                } else if (checkedId == R.id.loginOff) {
                    mAccount_login = 0;
                    Log.d(TAG, "onCheckedChanged: " + ">>>>>>>>>>>>else----------" + mAccount_login);
                    binding.loginOff.setBackgroundResource(R.drawable.radio_button_bg_on);
                    binding.loginOn.setBackgroundResource(R.drawable.radio_button_bg_off);
                }
            }
        });
        // for Investment
        binding.radioGroupInvestment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (checkedId == R.id.investmentOn) {
                    mInvestment_opportunities = 1;
                    Log.d(TAG, "onCheckedChanged: " + ">>>>>>>>>>>>if----------" + mInvestment_opportunities);
                    binding.investmentOn.setBackgroundResource(R.drawable.radio_button_bg_on);
                    binding.investmentOff.setBackgroundResource(R.drawable.radio_button_bg_off);
                } else if (checkedId == R.id.investmentOff) {
                    mInvestment_opportunities = 0;
                    Log.d(TAG, "onCheckedChanged: " + ">>>>>>>>>>>>else----------" + mInvestment_opportunities);
                    binding.investmentOn.setBackgroundResource(R.drawable.radio_button_bg_off);
                    binding.investmentOff.setBackgroundResource(R.drawable.radio_button_bg_on);
                }
            }
        });
        // for InvestUpdate
        binding.radioGroupInvestUpdate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (checkedId == R.id.investUpdateOn) {
                    mInvestment_updates = 1;
                    binding.investUpdateOn.setBackgroundResource(R.drawable.radio_button_bg_on);
                    binding.investUpdateOff.setBackgroundResource(R.drawable.radio_button_bg_off);
                } else if (checkedId == R.id.investUpdateOff) {
                    mInvestment_updates = 0;
                    binding.investUpdateOff.setBackgroundResource(R.drawable.radio_button_bg_on);
                    binding.investUpdateOn.setBackgroundResource(R.drawable.radio_button_bg_off);
                }
            }
        });
        // for Newslatter
        binding.radioGroupNewslatter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (checkedId == R.id.newslatterOn) {
                    mNewsletter = 1;
                    binding.newslatterOn.setBackgroundResource(R.drawable.radio_button_bg_on);
                    binding.newslatterOff.setBackgroundResource(R.drawable.radio_button_bg_off);
                } else if (checkedId == R.id.newslatterOff) {
                    mNewsletter = 0;
                    binding.newslatterOff.setBackgroundResource(R.drawable.radio_button_bg_on);
                    binding.newslatterOn.setBackgroundResource(R.drawable.radio_button_bg_off);
                }
            }
        });
        binding.btnUpdatePreference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isInternetConnected(getActivity())) {
                    if (mLanguage != null) {
                        if (mActive_account.length() > 0 && mActive_account != null) {
                            if (mTrans_signing.length() > 0 && mTrans_signing != null) {
                                getUpdatePreferenceApi();
                            } else {
                                Toast.makeText(getActivity(), getResources().getString(R.string.hint_trans_signing), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.hint_active_account), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.hint_language), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return binding.getRoot();
    }
    private void getChangePreference() {
        pd = ProgressDialog.show(getActivity(), "Please Wait...");
        EndPointInterface git = ApiClient.getClient1(getActivity()).create(EndPointInterface.class);
        String XCSRF = Utils.getPreference(getActivity(), "mCsrf_token");
        Call<ChangePreferenceResponse> call = git.getChangePreferenceAPI("application/json", XCSRF);
        call.enqueue(new Callback<ChangePreferenceResponse>() {
            @Override
            public void onResponse(Call<ChangePreferenceResponse> call, Response<ChangePreferenceResponse> response) {
                Log.d(TAG, "onResponse: " + "><><" + new Gson().toJson(response.body()));
                try {
                    if (response.code() == 200) {
                        if (response != null && response.isSuccessful()) {
                            if (pd != null && pd.isShowing()) {
                                pd.dismiss();
                            }
                            if (response.body().getUserLoginStatus() == 1) {
                                /* For Language Preference */
                                binding.tvLanguage.setText(response.body().getPreferences().getData().getLanguage().getTitle());
                                if (response.body().getPreferences().getData().getLanguage().getOptions().size() > 0) {
                                    mSelected = response.body().getPreferences().getData().getLanguage().getValue();
                                    Log.d("><><selected><>", response.body().getPreferences().getData().getLanguage().getValue());
                                    languageArray.clear();
                                    languageArraySelected.clear();
                                    for (int i = 0; i < response.body().getPreferences().getData().getLanguage().getOptions().size(); i++) {
                                        if (response.body().getPreferences().getData().getLanguage().getOptions().get(i).getKey().equalsIgnoreCase(mSelected))
                                            languageArray.set(0, response.body().getPreferences().getData().getLanguage().getOptions().get(i));
                                        languageArray.add(response.body().getPreferences().getData().getLanguage().getOptions().get(i));
                                    }
                                    languageArraySelected.addAll(languageArray);
                                    languagePreferenceAdapter = new LanguagePreferenceAdapter(getActivity(), languageArraySelected);
                                    binding.spinnerLanguage.setAdapter(languagePreferenceAdapter);
                                }
                                /*For Active account */
                                List<CustomSpinnerModel> modelActiveList = new ArrayList<>();
                                List<CustomSpinnerModel> modelActiveList1 = new ArrayList<>();
                                binding.tvActiveAccount.setText(response.body().getPreferences().getData().getActiveAccount().getTitle());
                                modelActiveList.clear();
                                modelActiveList1.clear();
                                CustomSpinnerModel spinnerActiveModel1 = new CustomSpinnerModel();
                                spinnerActiveModel1.setKey("Please select");
                                spinnerActiveModel1.setVal("Please select");
                                modelActiveList1.add(0, spinnerActiveModel1);

                                if (response.body().getPreferences().getData().getActiveAccount().getOptions().size() > 0) {
                                    for (int i = 0; i < response.body().getPreferences().getData().getActiveAccount().getOptions().size(); i++) {
                                        CustomSpinnerModel spinnerModel = new CustomSpinnerModel();
                                        spinnerModel.setKey(response.body().getPreferences().getData().getActiveAccount().getOptions().get(i).getKey());
                                        spinnerModel.setVal(response.body().getPreferences().getData().getActiveAccount().getOptions().get(i).getVal());
                                        modelActiveList.add(spinnerModel);
                                    }
                                    modelActiveList1.addAll(modelActiveList);
                                    activeAccountAdapter = new CustomAdapter(getActivity(), modelActiveList1);
                                    binding.spinnerActiveAccount.setAdapter(activeAccountAdapter);
                                }

                                //For Transation Sigining
                                List<CustomSpinnerModel> modelList = new ArrayList<>();
                                List<CustomSpinnerModel> modelList1 = new ArrayList<>();
                                binding.tvTransactionSigning.setText(response.body().getPreferences().getData().getTransactionSigning().getTitle());
                                modelList.clear();
                                modelList1.clear();
                                CustomSpinnerModel spinnerModel1 = new CustomSpinnerModel();
                                spinnerModel1.setKey("Please select");
                                spinnerModel1.setVal("Please select");
                                modelList1.add(0, spinnerModel1);
                                if (response.body().getPreferences().getData().getTransactionSigning().getOptions().size() > 0) {
                                    for (int i = 0; i < response.body().getPreferences().getData().getTransactionSigning().getOptions().size(); i++) {
                                        CustomSpinnerModel spinnerModel = new CustomSpinnerModel();
                                        spinnerModel.setKey(response.body().getPreferences().getData().getTransactionSigning().getOptions().get(i).getKey());
                                        spinnerModel.setVal(response.body().getPreferences().getData().getTransactionSigning().getOptions().get(i).getVal());
                                        modelList.add(spinnerModel);
                                    }
                                    modelList1.addAll(modelList);
                                    signingAdapter = new CustomAdapter(getActivity(), modelList1);
                                    binding.spinnerTranSigning.setAdapter(signingAdapter);
                                }
                                //For Notification Preference
                                binding.tvNotifications.setText(response.body().getPreferences().getData().getNotificationPreferences().getHeading());
                                //For Login Account
                                binding.tvAccountLogin.setText(response.body().getPreferences().getData().getNotificationPreferences().getData().get(0).getTitle());
                                accountLogin = response.body().getPreferences().getData().getNotificationPreferences().getData().get(0).getValue();

                                if (response.body().getPreferences().getData().getNotificationPreferences().getData().get(0).getValue() == 1) {
                                    mAccount_login = response.body().getPreferences().getData().getNotificationPreferences().getData().get(0).getValue();
                                    binding.loginOn.setChecked(true);
                                    binding.loginOn.setBackgroundResource(R.drawable.radio_button_bg_on);
                                } else {
                                    mAccount_login = response.body().getPreferences().getData().getNotificationPreferences().getData().get(0).getValue();
                                    binding.loginOff.setChecked(false);
                                    binding.loginOff.setBackgroundResource(R.drawable.radio_button_bg_off);
                                }
                                //For InvestmentOpp
                                binding.tvInvestmentOpp.setText(response.body().getPreferences().getData().getNotificationPreferences().getData().get(1).getTitle());

                                if (response.body().getPreferences().getData().getNotificationPreferences().getData().get(1).getValue() == 1) {
                                    mInvestment_opportunities = response.body().getPreferences().getData().getNotificationPreferences().getData().get(1).getValue();
                                    binding.investmentOn.setChecked(true);
                                    binding.investmentOn.setBackgroundResource(R.drawable.radio_button_bg_on);
                                } else {
                                    mInvestment_opportunities = response.body().getPreferences().getData().getNotificationPreferences().getData().get(1).getValue();
                                    binding.investmentOff.setChecked(false);
                                    binding.investmentOff.setBackgroundResource(R.drawable.radio_button_bg_off);
                                }
                                // for InvestmentUpdate
                                binding.tvInvestmentUpdate.setText(response.body().getPreferences().getData().getNotificationPreferences().getData().get(2).getTitle());

                                if (response.body().getPreferences().getData().getNotificationPreferences().getData().get(2).getValue() == 1) {
                                    mInvestment_updates = response.body().getPreferences().getData().getNotificationPreferences().getData().get(2).getValue();
                                    binding.investUpdateOn.setChecked(true);
                                    binding.investUpdateOn.setBackgroundResource(R.drawable.radio_button_bg_on);
                                } else {
                                    mInvestment_updates = response.body().getPreferences().getData().getNotificationPreferences().getData().get(2).getValue();
                                    binding.investUpdateOff.setChecked(false);
                                    binding.investUpdateOff.setBackgroundResource(R.drawable.radio_button_bg_off);
                                }
                                //For Newslatter
                                binding.tvNewslatter.setText(response.body().getPreferences().getData().getNotificationPreferences().getData().get(3).getTitle());
                                Log.d(">>>>>>>>>", response.body().getPreferences().getData().getNotificationPreferences().getData().get(3).getTitle());

                                if (response.body().getPreferences().getData().getNotificationPreferences().getData().get(3).getValue() == 1) {
                                    mNewsletter = response.body().getPreferences().getData().getNotificationPreferences().getData().get(3).getValue();
                                   binding.newslatterOn.setChecked(true);
                                    binding.newslatterOn.setBackgroundResource(R.drawable.radio_button_bg_on);
                                } else {
                                    mNewsletter = response.body().getPreferences().getData().getNotificationPreferences().getData().get(3).getValue();
                                    binding.newslatterOff.setChecked(false);
                                    binding.newslatterOff.setBackgroundResource(R.drawable.radio_button_bg_off);
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ChangePreferenceResponse> call, Throwable t) {
                Log.e("", "error " + t.getMessage());
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getUpdatePreferenceApi() {
        pd = ProgressDialog.show(getActivity(), "Please Wait...");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("language", mLanguage);
        jsonObject.addProperty("active_account", mActive_account);
        jsonObject.addProperty("trans_signing", mTrans_signing);
        jsonObject.addProperty("account_login", mAccount_login);
        jsonObject.addProperty("investment_opportunities", mInvestment_opportunities);
        jsonObject.addProperty("investment_updates", mInvestment_updates);
        jsonObject.addProperty("newsletter", mNewsletter);
        Log.d(">>>>>>>>>>>>>>>", jsonObject.toString());
        EndPointInterface git = ApiClient.getClient1(getActivity()).create(EndPointInterface.class);
        String XCSRF = Utils.getPreference(getActivity(), "mCsrf_token");
        Call<UpdatePreferenceResponse> call = git.getUpdatePreferenceAPI("application/json", XCSRF, jsonObject);
        call.enqueue(new Callback<UpdatePreferenceResponse>() {
            @Override
            public void onResponse(Call<UpdatePreferenceResponse> call, Response<UpdatePreferenceResponse> response) {
                Log.d(TAG, "onResponse: " + "><><" + new Gson().toJson(response.body()));
                try {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    if (response.code() == 200) {
                        if (response != null && response.isSuccessful()) {
                            if (response.body().getUserLoginStatus() == 1) {
                                if (response.body().getResult() != null) {
                                    mLanguage = "";
                                    mActive_account = "";
                                    mTrans_signing = "";
                                    mAccount_login = 0;
                                    mInvestment_opportunities = 0;
                                    mInvestment_updates = 0;
                                    mNewsletter = 0;
                                    Toast.makeText(getActivity(), getResources().getString(R.string.hint_updated_succ), Toast.LENGTH_SHORT).show();
                                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                                    navController.navigateUp();
                                    Log.d(TAG, "onResponse: " + "><><" + response.body().getResult());
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<UpdatePreferenceResponse> call, Throwable t) {
                Log.e("", "error " + t.getMessage());
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void onResume() {
        super.onResume();
        // Set title bar
        ((HomeActivity) getActivity()).setActionBarTitle(getString(R.string.change_preference));
    }
}
