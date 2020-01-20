package com.auxesis.maxcrowdfund.mvvm.ui.changePreference;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.constant.ProgressDialog;
import com.auxesis.maxcrowdfund.constant.Utils;
import com.auxesis.maxcrowdfund.mvvm.activity.HomeActivity;
import com.auxesis.maxcrowdfund.mvvm.ui.changePreference.changePreferenceModel.ChangePreferenceResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.changePreference.changePreferenceModel.Option;
import com.auxesis.maxcrowdfund.mvvm.ui.changePreference.changePreferenceModel.Option_;
import com.auxesis.maxcrowdfund.mvvm.ui.changePreference.changePreferenceModel.Option__;
import com.auxesis.maxcrowdfund.mvvm.ui.changePreference.changePreferenceModel.TransactionSigningAdapter;
import com.auxesis.maxcrowdfund.restapi.ApiClient;
import com.auxesis.maxcrowdfund.restapi.EndPointInterface;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePreferenceFragment extends Fragment {
    private static final String TAG = "ChangePreferenceFragmen";
    ProgressDialog pd;
    TextView tvLanguage, tvActiveAccount, tvNotifications, tvTransaction_signing, tvAccountLogin, tvInvestmentOpp, tvInvestmentUpdate, tvNewslatter;
    Spinner spinnerLanguage, spinnerActiveAccount, spinnerTranSigning;

    LanguagePreferenceAdapter languagePreferenceAdapter;
    ActiveAccountAdapter activeAccountAdapter;
    TransactionSigningAdapter signingAdapter;
    RadioGroup radioGroupLogin, radioGroupInvestment, radioGroupInvestUpdate, radioGroupNewslatter;
    RadioButton loginOff, loginOn, investment_off, investment_on, investUpdate_off, investUpdate_on, newslatter_off, newslatter_on;
    private int accountLogin = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_change_preference, container, false);

        tvLanguage = root.findViewById(R.id.tvLanguage);
        tvActiveAccount = root.findViewById(R.id.tvActiveAccount);
        tvNotifications = root.findViewById(R.id.tvNotifications);
        tvTransaction_signing = root.findViewById(R.id.tvTransaction_signing);

        tvAccountLogin = root.findViewById(R.id.tvAccountLogin);
        tvInvestmentOpp = root.findViewById(R.id.tvInvestmentOpp);
        tvInvestmentUpdate = root.findViewById(R.id.tvInvestmentUpdate);
        tvNewslatter = root.findViewById(R.id.tvNewslatter);

        spinnerLanguage = root.findViewById(R.id.spinnerLanguage);
        spinnerActiveAccount = root.findViewById(R.id.spinnerActiveAccount);
        spinnerTranSigning = root.findViewById(R.id.spinnerTranSigning);

        radioGroupLogin = root.findViewById(R.id.radioGroupLogin);
        radioGroupLogin.clearCheck();

        radioGroupInvestment = root.findViewById(R.id.radioGroupInvestment);
        radioGroupInvestUpdate = root.findViewById(R.id.radioGroupInvestUpdate);
        radioGroupNewslatter = root.findViewById(R.id.radioGroupNewslatter);

        loginOff = root.findViewById(R.id.loginOff);
        loginOn = root.findViewById(R.id.loginOn);

        investment_off = root.findViewById(R.id.investment_off);
        investment_on = root.findViewById(R.id.investment_on);

        investUpdate_off = root.findViewById(R.id.investUpdate_off);
        investUpdate_on = root.findViewById(R.id.investUpdate_on);

        newslatter_off = root.findViewById(R.id.newslatter_off);
        newslatter_on = root.findViewById(R.id.newslatter_on);

        if (Utils.isInternetConnected(getActivity())) {
            getChangePreference();
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
        }

        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Option option = languagePreferenceAdapter.getItem(position);
                //selected_product_id = addProductModel.getProduct_id();
                Log.d(TAG, "onItemSelected: " + option.getKey());
                Log.d(TAG, "onItemSelected: " + option.getVal());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //For Active Account
        spinnerActiveAccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Option_ option = activeAccountAdapter.getItem(position);
                //selected_product_id = addProductModel.getProduct_id();
                Log.d(TAG, "onItemSelected: " + option.getKey());
                Log.d(TAG, "onItemSelected: " + option.getVal());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //For Transation Siging
        spinnerTranSigning.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Option__ option = signingAdapter.getItem(position);
                //selected_product_id = addProductModel.getProduct_id();
                Log.d(TAG, "onItemSelected: " + option.getKey());
                Log.d(TAG, "onItemSelected: " + option.getVal());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // for Login Prefernec
        radioGroupLogin.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (checkedId == R.id.loginOn) {
                    Log.d(TAG, "onCheckedChanged: "+">>>>>>>>>>>>>if----------");
                    loginOn.setBackgroundResource(R.drawable.radio_button_bg_on);
                    loginOff.setBackgroundResource(R.drawable.radio_button_bg_off);
                    Toast.makeText(getActivity(), rb.getText(), Toast.LENGTH_SHORT).show();
                }else if (checkedId==R.id.loginOff){
                    Log.d(TAG, "onCheckedChanged: "+">>>>>>>>>>>>else----------");
                    loginOff.setBackgroundResource(R.drawable.radio_button_bg_on);
                    loginOn.setBackgroundResource(R.drawable.radio_button_bg_off);
                    Toast.makeText(getActivity(), rb.getText(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // for Investment
        radioGroupInvestment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (checkedId == R.id.investment_on) {
                    investment_on.setBackgroundResource(R.drawable.radio_button_bg_on);
                    investment_off.setBackgroundResource(R.drawable.radio_button_bg_off);
                    Toast.makeText(getActivity(), rb.getText(), Toast.LENGTH_SHORT).show();
                }else if (checkedId==R.id.investment_off){
                    investment_on.setBackgroundResource(R.drawable.radio_button_bg_off);
                    investment_off.setBackgroundResource(R.drawable.radio_button_bg_on);
                    Toast.makeText(getActivity(), rb.getText(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // for InvestUpdate
        radioGroupInvestUpdate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (checkedId == R.id.investUpdate_on) {
                    investUpdate_on.setBackgroundResource(R.drawable.radio_button_bg_on);
                    investUpdate_off.setBackgroundResource(R.drawable.radio_button_bg_off);
                    Toast.makeText(getActivity(), rb.getText(), Toast.LENGTH_SHORT).show();
                }else if (checkedId==R.id.investUpdate_off){
                    investUpdate_off.setBackgroundResource(R.drawable.radio_button_bg_on);
                    investUpdate_on.setBackgroundResource(R.drawable.radio_button_bg_off);
                    Toast.makeText(getActivity(), rb.getText(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // for Newslatter
        radioGroupNewslatter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (checkedId == R.id.newslatter_on) {
                    newslatter_on.setBackgroundResource(R.drawable.radio_button_bg_on);
                    newslatter_off.setBackgroundResource(R.drawable.radio_button_bg_off);
                    Toast.makeText(getActivity(), rb.getText(), Toast.LENGTH_SHORT).show();
                }else if (checkedId==R.id.newslatter_off){
                    newslatter_off.setBackgroundResource(R.drawable.radio_button_bg_on);
                    newslatter_on.setBackgroundResource(R.drawable.radio_button_bg_off);
                    Toast.makeText(getActivity(), rb.getText(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return root;
    }

    private void getChangePreference() {
        pd = ProgressDialog.show(getActivity(), "Please Wait...");
        EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
        Call<ChangePreferenceResponse> call = git.getChangePreferenceAPI("application/json");
        call.enqueue(new Callback<ChangePreferenceResponse>() {
            @Override
            public void onResponse(Call<ChangePreferenceResponse> call, Response<ChangePreferenceResponse> response) {
                Log.d(TAG, "onResponse: " + "><><" + new Gson().toJson(response.body()));
                try {
                    if (response != null && response.isSuccessful()) {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        /* For Language Preference */
                        tvLanguage.setText(response.body().getPreferences().getData().getLanguage().getTitle());
                        if (response.body().getPreferences().getData().getLanguage().getOptions().size() > 0) {
                            languagePreferenceAdapter = new LanguagePreferenceAdapter(getActivity(), response.body().getPreferences().getData().getLanguage().getOptions());
                            spinnerLanguage.setAdapter(languagePreferenceAdapter);
                        }
                        /*For Active account */
                        tvActiveAccount.setText(response.body().getPreferences().getData().getActiveAccount().getTitle());
                        if (response.body().getPreferences().getData().getActiveAccount().getOptions().size() > 0) {
                            activeAccountAdapter = new ActiveAccountAdapter(getActivity(), response.body().getPreferences().getData().getActiveAccount().getOptions());
                            spinnerActiveAccount.setAdapter(activeAccountAdapter);
                        }
                        //For Transation Sigining
                        tvTransaction_signing.setText(response.body().getPreferences().getData().getTransactionSigning().getTitle());
                        if (response.body().getPreferences().getData().getTransactionSigning().getOptions().size() > 0) {
                            signingAdapter = new TransactionSigningAdapter(getActivity(), response.body().getPreferences().getData().getTransactionSigning().getOptions());
                            spinnerTranSigning.setAdapter(signingAdapter);
                        }

                        //For Notification Preference
                        tvNotifications.setText(response.body().getPreferences().getData().getNotificationPreferences().getHeading());
                        //For Login Account
                        tvAccountLogin.setText(response.body().getPreferences().getData().getNotificationPreferences().getData().get(0).getTitle());
                        accountLogin = response.body().getPreferences().getData().getNotificationPreferences().getData().get(0).getValue();

                        if (response.body().getPreferences().getData().getNotificationPreferences().getData().get(0).getValue() == 1) {
                            loginOn.setChecked(true);
                            loginOn.setBackgroundResource(R.drawable.radio_button_bg_on);
                        } else {
                            loginOff.setChecked(false);
                            loginOff.setBackgroundResource(R.drawable.radio_button_bg_off);
                        }
                        //For InvestmentOpp
                        tvInvestmentOpp.setText(response.body().getPreferences().getData().getNotificationPreferences().getData().get(1).getTitle());

                        if (response.body().getPreferences().getData().getNotificationPreferences().getData().get(1).getValue() == 1) {
                            investment_on.setChecked(true);
                            investment_on.setBackgroundResource(R.drawable.radio_button_bg_on);
                        } else {
                            investment_off.setChecked(false);
                            investment_off.setBackgroundResource(R.drawable.radio_button_bg_off);
                        }
                        // for InvestmentUpdate
                        tvInvestmentUpdate.setText(response.body().getPreferences().getData().getNotificationPreferences().getData().get(2).getTitle());

                        if (response.body().getPreferences().getData().getNotificationPreferences().getData().get(2).getValue() == 1) {
                            investUpdate_on.setChecked(true);
                            investUpdate_on.setBackgroundResource(R.drawable.radio_button_bg_on);
                        } else {
                            investUpdate_off.setChecked(false);
                            investUpdate_off.setBackgroundResource(R.drawable.radio_button_bg_off);
                        }
                        //For Newslatter
                        tvNewslatter.setText(response.body().getPreferences().getData().getNotificationPreferences().getData().get(3).getTitle());

                        if (response.body().getPreferences().getData().getNotificationPreferences().getData().get(3).getValue() == 1) {
                            newslatter_on.setChecked(true);
                            newslatter_on.setBackgroundResource(R.drawable.radio_button_bg_on);
                        } else {
                            newslatter_off.setChecked(false);
                            newslatter_off.setBackgroundResource(R.drawable.radio_button_bg_off);
                        }
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

    public void onResume() {
        super.onResume();
        // Set title bar
        ((HomeActivity) getActivity()).setActionBarTitle(getString(R.string.change_preference));
    }
}
