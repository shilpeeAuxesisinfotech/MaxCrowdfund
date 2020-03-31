package com.auxesis.maxcrowdfund.mvvm.ui.dashborad;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.constant.ProgressDialog;
import com.auxesis.maxcrowdfund.constant.Utils;
import com.auxesis.maxcrowdfund.mvvm.ui.dashborad.dashboardmodel.AccountBalanceModel;
import com.auxesis.maxcrowdfund.mvvm.ui.dashborad.dashboardmodel.AccountBalanceResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.dashborad.dd.PortfolioModel;
import com.auxesis.maxcrowdfund.mvvm.ui.dashborad.pendingmodel.PendingResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.login.LoginResponse;
import com.auxesis.maxcrowdfund.restapi.ApiClient;
import com.auxesis.maxcrowdfund.restapi.EndPointInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/*import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;*/

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.auxesis.maxcrowdfund.constant.Utils.getPreference;

public class DashboardFragment extends Fragment {
    private static final String TAG = "DashboardFragment";
    ProgressDialog pd;
    AccountBalanceAdapter accountbalanceadapter;
    PortFolioAdapter portFolioAdapter;
    List<AccountBalanceModel> accountlist = new ArrayList<>();
    List<AccountBalanceModel> depositedlist = new ArrayList<>();
    List<AccountBalanceModel> withdrawnlist = new ArrayList<>();
    List<AccountBalanceModel> investedlist = new ArrayList<>();
    List<AccountBalanceModel> pendinglist = new ArrayList<>();
    List<AccountBalanceModel> feeslist = new ArrayList<>();
    List<AccountBalanceModel> repaidlist = new ArrayList<>();
    List<AccountBalanceModel> interest_paidlist = new ArrayList<>();
    List<AccountBalanceModel> reservedlist = new ArrayList<>();
    List<AccountBalanceModel> written_offlist = new ArrayList<>();
    List<AccountBalanceModel> mpg_purchaselist = new ArrayList<>();
    List<AccountBalanceModel> mpgsPurchaseList = new ArrayList<>();
    List<AccountBalanceModel> lastButtonList = new ArrayList<>();
    List<PortfolioModel> Portfoliolist = new ArrayList<>();
    List<PortfolioModel> no_arrears = new ArrayList<>();
    List<PortfolioModel> arrears_1 = new ArrayList<>();
    List<PortfolioModel> arrears_2 = new ArrayList<>();
    List<PortfolioModel> arrears_3 = new ArrayList<>();
    List<PortfolioModel> arrears_4 = new ArrayList<>();
    List<PortfolioModel> reserved = new ArrayList<>();
    RecyclerView recyViewAccBalance, recyViewPortFolio;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        recyViewAccBalance = root.findViewById(R.id.recyViewAccBalance);
        recyViewPortFolio = root.findViewById(R.id.recyViewPortFolio);
        if (Utils.isInternetConnected(getActivity())) {
            getAccountBalance();
            getPortFolio();
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
        }
        return root;
    }

    private void getAccountBalance() {
        pd = ProgressDialog.show(getActivity(), "Please Wait...");
        String XCSRF = getPreference(getActivity(), "mCsrf_token");
        EndPointInterface git = ApiClient.getClient1(getActivity()).create(EndPointInterface.class);
        Call<AccountBalanceResponse> call = git.getBank("application/json", XCSRF);
        call.enqueue(new Callback<AccountBalanceResponse>() {
            @Override
            public void onResponse(Call<AccountBalanceResponse> call, Response<AccountBalanceResponse> response) {
                Log.d(TAG, "onResponse: " + "><dask><" + new Gson().toJson(response.body()));
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                if (response != null && response.isSuccessful()) {
                    accountlist.clear();
                    depositedlist.clear();
                    withdrawnlist.clear();
                    investedlist.clear();
                    pendinglist.clear();
                    feeslist.clear();
                    repaidlist.clear();
                    interest_paidlist.clear();
                    reservedlist.clear();
                    written_offlist.clear();
                    mpg_purchaselist.clear();
                    mpgsPurchaseList.clear();
                    lastButtonList.clear();

                    if (response.body().getBalance() != null) {
                        /*For Account balance*/
                        if (response.body().getBalance() != null) {
                            AccountBalanceModel balanceModel = new AccountBalanceModel();
                            balanceModel.setmTitle(response.body().getBalance().getHeading());
                            balanceModel.setmValue("");
                            balanceModel.setmType("");
                            accountlist.add(balanceModel);
                        }
                        //For deposit
                        if (response.body().getBalance().getData().getDeposited() != null) {
                            AccountBalanceModel balanceModel = new AccountBalanceModel();
                            balanceModel.setmTitle(response.body().getBalance().getData().getDeposited().getTitle());
                            balanceModel.setmValue(response.body().getBalance().getData().getDeposited().getValue());
                            balanceModel.setmType(response.body().getBalance().getData().getDeposited().getType());
                            depositedlist.add(balanceModel);
                        }

                        //For withdrawn
                        if (response.body().getBalance().getData().getWithdrawn() != null) {
                            AccountBalanceModel withdrawnModel = new AccountBalanceModel();
                            withdrawnModel.setmTitle(response.body().getBalance().getData().getWithdrawn().getTitle());
                            withdrawnModel.setmValue(response.body().getBalance().getData().getWithdrawn().getValue());
                            withdrawnModel.setmType(response.body().getBalance().getData().getWithdrawn().getType());
                            withdrawnlist.add(withdrawnModel);
                        }
                        //For invested
                        if (response.body().getBalance().getData().getInvested() != null) {
                            AccountBalanceModel investedModel = new AccountBalanceModel();
                            investedModel.setmTitle(response.body().getBalance().getData().getInvested().getTitle());
                            investedModel.setmValue(response.body().getBalance().getData().getInvested().getValue());
                            investedModel.setmType(response.body().getBalance().getData().getInvested().getType());
                            investedlist.add(investedModel);
                        }
                        //For pending
                        if (response.body().getBalance().getData().getPending() != null) {
                            AccountBalanceModel pendinglistModel = new AccountBalanceModel();
                            pendinglistModel.setmTitle(response.body().getBalance().getData().getPending().getTitle());
                            pendinglistModel.setmValue(response.body().getBalance().getData().getPending().getValue());
                            pendinglistModel.setmType(response.body().getBalance().getData().getPending().getType());
                            pendinglist.add(pendinglistModel);
                        }
                        //For fees
                        if (response.body().getBalance().getData().getFees() != null) {
                            AccountBalanceModel feeslistModel = new AccountBalanceModel();
                            feeslistModel.setmTitle(response.body().getBalance().getData().getFees().getTitle());
                            feeslistModel.setmValue(response.body().getBalance().getData().getFees().getValue());
                            feeslistModel.setmType(response.body().getBalance().getData().getFees().getType());
                            feeslist.add(feeslistModel);
                        }
                        //For repaidlist
                        if (response.body().getBalance().getData().getRepaid() != null) {
                            AccountBalanceModel repaidModel = new AccountBalanceModel();
                            repaidModel.setmTitle(response.body().getBalance().getData().getRepaid().getTitle());
                            repaidModel.setmValue(response.body().getBalance().getData().getRepaid().getValue());
                            repaidModel.setmType(response.body().getBalance().getData().getRepaid().getType());
                            repaidlist.add(repaidModel);
                        }
                        if (response.body().getBalance().getData().getInterestPaid() != null) {
                            AccountBalanceModel interest_paidModel = new AccountBalanceModel();
                            interest_paidModel.setmTitle(response.body().getBalance().getData().getInterestPaid().getTitle());
                            interest_paidModel.setmValue(response.body().getBalance().getData().getInterestPaid().getValue());
                            interest_paidModel.setmType(response.body().getBalance().getData().getInterestPaid().getType());
                            interest_paidlist.add(interest_paidModel);
                        }
                        //For reserved
                        if (response.body().getBalance().getData().getReserved() != null) {
                            AccountBalanceModel reservedModel = new AccountBalanceModel();
                            reservedModel.setmTitle(response.body().getBalance().getData().getReserved().getTitle());
                            reservedModel.setmValue(response.body().getBalance().getData().getReserved().getValue());
                            reservedModel.setmType(response.body().getBalance().getData().getReserved().getType());
                            reservedlist.add(reservedModel);
                        }
                        //For written
                        if (response.body().getBalance().getData().getWrittenOff() != null) {
                            AccountBalanceModel written_offModel = new AccountBalanceModel();
                            written_offModel.setmTitle(response.body().getBalance().getData().getWrittenOff().getTitle());
                            written_offModel.setmValue(response.body().getBalance().getData().getWrittenOff().getValue());
                            written_offModel.setmType(response.body().getBalance().getData().getWrittenOff().getType());
                            written_offlist.add(written_offModel);
                        }
                        //For mpg_purchage
                        if (response.body().getBalance().getData().getMpgPurchase() != null) {
                            AccountBalanceModel mpg_purchaseModel = new AccountBalanceModel();
                            mpg_purchaseModel.setmTitle(response.body().getBalance().getData().getMpgPurchase().getTitle());
                            mpg_purchaseModel.setmValue(response.body().getBalance().getData().getMpgPurchase().getValue());
                            mpg_purchaseModel.setmType(response.body().getBalance().getData().getMpgPurchase().getType());
                            mpg_purchaselist.add(mpg_purchaseModel);
                        }
                        //For mpgs_purchage
                        if (response.body().getBalance().getData().getMpgsPurchase() != null) {
                            AccountBalanceModel mpgs_purchaseModel = new AccountBalanceModel();
                            mpgs_purchaseModel.setmTitle(response.body().getBalance().getData().getMpgsPurchase().getTitle());
                            mpgs_purchaseModel.setmValue(response.body().getBalance().getData().getMpgsPurchase().getValue());
                            mpgs_purchaseModel.setmType(response.body().getBalance().getData().getMpgsPurchase().getType());
                            mpgsPurchaseList.add(mpgs_purchaseModel);
                        }
                    }
                    AccountBalanceModel buttonModel = new AccountBalanceModel();
                    buttonModel.setmTitle("");
                    buttonModel.setmValue("");
                    buttonModel.setmType("");
                    lastButtonList.add(buttonModel);
                    accountlist.addAll(depositedlist);
                    accountlist.addAll(withdrawnlist);
                    accountlist.addAll(investedlist);
                    accountlist.addAll(pendinglist);
                    accountlist.addAll(feeslist);
                    accountlist.addAll(repaidlist);
                    accountlist.addAll(interest_paidlist);
                    accountlist.addAll(reservedlist);
                    accountlist.addAll(written_offlist);
                    accountlist.addAll(mpg_purchaselist);
                    accountlist.addAll(mpgsPurchaseList);
                    accountlist.addAll(lastButtonList);

                    if (accountlist.size() > 0) {
                        accountbalanceadapter = new AccountBalanceAdapter(getActivity(), accountlist);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        recyViewAccBalance.setLayoutManager(mLayoutManager);
                        recyViewAccBalance.setItemAnimator(new DefaultItemAnimator());
                        recyViewAccBalance.setAdapter(accountbalanceadapter);
                        accountbalanceadapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccountBalanceResponse> call, Throwable t) {
                Log.e("response", "error " + t.getMessage());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
            }
        });
    }

    private void getPortFolio() {
        EndPointInterface git = ApiClient.getClient1(getActivity()).create(EndPointInterface.class);
        Call<PendingResponse> call = git.getPendingAPI("application/json");
        call.enqueue(new Callback<PendingResponse>() {
            @Override
            public void onResponse(Call<PendingResponse> call, Response<PendingResponse> response) {
                Log.d(TAG, "onResponse: " + "><Pending><" + new Gson().toJson(response.body()));
                if (response != null && response.isSuccessful()) {
                    Portfoliolist.clear();
                    no_arrears.clear();
                    arrears_1.clear();
                    arrears_2.clear();
                    arrears_3.clear();
                    arrears_4.clear();
                    reserved.clear();
                    if (response.body().getBalance() != null) {
                        //For Portfolio
                        if (response.body().getBalance().getHeading() != null) {
                            PortfolioModel portfolioModel = new PortfolioModel();
                            portfolioModel.setmPortfolioTitle(response.body().getBalance().getHeading());
                            portfolioModel.setmPortfolioValue("");
                            Portfoliolist.add(portfolioModel);
                        }
                        //For no_arrears
                        if (response.body().getBalance().getData().getNoArrears() != null) {
                            PortfolioModel no_arreresModel = new PortfolioModel();
                            no_arreresModel.setmPortfolioTitle(response.body().getBalance().getData().getNoArrears().getTitle());
                            no_arreresModel.setmPortfolioValue(response.body().getBalance().getData().getNoArrears().getValue());
                            no_arrears.add(no_arreresModel);
                        }
                        //For arrears4589
                        if (response.body().getBalance().getData().getArrears4589() != null) {
                            PortfolioModel no_arreresModel_1 = new PortfolioModel();
                            no_arreresModel_1.setmPortfolioTitle(response.body().getBalance().getData().getArrears4589().getTitle());
                            no_arreresModel_1.setmPortfolioValue(response.body().getBalance().getData().getArrears4589().getValue());
                            arrears_1.add(no_arreresModel_1);
                        }
                        //For arrears90179
                        if (response.body().getBalance().getData().getArrears90179() != null) {
                            PortfolioModel no_arreresModel_2 = new PortfolioModel();
                            no_arreresModel_2.setmPortfolioTitle(response.body().getBalance().getData().getArrears90179().getTitle());
                            no_arreresModel_2.setmPortfolioValue(response.body().getBalance().getData().getArrears90179().getValue());
                            arrears_2.add(no_arreresModel_2);
                        }
                        //For arrears180364
                        if (response.body().getBalance().getData().getArrears180364() != null) {
                            PortfolioModel no_arreresModel_3 = new PortfolioModel();
                            no_arreresModel_3.setmPortfolioTitle(response.body().getBalance().getData().getArrears180364().getTitle());
                            no_arreresModel_3.setmPortfolioValue(response.body().getBalance().getData().getArrears180364().getValue());
                            arrears_3.add(no_arreresModel_3);
                        }
                        //For arrears365
                        if (response.body().getBalance().getData().getArrears365() != null) {
                            PortfolioModel no_arreresModel_4 = new PortfolioModel();
                            no_arreresModel_4.setmPortfolioTitle(response.body().getBalance().getData().getArrears365().getTitle());
                            no_arreresModel_4.setmPortfolioValue(response.body().getBalance().getData().getArrears365().getValue());
                            arrears_4.add(no_arreresModel_4);
                        }
                        //For reserved
                        if (response.body().getBalance().getData().getReserved() != null) {
                            PortfolioModel reservedModel = new PortfolioModel();
                            reservedModel.setmPortfolioTitle(response.body().getBalance().getData().getReserved().getTitle());
                            reservedModel.setmPortfolioValue(response.body().getBalance().getData().getReserved().getValue());
                            reserved.add(reservedModel);
                        }
                        Portfoliolist.addAll(no_arrears);
                        Portfoliolist.addAll(arrears_1);
                        Portfoliolist.addAll(arrears_2);
                        Portfoliolist.addAll(arrears_3);
                        Portfoliolist.addAll(arrears_4);
                        Portfoliolist.addAll(reserved);
                        if (Portfoliolist.size() > 0) {
                            portFolioAdapter = new PortFolioAdapter(getActivity(), Portfoliolist);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                            recyViewPortFolio.setLayoutManager(mLayoutManager);
                            recyViewPortFolio.setItemAnimator(new DefaultItemAnimator());
                            recyViewPortFolio.setAdapter(portFolioAdapter);
                            portFolioAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<PendingResponse> call, Throwable t) {
                Log.e("response", "error " + t.getMessage());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}