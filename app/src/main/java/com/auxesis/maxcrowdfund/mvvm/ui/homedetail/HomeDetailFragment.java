package com.auxesis.maxcrowdfund.mvvm.ui.homedetail;


import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.auxesis.maxcrowdfund.mvvm.activity.HomeActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import static com.auxesis.maxcrowdfund.constant.APIUrl.GER_MY_INVEST_DETAILS;
import static com.auxesis.maxcrowdfund.constant.Utils.showToast;

public class HomeDetailFragment extends Fragment implements OnCustomClickListener, OnDownloadClickListener {
    private static final String TAG = "HomeDetailFragment";
    TextView tv_mTittle, tv_interest_pr, tv_risk_c, tv_currency_left_amt, tv_cur_symbol_amt, tvAmount, tv_filled, tv_investors,
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home_detail, container, false);

        //For header max Tittle
        tv_mTittle = root.findViewById(R.id.tv_mTittle);
        tv_interest_pr = root.findViewById(R.id.tv_interest_pr);
        tv_risk_c = root.findViewById(R.id.tv_risk_c);
        tv_currency_left_amt = root.findViewById(R.id.tv_currency_left_amt);
        tv_cur_symbol_amt = root.findViewById(R.id.tv_cur_symbol_amt);
        tvAmount = root.findViewById(R.id.tvAmount);
        tv_filled = root.findViewById(R.id.tv_filled);
        tv_investors = root.findViewById(R.id.tv_investors);
        tv_left_amount = root.findViewById(R.id.tv_left_amount);
        tv_left = root.findViewById(R.id.tv_left);
        tv_months = root.findViewById(R.id.tv_months);
        tvType = root.findViewById(R.id.tvType);
        tv_location = root.findViewById(R.id.tv_location);
        progessBar = root.findViewById(R.id.progessBar);
        iv_main_ing = root.findViewById(R.id.iv_main_ing);

        //For photo
        recyclerView = root.findViewById(R.id.recyclerView);
        tvNoRecord = root.findViewById(R.id.tvNoRecord);

        //For Summary data
        tv_arrow_summary = root.findViewById(R.id.tv_arrow_summary);
        tv_summary = root.findViewById(R.id.tv_summary);
        txt_summary_content = root.findViewById(R.id.txt_summary_content);

        //For Loan Term
        tv_arrow_loan_terms = root.findViewById(R.id.tv_arrow_loan_terms);
        tv_loan_terms = root.findViewById(R.id.tv_loan_terms);
        tvNoRecord_loan_term = root.findViewById(R.id.tvNoRecord_loan_term);
        recyViewLoanTerm = root.findViewById(R.id.recyViewLoanTerm);

        //For FoundRaiser
        iv_logo = root.findViewById(R.id.iv_logo);
        tv_fundraiser = root.findViewById(R.id.tv_fundraiser);
        recyViewFoundRaiser = root.findViewById(R.id.recyViewFoundRaiser);
        tvNoRecord_foundRaiser = root.findViewById(R.id.tvNoRecord_foundRaiser);
        tv_addnal_info_tittle = root.findViewById(R.id.tv_addnal_info_tittle);
        tv_addnal_content = root.findViewById(R.id.tv_addnal_content);

        //For Risk
        tv_rik = root.findViewById(R.id.tv_rik);
        tvNoRecord_Risk = root.findViewById(R.id.tvNoRecord_Risk);
        recyViewRisk = root.findViewById(R.id.recyViewRisk);

        //For Investment Plan
        tv_arrow_investment_plan = root.findViewById(R.id.tv_arrow_investment_plan);
        tv_investment_plan = root.findViewById(R.id.tv_investment_plan);
        tvNoRecord_investment = root.findViewById(R.id.tvNoRecord_investment);
        recyViewInvestment = root.findViewById(R.id.recyViewInvestment);
        btn_investment_plan = root.findViewById(R.id.btn_investment_plan);

        //for colletral
        tv_arrow_collateral = root.findViewById(R.id.tv_arrow_collateral);
        tv_collateral = root.findViewById(R.id.tv_collateral);
        tvNoRecord_Collateral = root.findViewById(R.id.tvNoRecord_Collateral);
        recyViewCollateral = root.findViewById(R.id.recyViewCollateral);

        //For Document
        recyViewDocument = root.findViewById(R.id.recyViewDocument);
        tvNoRecord_Document = root.findViewById(R.id.tvNoRecord_Document);
        tv_document = root.findViewById(R.id.tv_document);

        //For Last investment
        recyViewLastInvestment = root.findViewById(R.id.recyViewLastInvestment);
        tvNoRecord_LastInvestment = root.findViewById(R.id.tvNoRecord_LastInvestment);
        tv_last_investment = root.findViewById(R.id.tv_last_investment);

        //For All Arrow
        tv_arrow_fundraiser = root.findViewById(R.id.tv_arrow_fundraiser);

        tv_arrow_risk = root.findViewById(R.id.tv_arrow_risk);
        tv_arrow_document = root.findViewById(R.id.tv_arrow_document);
        tv_arrow_last_investment = root.findViewById(R.id.tv_arrow_last_investment);

        ll_contant_summary = root.findViewById(R.id.ll_contant_summary);
        ll_contant_loan_terms = root.findViewById(R.id.ll_contant_loan_terms);
        ll_contant_collateral = root.findViewById(R.id.ll_contant_collateral);
        ll_contant_fundraiser = root.findViewById(R.id.ll_contant_fundraiser);
        ll_contant_investment = root.findViewById(R.id.ll_contant_investment);
        ll_contant_risk = root.findViewById(R.id.ll_contant_risk);
        ll_contant_document = root.findViewById(R.id.ll_contant_document);
        ll_contant_last_invest = root.findViewById(R.id.ll_contant_last_invest);

        rl_summary_for = root.findViewById(R.id.rl_summary_for);
        rl_loan_term_click = root.findViewById(R.id.rl_loan_term_click);
        rl_collateral_click = root.findViewById(R.id.rl_collateral_click);
        rl_foundaiser_click = root.findViewById(R.id.rl_foundaiser_click);
        rl_investment_click = root.findViewById(R.id.rl_investment_click);
        rl_risk_click = root.findViewById(R.id.rl_risk_click);
        rl_document_click = root.findViewById(R.id.rl_document_click);
        rl_last_invest_click = root.findViewById(R.id.rl_last_invest_click);

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
                    Utils.slide_up(getActivity(), ll_contant_summary);
                    tv_arrow_summary.setBackgroundResource(R.drawable.ic_arrow_down);
                    ll_contant_summary.setVisibility(View.GONE);
                } else {
                    ll_contant_summary.setVisibility(View.VISIBLE);
                    Utils.slide_down(getActivity(), ll_contant_summary);
                    tv_arrow_summary.setBackgroundResource(R.drawable.ic_arrow_up);
                }
            }
        });

        //For Loan terms
        rl_loan_term_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ll_contant_loan_terms.isShown()) {
                    Utils.slide_up(getActivity(), ll_contant_loan_terms);
                    tv_arrow_loan_terms.setBackgroundResource(R.drawable.ic_arrow_down);
                    ll_contant_loan_terms.setVisibility(View.GONE);
                } else {
                    ll_contant_loan_terms.setVisibility(View.VISIBLE);
                    Utils.slide_down(getActivity(), ll_contant_loan_terms);
                    tv_arrow_loan_terms.setBackgroundResource(R.drawable.ic_arrow_up);
                }
            }
        });
        //For colla
        rl_collateral_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ll_contant_collateral.isShown()) {
                    Utils.slide_up(getActivity(), ll_contant_collateral);
                    tv_arrow_collateral.setBackgroundResource(R.drawable.ic_arrow_down);
                    ll_contant_collateral.setVisibility(View.GONE);
                } else {
                    ll_contant_collateral.setVisibility(View.VISIBLE);
                    Utils.slide_down(getActivity(), ll_contant_collateral);
                    tv_arrow_collateral.setBackgroundResource(R.drawable.ic_arrow_up);
                }
            }
        });

        //For fundraiser
        rl_foundaiser_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ll_contant_fundraiser.isShown()) {
                    Utils.slide_up(getActivity(), ll_contant_fundraiser);
                    tv_arrow_fundraiser.setBackgroundResource(R.drawable.ic_arrow_down);
                    ll_contant_fundraiser.setVisibility(View.GONE);
                } else {
                    ll_contant_fundraiser.setVisibility(View.VISIBLE);
                    Utils.slide_down(getActivity(), ll_contant_fundraiser);
                    tv_arrow_fundraiser.setBackgroundResource(R.drawable.ic_arrow_up);
                }
            }
        });

        //For investment
        rl_investment_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ll_contant_investment.isShown()) {
                    Utils.slide_up(getActivity(), ll_contant_investment);
                    tv_arrow_investment_plan.setBackgroundResource(R.drawable.ic_arrow_down);
                    ll_contant_investment.setVisibility(View.GONE);
                } else {
                    ll_contant_investment.setVisibility(View.VISIBLE);
                    Utils.slide_down(getActivity(), ll_contant_investment);
                    tv_arrow_investment_plan.setBackgroundResource(R.drawable.ic_arrow_up);
                }
            }
        });
        //For risk
        rl_risk_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ll_contant_risk.isShown()) {
                    Utils.slide_up(getActivity(), ll_contant_risk);
                    tv_arrow_risk.setBackgroundResource(R.drawable.ic_arrow_down);
                    ll_contant_risk.setVisibility(View.GONE);
                } else {
                    ll_contant_risk.setVisibility(View.VISIBLE);
                    Utils.slide_down(getActivity(), ll_contant_risk);
                    tv_arrow_risk.setBackgroundResource(R.drawable.ic_arrow_up);
                }
            }
        });
        //For document
        rl_document_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ll_contant_document.isShown()) {
                    Utils.slide_up(getActivity(), ll_contant_document);
                    tv_arrow_document.setBackgroundResource(R.drawable.ic_arrow_down);
                    ll_contant_document.setVisibility(View.GONE);
                } else {
                    ll_contant_document.setVisibility(View.VISIBLE);
                    Utils.slide_down(getActivity(), ll_contant_document);
                    tv_arrow_document.setBackgroundResource(R.drawable.ic_arrow_up);
                }
            }
        });

        //For last invest
        rl_last_invest_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ll_contant_last_invest.isShown()) {
                    Utils.slide_up(getActivity(), ll_contant_last_invest);
                    tv_arrow_last_investment.setBackgroundResource(R.drawable.ic_arrow_down);
                    ll_contant_last_invest.setVisibility(View.GONE);
                } else {
                    ll_contant_last_invest.setVisibility(View.VISIBLE);
                    Utils.slide_down(getActivity(), ll_contant_last_invest);
                    tv_arrow_last_investment.setBackgroundResource(R.drawable.ic_arrow_up);
                }
            }
        });

        if (Utils.isInternetConnected(getActivity())) {
            Log.d(TAG, "init: " + "Calling My Investment");
            getInvestedDetailsList();
        } else {
            showToast(getActivity(), getResources().getString(R.string.oops_connect_your_internet));
        }

        return root;
    }
    private void getInvestedDetailsList() {
        pd = ProgressDialog.show(getActivity(), "Please Wait...");
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
                                    Glide.with(getActivity()).load(main_img)
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
                                adapter = new MyPhotoAdapter(getActivity(), photosVideosList_f, HomeDetailFragment.this);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
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
                                loanTermAdapter = new LoanTermAdapter(getActivity(), loanTermList);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
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
                                collateralAdapter = new CollateralAdapter(getActivity(), collateralListFirst);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
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
                                        Glide.with(getActivity()).load(logoImg)
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
                                    fundraiserAdapter = new FundraiserAdapter(getActivity(), fundraiserModelList);
                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
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
                                riskAdapter = new RiskAdapter(getActivity(), riskList);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
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
                                investmentPlanAdapter = new InvestmentPlanAdapter(getActivity(), investmentPlanList);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
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
                                documentAdapter = new DocumentAdapter(getActivity(), HomeDetailFragment.this, documentList);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
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
                                lastInvestmentAdapter = new LastInvestmentAdapter(getActivity(), lastInvestmentList);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
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
                                showToast(getActivity(), getResources().getString(R.string.something_went));
                            } else if (response.statusCode == 401) {

                            } else if (response.statusCode == 422) {
                                //  json = trimMessage(new String(response.data));
                                if (json != "" && json != null) {
                                    // displayMessage(json);
                                } else {
                                    showToast(getActivity(), getResources().getString(R.string.please_try_again));
                                }
                            } else if (response.statusCode == 503) {
                                showToast(getActivity(), getResources().getString(R.string.server_down));
                            } else {
                                showToast(getActivity(), getResources().getString(R.string.please_try_again));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (error instanceof NoConnectionError) {
                            showToast(getActivity(), getResources().getString(R.string.oops_connect_your_internet));
                        } else if (error instanceof NetworkError) {
                            showToast(getActivity(), getResources().getString(R.string.oops_connect_your_internet));
                        } else if (error instanceof TimeoutError) {
                            try {
                                if (error.networkResponse == null) {
                                    if (error.getClass().equals(TimeoutError.class)) {
                                        // Show timeout error message
                                        showToast(getActivity(), getResources().getString(R.string.timed_out));
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
            RequestQueue queue = Volley.newRequestQueue(getActivity());
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
    public void onMyDownloadClick(String mdownload) {

    }

    @Override
    public void onCustomClick(String imageData) {
        try {
            if (imageData != null && !imageData.isEmpty() && !imageData.equals("null")) {
                Glide.with(getActivity()).load(imageData)
                        .thumbnail(0.5f)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(iv_main_ing);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onResume(){
        super.onResume();
        // Set title bar
        ((HomeActivity) getActivity()).setActionBarTitle(getString(R.string.max_property_group));
    }
}
