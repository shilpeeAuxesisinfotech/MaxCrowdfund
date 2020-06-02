package maxcrowdfund.com.mvvm.ui.myWallet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import maxcrowdfund.com.R;
import maxcrowdfund.com.constant.MaxCrowdFund;
import maxcrowdfund.com.constant.ProgressDialog;
import maxcrowdfund.com.constant.Utils;
import maxcrowdfund.com.databinding.FragmentMyWalletBinding;
import maxcrowdfund.com.mvvm.activity.LoginActivity;
import maxcrowdfund.com.mvvm.ui.commonmodel.CommonModel;
import maxcrowdfund.com.mvvm.ui.myWallet.adapter.WalletAdapter;
import maxcrowdfund.com.mvvm.ui.myWallet.responsemodel.UserPurchaseOrderResponse;
import maxcrowdfund.com.mvvm.ui.myWallet.responsemodel.UserTransactionResponse;
import maxcrowdfund.com.mvvm.ui.myWallet.responsemodel.WalletDetailResponse;
import maxcrowdfund.com.restapi.ApiClient;
import maxcrowdfund.com.restapi.EndPointInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyWalletFragment extends Fragment {
    ProgressDialog pd;
    FragmentMyWalletBinding binding;
    boolean isPurchaseOrder = false;
    boolean isTransaction = false;
    Activity mActivity;
    String XCSRFTOKEN = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyWalletBinding.inflate(inflater, container, false);
        mActivity = getActivity();
        XCSRFTOKEN = Utils.getPreference(getActivity(), "mCsrf_token");
        binding.lLayoutTransContent.setVisibility(View.GONE);
        binding.lLPurchaseContent.setVisibility(View.GONE);
        if (Utils.isInternetConnected(getActivity())) {
            getWallet();
            getPurchaseOrder();
            getTransaction();
        } else {
            Toast.makeText(getActivity(), Utils.oops_connect_your_internet, Toast.LENGTH_SHORT).show();
        }
        binding.rLayoutPurchaseClick.setOnClickListener(v -> {
            if (binding.lLPurchaseContent.isShown()) {
                Utils.slide_up(mActivity, binding.lLPurchaseContent);
                isPurchaseOrder = false;
                binding.tvArrowPurchase.setBackgroundResource(R.drawable.ic_arrow_down);
                binding.lLPurchaseContent.setVisibility(View.GONE);
            } else {
                binding.lLPurchaseContent.setVisibility(View.VISIBLE);
                isPurchaseOrder = true;
                Utils.slide_down(mActivity, binding.lLPurchaseContent);
                binding.tvArrowPurchase.setBackgroundResource(R.drawable.ic_arrow_up);
            }
            if (isTransaction) {
                if (binding.lLayoutTransContent.isShown()) {
                    Utils.slide_up(mActivity, binding.lLayoutTransContent);
                    isTransaction = false;
                    binding.tvArrowTrans.setBackgroundResource(R.drawable.ic_arrow_down);
                    binding.lLayoutTransContent.setVisibility(View.GONE);
                }
            }
        });
        binding.rLayoutTransClick.setOnClickListener(v -> {
            if (binding.lLayoutTransContent.isShown()) {
                Utils.slide_up(mActivity, binding.lLayoutTransContent);
                isTransaction = false;
                binding.tvArrowTrans.setBackgroundResource(R.drawable.ic_arrow_down);
                binding.lLayoutTransContent.setVisibility(View.GONE);
            } else {
                binding.lLayoutTransContent.setVisibility(View.VISIBLE);
                isTransaction = true;
                Utils.slide_down(mActivity, binding.lLayoutTransContent);
                binding.tvArrowTrans.setBackgroundResource(R.drawable.ic_arrow_up);
            }
            if (isPurchaseOrder) {
                if (binding.lLPurchaseContent.isShown()) {
                    Utils.slide_up(mActivity, binding.lLPurchaseContent);
                    isPurchaseOrder = false;
                    binding.tvArrowPurchase.setBackgroundResource(R.drawable.ic_arrow_down);
                    binding.lLPurchaseContent.setVisibility(View.GONE);
                }
            }
        });
        binding.btnBuyMPG.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.action_nav_my_wallet_to_walletFragment);
        });

        binding.btnSendMPG.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.action_nav_my_wallet_to_securityShareFragment);
        });
        binding.btnBuyMPGS.setOnClickListener(v -> {
        });
        return binding.getRoot();
    }
    private void getTransaction() {
        EndPointInterface git = ApiClient.getClient1(mActivity).create(EndPointInterface.class);
        Call<UserTransactionResponse> call = git.getUserTransaction("json", "application/json", XCSRFTOKEN);
        call.enqueue(new Callback<UserTransactionResponse>() {
            @Override
            public void onResponse(Call<UserTransactionResponse> call, Response<UserTransactionResponse> response) {
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                List<CommonModel> arrayList = new ArrayList<>();
                arrayList.clear();
                if (response != null) {
                    if (response != null && response.isSuccessful()) {
                        if (response.body().getUserLoginStatus() == 1) {
                            if (response.body().getUserTransactionDetail() != null) {
                                binding.tvTransaction.setText("Transactions");
                                if (response.body().getUserTransactionDetail().size() > 0) {
                                    for (int i = 0; i < response.body().getUserTransactionDetail().size(); i++) {
                                        arrayList.add(new CommonModel(response.body().getUserTransactionDetail().get(i).getDateTitle(),
                                                response.body().getUserTransactionDetail().get(i).getDescriptionTitle(),
                                                response.body().getUserTransactionDetail().get(i).getAmountTitle()));
                                    }
                                }
                                if (arrayList.size() > 0 && !arrayList.isEmpty()) {
                                    binding.tvNoRecordTrans.setVisibility(View.GONE);
                                    binding.recyclerViewTrans.setVisibility(View.VISIBLE);
                                    WalletAdapter adapter = new WalletAdapter(getActivity(), "Transactions", arrayList);
                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                    binding.recyclerViewTrans.setLayoutManager(mLayoutManager);
                                    binding.recyclerViewTrans.setItemAnimator(new DefaultItemAnimator());
                                    binding.recyclerViewTrans.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                } else {
                                    binding.tvNoRecordTrans.setVisibility(View.VISIBLE);
                                    binding.recyclerViewTrans.setVisibility(View.GONE);
                                }
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
                        Toast.makeText(getActivity(), Utils.noDataFound, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), Utils.noDataFound, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserTransactionResponse> call, Throwable t) {
                Log.e("response", "error " + t.getMessage());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
            }
        });
    }

    private void getWallet() {
        pd = ProgressDialog.show(getActivity(), Utils.pleaseWait);
        EndPointInterface git = ApiClient.getClient1(mActivity).create(EndPointInterface.class);
        Call<WalletDetailResponse> call = git.getWalletDetail("json", "application/json", XCSRFTOKEN);
        call.enqueue(new Callback<WalletDetailResponse>() {
            @Override
            public void onResponse(Call<WalletDetailResponse> call, Response<WalletDetailResponse> response) {
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                List<CommonModel> arrayList = new ArrayList<>();
                List<CommonModel> arrayList1 = new ArrayList<>();
                arrayList1.clear();
                arrayList.clear();
                if (response != null) {
                    if (response != null && response.isSuccessful()) {
                        if (response.body().getUserLoginStatus() == 1) {
                            if (response.body().getWalletDetail() != null) {
                                if (response.body().getWalletDetail().getData().size() > 0) {
                                    arrayList1.add(0, new CommonModel(response.body().getWalletDetail().getHeading(), ""));
                                    for (int i = 0; i < response.body().getWalletDetail().getData().size(); i++) {
                                        arrayList.add(new CommonModel(response.body().getWalletDetail().getData().get(i).getTitle(),
                                                response.body().getWalletDetail().getData().get(i).getValue()));
                                    }
                                }
                            }
                            arrayList1.addAll(arrayList);
                            if (arrayList1.size() > 0 && !arrayList1.isEmpty()) {
                                binding.tvNoDataFound.setVisibility(View.GONE);
                                binding.recyclerView.setVisibility(View.VISIBLE);
                                WalletAdapter adapter = new WalletAdapter(getActivity(),"Wallet",arrayList1);
                                binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
                                binding.recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            } else {
                                binding.tvNoDataFound.setVisibility(View.VISIBLE);
                                binding.recyclerView.setVisibility(View.GONE);
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
                        Toast.makeText(getActivity(), Utils.noDataFound, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), Utils.noDataFound, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<WalletDetailResponse> call, Throwable t) {
                Log.e("response", "error " + t.getMessage());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
            }
        });
    }
    private void getPurchaseOrder() {
        EndPointInterface git = ApiClient.getClient1(mActivity).create(EndPointInterface.class);
        Call<UserPurchaseOrderResponse> call = git.getUserPurchaseOrder("json", "application/json", XCSRFTOKEN);
        call.enqueue(new Callback<UserPurchaseOrderResponse>() {
            @Override
            public void onResponse(Call<UserPurchaseOrderResponse> call, Response<UserPurchaseOrderResponse> response) {
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                if (response != null) {
                    if (response != null && response.isSuccessful()) {
                        if (response.body().getUserLoginStatus() == 1) {
                            binding.tvPurchase.setText("Purchase orders");
                            List<CommonModel> arrayList = new ArrayList<>();
                            arrayList.clear();
                            if (response.body().getPurchaseOrderDetail() != null) {
                                if (response.body().getPurchaseOrderDetail().size() > 0) {
                                    for (int i = 0; i < response.body().getPurchaseOrderDetail().size(); i++) {
                                        arrayList.add(new CommonModel(response.body().getPurchaseOrderDetail().get(i).getDateTitle(),
                                                response.body().getPurchaseOrderDetail().get(i).getTypeTitle(), response.body().getPurchaseOrderDetail().get(i).getAmountTitle()));
                                    }
                                }
                            }
                            if (arrayList.size() > 0 && !arrayList.isEmpty()) {
                                binding.tvNoRecordPurchase.setVisibility(View.GONE);
                                binding.recyclerViewPurchase.setVisibility(View.VISIBLE);
                                WalletAdapter adapter = new WalletAdapter(getActivity(), "Purchase", arrayList);
                                binding.recyclerViewPurchase.setLayoutManager(new LinearLayoutManager(getActivity()));
                                binding.recyclerViewPurchase.setItemAnimator(new DefaultItemAnimator());
                                binding.recyclerViewPurchase.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            } else {
                                binding.tvNoRecordPurchase.setVisibility(View.VISIBLE);
                                binding.recyclerViewPurchase.setVisibility(View.GONE);
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
                        Toast.makeText(getActivity(), Utils.noDataFound, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), Utils.noDataFound, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<UserPurchaseOrderResponse> call, Throwable t) {
                Log.e("response", "error " + t.getMessage());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
            }
        });
    }
}
