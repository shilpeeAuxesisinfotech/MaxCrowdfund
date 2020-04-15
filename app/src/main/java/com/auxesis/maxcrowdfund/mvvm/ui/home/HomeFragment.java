package com.auxesis.maxcrowdfund.mvvm.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
        APIUrl.investStatus = "active";
        listArrayActive.clear();
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
                            if (response.body().getFundraiserType().equals("active")) {
                                listArrayActive.clear();
                                mActiveTotal = response.body().getTotal();
                                if (mActiveTotal == 0) {
                                    isRedirectData = true; //redirect to expire page
                                } else {
                                    Log.d("<><><><><", "if--for rest active only--"); //for active only //for rest Active
                                    mActiveTotal = response.body().getTotal();
                                    if (mActiveTotal != 0) {
                                        listArrayActive.clear();
                                        if (response.body().getData() != null && response.body().getData().size() > 0) {
                                            if (mActiveTotal <= VISIBLE_THRESHOLD) {
                                                TOTAL_ACTIVE_PAGES = 0;
                                                mActiveCount = 0;
                                                TOTAL_PAGES = TOTAL_ACTIVE_PAGES;
                                                APICount = mActiveCount;
                                                for (int i = 0; i < response.body().getData().size(); i++) {
                                                    InvestmentOppModel myListModel = new InvestmentOppModel();
                                                    myListModel.setId(response.body().getData().get(i).getId());
                                                    myListModel.setmTitle(response.body().getData().get(i).getTitle());
                                                    myListModel.setInterest_pa(response.body().getData().get(i).getInterestPa());
                                                    myListModel.setRisk_class(response.body().getData().get(i).getRiskClass());
                                                    myListModel.setAmount(response.body().getData().get(i).getAmount());
                                                    myListModel.setCurrency(response.body().getData().get(i).getCurrency());
                                                    myListModel.setCurrency_symbol(response.body().getData().get(i).getCurrencySymbol());
                                                    myListModel.setFilled(response.body().getData().get(i).getFilled());
                                                    myListModel.setNo_of_investors(response.body().getData().get(i).getNoOfInvestors());
                                                    myListModel.setAmount_left(response.body().getData().get(i).getAmountLeft());
                                                    myListModel.setMonths(response.body().getData().get(i).getMonths());
                                                    myListModel.setType(response.body().getData().get(i).getType());
                                                    myListModel.setLocation(response.body().getData().get(i).getLocation());
                                                    myListModel.setLocation_flag_img(response.body().getData().get(i).getLocationFlagImg());
                                                    myListModel.setInvestment_status(response.body().getFundraiserType());
                                                    listArrayActive.add(myListModel);
                                                }
                                            } else if (mActiveTotal > VISIBLE_THRESHOLD) {
                                                TOTAL_ACTIVE_PAGES = (mActiveTotal / VISIBLE_THRESHOLD);
                                                TOTAL_PAGES = TOTAL_ACTIVE_PAGES;
                                                mActiveCount = mActiveCount + 1;
                                                APICount = mActiveCount;
                                               // APICount++;
                                                for (int i = 0; i < response.body().getData().size(); i++) {
                                                    InvestmentOppModel myListModel = new InvestmentOppModel();
                                                    myListModel.setId(response.body().getData().get(i).getId());
                                                    myListModel.setmTitle(response.body().getData().get(i).getTitle());
                                                    myListModel.setInterest_pa(response.body().getData().get(i).getInterestPa());
                                                    myListModel.setRisk_class(response.body().getData().get(i).getRiskClass());
                                                    myListModel.setAmount(response.body().getData().get(i).getAmount());
                                                    myListModel.setCurrency(response.body().getData().get(i).getCurrency());
                                                    myListModel.setCurrency_symbol(response.body().getData().get(i).getCurrencySymbol());
                                                    myListModel.setFilled(response.body().getData().get(i).getFilled());
                                                    myListModel.setNo_of_investors(response.body().getData().get(i).getNoOfInvestors());
                                                    myListModel.setAmount_left(response.body().getData().get(i).getAmountLeft());
                                                    myListModel.setMonths(response.body().getData().get(i).getMonths());
                                                    myListModel.setType(response.body().getData().get(i).getType());
                                                    myListModel.setLocation(response.body().getData().get(i).getLocation());
                                                    myListModel.setLocation_flag_img(response.body().getData().get(i).getLocationFlagImg());
                                                    myListModel.setInvestment_status(response.body().getFundraiserType());
                                                    listArrayActive.add(myListModel);
                                                }
                                            }
                                            //manage progress view
                                            if (currentPage != PAGE_START) {
                                                adapter.removeLoading();
                                            }
                                            adapter.addItems(listArrayActive); //data added into the list
                                            if (currentPage <TOTAL_ACTIVE_PAGES) {
                                                Log.d("><><>", "page "+String.valueOf(currentPage));
                                                adapter.addLoading(); //TOTAL_ACTIVE_PAGES  if (currentPage == APICount)
                                            }else if (currentPage == TOTAL_ACTIVE_PAGES) {
                                                listArrayActive.clear();
                                                listArrayMeddle.clear();
                                                InvestmentOppModel myListModel = new InvestmentOppModel();
                                                myListModel.setAverage_return(response.body().getAverageReturn());
                                                myListModel.setTotal_raised(response.body().getTotalRaised());
                                                myListModel.setActive_investors(response.body().getActiveInvestors());
                                                myListModel.setInvestment_status("OtherItem");
                                                listArrayMeddle.add(myListModel);
                                                listArrayActive.addAll(listArrayMeddle);
                                                adapter.addItems(listArrayActive);
                                                APIUrl.investStatus = "expired";
                                                APICount = 0;
                                                adapter.addLoading();
                                            } else {
                                                isLastPage = true;
                                            }
                                            isLoading = false;
                                        } else {
                                            Toast.makeText(getActivity(), getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                } //end
                            } else {
                                mExpiredTotal = response.body().getTotal();
                                Log.d("<><><><><", "ifelse--for expired only--" + mExpiredTotal); //for active only
                                if (mExpiredTotal != 0) {
                                    listArrayExpired.clear();
                                    if (response.body().getData() != null && response.body().getData().size() > 0) {
                                        if (mExpiredTotal <= VISIBLE_THRESHOLD) {
                                            TOTAL_EXPIRED_PAGES = 0;
                                            TOTAL_PAGES = TOTAL_EXPIRED_PAGES;
                                            mExpiredCount = 0;
                                            APICount = mExpiredCount;
                                            for (int i = 0; i < response.body().getData().size(); i++) {
                                                InvestmentOppModel myListModel = new InvestmentOppModel();
                                                myListModel.setId(response.body().getData().get(i).getId());
                                                myListModel.setmTitle(response.body().getData().get(i).getTitle());
                                                myListModel.setInterest_pa(response.body().getData().get(i).getInterestPa());
                                                myListModel.setRisk_class(response.body().getData().get(i).getRiskClass());
                                                myListModel.setAmount(response.body().getData().get(i).getAmount());
                                                myListModel.setCurrency(response.body().getData().get(i).getCurrency());
                                                myListModel.setCurrency_symbol(response.body().getData().get(i).getCurrencySymbol());
                                                myListModel.setFilled(response.body().getData().get(i).getFilled());
                                                myListModel.setNo_of_investors(response.body().getData().get(i).getNoOfInvestors());
                                                myListModel.setAmount_left(response.body().getData().get(i).getAmountLeft());
                                                myListModel.setMonths(response.body().getData().get(i).getMonths());
                                                myListModel.setType(response.body().getData().get(i).getType());
                                                myListModel.setLocation(response.body().getData().get(i).getLocation());
                                                myListModel.setLocation_flag_img(response.body().getData().get(i).getLocationFlagImg());
                                                myListModel.setInvestment_status(response.body().getFundraiserType());
                                                listArrayExpired.add(myListModel);
                                            }
                                        } else if (mExpiredTotal > VISIBLE_THRESHOLD) {
                                            TOTAL_EXPIRED_PAGES = (mExpiredTotal / VISIBLE_THRESHOLD);
                                            TOTAL_PAGES = TOTAL_EXPIRED_PAGES;
                                            mExpiredCount = mExpiredCount + 1;
                                            APICount = mExpiredCount;
                                            for (int i = 0; i < response.body().getData().size(); i++) {
                                                InvestmentOppModel myListModel = new InvestmentOppModel();
                                                myListModel.setId(response.body().getData().get(i).getId());
                                                myListModel.setmTitle(response.body().getData().get(i).getTitle());
                                                myListModel.setInterest_pa(response.body().getData().get(i).getInterestPa());
                                                myListModel.setRisk_class(response.body().getData().get(i).getRiskClass());
                                                myListModel.setAmount(response.body().getData().get(i).getAmount());
                                                myListModel.setCurrency(response.body().getData().get(i).getCurrency());
                                                myListModel.setCurrency_symbol(response.body().getData().get(i).getCurrencySymbol());
                                                myListModel.setFilled(response.body().getData().get(i).getFilled());
                                                myListModel.setNo_of_investors(response.body().getData().get(i).getNoOfInvestors());
                                                myListModel.setAmount_left(response.body().getData().get(i).getAmountLeft());
                                                myListModel.setMonths(response.body().getData().get(i).getMonths());
                                                myListModel.setType(response.body().getData().get(i).getType());
                                                myListModel.setLocation(response.body().getData().get(i).getLocation());
                                                myListModel.setLocation_flag_img(response.body().getData().get(i).getLocationFlagImg());
                                                myListModel.setInvestment_status(response.body().getFundraiserType());
                                                listArrayExpired.add(myListModel);
                                            }
                                        } //end greater
                                        //manage progress view
                                        if (currentPage != PAGE_START) {
                                            adapter.removeLoading();
                                        }
                                        adapter.addItems(listArrayExpired);
                                        if (currentPage < TOTAL_EXPIRED_PAGES) {
                                            adapter.addLoading();
                                        } else {
                                            isLastPage = true;
                                        }
                                        isLoading = false;
                                    }
                                }
                            }
                            if (isRedirectData) {
                                APICount = 0;
                                APIUrl.investStatus = "expired";
                                isRedirectData = false;
                                getInvestmentOpp();
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
        currentPage = 0;
        mActiveTotal = 0;
        mActiveCount = 0;
        mExpiredCount = 0;
        mExpiredTotal = 0;
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