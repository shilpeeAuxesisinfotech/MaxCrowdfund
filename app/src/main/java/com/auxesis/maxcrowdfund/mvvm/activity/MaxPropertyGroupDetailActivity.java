package com.auxesis.maxcrowdfund.mvvm.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.volley.AuthFailureError;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.customClickListener.OnCustomClickListener;
import com.auxesis.maxcrowdfund.customClickListener.OnDownloadClickListener;
import com.auxesis.maxcrowdfund.adapter.CollateralAdapter;
import com.auxesis.maxcrowdfund.adapter.DocumentAdapter;
import com.auxesis.maxcrowdfund.adapter.FundraiserAdapter;
import com.auxesis.maxcrowdfund.adapter.InvestmentPlanAdapter;
import com.auxesis.maxcrowdfund.adapter.LastInvestmentAdapter;
import com.auxesis.maxcrowdfund.adapter.LoanTermAdapter;
import com.auxesis.maxcrowdfund.adapter.MyPhotoAdapter;
import com.auxesis.maxcrowdfund.adapter.RiskAdapter;
import com.auxesis.maxcrowdfund.constant.APIUrl;
import com.auxesis.maxcrowdfund.constant.CheckForSDCard;
import com.auxesis.maxcrowdfund.constant.ProgressDialog;
import com.auxesis.maxcrowdfund.constant.Utils;
import com.auxesis.maxcrowdfund.model.CollateralModelP;
import com.auxesis.maxcrowdfund.model.DocumentModel;
import com.auxesis.maxcrowdfund.model.FundraiserModel;
import com.auxesis.maxcrowdfund.model.InvestmentPlanModel;
import com.auxesis.maxcrowdfund.model.LastInvestmentModel;
import com.auxesis.maxcrowdfund.model.LoanTermModel;
import com.auxesis.maxcrowdfund.model.PhotosVideosModel;
import com.auxesis.maxcrowdfund.model.RiskModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.auxesis.maxcrowdfund.constant.APIUrl.GER_MY_INVEST_DETAILS;
import static com.auxesis.maxcrowdfund.constant.Utils.showToast;

public class MaxPropertyGroupDetailActivity extends AppCompatActivity implements OnCustomClickListener, OnDownloadClickListener {
    private static final String TAG = "MaxPropertyGroupDetailA";
    TextView tv_back_arrow, tvHeaderTitle, tv_mTittle, tv_interest_pr, tv_risk_c, tv_currency_left_amt, tv_cur_symbol_amt, tvAmount, tv_filled, tv_investors,
            tv_left_amount, tv_left, tv_months, tvType, tv_location, tvNoRecord, tv_summary, txt_summary_content, tv_loan_terms, tvNoRecord_loan_term, tv_investment_plan,
            tvNoRecord_investment, tv_collateral, tvNoRecord_Collateral, tv_fundraiser, tvNoRecord_foundRaiser, tv_addnal_info_tittle, tv_addnal_content, tv_rik,
            tvNoRecord_Risk, tvNoRecord_Document, tvNoRecord_LastInvestment, tv_last_investment, tv_document;
    ImageView iv_main_ing, iv_logo;
    Button btn_investment_plan;
    TextView tv_arrow_summary, tv_arrow_loan_terms, tv_arrow_collateral, tv_arrow_fundraiser, tv_arrow_investment_plan, tv_arrow_risk, tv_arrow_document, tv_arrow_last_investment;

    LinearLayout ll_contant_summary, ll_contant_loan_terms, ll_contant_collateral, ll_contant_fundraiser, ll_contant_investment, ll_contant_risk, ll_contant_document, ll_contant_last_invest;
    RelativeLayout rl_summary_for, rl_loan_term_click, rl_collateral_click, rl_foundaiser_click, rl_investment_click, rl_risk_click, rl_document_click, rl_last_invest_click;

    List<PhotosVideosModel> photosVideosList = new ArrayList<>();
    List<PhotosVideosModel> photosVideosList_f = new ArrayList<>();
    List<LoanTermModel> loanTermList = new ArrayList<>();

    List<CollateralModelP> collateralListFirst = new ArrayList<>();
    List<CollateralModelP> collateralListSecond = new ArrayList<>();

    List<FundraiserModel> fundraiserModelList = new ArrayList<>();
    List<InvestmentPlanModel> investmentPlanList = new ArrayList<>();
    List<RiskModel> riskList = new ArrayList<>();

    List<DocumentModel> documentList = new ArrayList<>();
    List<LastInvestmentModel> lastInvestmentList = new ArrayList<>();

    ProgressDialog pd;
    ProgressBar progessBar;
    RecyclerView recyclerView, recyViewLoanTerm, recyViewInvestment, recyViewCollateral, recyViewFoundRaiser, recyViewRisk, recyViewDocument, recyViewLastInvestment;
    MyPhotoAdapter adapter;
    LoanTermAdapter loanTermAdapter;
    InvestmentPlanAdapter investmentPlanAdapter;
    CollateralAdapter collateralAdapter;
    FundraiserAdapter fundraiserAdapter;
    RiskAdapter riskAdapter;
    DocumentAdapter documentAdapter;
    LastInvestmentAdapter lastInvestmentAdapter;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private String mdownloadFile;
    private long downloadID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_max_property_group_detail);
        init();
    }

    private void init() {
        tv_back_arrow = findViewById(R.id.tv_back_arrow);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setText(R.string.max_property_group);
        tv_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //For header max Tittle
        tv_mTittle = findViewById(R.id.tv_mTittle);
        tv_interest_pr = findViewById(R.id.tv_interest_pr);
        tv_risk_c = findViewById(R.id.tv_risk_c);
        tv_currency_left_amt = findViewById(R.id.tv_currency_left_amt);
        tv_cur_symbol_amt = findViewById(R.id.tv_cur_symbol_amt);
        tvAmount = findViewById(R.id.tvAmount);
        tv_filled = findViewById(R.id.tv_filled);
        tv_investors = findViewById(R.id.tv_investors);
        tv_left_amount = findViewById(R.id.tv_left_amount);
        tv_left = findViewById(R.id.tv_left);
        tv_months = findViewById(R.id.tv_months);
        tvType = findViewById(R.id.tvType);
        tv_location = findViewById(R.id.tv_location);
        progessBar = findViewById(R.id.progessBar);
        iv_main_ing = findViewById(R.id.iv_main_ing);

        //For photo
        recyclerView = findViewById(R.id.recyclerView);
        tvNoRecord = findViewById(R.id.tvNoRecord);

        //For Summary data
        tv_arrow_summary = findViewById(R.id.tv_arrow_summary);
        tv_summary = findViewById(R.id.tv_summary);
        txt_summary_content = findViewById(R.id.txt_summary_content);

        //For Loan Term
        tv_arrow_loan_terms = findViewById(R.id.tv_arrow_loan_terms);
        tv_loan_terms = findViewById(R.id.tv_loan_terms);
        tvNoRecord_loan_term = findViewById(R.id.tvNoRecord_loan_term);
        recyViewLoanTerm = findViewById(R.id.recyViewLoanTerm);

        //For FoundRaiser
        iv_logo = findViewById(R.id.iv_logo);
        tv_fundraiser = findViewById(R.id.tv_fundraiser);
        recyViewFoundRaiser = findViewById(R.id.recyViewFoundRaiser);
        tvNoRecord_foundRaiser = findViewById(R.id.tvNoRecord_foundRaiser);
        tv_addnal_info_tittle = findViewById(R.id.tv_addnal_info_tittle);
        tv_addnal_content = findViewById(R.id.tv_addnal_content);

        //For Risk
        tv_rik = findViewById(R.id.tv_rik);
        tvNoRecord_Risk = findViewById(R.id.tvNoRecord_Risk);
        recyViewRisk = findViewById(R.id.recyViewRisk);

        //For Investment Plan
        tv_arrow_investment_plan = findViewById(R.id.tv_arrow_investment_plan);
        tv_investment_plan = findViewById(R.id.tv_investment_plan);
        tvNoRecord_investment = findViewById(R.id.tvNoRecord_investment);
        recyViewInvestment = findViewById(R.id.recyViewInvestment);
        btn_investment_plan = findViewById(R.id.btn_investment_plan);

        //for colletral
        tv_arrow_collateral = findViewById(R.id.tv_arrow_collateral);
        tv_collateral = findViewById(R.id.tv_collateral);
        tvNoRecord_Collateral = findViewById(R.id.tvNoRecord_Collateral);
        recyViewCollateral = findViewById(R.id.recyViewCollateral);

        //For Document
        recyViewDocument = findViewById(R.id.recyViewDocument);
        tvNoRecord_Document = findViewById(R.id.tvNoRecord_Document);
        tv_document = findViewById(R.id.tv_document);

        //For Last investment
        recyViewLastInvestment = findViewById(R.id.recyViewLastInvestment);
        tvNoRecord_LastInvestment = findViewById(R.id.tvNoRecord_LastInvestment);
        tv_last_investment = findViewById(R.id.tv_last_investment);

        //For All Arrow
        tv_arrow_fundraiser = findViewById(R.id.tv_arrow_fundraiser);

        tv_arrow_risk = findViewById(R.id.tv_arrow_risk);
        tv_arrow_document = findViewById(R.id.tv_arrow_document);
        tv_arrow_last_investment = findViewById(R.id.tv_arrow_last_investment);

        ll_contant_summary = findViewById(R.id.ll_contant_summary);
        ll_contant_loan_terms = findViewById(R.id.ll_contant_loan_terms);
        ll_contant_collateral = findViewById(R.id.ll_contant_collateral);
        ll_contant_fundraiser = findViewById(R.id.ll_contant_fundraiser);
        ll_contant_investment = findViewById(R.id.ll_contant_investment);
        ll_contant_risk = findViewById(R.id.ll_contant_risk);
        ll_contant_document = findViewById(R.id.ll_contant_document);
        ll_contant_last_invest = findViewById(R.id.ll_contant_last_invest);

        rl_summary_for = findViewById(R.id.rl_summary_for);
        rl_loan_term_click = findViewById(R.id.rl_loan_term_click);
        rl_collateral_click = findViewById(R.id.rl_collateral_click);
        rl_foundaiser_click = findViewById(R.id.rl_foundaiser_click);
        rl_investment_click = findViewById(R.id.rl_investment_click);
        rl_risk_click = findViewById(R.id.rl_risk_click);
        rl_document_click = findViewById(R.id.rl_document_click);
        rl_last_invest_click = findViewById(R.id.rl_last_invest_click);

        ll_contant_summary.setVisibility(View.VISIBLE);
        ll_contant_loan_terms.setVisibility(View.GONE);
        ll_contant_collateral.setVisibility(View.GONE);
        ll_contant_fundraiser.setVisibility(View.GONE);
        ll_contant_investment.setVisibility(View.GONE);
        ll_contant_risk.setVisibility(View.GONE);
        ll_contant_document.setVisibility(View.GONE);
        ll_contant_last_invest.setVisibility(View.GONE);

        rl_summary_for.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ll_contant_summary.isShown()) {
                    Utils.slide_up(MaxPropertyGroupDetailActivity.this, ll_contant_summary);
                    tv_arrow_summary.setBackgroundResource(R.drawable.ic_arrow_down);
                    ll_contant_summary.setVisibility(View.GONE);
                } else {
                    ll_contant_summary.setVisibility(View.VISIBLE);
                    Utils.slide_down(MaxPropertyGroupDetailActivity.this, ll_contant_summary);
                    tv_arrow_summary.setBackgroundResource(R.drawable.ic_arrow_up);
                }

            }
        });

        //For Loan terms
        rl_loan_term_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ll_contant_loan_terms.isShown()) {
                    Utils.slide_up(MaxPropertyGroupDetailActivity.this, ll_contant_loan_terms);
                    tv_arrow_loan_terms.setBackgroundResource(R.drawable.ic_arrow_down);
                    ll_contant_loan_terms.setVisibility(View.GONE);
                } else {
                    ll_contant_loan_terms.setVisibility(View.VISIBLE);
                    Utils.slide_down(MaxPropertyGroupDetailActivity.this, ll_contant_loan_terms);
                    tv_arrow_loan_terms.setBackgroundResource(R.drawable.ic_arrow_up);
                }
            }
        });

        //For colla
        rl_collateral_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ll_contant_collateral.isShown()) {
                    Utils.slide_up(MaxPropertyGroupDetailActivity.this, ll_contant_collateral);
                    tv_arrow_collateral.setBackgroundResource(R.drawable.ic_arrow_down);
                    ll_contant_collateral.setVisibility(View.GONE);

                } else {
                    ll_contant_collateral.setVisibility(View.VISIBLE);
                    Utils.slide_down(MaxPropertyGroupDetailActivity.this, ll_contant_collateral);
                    tv_arrow_collateral.setBackgroundResource(R.drawable.ic_arrow_up);
                }
            }
        });

        //For fundraiser
        rl_foundaiser_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ll_contant_fundraiser.isShown()) {
                    Utils.slide_up(MaxPropertyGroupDetailActivity.this, ll_contant_fundraiser);
                    tv_arrow_fundraiser.setBackgroundResource(R.drawable.ic_arrow_down);
                    ll_contant_fundraiser.setVisibility(View.GONE);

                } else {
                    ll_contant_fundraiser.setVisibility(View.VISIBLE);
                    Utils.slide_down(MaxPropertyGroupDetailActivity.this, ll_contant_fundraiser);
                    tv_arrow_fundraiser.setBackgroundResource(R.drawable.ic_arrow_up);
                }
            }
        });

        //For investment
        rl_investment_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ll_contant_investment.isShown()) {
                    Utils.slide_up(MaxPropertyGroupDetailActivity.this, ll_contant_investment);
                    tv_arrow_investment_plan.setBackgroundResource(R.drawable.ic_arrow_down);
                    ll_contant_investment.setVisibility(View.GONE);
                } else {
                    ll_contant_investment.setVisibility(View.VISIBLE);
                    Utils.slide_down(MaxPropertyGroupDetailActivity.this, ll_contant_investment);
                    tv_arrow_investment_plan.setBackgroundResource(R.drawable.ic_arrow_up);
                }
            }
        });
        //For risk
        rl_risk_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ll_contant_risk.isShown()) {
                    Utils.slide_up(MaxPropertyGroupDetailActivity.this, ll_contant_risk);
                    tv_arrow_risk.setBackgroundResource(R.drawable.ic_arrow_down);
                    ll_contant_risk.setVisibility(View.GONE);

                } else {
                    ll_contant_risk.setVisibility(View.VISIBLE);
                    Utils.slide_down(MaxPropertyGroupDetailActivity.this, ll_contant_risk);
                    tv_arrow_risk.setBackgroundResource(R.drawable.ic_arrow_up);
                }
            }
        });
        //For document
        rl_document_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ll_contant_document.isShown()) {
                    Utils.slide_up(MaxPropertyGroupDetailActivity.this, ll_contant_document);
                    tv_arrow_document.setBackgroundResource(R.drawable.ic_arrow_down);
                    ll_contant_document.setVisibility(View.GONE);
                } else {
                    ll_contant_document.setVisibility(View.VISIBLE);
                    Utils.slide_down(MaxPropertyGroupDetailActivity.this, ll_contant_document);
                    tv_arrow_document.setBackgroundResource(R.drawable.ic_arrow_up);
                }
            }
        });

        //For last invest
        rl_last_invest_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ll_contant_last_invest.isShown()) {
                    Utils.slide_up(MaxPropertyGroupDetailActivity.this, ll_contant_last_invest);
                    tv_arrow_last_investment.setBackgroundResource(R.drawable.ic_arrow_down);
                    ll_contant_last_invest.setVisibility(View.GONE);
                } else {
                    ll_contant_last_invest.setVisibility(View.VISIBLE);
                    Utils.slide_down(MaxPropertyGroupDetailActivity.this, ll_contant_last_invest);
                    tv_arrow_last_investment.setBackgroundResource(R.drawable.ic_arrow_up);
                }
            }
        });

        if (Utils.isInternetConnected(getApplicationContext())) {
            Log.d(TAG, "init: " + "Calling My Investment");
            getInvestedDetailsList();
        } else {
            showToast(MaxPropertyGroupDetailActivity.this, getResources().getString(R.string.oops_connect_your_internet));
        }
    }

    private void getInvestedDetailsList() {
        Utils.hideKeyboard(this);
        pd = ProgressDialog.show(MaxPropertyGroupDetailActivity.this, "Please Wait...");
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, GER_MY_INVEST_DETAILS, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        Log.d(TAG, "onResponse: " + response);

                        JSONObject jsonObjMain = new JSONObject(response.toString());
                        if (jsonObjMain != null) {
                            JSONObject jsonObjDetails = jsonObjMain.getJSONObject("details");
                            String currency_symbol = jsonObjDetails.getString("currency_symbol");
                            int filled = jsonObjDetails.getInt("filled");
                            String location_flag_img = jsonObjDetails.getString("location_flag_img");
                            String main_img = jsonObjDetails.getString("main_img");
                            String currency = jsonObjDetails.getString("currency");
                            tv_mTittle.setText(jsonObjDetails.getString("title"));
                            tv_interest_pr.setText(jsonObjDetails.getString("interest_pa") + "%");
                            tv_risk_c.setText(jsonObjDetails.getString("risk_class"));
                            tv_cur_symbol_amt.setText(currency_symbol);
                            tvAmount.setText(String.valueOf(jsonObjDetails.getInt("amount")));
                            tv_filled.setText(String.valueOf(filled) + "%");
                            tv_investors.setText(String.valueOf(jsonObjDetails.getString("no_of_investors")));
                            tv_currency_left_amt.setText(currency_symbol);
                            tv_left_amount.setText(String.valueOf(jsonObjDetails.getInt("amount_left")));
                            tv_months.setText(String.valueOf(jsonObjDetails.getInt("months")));
                            tvType.setText(jsonObjDetails.getString("type"));
                            tv_location.setText(jsonObjDetails.getString("location"));
                            progessBar.setProgress(filled);

                            try {
                                if (main_img != null && !main_img.isEmpty() && !main_img.equals("null")) {
                                    Glide.with(MaxPropertyGroupDetailActivity.this).load(main_img)
                                            .thumbnail(0.5f)
                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                            .into(iv_main_ing);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            JSONArray other_photos_videos = jsonObjDetails.getJSONArray("other_photos_videos");
                            photosVideosList.clear();
                            photosVideosList_f.clear();

                            if (other_photos_videos.length() > 0) {
                                for (int i = 0; i < other_photos_videos.length(); i++) {
                                    PhotosVideosModel photosVideosModel = new PhotosVideosModel();
                                    photosVideosModel.setType(other_photos_videos.getJSONObject(i).getString("type"));
                                    photosVideosModel.setUrl(other_photos_videos.getJSONObject(i).getString("url"));
                                    photosVideosList.add(photosVideosModel);
                                }
                            }

                            if (photosVideosList.size() > 0) {
                                PhotosVideosModel model = new PhotosVideosModel();
                                model.setUrl(main_img);
                                model.setType("photo");
                                photosVideosList_f.add(model);
                                photosVideosList_f.addAll(photosVideosList);

                                recyclerView.setVisibility(View.VISIBLE);
                                tvNoRecord.setVisibility(View.GONE);
                                adapter = new MyPhotoAdapter(MaxPropertyGroupDetailActivity.this, photosVideosList_f, MaxPropertyGroupDetailActivity.this);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MaxPropertyGroupDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            } else {
                                recyclerView.setVisibility(View.GONE);
                                tvNoRecord.setVisibility(View.VISIBLE);
                            }
                            //For Summary data
                            JSONObject jsonObjSummary = jsonObjDetails.getJSONObject("summary");
                            tv_summary.setText(jsonObjSummary.getString("heading"));

                            // set Text in TextView using fromHtml() method with version check
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                txt_summary_content.setText(Html.fromHtml(jsonObjSummary.getString("data")));
                                Log.d(TAG, "onResponse: " + "><><>" + Html.fromHtml(jsonObjSummary.getString("heading")));
                            } else {
                                txt_summary_content.setText(Html.fromHtml(jsonObjSummary.getString("data")));
                            }
                            Log.d(TAG, "onResponse: " + "><><>" + Html.fromHtml(jsonObjSummary.getString("data")));
                            //For loan_terms
                            JSONObject jsonObjLoanTerms = jsonObjDetails.getJSONObject("loan_terms");
                            tv_loan_terms.setText(jsonObjLoanTerms.getString("heading"));
                            JSONArray loanTermArray = jsonObjLoanTerms.getJSONArray("data");
                            loanTermList.clear();
                            if (loanTermArray.length() > 0) {
                                for (int i = 0; i < loanTermArray.length(); i++) {
                                    LoanTermModel loanTermModel = new LoanTermModel();
                                    loanTermModel.setmLoanTermTitle(loanTermArray.getJSONObject(i).getString("title"));
                                    loanTermModel.setmLoanTermValue(loanTermArray.getJSONObject(i).getString("value"));
                                    loanTermList.add(loanTermModel);
                                }
                            }
                            if (loanTermList.size() > 0) {
                                recyViewLoanTerm.setVisibility(View.VISIBLE);
                                tvNoRecord_loan_term.setVisibility(View.GONE);
                                loanTermAdapter = new LoanTermAdapter(MaxPropertyGroupDetailActivity.this, loanTermList);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MaxPropertyGroupDetailActivity.this, LinearLayoutManager.VERTICAL, false);
                                recyViewLoanTerm.setLayoutManager(mLayoutManager);
                                recyViewLoanTerm.setItemAnimator(new DefaultItemAnimator());
                                recyViewLoanTerm.setAdapter(loanTermAdapter);
                                loanTermAdapter.notifyDataSetChanged();
                            } else {
                                recyViewLoanTerm.setVisibility(View.GONE);
                                tvNoRecord_loan_term.setVisibility(View.VISIBLE);
                            }
                            //For collateral
                            JSONObject jsonObjCollateral = jsonObjDetails.getJSONObject("collateral");
                            tv_collateral.setText(jsonObjCollateral.getString("heading"));
                            JSONArray collateralArray = jsonObjCollateral.getJSONArray("data");

                            collateralListFirst.clear();
                            collateralListSecond.clear();

                            if (collateralArray.length() > 0) {
                                for (int i = 0; i < collateralArray.length(); i++) {
                                    CollateralModelP collateralModel = new CollateralModelP(collateralArray.getJSONObject(i).getString("heading"));
                                    JSONArray jsonArraySub = collateralArray.getJSONObject(i).getJSONArray("data");
                                    collateralListFirst.add(collateralModel);
                                    collateralListSecond.clear();
                                    for (int j = 0; j < jsonArraySub.length(); j++) {
                                        CollateralModelP collateralModelCh = new CollateralModelP(jsonArraySub.getJSONObject(j).getString("title"), jsonArraySub.getJSONObject(j).getString("value"));
                                        collateralListSecond.add(collateralModelCh);
                                    }
                                    collateralListFirst.addAll(collateralListSecond);
                                }
                            }

                            if (collateralListFirst.size() > 0) {
                                recyViewCollateral.setVisibility(View.VISIBLE);
                                tvNoRecord_Collateral.setVisibility(View.GONE);
                                collateralAdapter = new CollateralAdapter(MaxPropertyGroupDetailActivity.this, collateralListFirst);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MaxPropertyGroupDetailActivity.this, LinearLayoutManager.VERTICAL, false);
                                recyViewCollateral.setLayoutManager(mLayoutManager);
                                recyViewCollateral.setItemAnimator(new DefaultItemAnimator());
                                recyViewCollateral.setAdapter(collateralAdapter);
                                collateralAdapter.notifyDataSetChanged();
                            } else {
                                recyViewCollateral.setVisibility(View.GONE);
                                tvNoRecord_Collateral.setVisibility(View.VISIBLE);
                            }
                            //For FoundRaiser
                            try {
                                JSONObject foundraiser = jsonObjDetails.getJSONObject("fundraiser");
                                tv_fundraiser.setText(foundraiser.getString("heading"));
                                JSONObject jsonObject = foundraiser.getJSONObject("data");
                                String logoImg = jsonObject.getString("logo");
                                try {
                                    if (logoImg != null && !logoImg.isEmpty() && !logoImg.equals("null")) {
                                        Glide.with(MaxPropertyGroupDetailActivity.this).load(logoImg)
                                                .thumbnail(0.5f)
                                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                .into(iv_logo);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                JSONArray foundraiserArray = jsonObject.getJSONArray("data");
                                fundraiserModelList.clear();
                                if (foundraiserArray.length() > 0) {
                                    for (int i = 0; i < foundraiserArray.length(); i++) {
                                        FundraiserModel foundraiserModel = new FundraiserModel(foundraiserArray.getJSONObject(i).getString("title"), foundraiserArray.getJSONObject(i).getString("value"));
                                        fundraiserModelList.add(foundraiserModel);
                                    }
                                }

                                if (fundraiserModelList.size() > 0) {
                                    recyViewFoundRaiser.setVisibility(View.VISIBLE);
                                    tvNoRecord_foundRaiser.setVisibility(View.GONE);
                                    fundraiserAdapter = new FundraiserAdapter(MaxPropertyGroupDetailActivity.this, fundraiserModelList);
                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MaxPropertyGroupDetailActivity.this, LinearLayoutManager.VERTICAL, false);
                                    recyViewFoundRaiser.setLayoutManager(mLayoutManager);
                                    recyViewFoundRaiser.setItemAnimator(new DefaultItemAnimator());
                                    recyViewFoundRaiser.setAdapter(fundraiserAdapter);
                                    fundraiserAdapter.notifyDataSetChanged();
                                } else {
                                    recyViewFoundRaiser.setVisibility(View.GONE);
                                    tvNoRecord_foundRaiser.setVisibility(View.VISIBLE);
                                }

                                JSONObject additionalInfo = jsonObject.getJSONObject("additional");
                                tv_addnal_info_tittle.setText(additionalInfo.getString("title"));
                                // tv_addnal_content.setText(additionalInfo.getString("value"));/
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    tv_addnal_content.setText(Html.fromHtml(additionalInfo.getString("value")));
                                    Log.d(TAG, "onResponse: " + "><><>" + Html.fromHtml(additionalInfo.getString("value")));
                                } else {
                                    tv_addnal_content.setText(Html.fromHtml(additionalInfo.getString("value")));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            //For Risk
                            JSONObject jsonObjRisk = jsonObjDetails.getJSONObject("risk");
                            tv_rik.setText(jsonObjRisk.getString("heading"));
                            JSONArray riskArray = jsonObjRisk.getJSONArray("data");
                            riskList.clear();
                            if (riskArray.length() > 0) {
                                for (int i = 0; i < riskArray.length(); i++) {
                                    RiskModel riskModel = new RiskModel();
                                    riskModel.setmRiskTitle(riskArray.getJSONObject(i).getJSONObject("risk").getString("title"));
                                    riskModel.setmRiskValue(riskArray.getJSONObject(i).getJSONObject("risk").getString("value"));
                                    riskModel.setmScoreTitle(riskArray.getJSONObject(i).getJSONObject("score").getString("title"));
                                    riskModel.setmScoreValue(riskArray.getJSONObject(i).getJSONObject("score").getString("value"));
                                    riskModel.setmMeasureTitle(riskArray.getJSONObject(i).getJSONObject("measure").getString("title"));
                                    riskModel.setmMeasureValue(riskArray.getJSONObject(i).getJSONObject("measure").getString("value"));
                                    riskList.add(riskModel);
                                }
                            }

                            if (riskList.size() > 0) {
                                recyViewRisk.setVisibility(View.VISIBLE);
                                tvNoRecord_Risk.setVisibility(View.GONE);
                                riskAdapter = new RiskAdapter(MaxPropertyGroupDetailActivity.this, riskList);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MaxPropertyGroupDetailActivity.this, LinearLayoutManager.VERTICAL, false);
                                recyViewRisk.setLayoutManager(mLayoutManager);
                                recyViewRisk.setItemAnimator(new DefaultItemAnimator());
                                recyViewRisk.setAdapter(riskAdapter);
                                riskAdapter.notifyDataSetChanged();
                            } else {
                                recyViewRisk.setVisibility(View.GONE);
                                tvNoRecord_Risk.setVisibility(View.VISIBLE);
                            }

                            //For Investment plan
                            JSONObject jsonObjinvestment_plan = jsonObjDetails.getJSONObject("investment_plan");
                            tv_investment_plan.setText(jsonObjinvestment_plan.getString("heading"));
                            JSONArray investment_planArray = jsonObjinvestment_plan.getJSONArray("data");
                            investmentPlanList.clear();
                            if (investment_planArray.length() > 0) {
                                for (int i = 0; i < investment_planArray.length(); i++) {
                                    InvestmentPlanModel investmentPlanModel = new InvestmentPlanModel();
                                    investmentPlanModel.setmInvestmentPlanTitle(investment_planArray.getJSONObject(i).getString("title"));
                                    investmentPlanModel.setmInvestmentPlanValue(investment_planArray.getJSONObject(i).getString("value"));
                                    investmentPlanList.add(investmentPlanModel);
                                }
                            }

                            if (investmentPlanList.size() > 0) {
                                recyViewInvestment.setVisibility(View.VISIBLE);
                                tvNoRecord_investment.setVisibility(View.GONE);
                                investmentPlanAdapter = new InvestmentPlanAdapter(MaxPropertyGroupDetailActivity.this, investmentPlanList);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MaxPropertyGroupDetailActivity.this, LinearLayoutManager.VERTICAL, false);
                                recyViewInvestment.setLayoutManager(mLayoutManager);
                                recyViewInvestment.setItemAnimator(new DefaultItemAnimator());
                                recyViewInvestment.setAdapter(investmentPlanAdapter);
                                investmentPlanAdapter.notifyDataSetChanged();
                            } else {
                                recyViewInvestment.setVisibility(View.GONE);
                                tvNoRecord_investment.setVisibility(View.VISIBLE);
                            }

                            //For Document
                            JSONObject jsonObjDocument = jsonObjDetails.getJSONObject("documents");
                            tv_document.setText(jsonObjDocument.getString("heading"));
                            JSONArray documentArray = jsonObjDocument.getJSONArray("data");
                            documentList.clear();
                            if (documentArray.length() > 0) {
                                for (int i = 0; i < documentArray.length(); i++) {
                                    DocumentModel documentModel = new DocumentModel(documentArray.getJSONObject(i).getString("title"),
                                            documentArray.getJSONObject(i).getString("url"));
                                    documentList.add(documentModel);
                                }
                            }
                            if (documentList.size() > 0) {
                                recyViewDocument.setVisibility(View.VISIBLE);
                                tvNoRecord_Document.setVisibility(View.GONE);
                                documentAdapter = new DocumentAdapter(getApplicationContext(), MaxPropertyGroupDetailActivity.this, documentList);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MaxPropertyGroupDetailActivity.this, LinearLayoutManager.VERTICAL, false);
                                recyViewDocument.setLayoutManager(mLayoutManager);
                                recyViewDocument.setItemAnimator(new DefaultItemAnimator());
                                recyViewDocument.setAdapter(documentAdapter);
                                documentAdapter.notifyDataSetChanged();
                            } else {
                                recyViewDocument.setVisibility(View.GONE);
                                tvNoRecord_Document.setVisibility(View.VISIBLE);
                            }

                            //For last Investment
                            JSONObject jsonObjlastInvestment = jsonObjDetails.getJSONObject("last_investment");
                            tv_last_investment.setText(jsonObjlastInvestment.getString("heading"));
                            JSONArray lastInvestmentArray = jsonObjlastInvestment.getJSONArray("data");
                            lastInvestmentList.clear();
                            if (lastInvestmentArray.length() > 0) {
                                for (int i = 0; i < lastInvestmentArray.length(); i++) {
                                    LastInvestmentModel lastInvestmentModel = new LastInvestmentModel(lastInvestmentArray.getJSONObject(i).getString("data"));
                                    lastInvestmentList.add(lastInvestmentModel);
                                }
                            }
                            if (lastInvestmentList.size() > 0) {
                                recyViewLastInvestment.setVisibility(View.VISIBLE);
                                tvNoRecord_LastInvestment.setVisibility(View.GONE);
                                lastInvestmentAdapter = new LastInvestmentAdapter(MaxPropertyGroupDetailActivity.this, lastInvestmentList);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MaxPropertyGroupDetailActivity.this, LinearLayoutManager.VERTICAL, false);
                                recyViewLastInvestment.setLayoutManager(mLayoutManager);
                                recyViewLastInvestment.setItemAnimator(new DefaultItemAnimator());
                                recyViewLastInvestment.setAdapter(lastInvestmentAdapter);
                                lastInvestmentAdapter.notifyDataSetChanged();
                            } else {
                                recyViewLastInvestment.setVisibility(View.GONE);
                                tvNoRecord_LastInvestment.setVisibility(View.VISIBLE);
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    //showToast(MyInvestmentDetailActivity.this, getResources().getString(R.string.something_went));
                    String json = null;
                    String Message;
                    NetworkResponse response = error.networkResponse;
                    if (response != null && response.data != null) {
                        try {
                            JSONObject errorObj = new JSONObject(new String(response.data));
                            if (response.statusCode == 400 || response.statusCode == 405 || response.statusCode == 500) {
                                showToast(MaxPropertyGroupDetailActivity.this, getResources().getString(R.string.something_went));
                            } else if (response.statusCode == 401) {

                            } else if (response.statusCode == 422) {
                                //  json = trimMessage(new String(response.data));
                                if (json != "" && json != null) {
                                    // displayMessage(json);
                                } else {
                                    showToast(MaxPropertyGroupDetailActivity.this, getResources().getString(R.string.please_try_again));
                                }
                            } else if (response.statusCode == 503) {
                                showToast(MaxPropertyGroupDetailActivity.this, getResources().getString(R.string.server_down));
                            } else {
                                showToast(MaxPropertyGroupDetailActivity.this, getResources().getString(R.string.please_try_again));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (error instanceof NoConnectionError) {
                            showToast(MaxPropertyGroupDetailActivity.this, getResources().getString(R.string.oops_connect_your_internet));
                        } else if (error instanceof NetworkError) {
                            showToast(MaxPropertyGroupDetailActivity.this, getResources().getString(R.string.oops_connect_your_internet));
                        } else if (error instanceof TimeoutError) {
                            try {
                                if (error.networkResponse == null) {
                                    if (error.getClass().equals(TimeoutError.class)) {
                                        // Show timeout error message
                                        showToast(MaxPropertyGroupDetailActivity.this, getResources().getString(R.string.timed_out));
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (error instanceof AuthFailureError) {
                            Log.d(TAG, "onErrorResponse: " + "AuthFailureError" + AuthFailureError.class);
                        } else if (error instanceof ServerError) {
                            Log.d(TAG, "onErrorResponse: " + "ServerError" + ServerError.class);
                            //Indicates that the server responded with a error response
                        } else if (error instanceof ParseError) {
                            Log.d(TAG, "onErrorResponse: " + "ParseError" + ParseError.class);
                            // Indicates that the server response could not be parsed
                        }
                    }
                    error.printStackTrace();
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json";
                }
            };
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(jsonObjectRequest);
        } catch (Error e) {
            if (pd != null && pd.isShowing()) {
                pd.dismiss();
            }
            e.printStackTrace();
        } catch (Exception e) {
            if (pd != null && pd.isShowing()) {
                pd.dismiss();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    @Override
    public void onCustomClick(String imageData) {
        try {
            if (imageData != null && !imageData.isEmpty() && !imageData.equals("null")) {
                Glide.with(MaxPropertyGroupDetailActivity.this).load(imageData)
                        .thumbnail(0.5f)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(iv_main_ing);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMyDownloadClick(String mdownload) {
        mdownloadFile = mdownload;
        if (mdownloadFile != null) {
            if (Utils.isInternetConnected(getApplicationContext())) {
                if (Utils.checkRequestPermiss(getApplicationContext(), MaxPropertyGroupDetailActivity.this)) {
                    doPermissionGranted();
                }
            } else {
                showToast(MaxPropertyGroupDetailActivity.this, getResources().getString(R.string.oops_connect_your_internet));
            }
        }
        /* if (isInternetConnected(mContext)) {


                    list.clear();
                    downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
                    Download_Uri = Uri.parse(arrayList.get(position).getmDocumentUrl());
                    DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                    request.setAllowedOverRoaming(false);
                    request.setTitle("GadgetSaint Downloading " + "Sample" + ".png");
                    request.setDescription("Downloading " + "Sample" + ".png");
                    request.setVisibleInDownloadsUi(true);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/GadgetSaint/" + "/" + "Sample" + ".png");
                    refid = downloadManager.enqueue(request);
                    list.add(refid);

                    Log.d(TAG, "onClick: " + "><refid><" + refid);
                    Log.d(TAG, "onClick: " + "><size><" + String.valueOf(list));
                } else {
                    showToast(mActivity, mContext.getResources().getString(R.string.oops_connect_your_internet));
                }*/
    }


    private void doPermissionGranted() {
        Log.d(TAG, "onMyDownloadClick: " + "><><" + mdownloadFile);
      //  new DownloadingTask().execute();

        //////////////////////////
        // Toast.makeText(this, "Download....", Toast.LENGTH_SHORT).show();
        //File file=new File(getExternalFilesDir(null),"Dummy");
        /*File file=new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)));
        Log.d(TAG, "doPermissionGranted: "+"file-------"+file);
        DownloadManager.Request request= null;// Set if download is allowed on roaming network
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            request = new DownloadManager.Request(Uri.parse(mdownloadFile))
                    .setTitle("File")// Title of the Download Notification
                    .setDescription("Downloading")// Description of the Download Notification
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)// Visibility of the download Notification
                    .setDestinationUri(Uri.fromFile(file))// Uri of the destination file
                    .setRequiresCharging(false)// Set if charging is required to begin the download
                    .setAllowedOverMetered(true)// Set if download is allowed on Mobile network
                    .setAllowedOverRoaming(true);
        }
        DownloadManager downloadManager= (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        downloadID = downloadManager.enqueue(request);// enqueue puts the download request in the queue.
*/
    }


    private class DownloadingTask extends AsyncTask<Void, Void, Void> {
        File apkStorage = null;
        File outputFile = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = ProgressDialog.show(MaxPropertyGroupDetailActivity.this, "Please Wait...");
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                pd.dismiss();
                URL url = new URL(mdownloadFile);//Create Download URl
                HttpURLConnection c = (HttpURLConnection) url.openConnection();//Open Url Connection
                c.setRequestMethod("GET");//Set Request Method to "GET" since we are grtting data
                c.connect();//connect the URL Connection
                //If Connection response is not OK then show Logs
                if (c.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    Log.e(TAG, "Server returned HTTP " + c.getResponseCode() + " " + c.getResponseMessage());
                }

                //Get File if SD card is present
                if (new CheckForSDCard().isSDCardPresent()) {
                    apkStorage = new File(Environment.getExternalStorageDirectory() + "/"+ APIUrl.downloadDirectory);
                } else
                    showToast(MaxPropertyGroupDetailActivity.this,getResources().getString(R.string.noSdcard));

                //If File is not present create directory
                if (!apkStorage.exists()) {
                    apkStorage.mkdir();
                    Log.e(TAG, "Directory Created.");
                }
                outputFile = new File(apkStorage, mdownloadFile);//Create Output file in Main File
                //Create New File if not present
                if (!outputFile.exists()) {
                    outputFile.createNewFile();
                    Log.e(TAG, "File Created");
                }

                FileOutputStream fos = new FileOutputStream(outputFile);//Get OutputStream for NewFile Location
                InputStream is = c.getInputStream();//Get InputStream for connection
                byte[] buffer = new byte[1024];//Set buffer type
                int len1 = 0;//init length
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);//Write new file
                }
                //Close all connection after doing task
                fos.close();
                is.close();

            } catch (Exception e) {
                //Read exception if something went wrong
                e.printStackTrace();
                outputFile = null;
                Log.e(TAG, "Download Error Exception " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d(TAG, "onPostExecute: "+"><result><"+result);
            pd.dismiss();
            try {
                if (outputFile != null) {
                    //  buttonText.setEnabled(true);
                    //  buttonText.setText(R.string.downloadCompleted);//If Download completed then change button text
                } else {
                    // buttonText.setText(R.string.downloadFailed);//If download failed change button text
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //  buttonText.setEnabled(true);
                            // buttonText.setText(R.string.downloadAgain);//Change button text again after 3sec
                            showToast(MaxPropertyGroupDetailActivity.this,getResources().getString(R.string.downloadAgain));
                        }
                    }, 3000);
                    Log.e(TAG, "Download Failed");
                }
            } catch (Exception e) {
                e.printStackTrace();
                //Change button text if exception occurs
                // buttonText.setText(R.string.downloadFailed);
                showToast(MaxPropertyGroupDetailActivity.this,getResources().getString(R.string.downloadFailed));

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // buttonText.setEnabled(true);
                        //buttonText.setText(R.string.downloadAgain);
                        showToast(MaxPropertyGroupDetailActivity.this,getResources().getString(R.string.downloadAgain));
                    }
                }, 3000);
                Log.e(TAG, "Download Failed with Exception - " + e.getLocalizedMessage());

            }

            super.onPostExecute(result);
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<>();
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);

                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "CAMERA  permission granted");
                        doPermissionGranted();
                    } else {
                        Log.d(TAG, "Camera Permission are not granted ask again ");
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            showDialogOK("Camera Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    Utils.checkRequestPermiss(getApplicationContext(), MaxPropertyGroupDetailActivity.this);
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        } else {
                            explain("Go to settings and enable permissions");
                        }
                    }
                }
            }
        }
    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    private void explain(String msg) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(msg)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("com.auxesis.maxcrowdfund")));
                        // startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        finish();
                    }
                });
        dialog.show();
    }

}
