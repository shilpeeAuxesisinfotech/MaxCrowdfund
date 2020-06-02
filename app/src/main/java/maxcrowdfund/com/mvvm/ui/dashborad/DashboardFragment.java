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
import maxcrowdfund.com.constant.MaxCrowdFund;
import maxcrowdfund.com.constant.ProgressDialog;
import maxcrowdfund.com.constant.Utils;
import maxcrowdfund.com.databinding.FragmentDashboardBinding;
import maxcrowdfund.com.mvvm.activity.HomeActivity;
import maxcrowdfund.com.mvvm.activity.LoginActivity;
import maxcrowdfund.com.mvvm.ui.commonmodel.CommonModel;
import maxcrowdfund.com.mvvm.ui.dashborad.adapter.AccountBalanceAdapter;
import maxcrowdfund.com.mvvm.ui.dashborad.model.AccountBalanceResponse;
import maxcrowdfund.com.mvvm.ui.dashborad.model.Balance;
import maxcrowdfund.com.mvvm.ui.dashborad.model.NetReturn;
import maxcrowdfund.com.mvvm.ui.dashborad.model.Portfolio;
import maxcrowdfund.com.restapi.ApiClient;
import maxcrowdfund.com.restapi.EndPointInterface;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {
    ProgressDialog pd;
    AccountBalanceAdapter accountbalanceadapter;
    AccountBalanceAdapter portFolioAdapter;
    AccountBalanceAdapter netReturnAdapter;
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
        } else {
            Toast.makeText(getActivity(), Utils.oops_connect_your_internet, Toast.LENGTH_SHORT).show();
        }
        return binding.getRoot();
    }

    private void getAccountBalance() {
        pd = ProgressDialog.show(getActivity(), Utils.pleaseWait);
        EndPointInterface git = ApiClient.getClient1(getActivity()).create(EndPointInterface.class);
        Call<AccountBalanceResponse> call = git.getAccountBalance("application/json", XCSRF);
        call.enqueue(new Callback<AccountBalanceResponse>() {
            @Override
            public void onResponse(Call<AccountBalanceResponse> call, Response<AccountBalanceResponse> response) {
                //Log.d(TAG, "onResponse: " + ">AccountBalance<" + new Gson().toJson(response.body()));
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                if (response != null) {
                    if (response != null && response.isSuccessful()) {
                        if (response.body().getUserLoginStatus() == 1) {
                            if (response.body().getBalance() != null) {
                                getAccountData(response.body().getBalance());
                            }
                            if (response.body().getPortfolio() != null) {
                                getPortFolio(response.body().getPortfolio());
                            }
                            if (response.body().getNetReturn() != null) {
                                getNetReturn(response.body().getNetReturn());
                            }
                        } else {
                            Utils.setPreference(getActivity(), "user_id", "");
                            Utils.setPreference(getActivity(), "mLogout_token", "");
                            MaxCrowdFund.getInstance().getClearCookies(getActivity(), "cookies", "");
                            Toast.makeText(getActivity(), Utils.sessionExpire, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                            mActivity.finish();
                        }
                    } else {
                        Toast.makeText(getActivity(),Utils.noDataFound, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), Utils.noDataFound, Toast.LENGTH_SHORT).show();
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

    private void getNetReturn(NetReturn netReturn) {
        List<CommonModel> netReturnList = new ArrayList<>();
        netReturnList.clear();
        if (netReturn != null) {
            if (netReturn.getData().getNetReturn() != null) {
                netReturnList.add(new CommonModel(netReturn.getData().getNetReturn().getTitle(), netReturn.getData().getNetReturn().getValue()));
            }
            if (netReturn.getData().getAverageReturn() != null) {
                netReturnList.add(new CommonModel(netReturn.getData().getAverageReturn().getTitle(), netReturn.getData().getAverageReturn().getValue()));
            }
            if (netReturn.getData().getIncidentalPayments() != null) {
                netReturnList.add(new CommonModel(netReturn.getData().getIncidentalPayments().getTitle(), netReturn.getData().getIncidentalPayments().getValue()));
            }
            if (netReturn.getData().getFees() != null) {
                netReturnList.add(new CommonModel(netReturn.getData().getFees().getTitle(), netReturn.getData().getFees().getValue()));
            }
            if (netReturn.getData().getReserved() != null) {
                netReturnList.add(new CommonModel(netReturn.getData().getReserved().getTitle(), netReturn.getData().getReserved().getValue()));
            }
            if (netReturn.getData().getWrittenOff() != null) {
                netReturnList.add(new CommonModel(netReturn.getData().getWrittenOff().getTitle(), netReturn.getData().getWrittenOff().getValue()));
            }
            if (netReturn.getData().getLastNetReturn() != null) {
                netReturnList.add(new CommonModel(netReturn.getData().getLastNetReturn().getTitle(), netReturn.getData().getLastNetReturn().getValue()));
            }

            if (netReturnList.size() > 0) {
                binding.tvNoDataFoundNetR.setVisibility(View.GONE);
                binding.recViewNetReturn.setVisibility(View.VISIBLE);
                netReturnAdapter = new AccountBalanceAdapter(getActivity(), getActivity(), "NetReturn", netReturnList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                binding.recViewNetReturn.setLayoutManager(mLayoutManager);
                binding.recViewNetReturn.setItemAnimator(new DefaultItemAnimator());
                binding.recViewNetReturn.setAdapter(netReturnAdapter);
                netReturnAdapter.notifyDataSetChanged();
            } else {
                binding.tvNoDataFoundNetR.setText(Utils.noDataFound);
                binding.tvNoDataFoundNetR.setVisibility(View.VISIBLE);
                binding.recViewNetReturn.setVisibility(View.GONE);
            }
        }
    }

    private void getPortFolio(Portfolio portfolio) {
        List<CommonModel> portfolioList = new ArrayList<>();
        portfolioList.clear();
        if (portfolio != null) {
            if (portfolio.getData().getPortfolio() != null) {
                portfolioList.add(new CommonModel(portfolio.getData().getPortfolio().getTitle(), portfolio.getData().getPortfolio().getValue()));
            }
            if (portfolio.getData().getNoArrear() != null) {
                portfolioList.add(new CommonModel(portfolio.getData().getNoArrear().getTitle(), portfolio.getData().getNoArrear().getValue()));
            }
            if (portfolio.getData().getArrears4589Days() != null) {
                portfolioList.add(new CommonModel(portfolio.getData().getArrears4589Days().getTitle(), portfolio.getData().getArrears4589Days().getValue()));
            }
            if (portfolio.getData().getArrears90179Days() != null) {
                portfolioList.add(new CommonModel(portfolio.getData().getArrears90179Days().getTitle(), portfolio.getData().getArrears90179Days().getValue()));
            }
            if (portfolio.getData().getArrears190364Days() != null) {
                portfolioList.add(new CommonModel(portfolio.getData().getArrears190364Days().getTitle(), portfolio.getData().getArrears190364Days().getValue()));
            }
            if (portfolio.getData().getArrears365Days() != null) {
                portfolioList.add(new CommonModel(portfolio.getData().getArrears365Days().getTitle(), portfolio.getData().getArrears365Days().getValue()));
            }
            if (portfolio.getData().getTotalReceivable() != null) {
                portfolioList.add(new CommonModel(portfolio.getData().getTotalReceivable().getTitle(), portfolio.getData().getTotalReceivable().getValue()));
            }
            if (portfolio.getData().getReserved() != null) {
                portfolioList.add(new CommonModel(portfolio.getData().getReserved().getTitle(), portfolio.getData().getReserved().getValue()));
            }
            if (portfolio.getData().getPortfolioValue() != null) {
                portfolioList.add(new CommonModel(portfolio.getData().getPortfolioValue().getTitle(), portfolio.getData().getPortfolioValue().getValue()));
            }
            if (portfolioList.size() > 0) {
                binding.tvNoDataFoundPort.setVisibility(View.GONE);
                binding.recyViewPortFolio.setVisibility(View.VISIBLE);
                portFolioAdapter = new AccountBalanceAdapter(getActivity(), getActivity(), "Portfolio", portfolioList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                binding.recyViewPortFolio.setLayoutManager(mLayoutManager);
                binding.recyViewPortFolio.setItemAnimator(new DefaultItemAnimator());
                binding.recyViewPortFolio.setAdapter(portFolioAdapter);
                portFolioAdapter.notifyDataSetChanged();
            } else {
                binding.tvNoDataFoundPort.setText(Utils.noDataFound);
                binding.tvNoDataFoundPort.setVisibility(View.VISIBLE);
                binding.recyViewPortFolio.setVisibility(View.GONE);
            }
        }
    }

    private void getAccountData(Balance balance) {
        List<CommonModel> accountBalanceList = new ArrayList<>();
        accountBalanceList.clear();
        if (balance != null) {
            accountBalanceList.add(new CommonModel(balance.getHeading(), balance.getData().getTotalBalance().getValue(), balance.getData().getTotalBalance().getType()));
        }
        if (balance.getData().getDeposited() != null) {
            accountBalanceList.add(new CommonModel(balance.getData().getDeposited().getTitle(), balance.getData().getDeposited().getValue(), balance.getData().getDeposited().getType()));
        }
        if (balance.getData().getWithdrawn() != null) {
            accountBalanceList.add(new CommonModel(balance.getData().getWithdrawn().getTitle(), balance.getData().getWithdrawn().getValue(), balance.getData().getWithdrawn().getType()));
        }
        if (balance.getData().getInvested() != null) {
            accountBalanceList.add(new CommonModel(balance.getData().getInvested().getTitle(), balance.getData().getInvested().getValue(), balance.getData().getInvested().getType()));
        }
        if (balance.getData().getPending() != null) {
            accountBalanceList.add(new CommonModel(balance.getData().getPending().getTitle(), balance.getData().getPending().getValue(), balance.getData().getPending().getType()));
        }
        if (balance.getData().getFees() != null) {
            accountBalanceList.add(new CommonModel(balance.getData().getFees().getTitle(), balance.getData().getFees().getValue(), balance.getData().getFees().getType()));
        }
        if (balance.getData().getRepaid() != null) {
            accountBalanceList.add(new CommonModel(balance.getData().getRepaid().getTitle(), balance.getData().getRepaid().getValue(), balance.getData().getRepaid().getType()));
        }
        if (balance.getData().getInterestPaid() != null) {
            accountBalanceList.add(new CommonModel(balance.getData().getInterestPaid().getTitle(), balance.getData().getInterestPaid().getValue(), balance.getData().getInterestPaid().getType()));
        }
        if (balance.getData().getReserved() != null) {
            accountBalanceList.add(new CommonModel(balance.getData().getReserved().getTitle(), balance.getData().getReserved().getValue(), balance.getData().getReserved().getType()));
        }
        if (balance.getData().getWrittenOff() != null) {
            accountBalanceList.add(new CommonModel(balance.getData().getWrittenOff().getTitle(), balance.getData().getWrittenOff().getValue(), balance.getData().getWrittenOff().getType()));
        }
        if (balance.getData().getMpgPurchase() != null) {
            accountBalanceList.add(new CommonModel(balance.getData().getMpgPurchase().getTitle(), balance.getData().getMpgPurchase().getValue(), balance.getData().getMpgPurchase().getType()));
        }
        if (balance.getData().getMpgsPurchase() != null) {
            accountBalanceList.add(new CommonModel(balance.getData().getMpgsPurchase().getTitle(), balance.getData().getMpgsPurchase().getValue(), balance.getData().getMpgsPurchase().getType()));
        }
        if (balance.getData().getTotalBalance() != null) {
            accountBalanceList.add(new CommonModel(balance.getData().getTotalBalance().getTitle(), balance.getData().getTotalBalance().getValue(), balance.getData().getTotalBalance().getType()));
        }
        accountBalanceList.add(new CommonModel("", "", ""));
        if (accountBalanceList.size() > 0) {
            binding.tvNoDataFoundBalance.setVisibility(View.GONE);
            binding.recyViewAccBalance.setVisibility(View.VISIBLE);
            accountbalanceadapter = new AccountBalanceAdapter(getActivity(), getActivity(), "AccountBalance", accountBalanceList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            binding.recyViewAccBalance.setLayoutManager(mLayoutManager);
            binding.recyViewAccBalance.setItemAnimator(new DefaultItemAnimator());
            binding.recyViewAccBalance.setAdapter(accountbalanceadapter);
            accountbalanceadapter.notifyDataSetChanged();
        } else {
            binding.tvNoDataFoundBalance.setText(Utils.noDataFound);
            binding.tvNoDataFoundBalance.setVisibility(View.VISIBLE);
            binding.recyViewAccBalance.setVisibility(View.GONE);
        }
    }

    public void onResume() {
        super.onResume();
        ((HomeActivity) getActivity()).setActionBarTitle(Utils.menuDashboard);
    }
}