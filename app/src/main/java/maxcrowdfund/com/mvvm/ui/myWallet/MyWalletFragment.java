package maxcrowdfund.com.mvvm.ui.myWallet;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import maxcrowdfund.com.R;
import maxcrowdfund.com.constant.Utils;
import maxcrowdfund.com.databinding.FragmentMyWalletBinding;
import maxcrowdfund.com.mvvm.ui.myWallet.adapter.PurchaseOrderAdapter;
import maxcrowdfund.com.mvvm.ui.myWallet.adapter.WalletAdapter;
import maxcrowdfund.com.mvvm.ui.myWallet.model.PurchaseOrderModel;
import maxcrowdfund.com.mvvm.ui.myWallet.model.WalletModel;

import static android.widget.LinearLayout.VERTICAL;

public class MyWalletFragment extends Fragment {
    FragmentMyWalletBinding binding;
    List<WalletModel> arrayList = new ArrayList<>();
    List<PurchaseOrderModel> arrayListPurchase = new ArrayList<>();
    WalletAdapter adapter;
    PurchaseOrderAdapter purchaseOrderAdapter;
    boolean isPurchaseOrder = false;
    boolean isTransaction = false;
    Activity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyWalletBinding.inflate(inflater, container, false);
        mActivity = getActivity();
        binding.lLayoutTransContent.setVisibility(View.GONE);
        binding.lLPurchaseContent.setVisibility(View.GONE);

        getWallet();
        getPurchaseOrder();
        getTransaction();

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
        return binding.getRoot();
    }

    private void getTransaction() {
        binding.tvTransaction.setText("Transactions");
        arrayListPurchase.clear();
        arrayListPurchase.add(new PurchaseOrderModel("Date", "Coin / Asset", "Amount", false));
        arrayListPurchase.add(new PurchaseOrderModel("05/05", "MPG", "1,000", true));
        arrayListPurchase.add(new PurchaseOrderModel("15/04", "MPG", "1,000", false));
        arrayListPurchase.add(new PurchaseOrderModel("03/04", "MPGS", "1,500", false));

        if (arrayListPurchase.size() > 0 && !arrayListPurchase.isEmpty()) {
            binding.tvNoRecordTrans.setVisibility(View.GONE);
            binding.recyclerViewTrans.setVisibility(View.VISIBLE);
            purchaseOrderAdapter = new PurchaseOrderAdapter(getActivity(), arrayListPurchase);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            binding.recyclerViewTrans.setLayoutManager(mLayoutManager);
            binding.recyclerViewTrans.setItemAnimator(new DefaultItemAnimator());
            binding.recyclerViewTrans.setAdapter(purchaseOrderAdapter);
            purchaseOrderAdapter.notifyDataSetChanged();
        } else {
            binding.tvNoRecordTrans.setVisibility(View.VISIBLE);
            binding.recyclerViewTrans.setVisibility(View.GONE);
        }
    }

    private void getWallet() {
        arrayList.clear();
        arrayList.add(new WalletModel("ARDOR-ABCD-1234-EFGH-A283Z", ""));
        arrayList.add(new WalletModel("MPG", "678,1234568"));
        arrayList.add(new WalletModel("MPGS", "1,500"));
        if (arrayList.size() > 0 && !arrayList.isEmpty()) {
            binding.tvNoDataFound.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.VISIBLE);
            adapter = new WalletAdapter(getActivity(), arrayList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            binding.recyclerView.setLayoutManager(mLayoutManager);
            binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            binding.tvNoDataFound.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.GONE);
        }
    }

    private void getPurchaseOrder() {
        binding.tvPurchase.setText("Purchase orders");
        arrayListPurchase.clear();
        arrayListPurchase.add(new PurchaseOrderModel("Date", "Coin / Asset", "Amount", false));
        arrayListPurchase.add(new PurchaseOrderModel("05/05", "MPG", "1,000", true));
        arrayListPurchase.add(new PurchaseOrderModel("15/04", "MPG", "1,000", false));
        arrayListPurchase.add(new PurchaseOrderModel("03/04", "MPGS", "1,500", false));

        if (arrayListPurchase.size() > 0 && !arrayListPurchase.isEmpty()) {
            binding.tvNoRecordPurchase.setVisibility(View.GONE);
            binding.recyclerViewPurchase.setVisibility(View.VISIBLE);
            purchaseOrderAdapter = new PurchaseOrderAdapter(getActivity(), arrayListPurchase);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            binding.recyclerViewPurchase.setLayoutManager(mLayoutManager);
            binding.recyclerViewPurchase.setItemAnimator(new DefaultItemAnimator());
            binding.recyclerViewPurchase.setAdapter(purchaseOrderAdapter);
            purchaseOrderAdapter.notifyDataSetChanged();
        } else {
            binding.tvNoRecordPurchase.setVisibility(View.VISIBLE);
            binding.recyclerViewPurchase.setVisibility(View.GONE);
        }
    }
}
