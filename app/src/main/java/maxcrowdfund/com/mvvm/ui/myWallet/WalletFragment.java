package maxcrowdfund.com.mvvm.ui.myWallet;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import maxcrowdfund.com.R;
import maxcrowdfund.com.databinding.FragmentWalletBinding;
import maxcrowdfund.com.mvvm.ui.customAdapter.CustomAdapter;
import maxcrowdfund.com.mvvm.ui.customModels.CustomSpinnerModel;

public class WalletFragment extends Fragment {
    FragmentWalletBinding binding;
    CustomAdapter adapter = null;
    CustomAdapter adapterAmount = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding =FragmentWalletBinding.inflate(inflater,container,false);
        getCurrencyData();
        getAmount();
        binding.btnContinue.setOnClickListener(v->{
            String mAmount = binding.edtAmount.getText().toString().trim();
            if (mAmount!=null && !mAmount.isEmpty()){
                binding.cardViewConfirm.setVisibility(View.VISIBLE);
                binding.cardViewBuy.setVisibility(View.GONE);
            }else {
                Toast.makeText(getActivity(), getResources().getString(R.string.enter_amount), Toast.LENGTH_SHORT).show();
            }
        });
        return binding.getRoot();
    }
    private void getAmount() {
        List<CustomSpinnerModel> arrayList = new ArrayList<>();
        arrayList.clear();
        arrayList.add(new CustomSpinnerModel("name","Select amount"));
        arrayList.add(new CustomSpinnerModel("name","100"));
        arrayList.add(new CustomSpinnerModel("name","200"));
        arrayList.add(new CustomSpinnerModel("name","400"));
        arrayList.add(new CustomSpinnerModel("name","600"));
        arrayList.add(new CustomSpinnerModel("name","800"));
        arrayList.add(new CustomSpinnerModel("name","1,000"));

        if (arrayList.size()>0 && !arrayList.isEmpty()) {
            adapterAmount = new CustomAdapter(getActivity(), arrayList);
            binding.spinnerAccount.setAdapter(adapterAmount);
        }
        binding.spinnerAccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CustomSpinnerModel model = adapterAmount.getItem(position);
                Log.d("onItemSelected: ",model.getKey());
                Log.d("onItemSelected: ",model.getVal());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void getCurrencyData() {
        List<CustomSpinnerModel> arrayList = new ArrayList<>();
        arrayList.clear();
        arrayList.add(new CustomSpinnerModel("name","Select currency"));
        arrayList.add(new CustomSpinnerModel("name","Euro"));
        arrayList.add(new CustomSpinnerModel("name","Euro"));
        arrayList.add(new CustomSpinnerModel("name","Euro"));
        arrayList.add(new CustomSpinnerModel("name","Euro"));
        arrayList.add(new CustomSpinnerModel("name","Euro"));
        arrayList.add(new CustomSpinnerModel("name","Euro"));
        arrayList.add(new CustomSpinnerModel("name","Euro"));
        arrayList.add(new CustomSpinnerModel("name","Euro"));
        arrayList.add(new CustomSpinnerModel("name","Euro"));

        if (arrayList.size()>0 && !arrayList.isEmpty()) {
             adapter = new CustomAdapter(getActivity(), arrayList);
            binding.spinnerCurrency.setAdapter(adapter);
        }

        binding.spinnerCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CustomSpinnerModel model = adapter.getItem(position);
                Log.d("onItemSelected: ",model.getKey());
                Log.d("onItemSelected: ",model.getVal());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
