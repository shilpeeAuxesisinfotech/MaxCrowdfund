package com.auxesis.maxcrowdfund.mvvm.ui.investform;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import com.auxesis.maxcrowdfund.mvvm.ui.investform.questmodel.famodel.SpinnerModel;
import com.auxesis.maxcrowdfund.mvvm.ui.investform.questmodel.famodel.TfaValidateResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.investform.questmodel.model.SurveyFormDataInsertResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.investform.questmodel.question.InvestSurveyQuestionResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.investform.questmodel.questionlistmodel.InvestAmountKeyUpResponse;
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
    TextView tvLoanId, tvLoanName, tvMpgToken, tvCharged, tvWaringMessage, tvQuestionHeading, tvQuestion, tvWaringBeforeMessage, tvTotalAccount, tvTotalWallet, tvInvestError;
    Spinner spinnerFA;
    EditText edtAccount, edtOTP;
    LinearLayout lLayoutOTP, lLayoutInvestForm1, lLayoutInvestForm2;
    String error_msg = "";
    ProgressDialog pd;
    Activity mActivity;
    String mLoanId = "";
    String mTittle = "";
    Button btnInvestContinue, btnInvestCancel, btnWarningCancel, btnWaringContinue, btnQuestionContinue, btnQuestCancel,
            btnWaringBeforeContinue, btnWarningBeforeCancel, btnSend, btnFACancel, btnFASubmit, btnQ1, btnQ2, btnQ3;
    CardView cvInvestForm, cvWarningMessage, cvQuestion, cvWarningBeforeMessage, cvTwoFA;
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
    boolean isQuestionSet10 = false;
    boolean isQuestionSet11 = false;
    private String mQuestSelected1 = "";
    private String mQuestSelected2 = "";
    private String mQuestSelected3 = "";
    private String mQuestSelected4 = "";
    private String mQuestSelected5 = "";
    private String mQuestSelected6 = "";
    private String mQuestSelected7 = "";
    private String mQuestSelected8 = "";
    private String mQuestSelected9 = "";
    private String mQuestSelected10 = "";
    private String mQuestSelected11 = "";
    private String mAmountData = "";

    private String mTotalChargeMpgToken = "";
    TransactionSigningAdapter signingAdapter;
    String mTrans_signing = "";
    String mResponseOTP = "";
    String mEnterOTP = "";
    List<SpinnerModel> modelList = new ArrayList<>();
    List<SpinnerModel> modelList1 = new ArrayList<>();
    int mQuestionStartFrom = 0;
    int mQuestion1 = 0;
    int mQuestion2 = 0;
    int mQuestion3 = 0;
    int mQuestion4 = 0;
    int mQuestion5 = 0;
    int mQuestion6 = 0;
    int mQuestion7 = 0;
    int mQuestion8 = 0;
    int mQuestion9 = 0;
    int mQuestion10 = 0;
    int mQuestion11 = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_invest_form, container, false);
        mActivity = getActivity();
        try {
            mLoanId = getPreference(mActivity, "loan_id");
            mTittle = getPreference(mActivity, "tittle");
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*For invest from */
        cvInvestForm = root.findViewById(R.id.cvInvestForm);
        lLayoutInvestForm1 = root.findViewById(R.id.lLayoutInvestForm1);
        lLayoutInvestForm2 = root.findViewById(R.id.lLayoutInvestForm2);
        /*For header*/
        tvLoanId = root.findViewById(R.id.tvLoanId);
        tvLoanName = root.findViewById(R.id.tvLoanName);
        if (mLoanId != null && mTittle != null) {
            tvLoanName.setText("-" + mTittle);
            tvLoanId.setText("#" + mLoanId);
        }
        /*For invest form 1*/
        edtAccount = root.findViewById(R.id.edtAccount);
        btnInvestContinue = root.findViewById(R.id.btnInvestContinue);
        btnInvestCancel = root.findViewById(R.id.btnInvestCancel);
        tvInvestError = root.findViewById(R.id.tvInvestError);
        /*For invest Form 2*/
        tvMpgToken = root.findViewById(R.id.tvMpgToken);
        tvCharged = root.findViewById(R.id.tvCharged);
        /*For 2 FA Form*/
        cvTwoFA = root.findViewById(R.id.cvTwoFA);
        tvTotalAccount = root.findViewById(R.id.tvTotalAccount);
        tvTotalWallet = root.findViewById(R.id.tvTotalWallet);
        spinnerFA = root.findViewById(R.id.spinnerFA);
        edtOTP = root.findViewById(R.id.edtOTP);
        lLayoutOTP = root.findViewById(R.id.lLayoutOTP);
        btnSend = root.findViewById(R.id.btnSend);
        btnFACancel = root.findViewById(R.id.btnFACancel);
        btnFASubmit = root.findViewById(R.id.btnFASubmit);
        /*For warning message*/
        cvWarningMessage = root.findViewById(R.id.cvWarningMessage);
        tvWaringMessage = root.findViewById(R.id.tvWaringMessage);
        btnWaringContinue = root.findViewById(R.id.btnWaringContinue);
        btnWarningCancel = root.findViewById(R.id.btnWarningCancel);
        /*For Question */
        cvQuestion = root.findViewById(R.id.cvQuestion);
        tvQuestionHeading = root.findViewById(R.id.tvQuestionHeading);
        tvQuestion = root.findViewById(R.id.tvQuestion);
        btnQuestCancel = root.findViewById(R.id.btnQuestCancel);
        btnQuestionContinue = root.findViewById(R.id.btnQuestionContinue);

        btnQ1 = root.findViewById(R.id.btnQ1);
        btnQ2 = root.findViewById(R.id.btnQ2);
        btnQ3 = root.findViewById(R.id.btnQ3);

        /*For Before warring message*/
        cvWarningBeforeMessage = root.findViewById(R.id.cvWarningBeforeMessage);
        tvWaringBeforeMessage = root.findViewById(R.id.tvWaringBeforeMessage);
        btnWaringBeforeContinue = root.findViewById(R.id.btnWaringBeforeContinue);
        btnWarningBeforeCancel = root.findViewById(R.id.btnWarningBeforeCancel);
        cvInvestForm.setVisibility(View.VISIBLE);
        lLayoutInvestForm1.setVisibility(View.VISIBLE);
        lLayoutInvestForm2.setVisibility(View.GONE);
        cvWarningMessage.setVisibility(View.GONE);
        cvQuestion.setVisibility(View.GONE);
        cvWarningBeforeMessage.setVisibility(View.GONE);
        cvTwoFA.setVisibility(View.GONE);
        /*For invest form*/
        if (isInternetConnected(mActivity)) {
            if (mLoanId != null) {
                getInvestFormPreload();
            }
        } else {
            Toast.makeText(mActivity, getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
        }

        /*For invest form*/
        edtAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged: " + s.toString());
                if (isInternetConnected(mActivity)) {
                    if (mLoanId != null) {
                        getInvestAmountKeyUp(s.toString());
                    }
                } else {
                    Toast.makeText(mActivity, getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btnInvestCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle(getResources().getString(R.string.app_name))
                        .setMessage(getResources().getString(R.string.sure_cancel))
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                                navController.navigateUp();
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

        if (isInternetConnected(mActivity)) {
            getChangePreference();
        } else {
            Toast.makeText(mActivity, getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
        }
        return root;
    }

    private void getInvestFormPreload() {
        pd = ProgressDialog.show(mActivity, "Please Wait...");
        String XCSRF = getPreference(mActivity, "mCsrf_token");
        EndPointInterface endPointInterface = ApiClient.getClient1(mActivity).create(EndPointInterface.class);
        Call<InvestFormPreloadResponse> call = endPointInterface.getInvestFormPreload(mLoanId, "application/json", XCSRF);
        call.enqueue(new Callback<InvestFormPreloadResponse>() {
            @Override
            public void onResponse(Call<InvestFormPreloadResponse> call, Response<InvestFormPreloadResponse> response) {
                Log.d(TAG, "onResponse: " + "><B><" + new Gson().toJson(response.body()));
                try {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    if (response.code() == 200) {
                        if (response != null && response.isSuccessful()) {
                            if (response.body().getUserLoginStatus() == 1) {
                                if (response.body().getWarningShow() == 0) {
                                    // no warning message and no prepopulate amount
                                } else if (response.body().getWarningShow() == 1) {
                                    //  warning message and prepopulate amount
                                    tvInvestError.setVisibility(View.VISIBLE);
                                    tvInvestError.setText(response.body().getWarningMsg());
                                    edtAccount.setText(response.body().getAmount());
                                } else if (response.body().getWarningShow() == 2) {
                                    //  warning message show and no prepopulate amount
                                    tvInvestError.setVisibility(View.VISIBLE);
                                    tvInvestError.setText(response.body().getWarningMsg());
                                }
                            } else {
                                setPreference(mActivity, "user_id", "");
                                setPreference(mActivity, "mLogout_token", "");
                                MaxCrowdFund.getInstance().getClearCookies(mActivity, "cookies", "");
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

    private void getInvestAmountKeyUp(String mAmount) {
        pd = ProgressDialog.show(mActivity, "Please Wait...");
        String XCSRF = getPreference(mActivity, "mCsrf_token");
        EndPointInterface endPointInterface = ApiClient.getClient1(mActivity).create(EndPointInterface.class);
        Log.d("><><", mLoanId + ">>>>M>>>>" + mAmount);
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
                                cvInvestForm.setVisibility(View.VISIBLE);
                                lLayoutInvestForm1.setVisibility(View.VISIBLE);
                                if (response.body().getInvestCreateCheck() == 1) {
                                    tvInvestError.setVisibility(View.GONE);
                                    int mExtraEuro = response.body().getTokenRequired();
                                    Log.d("><><><", "mExtraEuro-----insise-" + mExtraEuro);
                                    if (mExtraEuro == 0) {
                                        lLayoutInvestForm2.setVisibility(View.GONE);  //if Token = 0 than hide
                                        mTotalChargeMpgToken = String.valueOf(response.body().getTotalChargeMpgToken());
                                    } else {
                                        lLayoutInvestForm2.setVisibility(View.VISIBLE);
                                        tvMpgToken.setText(String.valueOf(response.body().getRequiredExtraEuroAmountOfMpg()));
                                        tvCharged.setText(String.valueOf(response.body().getSumOfEuroMpgAndAmount()));
                                        mTotalChargeMpgToken = String.valueOf(response.body().getTotalChargeMpgToken());
                                    }
                                    /*for survey question*/
                                    btnInvestContinue.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (isInternetConnected(mActivity)) {
                                                if (mLoanId != null) {
                                                    String mEnAmount = edtAccount.getText().toString().trim();
                                                    if (mEnAmount != null && !mEnAmount.isEmpty()) {
                                                        getInvestSurveyQuestion(mEnAmount);
                                                    } else {
                                                        Toast.makeText(mActivity, getResources().getString(R.string.enter_amount), Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            } else {
                                                Toast.makeText(mActivity, getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    tvInvestError.setVisibility(View.VISIBLE);
                                    tvInvestError.setText(response.body().getErrorMsg());
                                    btnInvestContinue.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Toast.makeText(mActivity, response.body().getErrorMsg(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            } else {
                                setPreference(mActivity, "user_id", "");
                                setPreference(mActivity, "mLogout_token", "");
                                MaxCrowdFund.getInstance().getClearCookies(mActivity, "cookies", "");
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

    private void getInvestSurveyQuestion(String amount) {
        pd = ProgressDialog.show(mActivity, "Please Wait...");
        String XCSRF = getPreference(mActivity, "mCsrf_token");
        EndPointInterface endPointInterface = ApiClient.getClient1(mActivity).create(EndPointInterface.class);
        Call<InvestSurveyQuestionResponse> call = endPointInterface.getInvestSurvey(mLoanId, amount, "application/json", XCSRF);
        call.enqueue(new Callback<InvestSurveyQuestionResponse>() {
            @Override
            public void onResponse(Call<InvestSurveyQuestionResponse> call, Response<InvestSurveyQuestionResponse> response) {
                Log.d(TAG, "onResponse: " + "><getInvestSurvey><" + new Gson().toJson(response.body()));
                try {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    if (response.code() == 200) {
                        if (response != null && response.isSuccessful()) {
                            if (response.body().getUserLoginStatus() == 1) {
                                mAmountData = amount;
                                cvInvestForm.setVisibility(View.GONE);
                                lLayoutInvestForm1.setVisibility(View.GONE);
                                lLayoutInvestForm2.setVisibility(View.GONE);
                                cvQuestion.setVisibility(View.GONE);
                                cvTwoFA.setVisibility(View.GONE);
                                cvWarningBeforeMessage.setVisibility(View.GONE);
                                if (response.body().getThresholdDisplay() == 0) {
                                    mThresholdValue = response.body().getThresholdDisplay();
                                    getTwoFAForm(mAmountData);  /*for Two FA for */
                                } else {
                                    cvWarningMessage.setVisibility(View.VISIBLE);
                                    tvWaringMessage.setText(response.body().getThresholdIntroWarning());
                                    /*For Questing  start */
                                    btnWaringContinue.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            cvWarningMessage.setVisibility(View.GONE);
                                            cvQuestion.setVisibility(View.VISIBLE);
                                            if (response.body().getThresholdDisplay() == 1) {
                                                mThresholdValue = response.body().getThresholdDisplay();
                                                mQuestionStartFrom = 1;
                                                getThreshHoldDisplay(response.body(), mQuestionStartFrom);
                                            } else if (response.body().getThresholdDisplay() == 2) {
                                                mThresholdValue = response.body().getThresholdDisplay();
                                                mQuestionStartFrom = 5;
                                                getThreshHoldDisplay(response.body(), mQuestionStartFrom);
                                            }
                                        }
                                    });
                                    btnWarningCancel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            new AlertDialog.Builder(getActivity())
                                                    .setTitle(getResources().getString(R.string.app_name))
                                                    .setMessage(getResources().getString(R.string.sure_cancel))
                                                    .setIcon(R.mipmap.ic_launcher)
                                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                                                            navController.navigateUp();
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
                                }
                            } else {
                                setPreference(mActivity, "user_id", "");
                                setPreference(mActivity, "mLogout_token", "");
                                MaxCrowdFund.getInstance().getClearCookies(mActivity, "cookies", "");
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
            public void onFailure(Call<InvestSurveyQuestionResponse> call, Throwable t) {
                Log.e("", "error " + t.getMessage());
                Toast.makeText(mActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
            }
        });
    }

    private void getThreshHoldDisplay(InvestSurveyQuestionResponse body, int mQuestionStartFrom) {
        Log.d(">>>>>>>", "------start---" + mQuestionStartFrom);
        switch (mQuestionStartFrom) {
            case 1:
                getQuestion1(body, mQuestionStartFrom);
                break;
            case 2:
                getQuestion2(body, mQuestionStartFrom);
                break;
            case 3:
                getQuestion3(body, mQuestionStartFrom);
                break;
            case 4:
                getQuestion4(body, mQuestionStartFrom);
                break;
            case 5:
                getQuestion5(body, mQuestionStartFrom);
                break;
            case 6:
                getQuestion6(body, mQuestionStartFrom);
                break;
            case 7:
                getQuestion7(body, mQuestionStartFrom);
                break;
            case 8:
                getQuestion8(body, mQuestionStartFrom);
                break;
            case 9:
                getQuestion9(body, mQuestionStartFrom);
                break;
            case 10:
                getQuestion10(body, mQuestionStartFrom);
                break;
            case 11:
                getQuestion11(body, mQuestionStartFrom);
                break;
            default:
                break;
        }
    }

    private void getQuestion1(InvestSurveyQuestionResponse body, int mQuestionStartFrom) {
        mQuestion1 = mQuestionStartFrom;
        tvQuestion.setText("");
        btnQ1.setText("");
        btnQ2.setText("");
        btnQ3.setText("");
        btnQ1.setVisibility(View.VISIBLE);
        btnQ2.setVisibility(View.VISIBLE);
        btnQ3.setVisibility(View.GONE);
        tvQuestion.setText(body.getQuesOne().getQuestion());
        btnQ1.setText(body.getQuesOne().getOption().get1());
        btnQ2.setText(body.getQuesOne().getOption().get0());
        Drawable imgCheck = getContext().getResources().getDrawable(R.drawable.ic_radio_button_checked);
        Drawable imgUncheck = getContext().getResources().getDrawable(R.drawable.ic_radio_button_unchecked);
        imgCheck.setBounds(0, 0, 60, 60);
        imgUncheck.setBounds(0, 0, 60, 60);
        if (mQuestSelected1.length() == 0) {
            btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
            btnQ2.setCompoundDrawables(imgUncheck, null, null, null);
        } else {
            if (mQuestSelected1.equals("1")) {
                mQuestSelected1 = "1";
                isQuestionSet1 = true;
                btnQ1.setCompoundDrawables(imgCheck, null, null, null);
                btnQ2.setCompoundDrawables(imgUncheck, null, null, null);
            } else if (mQuestSelected1.equals("0")) {
                mQuestSelected1 = "0";
                isQuestionSet1 = true;
                btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ2.setCompoundDrawables(imgCheck, null, null, null);
            }
        }
        btnQ1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestSelected1 = "1";
                isQuestionSet1 = true;
                btnQ1.setCompoundDrawables(imgCheck, null, null, null);
                btnQ2.setCompoundDrawables(imgUncheck, null, null, null);
            }
        });

        btnQ2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle(getResources().getString(R.string.app_name))
                        .setMessage(body.getQuesOne().getWarning())
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mQuestSelected1 = "0";
                                isQuestionSet1 = true;
                                mQuestion1 = mQuestion1 + 1;
                                getThreshHoldDisplay(body, mQuestion1);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Abort", new DialogInterface.OnClickListener() {
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
                    mQuestion1 = mQuestion1 + 1;
                    getThreshHoldDisplay(body, mQuestion1);
                } else {
                    Toast.makeText(mActivity, "Please select question", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnQuestCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cvWarningMessage.setVisibility(View.VISIBLE);
                tvWaringMessage.setText(body.getThresholdIntroWarning());
                cvQuestion.setVisibility(View.GONE);
            }
        });
    }

    private void getQuestion2(InvestSurveyQuestionResponse body, int mQuestionStartFrom) {
        mQuestion2 = mQuestionStartFrom;
        tvQuestion.setText("");
        btnQ1.setText("");
        btnQ2.setText("");
        btnQ3.setText("");
        btnQ1.setVisibility(View.VISIBLE);
        btnQ2.setVisibility(View.VISIBLE);
        btnQ3.setVisibility(View.VISIBLE);
        tvQuestion.setText(body.getQuesTwo().getQuestion());
        btnQ1.setText(body.getQuesTwo().getOption().getLessThan1Year());
        btnQ2.setText(body.getQuesTwo().getOption().getBetween1And3Years());
        btnQ3.setText(body.getQuesTwo().getOption().getMoreThan3Year());
        Drawable imgCheck = getContext().getResources().getDrawable(R.drawable.ic_radio_button_checked);
        Drawable imgUncheck = getContext().getResources().getDrawable(R.drawable.ic_radio_button_unchecked);
        imgCheck.setBounds(0, 0, 60, 60);
        imgUncheck.setBounds(0, 0, 60, 60);
        if (mQuestSelected2.length() == 0) {
            btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
            btnQ2.setCompoundDrawables(imgUncheck, null, null, null);
            btnQ3.setCompoundDrawables(imgUncheck, null, null, null);
        } else {
            if (mQuestSelected2.equals("less_than_1_year")) {
                mQuestSelected2 = "less_than_1_year";
                isQuestionSet2 = true;
                btnQ1.setCompoundDrawables(imgCheck, null, null, null);
                btnQ2.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ3.setCompoundDrawables(imgUncheck, null, null, null);
            } else if (mQuestSelected2.equals("between_1_and_3_years")) {
                mQuestSelected2 = "between_1_and_3_years";
                isQuestionSet2 = true;
                btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ2.setCompoundDrawables(imgCheck, null, null, null);
                btnQ3.setCompoundDrawables(imgUncheck, null, null, null);
            } else if (mQuestSelected2.equals("more_than_3_year")) {
                mQuestSelected2 = "more_than_3_year";
                isQuestionSet2 = true;
                btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ2.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ3.setCompoundDrawables(imgCheck, null, null, null);
            }
        }
        btnQ1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle(getResources().getString(R.string.app_name))
                        .setMessage(body.getQuesTwo().getWarning())
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mQuestSelected2 = "less_than_1_year";
                                isQuestionSet2 = true;
                                mQuestion2 = mQuestion2 + 1;
                                getThreshHoldDisplay(body, mQuestion2);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Abort", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).show();
            }
        });
        btnQ2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestSelected2 = "between_1_and_3_years";
                isQuestionSet2 = true;
                btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ2.setCompoundDrawables(imgCheck, null, null, null);
                btnQ3.setCompoundDrawables(imgUncheck, null, null, null);
            }
        });
        btnQ3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestSelected2 = "more_than_3_year";
                isQuestionSet2 = true;
                btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ2.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ3.setCompoundDrawables(imgCheck, null, null, null);
            }
        });
        btnQuestionContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isQuestionSet2) {
                    mQuestion2 = mQuestion2 + 1;
                    getThreshHoldDisplay(body, mQuestion2);
                } else {
                    Toast.makeText(mActivity, "Please select question", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnQuestCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestion2 = mQuestion2 - 1;
                getThreshHoldDisplay(body, mQuestion2);
            }
        });
    }

    private void getQuestion3(InvestSurveyQuestionResponse body, int mQuestionStartFrom) {
        mQuestion3 = mQuestionStartFrom;
        tvQuestion.setText("");
        btnQ1.setText("");
        btnQ2.setText("");
        btnQ3.setText("");
        btnQ1.setVisibility(View.VISIBLE);
        btnQ2.setVisibility(View.VISIBLE);
        btnQ3.setVisibility(View.GONE);
        tvQuestion.setText(body.getQuesThree().getQuestion());
        btnQ1.setText(body.getQuesThree().getOption().get1());
        btnQ2.setText(body.getQuesThree().getOption().get0());
        Drawable imgCheck = getContext().getResources().getDrawable(R.drawable.ic_radio_button_checked);
        Drawable imgUncheck = getContext().getResources().getDrawable(R.drawable.ic_radio_button_unchecked);
        imgCheck.setBounds(0, 0, 60, 60);
        imgUncheck.setBounds(0, 0, 60, 60);
        if (mQuestSelected3.length() == 0) {
            btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
            btnQ2.setCompoundDrawables(imgUncheck, null, null, null);
        } else {
            if (mQuestSelected3.equals("1")) {
                mQuestSelected3 = "1";
                isQuestionSet3 = true;
                btnQ1.setCompoundDrawables(imgCheck, null, null, null);
                btnQ2.setCompoundDrawables(imgUncheck, null, null, null);
            } else if (mQuestSelected3.equals("0")) {
                mQuestSelected3 = "0";
                isQuestionSet3 = true;
                btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ2.setCompoundDrawables(imgCheck, null, null, null);
            }
        }
        btnQ1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle(getResources().getString(R.string.app_name))
                        .setMessage(body.getQuesThree().getWarning())
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mQuestSelected3 = "1";
                                isQuestionSet3 = true;
                                mQuestion3 = mQuestion3 + 1;
                                getThreshHoldDisplay(body, mQuestion3);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Abort", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).show();
            }
        });
        btnQ2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestSelected3 = "0";
                isQuestionSet3 = true;
                btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ2.setCompoundDrawables(imgCheck, null, null, null);
            }
        });

        btnQuestionContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isQuestionSet3) {
                    mQuestion3 = mQuestion3 + 1;
                    getThreshHoldDisplay(body, mQuestion3);
                } else {
                    Toast.makeText(mActivity, "Please select question", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnQuestCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestion3 = mQuestion3 - 1;
                getThreshHoldDisplay(body, mQuestion3);
            }
        });
    }

    private void getQuestion4(InvestSurveyQuestionResponse body, int mQuestionStartFrom) {
        mQuestion4 = mQuestionStartFrom;
        tvQuestion.setText("");
        btnQ1.setText("");
        btnQ2.setText("");
        btnQ3.setText("");
        btnQ1.setVisibility(View.VISIBLE);
        btnQ2.setVisibility(View.VISIBLE);
        btnQ3.setVisibility(View.GONE);
        tvQuestion.setText(body.getQuesFour().getQuestion());
        btnQ1.setText(body.getQuesFour().getOption().getThisHasNoConsequences());
        btnQ2.setText(body.getQuesFour().getOption().getICanLoseMyInvestment());
        Drawable imgCheck = getContext().getResources().getDrawable(R.drawable.ic_radio_button_checked);
        Drawable imgUncheck = getContext().getResources().getDrawable(R.drawable.ic_radio_button_unchecked);
        imgCheck.setBounds(0, 0, 60, 60);
        imgUncheck.setBounds(0, 0, 60, 60);
        if (mQuestSelected4.length() == 0) {
            btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
            btnQ2.setCompoundDrawables(imgUncheck, null, null, null);
        } else {
            if (mQuestSelected4.equals("this_has_no_consequences")) {
                mQuestSelected4 = "this_has_no_consequences";
                isQuestionSet4 = true;
                btnQ1.setCompoundDrawables(imgCheck, null, null, null);
                btnQ2.setCompoundDrawables(imgUncheck, null, null, null);
            } else if (mQuestSelected4.equals("i_can_lose_my_investment")) {
                mQuestSelected4 = "i_can_lose_my_investment";
                isQuestionSet4 = true;
                btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ2.setCompoundDrawables(imgCheck, null, null, null);
            }
        }
        btnQ1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle(getResources().getString(R.string.app_name))
                        .setMessage(body.getQuesFour().getWarning())
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mQuestSelected4 = "this_has_no_consequences";
                                isQuestionSet4 = true;
                                mQuestion4 = mQuestion4 + 1;
                                getThreshHoldDisplay(body, mQuestion4);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Abort", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).show();

            }
        });
        btnQ2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestSelected4 = "i_can_lose_my_investment";
                isQuestionSet4 = true;
                btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ2.setCompoundDrawables(imgCheck, null, null, null);
            }
        });
        btnQuestionContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isQuestionSet4) {
                    mQuestion4 = mQuestion4 + 1;
                    getThreshHoldDisplay(body, mQuestion4);
                } else {
                    Toast.makeText(mActivity, "Please select question", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnQuestCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestion4 = mQuestion4 - 1;
                getThreshHoldDisplay(body, mQuestion4);
            }
        });
    }

    private void getQuestion5(InvestSurveyQuestionResponse body, int mQuestionStartFrom) {
        mQuestion5 = mQuestionStartFrom;
        if (mThresholdValue == 1) {
            //for thresh holder 1
            tvQuestion.setText("");
            btnQ1.setText("");
            btnQ2.setText("");
            btnQ3.setText("");
            btnQ1.setVisibility(View.VISIBLE);
            btnQ2.setVisibility(View.VISIBLE);
            btnQ3.setVisibility(View.VISIBLE);
            tvQuestion.setText(body.getQuesFive().getQuestion());
            btnQ1.setText(body.getQuesFive().getOption().getThatIsNoProblemForMe());
            btnQ2.setText(body.getQuesFive().getOption().getThatCanBeDifficultForAWhile());
            btnQ3.setText(body.getQuesFive().getOption().getThenIHaveAProblem());
            Drawable imgCheck = getContext().getResources().getDrawable(R.drawable.ic_radio_button_checked);
            Drawable imgUncheck = getContext().getResources().getDrawable(R.drawable.ic_radio_button_unchecked);
            imgCheck.setBounds(0, 0, 60, 60);
            imgUncheck.setBounds(0, 0, 60, 60);
            if (mQuestSelected5.length() == 0) {
                btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ2.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ3.setCompoundDrawables(imgUncheck, null, null, null);
            } else {
                if (mQuestSelected5.equals("that_is_no_problem_for_me")) {
                    mQuestSelected5 = "that_is_no_problem_for_me";
                    isQuestionSet5 = true;
                    btnQ1.setCompoundDrawables(imgCheck, null, null, null);
                    btnQ2.setCompoundDrawables(imgUncheck, null, null, null);
                    btnQ3.setCompoundDrawables(imgUncheck, null, null, null);
                } else if (mQuestSelected5.equals("that_can_be_difficult_for_a_while")) {
                    mQuestSelected5 = "that_can_be_difficult_for_a_while";
                    isQuestionSet5 = true;
                    btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
                    btnQ2.setCompoundDrawables(imgCheck, null, null, null);
                    btnQ3.setCompoundDrawables(imgUncheck, null, null, null);
                } else if (mQuestSelected5.equals("then_i_have_a_problem")) {
                    mQuestSelected5 = "then_i_have_a_problem";
                    isQuestionSet5 = true;
                    btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
                    btnQ2.setCompoundDrawables(imgUncheck, null, null, null);
                    btnQ3.setCompoundDrawables(imgCheck, null, null, null);
                }
            }
            btnQ1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mQuestSelected5 = "that_is_no_problem_for_me";
                    isQuestionSet5 = true;
                    btnQ1.setCompoundDrawables(imgCheck, null, null, null);
                    btnQ2.setCompoundDrawables(imgUncheck, null, null, null);
                    btnQ3.setCompoundDrawables(imgUncheck, null, null, null);
                }
            });
            btnQ2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle(getResources().getString(R.string.app_name))
                            .setMessage(body.getQuesFive().getWarning())
                            .setIcon(R.mipmap.ic_launcher)
                            .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    mQuestSelected5 = "that_can_be_difficult_for_a_while";
                                    isQuestionSet5 = true;
                                    mQuestion5 = mQuestion5 + 1;
                                    getThreshHoldDisplay(body, mQuestion5);
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("Abort", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            }).show();
                }
            });
            btnQ3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle(getResources().getString(R.string.app_name))
                            .setMessage(body.getQuesFive().getWarning())
                            .setIcon(R.mipmap.ic_launcher)
                            .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    mQuestSelected5 = "then_i_have_a_problem";
                                    isQuestionSet5 = true;
                                    mQuestion5 = mQuestion5 + 1;
                                    getThreshHoldDisplay(body, mQuestion5);
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("Abort", new DialogInterface.OnClickListener() {
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
                        mQuestion5 = mQuestion5 + 1;
                        getThreshHoldDisplay(body, mQuestion5);
                    } else {
                        Toast.makeText(mActivity, "Please select question", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            btnQuestCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mQuestion5 = mQuestion5 - 1;
                    getThreshHoldDisplay(body, mQuestion5);
                }
            });
        } else {
            tvQuestion.setText("");
            btnQ1.setText("");
            btnQ2.setText("");
            btnQ3.setText("");
            btnQ1.setVisibility(View.VISIBLE);
            btnQ2.setVisibility(View.VISIBLE);
            btnQ3.setVisibility(View.VISIBLE);
            tvQuestion.setText(body.getQuesFive().getQuestion());
            btnQ1.setText(body.getQuesFive().getOption().getThatIsNoProblemForMe());
            btnQ2.setText(body.getQuesFive().getOption().getThatCanBeDifficultForAWhile());
            btnQ3.setText(body.getQuesFive().getOption().getThenIHaveAProblem());
            Drawable imgCheck = getContext().getResources().getDrawable(R.drawable.ic_radio_button_checked);
            Drawable imgUncheck = getContext().getResources().getDrawable(R.drawable.ic_radio_button_unchecked);
            imgCheck.setBounds(0, 0, 60, 60);
            imgUncheck.setBounds(0, 0, 60, 60);

            if (mQuestSelected5.length() == 0) {
                btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ2.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ3.setCompoundDrawables(imgUncheck, null, null, null);
            } else {
                if (mQuestSelected5.equals("that_is_no_problem_for_me")) {
                    mQuestSelected5 = "that_is_no_problem_for_me";
                    isQuestionSet5 = true;
                    btnQ1.setCompoundDrawables(imgCheck, null, null, null);
                    btnQ2.setCompoundDrawables(imgUncheck, null, null, null);
                    btnQ3.setCompoundDrawables(imgUncheck, null, null, null);
                } else if (mQuestSelected5.equals("that_can_be_difficult_for_a_while")) {
                    mQuestSelected5 = "that_can_be_difficult_for_a_while";
                    isQuestionSet5 = true;
                    btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
                    btnQ2.setCompoundDrawables(imgCheck, null, null, null);
                    btnQ3.setCompoundDrawables(imgUncheck, null, null, null);
                } else if (mQuestSelected5.equals("then_i_have_a_problem")) {
                    mQuestSelected5 = "then_i_have_a_problem";
                    isQuestionSet5 = true;
                    btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
                    btnQ2.setCompoundDrawables(imgUncheck, null, null, null);
                    btnQ3.setCompoundDrawables(imgCheck, null, null, null);
                }
            }
            btnQ1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mQuestSelected5 = "that_is_no_problem_for_me";
                    isQuestionSet5 = true;
                    btnQ1.setCompoundDrawables(imgCheck, null, null, null);
                    btnQ2.setCompoundDrawables(imgUncheck, null, null, null);
                    btnQ3.setCompoundDrawables(imgUncheck, null, null, null);
                }
            });
            btnQ2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle(getResources().getString(R.string.app_name))
                            .setMessage(body.getQuesFive().getWarning())
                            .setIcon(R.mipmap.ic_launcher)
                            .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    mQuestSelected5 = "that_can_be_difficult_for_a_while";
                                    isQuestionSet5 = true;
                                    mQuestion5 = mQuestion5 + 1;
                                    getThreshHoldDisplay(body, mQuestion5);
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("Abort", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            }).show();
                }
            });
            btnQ3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle(getResources().getString(R.string.app_name))
                            .setMessage(body.getQuesFive().getWarning())
                            .setIcon(R.mipmap.ic_launcher)
                            .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    mQuestSelected5 = "then_i_have_a_problem";
                                    isQuestionSet5 = true;
                                    mQuestion5 = mQuestion5 + 1;
                                    getThreshHoldDisplay(body, mQuestion5);
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("Abort", new DialogInterface.OnClickListener() {
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
                        mQuestion5 = mQuestion5 + 1;
                        getThreshHoldDisplay(body, mQuestion5);
                    } else {
                        Toast.makeText(mActivity, "Please select question", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            btnQuestCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cvQuestion.setVisibility(View.GONE);
                    cvWarningMessage.setVisibility(View.VISIBLE);
                    tvWaringMessage.setText(body.getThresholdIntroWarning());
                }
            });
        }
    }

    /*For Question 6*/
    private void getQuestion6(InvestSurveyQuestionResponse body, int mQuestionStartFrom) {
        mQuestion6 = mQuestionStartFrom;
        tvQuestion.setText("");
        btnQ1.setText("");
        btnQ2.setText("");
        btnQ3.setText("");
        btnQ1.setVisibility(View.VISIBLE);
        btnQ2.setVisibility(View.VISIBLE);
        btnQ3.setVisibility(View.GONE);
        tvQuestion.setText(body.getQuesSix().getQuestion());
        btnQ1.setText(body.getQuesSix().getOption().getICanHaveMyLoanRepaid());
        btnQ2.setText(body.getQuesSix().getOption().getICanCompensateForThisFromOtherIncome());

        Drawable imgCheck = getContext().getResources().getDrawable(R.drawable.ic_radio_button_checked);
        Drawable imgUncheck = getContext().getResources().getDrawable(R.drawable.ic_radio_button_unchecked);
        imgCheck.setBounds(0, 0, 60, 60);
        imgUncheck.setBounds(0, 0, 60, 60);
        if (mQuestSelected6.length() == 0) {
            btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
            btnQ2.setCompoundDrawables(imgUncheck, null, null, null);
            btnQ3.setCompoundDrawables(imgUncheck, null, null, null);
        } else {
            if (mQuestSelected6.equals("i_can_have_my_loan_repaid")) {
                mQuestSelected6 = "i_can_have_my_loan_repaid";
                isQuestionSet6 = true;
                btnQ1.setCompoundDrawables(imgCheck, null, null, null);
                btnQ2.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ3.setCompoundDrawables(imgUncheck, null, null, null);
            } else if (mQuestSelected6.equals("i_can_compensate_for_this_from_other_income")) {
                mQuestSelected6 = "i_can_compensate_for_this_from_other_income";
                isQuestionSet6 = true;
                btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ2.setCompoundDrawables(imgCheck, null, null, null);
                btnQ3.setCompoundDrawables(imgUncheck, null, null, null);
            }
        }
        btnQ1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle(getResources().getString(R.string.app_name))
                        .setMessage(body.getQuesSix().getWarning())
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mQuestSelected6 = "i_can_have_my_loan_repaid";
                                isQuestionSet6 = true;
                                mQuestion6 = mQuestion6 + 1;
                                getThreshHoldDisplay(body, mQuestion6);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Abort", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).show();
            }
        });
        btnQ2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestSelected6 = "i_can_compensate_for_this_from_other_income";
                isQuestionSet6 = true;
                btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ2.setCompoundDrawables(imgCheck, null, null, null);
                btnQ3.setCompoundDrawables(imgUncheck, null, null, null);
            }
        });
        btnQuestionContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isQuestionSet6) {
                    mQuestion6 = mQuestion6 + 1;
                    getThreshHoldDisplay(body, mQuestion6);
                } else {
                    Toast.makeText(mActivity, "Please select question", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnQuestCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestion6 = mQuestion6 - 1;
                getThreshHoldDisplay(body, mQuestion6);
            }
        });
    }

    /*For Question 7*/
    private void getQuestion7(InvestSurveyQuestionResponse body, int mQuestionStartFrom) {
        mQuestion7 = mQuestionStartFrom;
        tvQuestion.setText("");
        btnQ1.setText("");
        btnQ2.setText("");
        btnQ3.setText("");
        btnQ1.setVisibility(View.VISIBLE);
        btnQ2.setVisibility(View.VISIBLE);
        btnQ3.setVisibility(View.VISIBLE);
        tvQuestion.setText(body.getQuesSeven().getQuestion());
        btnQ1.setText(body.getQuesSeven().getOption().getTheEntrepreneurCanNoLongerMeet());
        btnQ2.setText(body.getQuesSeven().getOption().getTheValueOfTheRealEstate());
        btnQ3.setText(body.getQuesSeven().getOption().getBothTheAboveAnswersAreCorrect());
        Drawable imgCheck = getContext().getResources().getDrawable(R.drawable.ic_radio_button_checked);
        Drawable imgUncheck = getContext().getResources().getDrawable(R.drawable.ic_radio_button_unchecked);
        imgCheck.setBounds(0, 0, 60, 60);
        imgUncheck.setBounds(0, 0, 60, 60);
        if (mQuestSelected7.length() == 0) {
            btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
            btnQ2.setCompoundDrawables(imgUncheck, null, null, null);
            btnQ3.setCompoundDrawables(imgUncheck, null, null, null);
            Log.d(">>>>>>7>>>>>>>>>", "getQuestion7: " + "IF----" + mQuestSelected7);
        } else {
            if (mQuestSelected7.equals("the_entrepreneur_can_no_longer_meet")) {
                mQuestSelected7 = "the_entrepreneur_can_no_longer_meet";
                isQuestionSet7 = true;
                btnQ1.setCompoundDrawables(imgCheck, null, null, null);
                btnQ2.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ3.setCompoundDrawables(imgUncheck, null, null, null);
            } else if (mQuestSelected7.equals("the_value_of_the_real_estate")) {
                mQuestSelected7 = "the_value_of_the_real_estate";
                isQuestionSet7 = true;
                btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ2.setCompoundDrawables(imgCheck, null, null, null);
                btnQ3.setCompoundDrawables(imgUncheck, null, null, null);
            } else if (mQuestSelected7.equals("both_the_above_answers_are_correct")) {
                mQuestSelected7 = "both_the_above_answers_are_correct";
                isQuestionSet7 = true;
                btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ2.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ3.setCompoundDrawables(imgCheck, null, null, null);
            }
            Log.d(">>>>>>7>>>>>>>>>", "getQuestion7: " + "ELSE----" + mQuestSelected7);
        }

        btnQ1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle(getResources().getString(R.string.app_name))
                        .setMessage(body.getQuesSeven().getWarning())
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mQuestSelected7 = "the_entrepreneur_can_no_longer_meet";
                                isQuestionSet7 = true;
                                mQuestion7 = mQuestion7 + 1;
                                getThreshHoldDisplay(body, mQuestion7);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Abort", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).show();
            }
        });

        btnQ2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle(getResources().getString(R.string.app_name))
                        .setMessage(body.getQuesSeven().getWarning())
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mQuestSelected7 = "the_value_of_the_real_estate";
                                isQuestionSet7 = true;
                                mQuestion7 = mQuestion7 + 1;
                                getThreshHoldDisplay(body, mQuestion7);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Abort", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).show();
            }
        });

        btnQ3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestSelected7 = "both_the_above_answers_are_correct";
                isQuestionSet7 = true;
                btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ2.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ3.setCompoundDrawables(imgCheck, null, null, null);
            }
        });

        btnQuestionContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isQuestionSet7) {
                    mQuestion7 = mQuestion7 + 1;
                    getThreshHoldDisplay(body, mQuestion7);
                } else {
                    Toast.makeText(mActivity, "Please select question", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnQuestCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestion7 = mQuestion7 - 1;
                getThreshHoldDisplay(body, mQuestion7);
            }
        });
    }

    /*For Question 8*/
    private void getQuestion8(InvestSurveyQuestionResponse body, int mQuestionStartFrom) {
        mQuestion8 = mQuestionStartFrom;
        tvQuestion.setText("");
        btnQ1.setText("");
        btnQ2.setText("");
        btnQ3.setText("");
        btnQ1.setVisibility(View.VISIBLE);
        btnQ2.setVisibility(View.VISIBLE);
        btnQ3.setVisibility(View.VISIBLE);
        tvQuestion.setText(body.getQuesEight().getQuestion());
        btnQ1.setText(body.getQuesEight().getOption().getMoreThan25());
        btnQ2.setText(body.getQuesEight().getOption().getBetween10And25());
        btnQ3.setText(body.getQuesEight().getOption().getUpTo10());
        Drawable imgCheck = getContext().getResources().getDrawable(R.drawable.ic_radio_button_checked);
        Drawable imgUncheck = getContext().getResources().getDrawable(R.drawable.ic_radio_button_unchecked);
        imgCheck.setBounds(0, 0, 60, 60);
        imgUncheck.setBounds(0, 0, 60, 60);
        if (mQuestSelected8.length() == 0) {
            btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
            btnQ2.setCompoundDrawables(imgUncheck, null, null, null);
            btnQ3.setCompoundDrawables(imgUncheck, null, null, null);
        } else {
            if (mQuestSelected8.equals("more_than_25%")) {
                mQuestSelected8 = "more_than_25%";
                isQuestionSet8 = true;
                btnQ1.setCompoundDrawables(imgCheck, null, null, null);
                btnQ2.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ3.setCompoundDrawables(imgUncheck, null, null, null);
            } else if (mQuestSelected8.equals("between_10%_and_25%")) {
                mQuestSelected8 = "between_10%_and_25%";
                isQuestionSet8 = true;
                btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ2.setCompoundDrawables(imgCheck, null, null, null);
                btnQ3.setCompoundDrawables(imgUncheck, null, null, null);
            } else if (mQuestSelected8.equals("up_to_10%")) {
                mQuestSelected8 = "up_to_10%";
                isQuestionSet8 = true;
                btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ2.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ3.setCompoundDrawables(imgCheck, null, null, null);
            }
        }
        btnQ1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle(getResources().getString(R.string.app_name))
                        .setMessage(body.getQuesEight().getWarning())
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mQuestSelected8 = "more_than_25%";
                                isQuestionSet8 = true;
                                mQuestion8 = mQuestion8 + 1;
                                getThreshHoldDisplay(body, mQuestion8);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Abort", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).show();
            }
        });
        btnQ2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle(getResources().getString(R.string.app_name))
                        .setMessage(body.getQuesEight().getWarning())
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mQuestSelected8 = "between_10%_and_25%";
                                isQuestionSet8 = true;
                                mQuestion8 = mQuestion8 + 1;
                                getThreshHoldDisplay(body, mQuestion8);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Abort", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).show();
            }
        });
        btnQ3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestSelected8 = "up_to_10%";
                isQuestionSet8 = true;
                btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ2.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ3.setCompoundDrawables(imgCheck, null, null, null);
            }
        });

        btnQuestionContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isQuestionSet8) {
                    mQuestion8 = mQuestion8 + 1;
                    getThreshHoldDisplay(body, mQuestion8);
                } else {
                    Toast.makeText(mActivity, "Please select question", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnQuestCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestion8 = mQuestion8 - 1;
                getThreshHoldDisplay(body, mQuestion8);
            }
        });
    }

    /*For Question 9*/
    private void getQuestion9(InvestSurveyQuestionResponse body, int mQuestionStartFrom) {
        mQuestion9 = mQuestionStartFrom;
        tvQuestion.setText("");
        btnQ1.setText("");
        btnQ2.setText("");
        btnQ3.setText("");
        btnQ1.setVisibility(View.VISIBLE);
        btnQ2.setVisibility(View.VISIBLE);
        btnQ3.setVisibility(View.GONE);
        tvQuestion.setText(body.getQuesNine().getQuestion());
        btnQ1.setText(body.getQuesNine().getOption().getIInvestTheEntireAmountInOneLoan());
        btnQ2.setText(body.getQuesNine().getOption().getISpreadTheAmountOverSeveralLoans());
        Drawable imgCheck = getContext().getResources().getDrawable(R.drawable.ic_radio_button_checked);
        Drawable imgUncheck = getContext().getResources().getDrawable(R.drawable.ic_radio_button_unchecked);
        imgCheck.setBounds(0, 0, 60, 60);
        imgUncheck.setBounds(0, 0, 60, 60);
        if (mQuestSelected9.length() == 0) {
            btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
            btnQ2.setCompoundDrawables(imgUncheck, null, null, null);
            Log.d(TAG, "getQuestion9: " + "IF----" + mQuestSelected9);
        } else {
            if (mQuestSelected9.equals("i_invest_the_entire_amount_in_one_loan")) {
                mQuestSelected9 = "i_invest_the_entire_amount_in_one_loan";
                isQuestionSet9 = true;
                btnQ1.setCompoundDrawables(imgCheck, null, null, null);
                btnQ2.setCompoundDrawables(imgUncheck, null, null, null);
            } else if (mQuestSelected9.equals("i_spread_the_amount_over_several_loans")) {
                mQuestSelected9 = "i_spread_the_amount_over_several_loans";
                isQuestionSet9 = true;
                btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ2.setCompoundDrawables(imgCheck, null, null, null);
            }
        }
        btnQ1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle(getResources().getString(R.string.app_name))
                        .setMessage(body.getQuesNine().getWarning())
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mQuestSelected9 = "i_invest_the_entire_amount_in_one_loan";
                                isQuestionSet9 = true;
                                mQuestion9 = mQuestion9 + 1;
                                getThreshHoldDisplay(body, mQuestion9);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Abort", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).show();
            }
        });
        btnQ2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestSelected9 = "i_spread_the_amount_over_several_loans";
                isQuestionSet9 = true;
                btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ2.setCompoundDrawables(imgCheck, null, null, null);
            }
        });

        btnQuestionContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isQuestionSet9) {
                    mQuestion9 = mQuestion9 + 1;
                    getThreshHoldDisplay(body, mQuestion9);
                } else {
                    Toast.makeText(mActivity, "Please select question", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnQuestCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestion9 = mQuestion9 - 1;
                getThreshHoldDisplay(body, mQuestion9);
            }
        });
    }

    private void getQuestion10(InvestSurveyQuestionResponse body, int mQuestionStartFrom) {
        mQuestion10 = mQuestionStartFrom;
        tvQuestion.setText("");
        btnQ1.setText("");
        btnQ2.setText("");
        btnQ3.setText("");
        btnQ1.setVisibility(View.VISIBLE);
        btnQ2.setVisibility(View.VISIBLE);
        btnQ3.setVisibility(View.VISIBLE);
        tvQuestion.setText(body.getQuesTen().getQuestion());
        btnQ1.setText(body.getQuesTen().getOption().getIMNotAllowedToChangeOrCancelMyInvestment());
        btnQ2.setText(body.getQuesTen().getOption().getICanChangeOrCancelWithin24Hours());
        btnQ3.setText(body.getQuesTen().getOption().getICanChangeOrCancelWhileTheLoanIsNotFinal());
        Drawable imgCheck = getContext().getResources().getDrawable(R.drawable.ic_radio_button_checked);
        Drawable imgUncheck = getContext().getResources().getDrawable(R.drawable.ic_radio_button_unchecked);
        imgCheck.setBounds(0, 0, 60, 60);
        imgUncheck.setBounds(0, 0, 60, 60);
        if (mQuestSelected10.length() == 0) {
            btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
            btnQ2.setCompoundDrawables(imgUncheck, null, null, null);
            btnQ3.setCompoundDrawables(imgUncheck, null, null, null);
        } else {
            if (mQuestSelected10.equals("the_entrepreneur_can_no_longer_meet")) {
                mQuestSelected10 = "i_m_not_allowed_to_change_or_cancel_my_investment";
                isQuestionSet10 = true;
                btnQ1.setCompoundDrawables(imgCheck, null, null, null);
                btnQ2.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ3.setCompoundDrawables(imgUncheck, null, null, null);
            } else if (mQuestSelected10.equals("i_can_change_or_cancel_within_24_hours")) {
                mQuestSelected10 = "i_can_change_or_cancel_within_24_hours";
                isQuestionSet10 = true;
                btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ2.setCompoundDrawables(imgCheck, null, null, null);
                btnQ3.setCompoundDrawables(imgUncheck, null, null, null);
            } else if (mQuestSelected10.equals("i_can_change_or_cancel_while_the_loan_is_not_final")) {
                mQuestSelected10 = "i_can_change_or_cancel_while_the_loan_is_not_final";
                isQuestionSet10 = true;
                btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ2.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ3.setCompoundDrawables(imgCheck, null, null, null);
            }
        }
        btnQ1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle(getResources().getString(R.string.app_name))
                        .setMessage(body.getQuesTen().getWarning())
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mQuestSelected10 = "i_m_not_allowed_to_change_or_cancel_my_investment";
                                isQuestionSet10 = true;
                                mQuestion10 = mQuestion10 + 1;
                                getThreshHoldDisplay(body, mQuestion10);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Abort", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).show();
            }
        });
        btnQ2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestSelected10 = "i_can_change_or_cancel_within_24_hours";
                isQuestionSet10 = true;
                btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ2.setCompoundDrawables(imgCheck, null, null, null);
                btnQ3.setCompoundDrawables(imgUncheck, null, null, null);
            }
        });
        btnQ3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle(getResources().getString(R.string.app_name))
                        .setMessage(body.getQuesTen().getWarning())
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mQuestSelected10 = "i_can_change_or_cancel_while_the_loan_is_not_final";
                                isQuestionSet10 = true;
                                mQuestion10 = mQuestion10 + 1;
                                getThreshHoldDisplay(body, mQuestion10);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Abort", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).show();
            }
        });

        btnQuestionContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isQuestionSet10) {
                    mQuestion10 = mQuestion10 + 1;
                    getThreshHoldDisplay(body, mQuestion10);
                } else {
                    Toast.makeText(mActivity, "Please select question", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnQuestCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestion10 = mQuestion10 - 1;
                getThreshHoldDisplay(body, mQuestion10);
            }
        });
    }

    private void getQuestion11(InvestSurveyQuestionResponse body, int mQuestionStartFrom) {
        mQuestion11 = mQuestionStartFrom;
        tvQuestion.setText("");
        btnQ1.setText("");
        btnQ2.setText("");
        btnQ3.setText("");
        btnQ1.setVisibility(View.VISIBLE);
        btnQ2.setVisibility(View.VISIBLE);
        btnQ3.setVisibility(View.VISIBLE);
        tvQuestion.setText(body.getQuesEleven().getQuestion());
        btnQ1.setText(body.getQuesEleven().getOption().getNoMaximumIsApplicable());
        btnQ2.setText(body.getQuesEleven().getOption().getICannotInvestMoreThan8000Eur());
        btnQ3.setText(body.getQuesEleven().getOption().getICannotInvestMoreThan40000Eur());
        Drawable imgCheck = getContext().getResources().getDrawable(R.drawable.ic_radio_button_checked);
        Drawable imgUncheck = getContext().getResources().getDrawable(R.drawable.ic_radio_button_unchecked);
        imgCheck.setBounds(0, 0, 60, 60);
        imgUncheck.setBounds(0, 0, 60, 60);
        if (mQuestSelected11.length() == 0) {
            btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
            btnQ2.setCompoundDrawables(imgUncheck, null, null, null);
            btnQ3.setCompoundDrawables(imgUncheck, null, null, null);
        } else {
            if (mQuestSelected11.equals("no_maximum_is_applicable")) {
                mQuestSelected11 = "no_maximum_is_applicable";
                isQuestionSet11 = true;
                btnQ1.setCompoundDrawables(imgCheck, null, null, null);
                btnQ2.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ3.setCompoundDrawables(imgUncheck, null, null, null);
            } else if (mQuestSelected11.equals("i_cannot_invest_more_than_8000_eur")) {
                mQuestSelected11 = "i_cannot_invest_more_than_8000_eur";
                isQuestionSet11 = true;
                btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ2.setCompoundDrawables(imgCheck, null, null, null);
                btnQ3.setCompoundDrawables(imgUncheck, null, null, null);
            } else if (mQuestSelected11.equals("i_cannot_invest_more_than_40000_eur")) {
                mQuestSelected11 = "i_cannot_invest_more_than_40000_eur";
                isQuestionSet11 = true;
                btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ2.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ3.setCompoundDrawables(imgCheck, null, null, null);
            }
        }

        btnQ1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle(getResources().getString(R.string.app_name))
                        .setMessage(body.getQuesEleven().getWarning())
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mQuestSelected11 = "no_maximum_is_applicable";
                                isQuestionSet11 = true;
                                if (isQuestionSet11) {
                                    cvQuestion.setVisibility(View.GONE);
                                    getSurveyDataInserted();
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Abort", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).show();
            }
        });
        btnQ2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestSelected11 = "i_cannot_invest_more_than_8000_eur";
                isQuestionSet11 = true;
                btnQ1.setCompoundDrawables(imgUncheck, null, null, null);
                btnQ2.setCompoundDrawables(imgCheck, null, null, null);
                btnQ3.setCompoundDrawables(imgUncheck, null, null, null);
            }
        });
        btnQ3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle(getResources().getString(R.string.app_name))
                        .setMessage(body.getQuesEleven().getWarning())
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mQuestSelected11 = "i_cannot_invest_more_than_40000_eur";
                                isQuestionSet11 = true;
                                if (isQuestionSet11) {
                                    cvQuestion.setVisibility(View.GONE);
                                    getSurveyDataInserted();
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Abort", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).show();
            }
        });
        btnQuestionContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isQuestionSet11) {
                    getSurveyDataInserted();
                } else {
                    Toast.makeText(mActivity, "Please select question", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnQuestCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQuestion11 = mQuestion11 - 1;
                getThreshHoldDisplay(body, mQuestion11);
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
        jsonObject.addProperty("q10", mQuestSelected10);
        jsonObject.addProperty("q11", mQuestSelected11);
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
                                if (response.body().getDataInsert() == 1) {
                                    cvInvestForm.setVisibility(View.GONE);
                                    lLayoutInvestForm1.setVisibility(View.GONE);
                                    lLayoutInvestForm2.setVisibility(View.GONE);
                                    cvQuestion.setVisibility(View.GONE);
                                    cvWarningMessage.setVisibility(View.GONE);
                                    //for thresh holder 1
                                    cvWarningBeforeMessage.setVisibility(View.VISIBLE);
                                    tvWaringBeforeMessage.setText(response.body().getThresholdPassMsg());
                                    //for contnuie
                                    btnWaringBeforeContinue.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            getTwoFAForm(mAmountData);
                                        }
                                    });
                                    btnWarningBeforeCancel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            new AlertDialog.Builder(getActivity())
                                                    .setTitle(getResources().getString(R.string.app_name))
                                                    .setMessage(getResources().getString(R.string.sure_cancel))
                                                    .setIcon(R.mipmap.ic_launcher)
                                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                                                            navController.navigateUp();
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
                                }
                            } else {
                                setPreference(mActivity, "user_id", "");
                                setPreference(mActivity, "mLogout_token", "");
                                MaxCrowdFund.getInstance().getClearCookies(mActivity, "cookies", "");
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

    private void getTwoFAForm(String amountData) {
        cvWarningMessage.setVisibility(View.GONE);
        cvWarningBeforeMessage.setVisibility(View.GONE);
        cvTwoFA.setVisibility(View.VISIBLE);
        tvTotalAccount.setText(amountData);
        tvTotalWallet.setText(mTotalChargeMpgToken + " mpg");
        spinnerFA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerModel option = signingAdapter.getItem(position);
                if (position == 0) {
                    mTrans_signing = "";
                } else {
                    Log.d(TAG, "onItemSelected: " + option.getKey() + ">>>>>" + String.valueOf(position));
                    Log.d(TAG, "onItemSelected: " + option.getVal());
                    mTrans_signing = option.getKey();
                    if (mTrans_signing.equals("google_authentication")) {
                        lLayoutOTP.setVisibility(View.GONE);
                    } else {
                        lLayoutOTP.setVisibility(View.VISIBLE);
                    }
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
                    if (mTrans_signing != null && !mTrans_signing.isEmpty()) {
                        if (mTrans_signing.equals("google_authentication")) {
                        } else {
                            getSendOTP(mTrans_signing);
                        }
                    } else {
                        Toast.makeText(mActivity, "Please select email 2FA method you wish to use", Toast.LENGTH_SHORT).show();
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
                    Log.d(TAG, "onClick:----" + mEnterOTP);
                    if (mTrans_signing != null && !mTrans_signing.isEmpty()) {
                        if (mEnterOTP != null && !mEnterOTP.isEmpty()) {
                            if (mTrans_signing.equals("google_authentication")) {
                                //
                            } else {
                                if (mEnterOTP != null) {
                                    if (mResponseOTP != null) { //for validation
                                        getTFAValidate(mTrans_signing, mResponseOTP, mEnterOTP);
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(mActivity, getResources().getString(R.string.enter_otp), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mActivity, "Please select email 2FA method you wish to use", Toast.LENGTH_SHORT).show();
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
                                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                                navController.navigateUp();
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
    }

    private void getChangePreference() {
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
                                modelList.clear();
                                modelList1.clear();
                                SpinnerModel spinnerModel1 = new SpinnerModel();
                                spinnerModel1.setKey("Please select");
                                spinnerModel1.setVal("Please select");
                                modelList1.add(0, spinnerModel1);
                                if (response.body().getPreferences().getData().getTransactionSigning().getOptions().size() > 0) {
                                    for (int i = 0; i < response.body().getPreferences().getData().getTransactionSigning().getOptions().size(); i++) {
                                        SpinnerModel spinnerModel = new SpinnerModel();
                                        spinnerModel.setKey(response.body().getPreferences().getData().getTransactionSigning().getOptions().get(i).getKey());
                                        spinnerModel.setVal(response.body().getPreferences().getData().getTransactionSigning().getOptions().get(i).getVal());
                                        modelList.add(spinnerModel);
                                    }
                                    modelList1.addAll(modelList);
                                    signingAdapter = new TransactionSigningAdapter(getActivity(), modelList1);
                                    spinnerFA.setAdapter(signingAdapter);
                                }
                            } else {
                                setPreference(getActivity(), "user_id", "");
                                setPreference(getActivity(), "mLogout_token", "");
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
                                        cvTwoFA.setVisibility(View.GONE);
                                        cvWarningBeforeMessage.setVisibility(View.GONE);
                                        cvWarningMessage.setVisibility(View.GONE);
                                        cvQuestion.setVisibility(View.GONE);
                                        cvInvestForm.setVisibility(View.VISIBLE);
                                        lLayoutInvestForm1.setVisibility(View.VISIBLE);
                                        lLayoutInvestForm2.setVisibility(View.GONE);
                                        edtAccount.setText(String.valueOf(response.body().getDepositAmount()));
                                    } else {
                                        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                                        navController.navigate(R.id.action_investFormFragment_to_nav_my_investments);
                                    }
                                }
                            } else {
                                setPreference(mActivity, "user_id", "");
                                setPreference(mActivity, "mLogout_token", "");
                                MaxCrowdFund.getInstance().getClearCookies(mActivity, "cookies", "");
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


}
