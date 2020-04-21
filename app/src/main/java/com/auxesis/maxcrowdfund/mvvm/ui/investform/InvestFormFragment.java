package com.auxesis.maxcrowdfund.mvvm.ui.investform;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.constant.MaxCrowdFund;
import com.auxesis.maxcrowdfund.constant.ProgressDialog;
import com.auxesis.maxcrowdfund.mvvm.activity.LoginActivity;
import com.auxesis.maxcrowdfund.mvvm.ui.changeMobileNumber.changeMobileModel.SendOTPResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.changePreference.adapter.TransactionSigningAdapter;
import com.auxesis.maxcrowdfund.mvvm.ui.changePreference.model.ChangePreferenceResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.changePreference.model.Option__;
import com.auxesis.maxcrowdfund.mvvm.ui.home.homeDetail.investmodel.CreateInvestmentResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.investform.questmodel.InvestFormPreloadResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.investform.questmodel.famodel.TfaValidateResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.investform.questmodel.model.SurveyFormDataInsertResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.investform.questmodel.questionlistmodel.InvestAmountKeyUpResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.investform.questmodel.questionlistmodel.InvestSurveyRuestionResponse;
import com.auxesis.maxcrowdfund.restapi.ApiClient;
import com.auxesis.maxcrowdfund.restapi.EndPointInterface;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.auxesis.maxcrowdfund.constant.Utils.getPreference;
import static com.auxesis.maxcrowdfund.constant.Utils.isInternetConnected;
import static com.auxesis.maxcrowdfund.constant.Utils.setPreference;

public class InvestFormFragment extends Fragment {
    private static final String TAG = "InvestFormFragment";
    TextView tvLoanId, tvLoanName, tvMpgToken, tvCharged, tvHeading, tvQuestion, tvTotalAccount, tvTotalWallet;
    Spinner spinnerFA;
    EditText edtAccount, edtOTP;
    LinearLayout lLayoutOTP;
    String error_msg = "";
    ProgressDialog pd;
    Activity mActivity;
    String mLoanId = "";
    String mTittle = "";
    Button btnContinue, btnQuestionContinue, btnQuestCancel, btnSend, btnFACancel, btnFASubmit;
    RadioButton radBtnQ1, radBtnQ2, radBtnQ3;
    CardView cvInvestForm, cvQuestion, cvTwoFA;
    RadioGroup radioGroup;
    private String mQuesting1 = "";
    private String mQuesting2 = "";
    private String mQuesting3 = "";
    private String mQuesting4 = "";
    private String mQuesting5 = "";
    private String mQuesting6 = "";
    private String mQuesting7 = "";
    private String mQuesting8 = "";
    private String mQuesting9 = "";
    private int mThresholdValue = 0;
    boolean isQuestionSet1 = false;
    boolean isQuestionSet2 = false;
    boolean isQuestionSet3 = false;
    boolean isQuestionSet4 = false;
    boolean isQuestionSet5 = false;
    boolean isQuestionSet6 = false;
    boolean isQuestionSet7 = false;
    boolean isQuestionSet8 = false;
    boolean isQuestionSet9 = false;

    private String mQuestSelected1 = "";
    private String mQuestSelected2 = "";
    private String mQuestSelected3 = "";
    private String mQuestSelected4 = "";
    private String mQuestSelected5 = "";
    private String mQuestSelected6 = "";
    private String mQuestSelected7 = "";
    private String mQuestSelected8 = "";
    private String mQuestSelected9 = "";
    private String mAmountData = "";
    private String mTotalChargeMpgToken = "";
    TransactionSigningAdapter signingAdapter;
    String mTrans_signing = "";
    String mResponseOTP = "";
    String mEnterOTP = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_invest_form, container, false);
        mActivity = getActivity();
        edtAccount = root.findViewById(R.id.edtAccount);
        tvLoanId = root.findViewById(R.id.tvLoanId);
        tvLoanName = root.findViewById(R.id.tvLoanName);
        tvMpgToken = root.findViewById(R.id.tvMpgToken);
        tvCharged = root.findViewById(R.id.tvCharged);

        tvTotalAccount = root.findViewById(R.id.tvTotalAccount);
        tvTotalWallet = root.findViewById(R.id.tvTotalWallet);

        spinnerFA = root.findViewById(R.id.spinnerFA);
        edtOTP = root.findViewById(R.id.edtOTP);
        lLayoutOTP = root.findViewById(R.id.lLayoutOTP);
        //lLayoutOTP.setVisibility(View.GONE);
        btnSend = root.findViewById(R.id.btnSend);
        btnFACancel = root.findViewById(R.id.btnFACancel);
        btnFASubmit = root.findViewById(R.id.btnFASubmit);

        cvInvestForm = root.findViewById(R.id.cvInvestForm);
        cvQuestion = root.findViewById(R.id.cvQuestion);
        cvTwoFA = root.findViewById(R.id.cvTwoFA);
        cvInvestForm.setVisibility(View.VISIBLE);
        cvQuestion.setVisibility(View.GONE);
        cvTwoFA.setVisibility(View.GONE);
        tvHeading = root.findViewById(R.id.tvHeading);
        tvQuestion = root.findViewById(R.id.tvQuestion);

        radioGroup = root.findViewById(R.id.radioGroup);
        radBtnQ1 = root.findViewById(R.id.radBtnQ1);
        radBtnQ2 = root.findViewById(R.id.radBtnQ2);
        radBtnQ3 = root.findViewById(R.id.radBtnQ3);
        btnContinue = root.findViewById(R.id.btnContinue);
        btnQuestCancel = root.findViewById(R.id.btnQuestCancel);
        btnQuestionContinue = root.findViewById(R.id.btnQuestionContinue);

        if (getPreference(mActivity, "loan_id") != null && getPreference(mActivity, "tittle") != null) {
            mLoanId = getPreference(mActivity, "loan_id");
            mTittle = getPreference(mActivity, "tittle");
            tvLoanName.setText("-" + mTittle);
            tvLoanId.setText("#" + mLoanId);
            if (isInternetConnected(mActivity)) {
                getInvestFormPreload(mLoanId);
            } else {
                Toast.makeText(mActivity, getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
            }
        }
        if (isInternetConnected(mActivity)) {
            getChangePreference();
        } else {
            Toast.makeText(mActivity, getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
        }
        return root;
    }

    private void getInvestFormPreload(String mLoanId) {
        pd = ProgressDialog.show(mActivity, "Please Wait...");
        String XCSRF = getPreference(mActivity, "mCsrf_token");
        EndPointInterface endPointInterface = ApiClient.getClient1(mActivity).create(EndPointInterface.class);
        Call<InvestFormPreloadResponse> call = endPointInterface.getInvestFormPreload(mLoanId, "application/json", XCSRF);
        call.enqueue(new Callback<InvestFormPreloadResponse>() {
            @Override
            public void onResponse(Call<InvestFormPreloadResponse> call, Response<InvestFormPreloadResponse> response) {
                Log.d(TAG, "onResponse: " + "><><" + new Gson().toJson(response.body()));
                try {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    if (response.code() == 200) {
                        if (response != null && response.isSuccessful()) {
                            if (response.body().getUserLoginStatus() == 1) {
                                if (response.body().getWarningShow() == 0) {
                                    // no warning message and no prepopulate amount
                                    btnContinue.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (isInternetConnected(mActivity)) {
                                                if (Validation()) {
                                                    String mAmount = edtAccount.getText().toString().trim();
                                                    if (getPreference(mActivity, "loanId") != null) {
                                                        getInvestAmountKeyUp(mLoanId, mAmount);
                                                    }
                                                } else {
                                                    Toast.makeText(mActivity, error_msg, Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Toast.makeText(mActivity, getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else if (response.body().getWarningShow() == 1) {
                                    //  warning message and prepopulate amount
                                    edtAccount.setText(response.body().getAmount());
                                    edtAccount.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                        }

                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                                            getInvestAmountKeyUp(mLoanId, s.toString());
                                        }

                                        @Override
                                        public void afterTextChanged(Editable s) {

                                        }
                                    });
                                } else if (response.body().getWarningShow() == 2) {
                                    //  warning message show and no prepopulate amount
                                    Toast.makeText(mActivity, response.body().getWarningMsg(), Toast.LENGTH_SHORT).show();
                                    btnContinue.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (isInternetConnected(mActivity)) {
                                                if (Validation()) {
                                                    String mAmount = edtAccount.getText().toString().trim();
                                                    if (getPreference(mActivity, "loanId") != null) {
                                                        getInvestAmountKeyUp(mLoanId, mAmount);
                                                        // getInvestSurveyQuestion(getPreference(mActivity, "loanId"), mAmount);
                                                    }
                                                } else {
                                                    Toast.makeText(mActivity, error_msg, Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Toast.makeText(mActivity, getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            } else {
                                setPreference(mActivity, "user_id", "");
                                setPreference(mActivity, "mLogout_token", "");
                                MaxCrowdFund.getClearCookies(mActivity, "cookies", "");
                                Toast.makeText(mActivity, getResources().getString(R.string.session_expire), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(mActivity, LoginActivity.class);
                                startActivity(intent);
                                mActivity.finish();
                            }
                        } else {
                            Toast.makeText(mActivity, getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mActivity, getResources().getString(R.string.something_went), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<InvestFormPreloadResponse> call, Throwable t) {
                Log.e("", "error " + t.getMessage());
                Toast.makeText(mActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
            }
        });
    }

    private void getInvestAmountKeyUp(String mLoanId, String mAmount) {
        pd = ProgressDialog.show(mActivity, "Please Wait...");
        String XCSRF = getPreference(mActivity, "mCsrf_token");
        EndPointInterface endPointInterface = ApiClient.getClient1(mActivity).create(EndPointInterface.class);
        Call<InvestAmountKeyUpResponse> call = endPointInterface.getInvestAmountKeyUp(mLoanId, mAmount, "application/json", XCSRF);
        call.enqueue(new Callback<InvestAmountKeyUpResponse>() {
            @Override
            public void onResponse(Call<InvestAmountKeyUpResponse> call, Response<InvestAmountKeyUpResponse> response) {
                Log.d(TAG, "onResponse: " + "><><" + new Gson().toJson(response.body()));
                try {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    if (response.code() == 200) {
                        if (response != null && response.isSuccessful()) {
                            if (response.body().getUserLoginStatus() == 1) {
                                if (response.body().getInvestCreateCheck() == 1) {
                                    btnContinue.setClickable(true);
                                    tvMpgToken.setText("€ " + String.valueOf(response.body().getRequiredExtraEuroAmountOfMpg().toString()));
                                    tvCharged.setText("€ " + String.valueOf(response.body().getTotalChargeMpgToken()));
                                    mTotalChargeMpgToken = String.valueOf(response.body().getTotalChargeMpgToken());
                                    btnContinue.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (isInternetConnected(mActivity)) {
                                                if (getPreference(mActivity, "loanId") != null) {
                                                    getInvestSurveyQuestion(getPreference(mActivity, "loanId"), mAmount);
                                                }
                                            } else {
                                                Toast.makeText(mActivity, getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    btnContinue.setClickable(false);
                                }
                            } else {
                                setPreference(mActivity, "user_id", "");
                                setPreference(mActivity, "mLogout_token", "");
                                MaxCrowdFund.getClearCookies(mActivity, "cookies", "");
                                Toast.makeText(mActivity, getResources().getString(R.string.session_expire), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(mActivity, LoginActivity.class);
                                startActivity(intent);
                                mActivity.finish();
                            }
                        } else {
                            Toast.makeText(mActivity, getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<InvestAmountKeyUpResponse> call, Throwable t) {
                Log.e("", "error " + t.getMessage());
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                Toast.makeText(mActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getInvestSurveyQuestion(String loanId, String amount) {
        pd = ProgressDialog.show(mActivity, "Please Wait...");
        String XCSRF = getPreference(mActivity, "mCsrf_token");
        EndPointInterface endPointInterface = ApiClient.getClient1(mActivity).create(EndPointInterface.class);
        Call<InvestSurveyRuestionResponse> call = endPointInterface.getInvestSurvey(loanId, amount, "application/json", XCSRF);
        call.enqueue(new Callback<InvestSurveyRuestionResponse>() {
            @Override
            public void onResponse(Call<InvestSurveyRuestionResponse> call, Response<InvestSurveyRuestionResponse> response) {
                Log.d(TAG, "onResponse: " + "><><" + new Gson().toJson(response.body()));
                try {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    if (response.code() == 200) {
                        if (response != null && response.isSuccessful()) {
                            if (response.body().getUserLoginStatus() == 1) {
                                if (response.body().getThresholdDisplay() == 0) {
                                    mThresholdValue = response.body().getThresholdDisplay();
                                    mAmountData = amount;
                                    cvInvestForm.setVisibility(View.GONE);
                                    cvQuestion.setVisibility(View.GONE);
                                    cvTwoFA.setVisibility(View.VISIBLE);
                                    if (isInternetConnected(mActivity)) {
                                        getSurveyDataInserted();
                                    } else {
                                        Toast.makeText(mActivity, getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
                                    }
                                } else if (response.body().getThresholdDisplay() == 1) {
                                    mAmountData = amount;
                                    mThresholdValue = response.body().getThresholdDisplay();
                                    cvInvestForm.setVisibility(View.GONE);
                                    cvQuestion.setVisibility(View.VISIBLE);
                                    if (response.body() != null) {
                                        getQuestion1(response.body());
                                        btnQuestCancel.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                new AlertDialog.Builder(getActivity())
                                                        .setTitle(getResources().getString(R.string.app_name))
                                                        .setMessage(getResources().getString(R.string.sure_cancel))
                                                        .setIcon(R.mipmap.ic_launcher)
                                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {
                                                                radBtnQ1.setText("");
                                                                radBtnQ2.setText("");
                                                                radBtnQ3.setText("");
                                                                radBtnQ1.setChecked(false);
                                                                radBtnQ2.setChecked(false);
                                                                radBtnQ3.setChecked(false);
                                                                edtAccount.setText("");
                                                                tvMpgToken.setText("");
                                                                tvCharged.setText("");
                                                                cvTwoFA.setVisibility(View.GONE);
                                                                cvQuestion.setVisibility(View.GONE);
                                                                cvInvestForm.setVisibility(View.VISIBLE);
                                                                dialog.dismiss();
                                                            }
                                                        })
                                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {
                                                                dialog.cancel();
                                                            }
                                                        }).show();
                                            }
                                        });
                                        btnQuestionContinue.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (isQuestionSet1) {
                                                    getQuestion2(response.body());
                                                    if (isQuestionSet2) {
                                                        getQuestion3(response.body());
                                                        if (isQuestionSet3) {
                                                            getQuestion4(response.body());
                                                            if (isQuestionSet4) {
                                                                getQuestion5(response.body());
                                                                if (isQuestionSet5) {
                                                                    getQuestion6(response.body());
                                                                    if (isQuestionSet6) {
                                                                        getQuestion7(response.body());
                                                                        if (isQuestionSet7) {
                                                                            getQuestion8(response.body());
                                                                            if (isQuestionSet8) {
                                                                                getQuestion9(response.body());
                                                                                if (isQuestionSet9) {
                                                                                    getSurveyDataInserted();
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        });
                                    }
                                } else if (response.body().getThresholdDisplay() == 2) {
                                    mThresholdValue = response.body().getThresholdDisplay();
                                    mAmountData = amount;
                                    cvInvestForm.setVisibility(View.GONE);
                                    cvQuestion.setVisibility(View.VISIBLE);
                                    if (response.body() != null) {
                                        getQuestion5(response.body());
                                        btnQuestCancel.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                new AlertDialog.Builder(getActivity())
                                                        .setTitle(getResources().getString(R.string.app_name))
                                                        .setMessage(getResources().getString(R.string.sure_cancel))
                                                        .setIcon(R.mipmap.ic_launcher)
                                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {
                                                                cvTwoFA.setVisibility(View.GONE);
                                                                cvQuestion.setVisibility(View.GONE);
                                                                radBtnQ1.setText("");
                                                                radBtnQ2.setText("");
                                                                radBtnQ3.setText("");
                                                                radBtnQ1.setChecked(false);
                                                                radBtnQ2.setChecked(false);
                                                                radBtnQ3.setChecked(false);
                                                                edtAccount.setText("");
                                                                tvMpgToken.setText("");
                                                                tvCharged.setText("");
                                                                cvInvestForm.setVisibility(View.VISIBLE);
                                                                dialog.dismiss();
                                                            }
                                                        })
                                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {
                                                                dialog.cancel();
                                                            }
                                                        }).show();
                                            }
                                        });

                                        btnQuestionContinue.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (isQuestionSet5) {
                                                    getQuestion6(response.body());
                                                    if (isQuestionSet6) {
                                                        getQuestion7(response.body());
                                                        if (isQuestionSet7) {
                                                            getQuestion8(response.body());
                                                            if (isQuestionSet8) {
                                                                getQuestion9(response.body());
                                                                if (isQuestionSet9) {
                                                                    getSurveyDataInserted();
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        });
                                    }
                                }
                            } else {
                                setPreference(mActivity, "user_id", "");
                                setPreference(mActivity, "mLogout_token", "");
                                MaxCrowdFund.getClearCookies(mActivity, "cookies", "");
                                Toast.makeText(mActivity, getResources().getString(R.string.session_expire), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(mActivity, LoginActivity.class);
                                startActivity(intent);
                                mActivity.finish();
                            }
                        } else {
                            Toast.makeText(mActivity, getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mActivity, getResources().getString(R.string.something_went), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<InvestSurveyRuestionResponse> call, Throwable t) {
                Log.e("", "error " + t.getMessage());
                Toast.makeText(mActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
            }
        });
    }

    private void getQuestion1(InvestSurveyRuestionResponse body) {
        radBtnQ1.setText("");
        radBtnQ2.setText("");
        radBtnQ3.setText("");
        radBtnQ1.setChecked(false);
        radBtnQ2.setChecked(false);
        radBtnQ3.setChecked(false);
        radBtnQ1.setVisibility(View.VISIBLE);
        radBtnQ2.setVisibility(View.VISIBLE);
        radBtnQ3.setVisibility(View.GONE);
        tvQuestion.setText(body.getQuesOne().getQuestion());
        radBtnQ1.setText(body.getQuesOne().getOption().get0());
        radBtnQ2.setText(body.getQuesOne().getOption().get1());

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = group.findViewById(checkedId);
                mQuesting1 = rb.getText().toString();
                if (mQuesting1.equals(body.getQuesOne().getOption().get1())) {
                    mQuestSelected1 = body.getQuesOne().getOption().get1();
                    isQuestionSet1 = true;
                } else if (mQuesting1.equals(body.getQuesOne().getOption().get0())) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle(getResources().getString(R.string.app_name))
                            .setMessage(body.getQuesOne().getWarning())
                            .setIcon(R.mipmap.ic_launcher)
                            .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    mQuestSelected1 = body.getQuesOne().getOption().get0();
                                    isQuestionSet1 = true;
                                    getQuestion2(body);
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("Abort", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            }).show();
                }
            }
        });
       /*
        btnQuestCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle(getResources().getString(R.string.app_name))
                        .setMessage(getResources().getString(R.string.app_name))
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                radBtnQ1.setText("");
                                radBtnQ2.setText("");
                                radBtnQ3.setText("");
                                radBtnQ1.setChecked(false);
                                radBtnQ2.setChecked(false);
                                radBtnQ3.setChecked(false);
                                cvTwoFA.setVisibility(View.GONE);
                                cvQuestion.setVisibility(View.GONE);
                                cvInvestForm.setVisibility(View.VISIBLE);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).show();
                *//*NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigateUp();*//*
            }
        });
        btnQuestionContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isQuestionSet1) {
                    getQuestion2(body);
                    if (isQuestionSet2) {
                        getQuestion3(body);
                        if (isQuestionSet3) {
                            getQuestion4(body);
                            if (isQuestionSet4) {
                                getQuestion5(body);
                                if (isQuestionSet5) {
                                    getQuestion6(body);
                                    if (isQuestionSet6) {
                                        getQuestion7(body);
                                        if (isQuestionSet7) {
                                            getQuestion8(body);
                                            if (isQuestionSet8) {
                                                getQuestion9(body);
                                                if (isQuestionSet9) {
                                                    getSurveyDataInserted();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });*/
    }

    private void getQuestion2(InvestSurveyRuestionResponse body) {
        radBtnQ1.setText("");
        radBtnQ2.setText("");
        radBtnQ3.setText("");
        radBtnQ1.setChecked(false);
        radBtnQ2.setChecked(false);
        radBtnQ3.setChecked(false);
        radBtnQ1.setVisibility(View.VISIBLE);
        radBtnQ2.setVisibility(View.VISIBLE);
        radBtnQ3.setVisibility(View.VISIBLE);
        tvQuestion.setText(body.getQuesTwo().getQuestion());
        radBtnQ1.setText(body.getQuesTwo().getOption().getLessThan1Year());
        radBtnQ2.setText(body.getQuesTwo().getOption().getBetween1And3Years());
        radBtnQ3.setText(body.getQuesTwo().getOption().getMoreThan3Year());

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = group.findViewById(checkedId);
                mQuesting2 = rb.getText().toString();
                if (mQuesting2.equals(body.getQuesTwo().getOption().getLessThan1Year())) {
                    mQuestSelected2 = mQuesting2;
                    isQuestionSet2 = true;
                } else if (mQuesting2.equals(body.getQuesTwo().getOption().getBetween1And3Years())) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle(getResources().getString(R.string.app_name))
                            .setMessage(body.getQuesTwo().getWarning())
                            .setIcon(R.mipmap.ic_launcher)
                            .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    mQuestSelected2 = mQuesting2;
                                    isQuestionSet2 = true;
                                    getQuestion3(body);
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("Abort", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            }).show();
                } else if (mQuesting2.equals(body.getQuesTwo().getOption().getMoreThan3Year())) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle(getResources().getString(R.string.app_name))
                            .setMessage(body.getQuesTwo().getWarning())
                            .setIcon(R.mipmap.ic_launcher)
                            .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    isQuestionSet2 = true;
                                    getQuestion3(body);
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("Abort", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            }).show();
                }
                Log.d(TAG, "onCheckedChanged: " + rb.getText().toString());
            }
        });
    }

    private void getQuestion3(InvestSurveyRuestionResponse body) {
        radBtnQ1.setText("");
        radBtnQ2.setText("");
        radBtnQ3.setText("");
        radBtnQ1.setChecked(false);
        radBtnQ2.setChecked(false);
        radBtnQ3.setChecked(false);
        radBtnQ1.setVisibility(View.VISIBLE);
        radBtnQ2.setVisibility(View.VISIBLE);
        radBtnQ3.setVisibility(View.GONE);
        tvQuestion.setText(body.getQuesThree().getQuestion());
        radBtnQ1.setText(body.getQuesThree().getOption().get1());
        radBtnQ2.setText(body.getQuesThree().getOption().get0());

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = group.findViewById(checkedId);
                mQuesting3 = rb.getText().toString();
                if (mQuesting3.equals(body.getQuesThree().getOption().get0())) {
                    mQuestSelected3 = mQuesting3;
                    isQuestionSet3 = true;
                } else if (mQuesting3.equals(body.getQuesThree().getOption().get1())) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle(getResources().getString(R.string.app_name))
                            .setMessage(body.getQuesThree().getWarning())
                            .setIcon(R.mipmap.ic_launcher)
                            .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    mQuestSelected3 = mQuesting3;
                                    isQuestionSet3 = true;
                                    getQuestion4(body);
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("Abort", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            }).show();
                }
                Log.d(TAG, "onCheckedChanged: " + rb.getText().toString());
            }
        });
    }

    private void getQuestion4(InvestSurveyRuestionResponse body) {
        radBtnQ1.setText("");
        radBtnQ2.setText("");
        radBtnQ3.setText("");
        radBtnQ1.setChecked(false);
        radBtnQ2.setChecked(false);
        radBtnQ3.setChecked(false);
        radBtnQ1.setVisibility(View.VISIBLE);
        radBtnQ2.setVisibility(View.VISIBLE);
        radBtnQ3.setVisibility(View.GONE);
        tvQuestion.setText(body.getQuesFour().getQuestion());
        radBtnQ1.setText(body.getQuesFour().getOption().getThisHasNoConsequences());
        radBtnQ2.setText(body.getQuesFour().getOption().getICanLoseMyInvestment());
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = group.findViewById(checkedId);
                mQuesting4 = rb.getText().toString();
                if (mQuesting4.equals(body.getQuesFour().getOption().getICanLoseMyInvestment())) {
                    mQuestSelected4 = mQuesting4;
                    isQuestionSet4 = true;
                } else if (mQuesting4.equals(body.getQuesFour().getOption().getThisHasNoConsequences())) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle(getResources().getString(R.string.app_name))
                            .setMessage(body.getQuesFour().getWarning())
                            .setIcon(R.mipmap.ic_launcher)
                            .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    mQuestSelected4 = mQuesting4;
                                    isQuestionSet4 = true;
                                    getQuestion5(body);
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("Abort", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            }).show();
                }
                Log.d(TAG, "onCheckedChanged: " + rb.getText().toString());
            }
        });
    }

    private void getQuestion5(InvestSurveyRuestionResponse body) {
        radBtnQ1.setText("");
        radBtnQ2.setText("");
        radBtnQ3.setText("");
        radBtnQ1.setChecked(false);
        radBtnQ2.setChecked(false);
        radBtnQ3.setChecked(false);
        radBtnQ1.setVisibility(View.VISIBLE);
        radBtnQ2.setVisibility(View.VISIBLE);
        radBtnQ3.setVisibility(View.VISIBLE);
        tvQuestion.setText(body.getQuesFive().getQuestion());
        radBtnQ1.setText(body.getQuesFive().getOption().getThatIsNoProblemForMe());
        radBtnQ2.setText(body.getQuesFive().getOption().getThatCanBeDifficultForAWhile());
        radBtnQ3.setText(body.getQuesFive().getOption().getThenIHaveAProblem());

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = group.findViewById(checkedId);
                mQuesting5 = rb.getText().toString();
                Log.d("><><><>", rb.getText().toString());
                if (mQuesting5.equals(body.getQuesFive().getOption().getThatIsNoProblemForMe())) {
                    mQuestSelected5 = mQuesting5;
                    isQuestionSet5 = true;
                } else if (mQuesting5.equals(body.getQuesFive().getOption().getThatCanBeDifficultForAWhile())) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle(getResources().getString(R.string.app_name))
                            .setMessage(body.getQuesFive().getWarning())
                            .setIcon(R.mipmap.ic_launcher)
                            .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    mQuestSelected5 = mQuesting5;
                                    isQuestionSet5 = true;
                                    getQuestion6(body);
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("Abort", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            }).show();
                } else if (mQuesting5.equals(body.getQuesFive().getOption().getThenIHaveAProblem())) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle(getResources().getString(R.string.app_name))
                            .setMessage(body.getQuesFive().getWarning())
                            .setIcon(R.mipmap.ic_launcher)
                            .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    mQuestSelected5 = mQuesting5;
                                    isQuestionSet5 = true;
                                    getQuestion6(body);
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("Abort", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            }).show();
                }
                Log.d(TAG, "onCheckedChanged: " + rb.getText().toString());
            }
        });

      /*  btnQuestCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle(getResources().getString(R.string.app_name))
                        .setMessage(getResources().getString(R.string.app_name))
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                cvTwoFA.setVisibility(View.GONE);
                                cvQuestion.setVisibility(View.GONE);
                                radBtnQ1.setText("");
                                radBtnQ2.setText("");
                                radBtnQ3.setText("");
                                radBtnQ1.setChecked(false);
                                radBtnQ2.setChecked(false);
                                radBtnQ3.setChecked(false);
                                cvInvestForm.setVisibility(View.VISIBLE);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).show();
            }
        });

        btnQuestionContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isQuestionSet5) {
                    getQuestion6(body);
                    if (isQuestionSet6) {
                        getQuestion7(body);
                        if (isQuestionSet7) {
                            getQuestion8(body);
                            if (isQuestionSet8) {
                                getQuestion9(body);
                                if (isQuestionSet9) {
                                    getSurveyDataInserted();
                                }
                            }
                        }
                    }
                }
            }
        });*/
    }

    /*For Question 6*/
    private void getQuestion6(InvestSurveyRuestionResponse body) {
        radBtnQ1.setText("");
        radBtnQ2.setText("");
        radBtnQ3.setText("");
        radBtnQ1.setChecked(false);
        radBtnQ2.setChecked(false);
        radBtnQ3.setChecked(false);
        radBtnQ1.setVisibility(View.VISIBLE);
        radBtnQ2.setVisibility(View.VISIBLE);
        radBtnQ3.setVisibility(View.GONE);
        tvQuestion.setText(body.getQuesSix().getQuestion());
        radBtnQ1.setText(body.getQuesSix().getOption().getICanHaveMyLoanRepaid());
        radBtnQ2.setText(body.getQuesSix().getOption().getICanCompensateForThisFromOtherIncome());
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                RadioButton rb = group.findViewById(checkedId);
                mQuesting6 = rb.getText().toString();
                if (mQuesting6.equals(body.getQuesSix().getOption().getICanHaveMyLoanRepaid())) {
                    mQuestSelected6 = mQuesting6;
                    new AlertDialog.Builder(getActivity())
                            .setTitle(getResources().getString(R.string.app_name))
                            .setMessage(body.getQuesSix().getWarning())
                            .setIcon(R.mipmap.ic_launcher)
                            .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    mQuestSelected6 = mQuesting6;
                                    isQuestionSet6 = true;
                                    getQuestion7(body);
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("Abort", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            }).show();
                } else if (mQuesting6.equals(body.getQuesSix().getOption().getICanCompensateForThisFromOtherIncome())) {
                    mQuestSelected6 = mQuesting6;
                    isQuestionSet6 = true;
                }
                Log.d(TAG, "onCheckedChanged: " + rb.getText().toString());
            }
        });
    }

    /*For Question 7*/
    private void getQuestion7(InvestSurveyRuestionResponse body) {
        radBtnQ1.setText("");
        radBtnQ2.setText("");
        radBtnQ3.setText("");
        radBtnQ1.setChecked(false);
        radBtnQ2.setChecked(false);
        radBtnQ3.setChecked(false);
        radBtnQ1.setVisibility(View.VISIBLE);
        radBtnQ2.setVisibility(View.VISIBLE);
        radBtnQ3.setVisibility(View.VISIBLE);
        tvQuestion.setText(body.getQuesSeven().getQuestion());
        radBtnQ1.setText(body.getQuesSeven().getOption().getTheEntrepreneurCanNoLongerMeet());
        radBtnQ2.setText(body.getQuesSeven().getOption().getTheValueOfTheRealEstate());
        radBtnQ3.setText(body.getQuesSeven().getOption().getBothTheAboveAnswersAreCorrect());
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                RadioButton rb = group.findViewById(checkedId);
                mQuesting7 = rb.getText().toString();
                if (mQuesting7.equals(body.getQuesSeven().getOption().getTheEntrepreneurCanNoLongerMeet())) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle(getResources().getString(R.string.app_name))
                            .setMessage(body.getQuesSeven().getWarning())
                            .setIcon(R.mipmap.ic_launcher)
                            .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    mQuestSelected7 = mQuesting7;
                                    isQuestionSet7 = true;
                                    getQuestion8(body);
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("Abort", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            }).show();
                } else if (mQuesting7.equals(body.getQuesSeven().getOption().getTheValueOfTheRealEstate())) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle(getResources().getString(R.string.app_name))
                            .setMessage(body.getQuesSeven().getWarning())
                            .setIcon(R.mipmap.ic_launcher)
                            .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    mQuestSelected7 = mQuesting7;
                                    isQuestionSet7 = true;
                                    getQuestion8(body);
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("Abort", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            }).show();
                } else if (mQuesting7.equals(body.getQuesSeven().getOption().getBothTheAboveAnswersAreCorrect())) {
                    mQuestSelected7 = mQuesting7;
                    isQuestionSet7 = true;
                }
                Log.d(TAG, "onCheckedChanged: " + rb.getText().toString());
            }
        });
    }

    /*For Question 8*/
    private void getQuestion8(InvestSurveyRuestionResponse body) {
        radBtnQ1.setText("");
        radBtnQ2.setText("");
        radBtnQ3.setText("");
        radBtnQ1.setChecked(false);
        radBtnQ2.setChecked(false);
        radBtnQ3.setChecked(false);
        radBtnQ1.setVisibility(View.VISIBLE);
        radBtnQ2.setVisibility(View.VISIBLE);
        radBtnQ3.setVisibility(View.VISIBLE);
        tvQuestion.setText(body.getQuesEight().getQuestion());
        radBtnQ1.setText(body.getQuesEight().getOption().getMoreThan25());
        radBtnQ2.setText(body.getQuesEight().getOption().getBetween10And25());
        radBtnQ3.setText(body.getQuesEight().getOption().getUpTo10());
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                RadioButton rb = group.findViewById(checkedId);
                mQuesting8 = rb.getText().toString();
                if (mQuesting8.equals(body.getQuesEight().getOption().getMoreThan25())) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle(getResources().getString(R.string.app_name))
                            .setMessage(body.getQuesEight().getWarning())
                            .setIcon(R.mipmap.ic_launcher)
                            .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    mQuestSelected8 = mQuesting8;
                                    isQuestionSet8 = true;
                                    getQuestion9(body);
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("Abort", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            }).show();
                } else if (mQuesting8.equals(body.getQuesEight().getOption().getBetween10And25())) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle(getResources().getString(R.string.app_name))
                            .setMessage(body.getQuesEight().getWarning())
                            .setIcon(R.mipmap.ic_launcher)
                            .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    mQuestSelected8 = mQuesting8;
                                    isQuestionSet8 = true;
                                    getQuestion9(body);
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("Abort", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            }).show();
                } else if (mQuesting8.equals(body.getQuesEight().getOption().getUpTo10())) {
                    mQuestSelected8 = mQuesting8;
                    isQuestionSet8 = true;
                }
                Log.d(TAG, "onCheckedChanged: " + rb.getText().toString());
            }
        });
    }

    /*For Question 9*/
    private void getQuestion9(InvestSurveyRuestionResponse body) {
        radBtnQ1.setText("");
        radBtnQ2.setText("");
        radBtnQ3.setText("");
        radBtnQ1.setChecked(false);
        radBtnQ2.setChecked(false);
        radBtnQ3.setChecked(false);
        radBtnQ1.setVisibility(View.VISIBLE);
        radBtnQ2.setVisibility(View.VISIBLE);
        radBtnQ3.setVisibility(View.GONE);
        tvQuestion.setText(body.getQuesNine().getQuestion());
        radBtnQ1.setText(body.getQuesNine().getOption().getIInvestTheEntireAmountInOneLoan());
        radBtnQ2.setText(body.getQuesNine().getOption().getISpreadTheAmountOverSeveralLoans());
        radBtnQ3.setVisibility(View.GONE);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                RadioButton rb = group.findViewById(checkedId);
                mQuesting9 = rb.getText().toString();
                if (mQuesting9.equals(body.getQuesNine().getOption().getIInvestTheEntireAmountInOneLoan())) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle(getResources().getString(R.string.app_name))
                            .setMessage(body.getQuesNine().getWarning())
                            .setIcon(R.mipmap.ic_launcher)
                            .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    mQuestSelected9 = mQuesting9;
                                    isQuestionSet9 = true;
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("Abort", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            }).show();
                } else if (mQuesting9.equals(body.getQuesNine().getOption().getISpreadTheAmountOverSeveralLoans())) {
                    mQuestSelected9 = mQuesting9;
                    isQuestionSet9 = true;
                }
                Log.d(TAG, "onCheckedChanged: " + rb.getText().toString());
            }
        });
    }

    //For inserted data
    private void getSurveyDataInserted() {
        pd = ProgressDialog.show(mActivity, "Please Wait...");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("invest_amount", mAmountData);
        if (mLoanId != null && !mLoanId.isEmpty()) {
            jsonObject.addProperty("loan_id", mLoanId);
        }
        jsonObject.addProperty("threshold_value", mThresholdValue);
        jsonObject.addProperty("q1", mQuestSelected1);
        jsonObject.addProperty("q2", mQuestSelected2);
        jsonObject.addProperty("q3", mQuestSelected3);
        jsonObject.addProperty("q4", mQuestSelected4);
        jsonObject.addProperty("q5", mQuestSelected5);
        jsonObject.addProperty("q6", mQuestSelected6);
        jsonObject.addProperty("q7", mQuestSelected7);
        jsonObject.addProperty("q8", mQuestSelected8);
        jsonObject.addProperty("q9", mQuestSelected9);
        Log.d(TAG, "getInvest: " + jsonObject);
        String XCSRF = getPreference(mActivity, "mCsrf_token");
        EndPointInterface git = ApiClient.getClient1(mActivity).create(EndPointInterface.class);
        Call<SurveyFormDataInsertResponse> call = git.getSurveyFormDataInserted("application/json", XCSRF, jsonObject);
        call.enqueue(new Callback<SurveyFormDataInsertResponse>() {
            @Override
            public void onResponse(Call<SurveyFormDataInsertResponse> call, Response<SurveyFormDataInsertResponse> response) {
                try {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    if (response.code() == 200) {
                        if (response != null && response.isSuccessful()) {
                            Log.d(TAG, "onResponse:" + "><><" + new Gson().toJson(response.body()));
                            if (response.body().getUserLoginStatus() == 1) {
                                cvInvestForm.setVisibility(View.GONE);
                                cvQuestion.setVisibility(View.GONE);
                                cvTwoFA.setVisibility(View.VISIBLE);
                                tvTotalAccount.setText(mAmountData);
                                tvTotalWallet.setText(mTotalChargeMpgToken);
                                //For Transation Siging
                                spinnerFA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        Option__ option = signingAdapter.getItem(position);
                                        Log.d(TAG, "onItemSelected: " + option.getKey());
                                        Log.d(TAG, "onItemSelected: " + option.getVal());
                                        mTrans_signing = option.getKey();
                                        if (mTrans_signing.equals("google_authentication")) {
                                            lLayoutOTP.setVisibility(View.GONE);
                                        } else {
                                            lLayoutOTP.setVisibility(View.VISIBLE);
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }
                                });

                                btnSend.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (isInternetConnected(mActivity)) {
                                            if (mTrans_signing != null) {
                                                getSendOTP(mTrans_signing);
                                            }
                                        } else {
                                            Toast.makeText(mActivity, getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                                btnFASubmit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (isInternetConnected(mActivity)) {
                                            mEnterOTP = edtOTP.getText().toString().trim();
                                            if (mEnterOTP != null) {
                                                if (mResponseOTP != null) {
                                                    getTFAValidate(mTrans_signing, mResponseOTP, mEnterOTP);
                                                }
                                            } else {
                                                Toast.makeText(mActivity, getResources().getString(R.string.enter_otp), Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(mActivity, getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                Log.d(TAG, "onItemSelected: --" + mTrans_signing);

                                btnFACancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        new AlertDialog.Builder(getActivity())
                                                .setTitle(getResources().getString(R.string.app_name))
                                                .setMessage(getResources().getString(R.string.sure_cancel))
                                                .setIcon(R.mipmap.ic_launcher)
                                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        cvTwoFA.setVisibility(View.GONE);
                                                        cvInvestForm.setVisibility(View.VISIBLE);
                                                        edtAccount.setText("");
                                                        tvTotalAccount.setText("");
                                                        tvTotalWallet.setText("");
                                                        tvMpgToken.setText("");
                                                        tvCharged.setText("");
                                                        dialog.dismiss();
                                                    }
                                                })
                                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                }).show();
                                    }
                                });
                            } else {
                                setPreference(mActivity, "user_id", "");
                                setPreference(mActivity, "mLogout_token", "");
                                MaxCrowdFund.getClearCookies(mActivity, "cookies", "");
                                Toast.makeText(mActivity, getResources().getString(R.string.session_expire), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(mActivity, LoginActivity.class);
                                startActivity(intent);
                                mActivity.finish();
                            }
                        }
                    } else {
                        Toast.makeText(mActivity, getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SurveyFormDataInsertResponse> call, Throwable t) {
                Log.e("error", "error " + t.getMessage());
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                Toast.makeText(mActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getTFAValidate(String mTrans_signing, String mResponseOTP, String mEnterOTP) {
        pd = ProgressDialog.show(getActivity(), "Please Wait...");
        EndPointInterface git = ApiClient.getClient1(getActivity()).create(EndPointInterface.class);
        String XCSRF = getPreference(getActivity(), "mCsrf_token");
        Call<TfaValidateResponse> call = git.getTFAValidated("application/json", XCSRF, mTrans_signing, mResponseOTP, mEnterOTP);
        call.enqueue(new Callback<TfaValidateResponse>() {
            @Override
            public void onResponse(Call<TfaValidateResponse> call, Response<TfaValidateResponse> response) {
                Log.d(TAG, "onResponse: " + "><><" + new Gson().toJson(response.body()));
                try {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    if (response.code() == 200) {
                        if (response != null && response.isSuccessful()) {
                            if (response.body().getUserLoginStatus() == 1) {
                                if (response.body().getNoErrCheck() == 0) {
                                    Toast.makeText(mActivity, response.body().getErrorMsg(), Toast.LENGTH_SHORT).show();
                                } else {
                                    getCreateInvest();
                                }
                            } else {
                                setPreference(getActivity(), "user_id", "");
                                setPreference(getActivity(), "mLogout_token", "");
                                MaxCrowdFund.getClearCookies(getActivity(), "cookies", "");
                                Toast.makeText(getActivity(), getResources().getString(R.string.session_expire), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                                mActivity.finish();
                            }
                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<TfaValidateResponse> call, Throwable t) {
                Log.e("", "error " + t.getMessage());
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getChangePreference() {
        //  pd = ProgressDialog.show(getActivity(), "Please Wait...");
        EndPointInterface git = ApiClient.getClient1(getActivity()).create(EndPointInterface.class);
        String XCSRF = getPreference(getActivity(), "mCsrf_token");
        Call<ChangePreferenceResponse> call = git.getChangePreferenceAPI("application/json", XCSRF);
        call.enqueue(new Callback<ChangePreferenceResponse>() {
            @Override
            public void onResponse(Call<ChangePreferenceResponse> call, Response<ChangePreferenceResponse> response) {
                Log.d(TAG, "onResponse: " + "><><" + new Gson().toJson(response.body()));
                try {
                    if (response.code() == 200) {
                        if (response != null && response.isSuccessful()) {
                            if (response.body().getUserLoginStatus() == 1) {
                                //For Transation Sigining
                                if (response.body().getPreferences().getData().getTransactionSigning().getOptions().size() > 0) {
                                    signingAdapter = new TransactionSigningAdapter(getActivity(), response.body().getPreferences().getData().getTransactionSigning().getOptions());
                                    spinnerFA.setAdapter(signingAdapter);
                                }
                            } else {
                                setPreference(getActivity(), "user_id", "");
                                setPreference(getActivity(), "mLogout_token", "");
                                MaxCrowdFund.getClearCookies(getActivity(), "cookies", "");
                                Toast.makeText(getActivity(), getResources().getString(R.string.session_expire), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                                mActivity.finish();
                            }
                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ChangePreferenceResponse> call, Throwable t) {
                Log.e("", "error " + t.getMessage());
               /* if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }*/
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getSendOTP(String mTwoFAType) {
        pd = ProgressDialog.show(getActivity(), "Please Wait...");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("tfa_type", mTwoFAType);
        Log.d(">>>>mTwoFAType>", mTwoFAType);
        String XCSRF = getPreference(getActivity(), "mCsrf_token");
        EndPointInterface git = ApiClient.getClient1(getActivity()).create(EndPointInterface.class);
        Call<SendOTPResponse> call = git.getTwoFASendOTP("application/json", XCSRF, jsonObject);
        call.enqueue(new Callback<SendOTPResponse>() {
            @Override
            public void onResponse(Call<SendOTPResponse> call, Response<SendOTPResponse> response) {
                try {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    if (response.code() == 200) {
                        if (response != null && response.isSuccessful()) {
                            Log.d(TAG, "onResponse: " + "><send><" + new Gson().toJson(response.body()));
                            if (response.body().getUserLoginStatus() == 1) {
                                if (response.body().getMessage() != null) {
                                    if (response.body().getOtp() != null) {
                                        mResponseOTP = response.body().getOtp().toString();
                                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                setPreference(getActivity(), "user_id", "");
                                setPreference(getActivity(), "mLogout_token", "");
                                MaxCrowdFund.getClearCookies(getActivity(), "cookies", "");
                                Toast.makeText(getActivity(), getResources().getString(R.string.session_expire), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                                mActivity.finish();
                            }
                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                    }
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SendOTPResponse> call, Throwable t) {
                Log.e("", "error " + t.getMessage());
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCreateInvest() {
        pd = ProgressDialog.show(mActivity, "Please Wait...");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("amount", mAmountData);
        if (mLoanId != null && !mLoanId.isEmpty()) {
            jsonObject.addProperty("loan_id", mLoanId);
        }
        Log.d(TAG, "getCreateInvest: " + jsonObject);
        String XCSRF = getPreference(mActivity, "mCsrf_token");
        EndPointInterface git = ApiClient.getClient1(mActivity).create(EndPointInterface.class);
        Call<CreateInvestmentResponse> call = git.getCreateInvestment("application/json", XCSRF, jsonObject);
        call.enqueue(new Callback<CreateInvestmentResponse>() {
            @Override
            public void onResponse(Call<CreateInvestmentResponse> call, Response<CreateInvestmentResponse> response) {
                try {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    if (response != null) {
                        if (response != null && response.isSuccessful()) {
                            Log.d(TAG, "onResponse:" + "><create><" + new Gson().toJson(response.body()));
                            if (response.body().getUserLoginStatus() == 1) {
                                if (response.body().getInvestCreateCheck() == 0) {
                                    Toast.makeText(mActivity, response.body().getInvestmentSuccess(), Toast.LENGTH_SHORT).show();
                                } else if (response.body().getInvestCreateCheck() == 1) {
                                    if (response.body().getDepositCheck() == 0) {
                                        cvInvestForm.setVisibility(View.VISIBLE);
                                        edtAccount.setText(String.valueOf(response.body().getDepositAmount()));
                                        cvTwoFA.setVisibility(View.GONE);
                                        cvQuestion.setVisibility(View.GONE);
                                    } else {
                                        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                                        navController.navigate(R.id.action_nav_my_investments_to_investFormFragment);
                                    }
                                }
                            } else {
                                setPreference(mActivity, "user_id", "");
                                setPreference(mActivity, "mLogout_token", "");
                                MaxCrowdFund.getClearCookies(mActivity, "cookies", "");
                                Toast.makeText(mActivity, getResources().getString(R.string.session_expire), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(mActivity, LoginActivity.class);
                                startActivity(intent);
                                mActivity.finish();
                            }
                        }
                    } else {
                        Toast.makeText(mActivity, getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CreateInvestmentResponse> call, Throwable t) {
                Log.e("error", "error " + t.getMessage());
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                Toast.makeText(mActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private boolean Validation() {
        error_msg = "";
        if (TextUtils.isEmpty(edtAccount.getText().toString().trim())) {
            error_msg = getString(R.string.enter_amount);
            return false;
        } else {
            return true;
        }
    }
}
