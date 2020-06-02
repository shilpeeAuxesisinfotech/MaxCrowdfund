package maxcrowdfund.com.mvvm.ui.myAccount.detail;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import maxcrowdfund.com.databinding.FragmentMyAccountDetailBinding;
import maxcrowdfund.com.mvvm.ui.commonmodel.CommonModel;
import maxcrowdfund.com.mvvm.ui.myWallet.adapter.WalletAdapter;

public class MyAccountDetailFragment extends Fragment {
    FragmentMyAccountDetailBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding =FragmentMyAccountDetailBinding.inflate(inflater,container,false);
        getAccountDetail();
        return binding.getRoot();
    }
    private void getAccountDetail() {
        List<CommonModel>arrayList =new ArrayList<>();
        arrayList.clear();
        arrayList.add(new CommonModel("Date", "Description", "Amount"));
        arrayList.add(new CommonModel("02/05", "Investment...", "- € 1,000.00"));
        arrayList.add(new CommonModel("02/05", "Deposit", "+ € 2,500.00"));
        arrayList.add(new CommonModel("30/04", "Investment...", "+ € 2,500.00"));
        arrayList.add(new CommonModel("25/04", "Repayment...", "+ € 60.00"));
        arrayList.add(new CommonModel("20/04", "Withdrawal", "- € 1,000.00"));
        arrayList.add(new CommonModel("20/04", "Repayment...", "+ € 40.00"));
        arrayList.add(new CommonModel("15/04", "Investment...", "- € 5,000.00"));
        arrayList.add(new CommonModel("15/04", "Buy MPG", "- € 250.00"));
        arrayList.add(new CommonModel("15/04", "Deposit", "+ € 10,000.00"));

        if (arrayList.size() > 0 && !arrayList.isEmpty()) {
            binding.tvNoRecord.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.VISIBLE);
            WalletAdapter adapter = new WalletAdapter(getActivity(), "Transactions",arrayList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            binding.recyclerView.setLayoutManager(mLayoutManager);
            binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            binding.tvNoRecord.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.GONE);
        }
    }
}
