package maxcrowdfund.com.mvvm.ui.myAccount;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import maxcrowdfund.com.R;
import maxcrowdfund.com.databinding.FragmentMyAccountBinding;
import maxcrowdfund.com.mvvm.ui.myAccount.adapter.MyAccountAdapter;
import maxcrowdfund.com.mvvm.ui.myAccount.model.AccountModel;

public class MyAccountFragment extends Fragment {
    FragmentMyAccountBinding binding;
    MyAccountAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyAccountBinding.inflate(inflater, container, false);
        List<AccountModel> arrayList = new ArrayList<>();
        arrayList.clear();
        arrayList.add(new AccountModel("Deposited", "C", "25,000.00"));
        arrayList.add(new AccountModel("Withdrawn", "D", "2,000.00"));
        arrayList.add(new AccountModel("Invested", "D", "15,000.00"));
        arrayList.add(new AccountModel("Pending", "D", "5,00000"));
        arrayList.add(new AccountModel("Fees", "D", "1.00"));
        arrayList.add(new AccountModel("Repaid", "C", "0.00"));
        arrayList.add(new AccountModel("Interest paid", "C", "100.00"));
        arrayList.add(new AccountModel("Reserved", "D", "0.00"));
        arrayList.add(new AccountModel("Written off ", "D", "0.00"));
        arrayList.add(new AccountModel("MPG", "D", "250.00"));
        arrayList.add(new AccountModel("MPGS", "D", "2,250.00"));
        arrayList.add(new AccountModel("Cash balance", "C", "1,599.00"));

        if (arrayList.size() > 0) {
            binding.tvNoDataFound.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.VISIBLE);
            adapter = new MyAccountAdapter(getActivity(),arrayList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            binding.recyclerView.setLayoutManager(mLayoutManager);
            binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            binding.tvNoDataFound.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.GONE);
        }

        binding.btnWithdraw.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.action_nav_my_account_to_myAccountDetailFragment);
        });

        return binding.getRoot();
    }
}
