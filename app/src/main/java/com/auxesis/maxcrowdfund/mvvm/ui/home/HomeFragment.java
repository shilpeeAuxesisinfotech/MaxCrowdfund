package com.auxesis.maxcrowdfund.mvvm.ui.home;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.constant.MaxCrowdFund;
import com.auxesis.maxcrowdfund.constant.PaginationListener;
import com.auxesis.maxcrowdfund.mvvm.activity.LoginActivity;
import com.auxesis.maxcrowdfund.constant.APIUrl;
import com.auxesis.maxcrowdfund.constant.ProgressDialog;
import com.auxesis.maxcrowdfund.mvvm.activity.HomeActivity;
import com.auxesis.maxcrowdfund.mvvm.ui.home.homeAdapter.InvestmentOppAdapter;
import com.auxesis.maxcrowdfund.mvvm.ui.home.oppmodel.InvestmentOppModel;
import com.auxesis.maxcrowdfund.mvvm.ui.home.oppmodel.InvestmentOppRes;
import com.auxesis.maxcrowdfund.restapi.ApiClient;
import com.auxesis.maxcrowdfund.restapi.EndPointInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static com.auxesis.maxcrowdfund.constant.PaginationListener.PAGE_START;
import static com.auxesis.maxcrowdfund.constant.PaginationListener.VISIBLE_THRESHOLD;
import static com.auxesis.maxcrowdfund.constant.Utils.getPreference;
import static com.auxesis.maxcrowdfund.constant.Utils.isInternetConnected;
import static com.auxesis.maxcrowdfund.constant.Utils.setPreference;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    InvestmentOppAdapter adapter;
    LinearLayoutManager layoutManager;
    Activity mActivity;
    ProgressDialog pd;
    List<InvestmentOppModel> listArrayActive = new ArrayList<>();
    List<InvestmentOppModel> listArrayMeddle = new ArrayList<>();
    List<InvestmentOppModel> listArrayExpired = new ArrayList<>();
    private int currentPage = PAGE_START;
    private int currentPageExpired = PAGE_START;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    boolean isRedirectData = false;
    private int TOTAL_PAGES = 0;
    private int TOTAL_ACTIVE_PAGES = 0;
    private int TOTAL_EXPIRED_PAGES = 0;
    int APICount = 0;
    int mActiveTotal = 0;
    int mActiveCount = 0;
    int mExpiredCount = 0;
    int mExpiredTotal = 0;
    boolean isFirstThreeActive = false;
    boolean isFirstThreeExpired = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        APICount = 0;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mActivity = getActivity();
        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new InvestmentOppAdapter(getContext(), getActivity(), new ArrayList<>());
        recyclerView.setAdapter(adapter);
        APICount = 0;
        currentPage = 1;
        APIUrl.investStatus = "active";
        if (isInternetConnected(getActivity())) {
            getInvestmentOpp();
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
        }
        // add scroll listener while user reach in bottom load more will call
        recyclerView.addOnScrollListener(new PaginationListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                if (isInternetConnected(getActivity())) {
                    getInvestmentOpp();
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
        return root;
    }

    private void getInvestmentOpp() {
        pd = ProgressDialog.show(getActivity(), "Please Wait...");
        String XCSRF = getPreference(getActivity(), "mCsrf_token");
        EndPointInterface git = ApiClient.getClient1(getActivity()).create(EndPointInterface.class);
        Call<InvestmentOppRes> call = git.getInvestmentOppHome("json", APIUrl.investStatus, APICount, "application/json", XCSRF);
        call.enqueue(new Callback<InvestmentOppRes>() {
            @Override
            public void onResponse(Call<InvestmentOppRes> call, retrofit2.Response<InvestmentOppRes> response) {
                Log.d("onResponse: ", new Gson().toJson(response.body()));
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                if (response.code() == 200) {
                    if (response != null && response.isSuccessful()) {
                        if (response.body().getUserLoginStatus() == 1) {
                            Log.d("><><><>", "onResponse: "+response.body().getUserLoginStatus());
                            listArrayActive.clear();
                            listArrayMeddle.clear();
                            listArrayExpired.clear();
                            InvestmentOppModel myListModel = new InvestmentOppModel();
                            myListModel.setAverage_return(response.body().getAverageReturn());
                            myListModel.setTotal_raised(response.body().getTotalRaised());
                            myListModel.setActive_investors(response.body().getActiveInvestors());
                            myListModel.setmDefaults(response.body().getDefaults());
                            myListModel.setTypeData(true);
                            listArrayMeddle.add(myListModel);

                            if (response.body().getFundraiserType().equals("active")) {
                                mActiveTotal = response.body().getTotal();
                                if (mActiveTotal == 0) {
                                    isRedirectData = true; //redirect to expire page
                                } else {
                                    Log.d("<><><><><", "if--for rest active only--" + String.valueOf(mActiveTotal)); //for active only //for rest Active
                                    mActiveTotal = response.body().getTotal();
                                    if (mActiveTotal != 0) {
                                        if (response.body().getData() != null && response.body().getData().size() > 0) {
                                            if (mActiveTotal <= VISIBLE_THRESHOLD) {
                                                Log.d("><><>", "Page -if---" + String.valueOf(mActiveTotal));
                                                TOTAL_ACTIVE_PAGES = 0;
                                                TOTAL_PAGES = TOTAL_ACTIVE_PAGES;
                                                APICount = 0;
                                                currentPage = 1;
                                                for (int i = 0; i < response.body().getData().size(); i++) {
                                                    InvestmentOppModel mModel = new InvestmentOppModel();
                                                    mModel.setId(response.body().getData().get(i).getId());
                                                    mModel.setmTitle(response.body().getData().get(i).getTitle());
                                                    mModel.setInterest_pa(response.body().getData().get(i).getInterestPa());
                                                    mModel.setRisk_class(response.body().getData().get(i).getRiskClass());
                                                    mModel.setAmount(response.body().getData().get(i).getAmount());
                                                    mModel.setCurrency(response.body().getData().get(i).getCurrency());
                                                    mModel.setCurrency_symbol(response.body().getData().get(i).getCurrencySymbol());
                                                    mModel.setFilled(response.body().getData().get(i).getFilled());
                                                    mModel.setNo_of_investors(response.body().getData().get(i).getNoOfInvestors());
                                                    mModel.setAmount_left(response.body().getData().get(i).getAmountLeft());
                                                    mModel.setMonths(response.body().getData().get(i).getMonths());
                                                    mModel.setType(response.body().getData().get(i).getType());
                                                    mModel.setLocation(response.body().getData().get(i).getLocation());
                                                    mModel.setLocation_flag_img(response.body().getData().get(i).getLocationFlagImg());
                                                    mModel.setInvested_total_amount_on_loan(response.body().getData().get(i).getInvestedTotalAmountOnLoan());
                                                    mModel.setTypeData(false);
                                                    listArrayActive.add(mModel);
                                                }
                                                isFirstThreeActive = true;
                                            } else if (mActiveTotal > VISIBLE_THRESHOLD) {
                                                isFirstThreeActive = false;
                                                TOTAL_ACTIVE_PAGES = (mActiveTotal / VISIBLE_THRESHOLD);
                                                TOTAL_PAGES = TOTAL_ACTIVE_PAGES;
                                                APICount = APICount + 1;
                                                currentPage = APICount;
                                                for (int i = 0; i < response.body().getData().size(); i++) {
                                                    InvestmentOppModel mSecondModelActive = new InvestmentOppModel();
                                                    mSecondModelActive.setId(response.body().getData().get(i).getId());
                                                    mSecondModelActive.setmTitle(response.body().getData().get(i).getTitle());
                                                    mSecondModelActive.setInterest_pa(response.body().getData().get(i).getInterestPa());
                                                    mSecondModelActive.setRisk_class(response.body().getData().get(i).getRiskClass());
                                                    mSecondModelActive.setAmount(response.body().getData().get(i).getAmount());
                                                    mSecondModelActive.setCurrency(response.body().getData().get(i).getCurrency());
                                                    mSecondModelActive.setCurrency_symbol(response.body().getData().get(i).getCurrencySymbol());
                                                    mSecondModelActive.setFilled(response.body().getData().get(i).getFilled());
                                                    mSecondModelActive.setNo_of_investors(response.body().getData().get(i).getNoOfInvestors());
                                                    mSecondModelActive.setAmount_left(response.body().getData().get(i).getAmountLeft());
                                                    mSecondModelActive.setMonths(response.body().getData().get(i).getMonths());
                                                    mSecondModelActive.setType(response.body().getData().get(i).getType());
                                                    mSecondModelActive.setLocation(response.body().getData().get(i).getLocation());
                                                    mSecondModelActive.setLocation_flag_img(response.body().getData().get(i).getLocationFlagImg());
                                                    mSecondModelActive.setInvestment_status(response.body().getFundraiserType());
                                                    mSecondModelActive.setInvested_total_amount_on_loan(response.body().getData().get(i).getInvestedTotalAmountOnLoan());
                                                    mSecondModelActive.setTypeData(false);
                                                    listArrayActive.add(mSecondModelActive);
                                                }
                                            }
                                            //manage progress view
                                            if (currentPage != PAGE_START) {
                                                adapter.removeLoading();
                                            }
                                            adapter.addItems(listArrayActive); //data added into the list
                                            if (currentPage <= TOTAL_ACTIVE_PAGES) {
                                                Log.d("><><>", "page " + String.valueOf(currentPage) + "><><>" + String.valueOf(TOTAL_ACTIVE_PAGES));
                                                adapter.addLoading();
                                            } else if (currentPage <= APICount) {
                                                Log.d("><><>", "page " + String.valueOf(currentPage) + "><>else<>" +String.valueOf(APICount)+">>>>t>>>>>>>>>>>" +String.valueOf(TOTAL_ACTIVE_PAGES));
                                                listArrayActive.clear();
                                                listArrayActive.addAll(listArrayMeddle);
                                                adapter.addItems(listArrayActive);
                                                APIUrl.investStatus = "expired";
                                                APICount = 0;
                                                isRedirectData = true;
                                                // adapter.addLoading();
                                            } /*else {
                                                isLastPage = true;
                                            }
                                            isLoading = false;*/
                                            // if Active Total is less than three //For first three
                                            if (isFirstThreeActive) {
                                                Log.d("><><><", "First threee----");
                                                if (currentPage != PAGE_START) {
                                                    adapter.removeLoading();
                                                }
                                                // adapter.addItems(listArrayActive); //data added into the list
                                                listArrayActive.clear();
                                                listArrayActive.addAll(listArrayMeddle);
                                                adapter.addItems(listArrayActive);
                                                APIUrl.investStatus = "expired";
                                                APICount = 0;
                                                isFirstThreeActive = false;
                                               // isRedirectData = true;
                                            }
                                        }
                                    }
                                } //end
                            } else {
                                mExpiredTotal = response.body().getTotal();
                                Log.d("<><><><><", "ifelse--for expired only--" + mExpiredTotal); //for active only
                                if (mExpiredTotal != 0) {
                                    if (pd != null && pd.isShowing()) {
                                        pd.dismiss();
                                    }
                                    if (response.body().getData() != null && response.body().getData().size() > 0) {
                                        if (mExpiredTotal <= VISIBLE_THRESHOLD) {
                                            TOTAL_EXPIRED_PAGES = 0;
                                            TOTAL_PAGES = TOTAL_EXPIRED_PAGES;
                                            APICount = 0;
                                            currentPageExpired = 1; //*****
                                            currentPage = currentPageExpired;  //*****
                                            for (int i = 0; i < response.body().getData().size(); i++) {
                                                InvestmentOppModel myListModelExFirst = new InvestmentOppModel();
                                                myListModelExFirst.setId(response.body().getData().get(i).getId());
                                                myListModelExFirst.setmTitle(response.body().getData().get(i).getTitle());
                                                myListModelExFirst.setInterest_pa(response.body().getData().get(i).getInterestPa());
                                                myListModelExFirst.setRisk_class(response.body().getData().get(i).getRiskClass());
                                                myListModelExFirst.setAmount(response.body().getData().get(i).getAmount());
                                                myListModelExFirst.setCurrency(response.body().getData().get(i).getCurrency());
                                                myListModelExFirst.setCurrency_symbol(response.body().getData().get(i).getCurrencySymbol());
                                                myListModelExFirst.setFilled(response.body().getData().get(i).getFilled());
                                                myListModelExFirst.setNo_of_investors(response.body().getData().get(i).getNoOfInvestors());
                                                myListModelExFirst.setAmount_left(response.body().getData().get(i).getAmountLeft());
                                                myListModelExFirst.setMonths(response.body().getData().get(i).getMonths());
                                                myListModelExFirst.setType(response.body().getData().get(i).getType());
                                                myListModelExFirst.setLocation(response.body().getData().get(i).getLocation());
                                                myListModelExFirst.setLocation_flag_img(response.body().getData().get(i).getLocationFlagImg());
                                                myListModelExFirst.setInvested_total_amount_on_loan(response.body().getData().get(i).getInvestedTotalAmountOnLoan());
                                                myListModelExFirst.setTypeData(false);
                                                listArrayExpired.add(myListModelExFirst);
                                            }
                                            isFirstThreeExpired = true;
                                        } else if (mExpiredTotal > VISIBLE_THRESHOLD) {
                                            TOTAL_EXPIRED_PAGES = (mExpiredTotal / VISIBLE_THRESHOLD);
                                            TOTAL_PAGES = TOTAL_EXPIRED_PAGES;
                                            isFirstThreeExpired = false;
                                            // mExpiredCount = mExpiredCount + 1;
                                            APICount++;
                                            currentPageExpired = APICount; //****
                                            currentPage = currentPageExpired; //*

                                            for (int i = 0; i < response.body().getData().size(); i++) {
                                                InvestmentOppModel mModelExSecond = new InvestmentOppModel();
                                                mModelExSecond.setId(response.body().getData().get(i).getId());
                                                mModelExSecond.setmTitle(response.body().getData().get(i).getTitle());
                                                mModelExSecond.setInterest_pa(response.body().getData().get(i).getInterestPa());
                                                mModelExSecond.setRisk_class(response.body().getData().get(i).getRiskClass());
                                                mModelExSecond.setAmount(response.body().getData().get(i).getAmount());
                                                mModelExSecond.setCurrency(response.body().getData().get(i).getCurrency());
                                                mModelExSecond.setCurrency_symbol(response.body().getData().get(i).getCurrencySymbol());
                                                mModelExSecond.setFilled(response.body().getData().get(i).getFilled());
                                                mModelExSecond.setNo_of_investors(response.body().getData().get(i).getNoOfInvestors());
                                                mModelExSecond.setAmount_left(response.body().getData().get(i).getAmountLeft());
                                                mModelExSecond.setMonths(response.body().getData().get(i).getMonths());
                                                mModelExSecond.setType(response.body().getData().get(i).getType());
                                                mModelExSecond.setLocation(response.body().getData().get(i).getLocation());
                                                mModelExSecond.setLocation_flag_img(response.body().getData().get(i).getLocationFlagImg());
                                                mModelExSecond.setInvested_total_amount_on_loan(response.body().getData().get(i).getInvestedTotalAmountOnLoan());
                                                mModelExSecond.setTypeData(false);
                                                listArrayExpired.add(mModelExSecond);
                                            }
                                        }
                                        //manage progress view
                                        if (currentPage != PAGE_START) {
                                            adapter.removeLoading();
                                        }
                                        adapter.addItems(listArrayExpired);
                                        if (currentPage <= TOTAL_EXPIRED_PAGES) {
                                            // if (currentPage <mExpiredTotal) {
                                            Log.d("><><>", String.valueOf(currentPage) + "><><>" + String.valueOf(APICount));
                                            adapter.addLoading();
                                        } /*else if (currentPage==mExpiredTotal){
                                            Toast.makeText(mActivity, getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                                        }*/ else {
                                            isLastPage = true;
                                        }
                                        isLoading = false;

                                        if (isFirstThreeExpired) {
                                            Log.d("><><><>", ">>>>For first Expired data");
                                            //manage progress view
                                            if (currentPage != PAGE_START) {
                                                adapter.removeLoading();
                                            }
                                            adapter.clear();
                                            adapter.addItems(listArrayExpired);
                                            isFirstThreeExpired = false;
                                        }
                                    }
                                }
                            }
                            if (isRedirectData) {
                                APICount = 0;
                                APIUrl.investStatus = "expired";
                                isRedirectData = false;
                                isFirstThreeActive = false;
                                getInvestmentOpp();
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
                } else if (response.code() == 500 || response.code() == 400) {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    Toast.makeText(getActivity(), getResources().getString(R.string.something_went), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InvestmentOppRes> call, Throwable t) {
                Log.e("response", "error " + t.getMessage());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
            }
        });
    }

    public void onResume() {
        super.onResume();
        isRedirectData = false;
        APICount = 0;
        currentPage = 1;
        mActiveTotal = 0;
        mActiveCount = 0;
        mExpiredCount = 0;
        mExpiredTotal = 0;
        isFirstThreeActive = false;
        isFirstThreeExpired = false;
        TOTAL_ACTIVE_PAGES = 0;
        TOTAL_EXPIRED_PAGES = 0;
        TOTAL_PAGES = 0;
        listArrayActive.clear();
        listArrayMeddle.clear();
        listArrayExpired.clear();
        APIUrl.investStatus = "active";
        ((HomeActivity) getActivity()).setActionBarTitle(getString(R.string.menu_investments_opportunity));
    }
}