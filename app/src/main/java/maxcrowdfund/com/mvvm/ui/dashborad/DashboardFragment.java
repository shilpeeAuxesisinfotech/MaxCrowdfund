package maxcrowdfund.com.mvvm.ui.dashborad;

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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import maxcrowdfund.com.R;
import maxcrowdfund.com.constant.MaxCrowdFund;
import maxcrowdfund.com.constant.ProgressDialog;
import maxcrowdfund.com.constant.Utils;
import maxcrowdfund.com.databinding.FragmentDashboardBinding;
import maxcrowdfund.com.mvvm.activity.HomeActivity;
import maxcrowdfund.com.mvvm.activity.LoginActivity;
import maxcrowdfund.com.mvvm.ui.commonmodel.CommonModel;
import maxcrowdfund.com.mvvm.ui.dashborad.adapter.AccountBalanceAdapter;
import maxcrowdfund.com.mvvm.ui.dashborad.adapter.NetReturnAdapter;
import maxcrowdfund.com.mvvm.ui.dashborad.adapter.PortFolioAdapter;
import maxcrowdfund.com.mvvm.ui.dashborad.netreturnmodel.NetReturnModel;
import maxcrowdfund.com.mvvm.ui.dashborad.pendingmodel.PortfolioModel;
import maxcrowdfund.com.mvvm.ui.dashborad.dashboardmodel.AccountResponse;
import maxcrowdfund.com.mvvm.ui.dashborad.pendingmodel.PendingResponse;
import maxcrowdfund.com.restapi.ApiClient;
import maxcrowdfund.com.restapi.EndPointInterface;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {
    private static final String TAG = "DashboardFragment";
    ProgressDialog pd;
    AccountBalanceAdapter accountbalanceadapter;
    PortFolioAdapter portFolioAdapter;
    NetReturnAdapter netReturnAdapter;
    List<CommonModel> accountlist = new ArrayList<>();
    List<CommonModel> depositedlist = new ArrayList<>();
    List<CommonModel> withdrawnlist = new ArrayList<>();
    List<CommonModel> investedlist = new ArrayList<>();
    List<CommonModel> pendinglist = new ArrayList<>();
    List<CommonModel> feeslist = new ArrayList<>();
    List<CommonModel> repaidlist = new ArrayList<>();
    List<CommonModel> interest_paidlist = new ArrayList<>();
    List<CommonModel> reservedlist = new ArrayList<>();
    List<CommonModel> written_offlist = new ArrayList<>();
    List<CommonModel> mpg_purchaselist = new ArrayList<>();
    List<CommonModel> mpgsPurchaseList = new ArrayList<>();
    List<CommonModel> totalBalanceList = new ArrayList<>();
    List<CommonModel> lastButtonList = new ArrayList<>();
    List<PortfolioModel> Portfoliolist = new ArrayList<>();
    List<PortfolioModel> no_arrears = new ArrayList<>();
    List<PortfolioModel> arrears_1 = new ArrayList<>();
    List<PortfolioModel> arrears_2 = new ArrayList<>();
    List<PortfolioModel> arrears_3 = new ArrayList<>();
    List<PortfolioModel> arrears_4 = new ArrayList<>();
    List<PortfolioModel> reserved = new ArrayList<>();
    Activity mActivity;
    String XCSRF = "";
    FragmentDashboardBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        mActivity = getActivity();
        XCSRF = Utils.getPreference(getActivity(), "mCsrf_token");

        if (Utils.isInternetConnected(getActivity())) {
            getAccountBalance();
            getPortFolio();
            getNetReturn();
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
        }
        return binding.getRoot();
    }

    private void getNetReturn() {
        List<NetReturnModel> netReturn = new ArrayList<>();
        netReturn.clear();
        netReturn.add(new NetReturnModel("Net return", "", ""));
        netReturn.add(new NetReturnModel("Average return", "C", "8.00"));
        netReturn.add(new NetReturnModel("Incidental", "D", "0.00"));
        netReturn.add(new NetReturnModel("Fees", "D", "0.50"));
        netReturn.add(new NetReturnModel("Reserved", "D", "0.00"));
        netReturn.add(new NetReturnModel("Written-off", "D", "0.00"));
        netReturn.add(new NetReturnModel("Net return", "D", "7.50"));

        if (netReturn.size() > 0) {
            binding.tvNoDataFoundNetR.setVisibility(View.GONE);
            binding.recViewNetReturn.setVisibility(View.VISIBLE);
            netReturnAdapter = new NetReturnAdapter(getActivity(), netReturn);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            binding.recViewNetReturn.setLayoutManager(mLayoutManager);
            binding.recViewNetReturn.setItemAnimator(new DefaultItemAnimator());
            binding.recViewNetReturn.setAdapter(netReturnAdapter);
            netReturnAdapter.notifyDataSetChanged();
        } else {
            binding.tvNoDataFoundNetR.setVisibility(View.VISIBLE);
            binding.recViewNetReturn.setVisibility(View.GONE);
        }
    }

    private void getAccountBalance() {
        pd = ProgressDialog.show(getActivity(), "Please Wait...");
        EndPointInterface git = ApiClient.getClient1(getActivity()).create(EndPointInterface.class);
        Call<AccountResponse> call = git.getAccountBalance("application/json", XCSRF);
        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                Log.d(TAG, "onResponse: " + ">AccountBalance<" + new Gson().toJson(response.body()));
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                if (response != null) {
                    if (response != null && response.isSuccessful()) {
                        Log.d(TAG, "onResponse: " + ">AccountBalance---------" + response.body().getUserLoginStatus());
                        if (response.body().getUserLoginStatus() == 1) {
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
                            totalBalanceList.clear();
                            lastButtonList.clear();
                            if (response.body().getBalance() != null) {
                                if (response.body().getBalance() != null) {
                                    accountlist.add(new CommonModel(response.body().getBalance().getHeading(), "", ""));
                                }
                                if (response.body().getBalance().getData().getDeposited() != null) {
                                    depositedlist.add(new CommonModel(response.body().getBalance().getData().getDeposited().getTitle(), response.body().getBalance().getData().getDeposited().getValue(), response.body().getBalance().getData().getDeposited().getType()));
                                }
                                if (response.body().getBalance().getData().getWithdrawn() != null) {
                                    withdrawnlist.add(new CommonModel(response.body().getBalance().getData().getWithdrawn().getTitle(), response.body().getBalance().getData().getWithdrawn().getValue(), response.body().getBalance().getData().getWithdrawn().getType()));
                                }
                                if (response.body().getBalance().getData().getInvested() != null) {
                                    investedlist.add(new CommonModel(response.body().getBalance().getData().getInvested().getTitle(), response.body().getBalance().getData().getInvested().getValue(), response.body().getBalance().getData().getInvested().getType()));
                                }
                                if (response.body().getBalance().getData().getPending() != null) {
                                    pendinglist.add(new CommonModel(response.body().getBalance().getData().getPending().getTitle(), response.body().getBalance().getData().getPending().getValue(), response.body().getBalance().getData().getPending().getType()));
                                }
                                if (response.body().getBalance().getData().getFees() != null) {
                                    feeslist.add(new CommonModel(response.body().getBalance().getData().getFees().getTitle(), response.body().getBalance().getData().getFees().getValue(), response.body().getBalance().getData().getFees().getType()));
                                }
                                if (response.body().getBalance().getData().getRepaid() != null) {
                                    repaidlist.add(new CommonModel(response.body().getBalance().getData().getRepaid().getTitle(), response.body().getBalance().getData().getRepaid().getValue(), response.body().getBalance().getData().getRepaid().getType()));
                                }
                                if (response.body().getBalance().getData().getInterestPaid() != null) {
                                    interest_paidlist.add(new CommonModel(response.body().getBalance().getData().getInterestPaid().getTitle(), response.body().getBalance().getData().getInterestPaid().getValue(), response.body().getBalance().getData().getInterestPaid().getType()));
                                }
                                if (response.body().getBalance().getData().getReserved() != null) {
                                    reservedlist.add(new CommonModel(response.body().getBalance().getData().getReserved().getTitle(), response.body().getBalance().getData().getReserved().getValue(), response.body().getBalance().getData().getReserved().getType()));
                                }
                                if (response.body().getBalance().getData().getWrittenOff() != null) {
                                    written_offlist.add(new CommonModel(response.body().getBalance().getData().getWrittenOff().getTitle(), response.body().getBalance().getData().getWrittenOff().getValue(), response.body().getBalance().getData().getWrittenOff().getType()));
                                }
                                if (response.body().getBalance().getData().getMpgPurchase() != null) {
                                    mpg_purchaselist.add(new CommonModel(response.body().getBalance().getData().getMpgPurchase().getTitle(), response.body().getBalance().getData().getMpgPurchase().getValue(), response.body().getBalance().getData().getMpgPurchase().getType()));
                                }
                                if (response.body().getBalance().getData().getMpgsPurchase() != null) {
                                    mpgsPurchaseList.add(new CommonModel(response.body().getBalance().getData().getMpgsPurchase().getTitle(), response.body().getBalance().getData().getMpgsPurchase().getValue(), response.body().getBalance().getData().getMpgsPurchase().getType()));
                                }
                                if (response.body().getBalance().getData().getTotalBalance() != null) {
                                    totalBalanceList.add(new CommonModel(response.body().getBalance().getData().getTotalBalance().getTitle(), response.body().getBalance().getData().getTotalBalance().getValue(), response.body().getBalance().getData().getTotalBalance().getType()));
                                }
                            }
                            CommonModel buttonModel = new CommonModel("", "", "");
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
                            accountlist.addAll(totalBalanceList);
                            accountlist.addAll(lastButtonList);

                            if (accountlist.size() > 0) {
                                binding.tvNoDataFoundBalance.setVisibility(View.GONE);
                                binding.recyViewAccBalance.setVisibility(View.VISIBLE);
                                accountbalanceadapter = new AccountBalanceAdapter(getActivity(), getActivity(), accountlist);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                binding.recyViewAccBalance.setLayoutManager(mLayoutManager);
                                binding.recyViewAccBalance.setItemAnimator(new DefaultItemAnimator());
                                binding.recyViewAccBalance.setAdapter(accountbalanceadapter);
                                accountbalanceadapter.notifyDataSetChanged();
                            } else {
                                binding.tvNoDataFoundBalance.setVisibility(View.VISIBLE);
                                binding.recyViewAccBalance.setVisibility(View.GONE);
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
                    Toast.makeText(getActivity(), getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
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
                            binding.tvNoDataFoundPort.setVisibility(View.GONE);
                            binding.recyViewPortFolio.setVisibility(View.VISIBLE);
                            portFolioAdapter = new PortFolioAdapter(getActivity(), Portfoliolist);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                            binding.recyViewPortFolio.setLayoutManager(mLayoutManager);
                            binding.recyViewPortFolio.setItemAnimator(new DefaultItemAnimator());
                            binding.recyViewPortFolio.setAdapter(portFolioAdapter);
                            portFolioAdapter.notifyDataSetChanged();
                        } else {
                            binding.tvNoDataFoundPort.setVisibility(View.VISIBLE);
                            binding.recyViewPortFolio.setVisibility(View.GONE);
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

    public void onResume() {
        super.onResume();
        ((HomeActivity) getActivity()).setActionBarTitle(getString(R.string.menu_dashboard));
    }
}