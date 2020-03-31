package com.auxesis.maxcrowdfund.mvvm.ui.changebankaccount.changebankaccountview;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.customClickListener.OnCustomClickListener;
import com.auxesis.maxcrowdfund.constant.ProgressDialog;
import com.auxesis.maxcrowdfund.constant.Utils;
import com.auxesis.maxcrowdfund.mvvm.activity.HomeActivity;
import com.auxesis.maxcrowdfund.mvvm.ui.changebankaccount.changebankAdapter.ChangeBankAccountAdapter;
import com.auxesis.maxcrowdfund.mvvm.ui.changebankaccount.changebankaccountmodel.ActiveBankAccountResponse;
import com.auxesis.maxcrowdfund.mvvm.ui.changebankaccount.changebankaccountmodel.ChangeBankAccountResponse;
import com.auxesis.maxcrowdfund.restapi.ApiClient;
import com.auxesis.maxcrowdfund.restapi.EndPointInterface;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.auxesis.maxcrowdfund.constant.Utils.getPreference;

public class ChangeBankAccountFragment extends Fragment implements OnCustomClickListener {
    private static final String TAG = "ChangeBankAccountFragme";
    RecyclerView recyclerView;
    TextView tvNoRecord;
    ProgressDialog pd;
    ChangeBankAccountAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_change_bank_account, container, false);
        recyclerView = root.findViewById(R.id.recyclerView);
        tvNoRecord = root.findViewById(R.id.tvNoRecord);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new ChangeBankAccountAdapter(getActivity(), ChangeBankAccountFragment.this, new ArrayList<>());
        recyclerView.setAdapter(adapter);

        if (Utils.isInternetConnected(getActivity())) {
            getChangeBankAccountApi();
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
        }
        return root;
    }

    @Override
    public void onCustomClick(String data) {
        Log.d(TAG, "onCustomClick: " + "<<<<data<<<<<<<<<<<" + data);
        if (Utils.isInternetConnected(getActivity())) {
            pd = ProgressDialog.show(getActivity(), "Please Wait...");
            EndPointInterface git = ApiClient.getClient1(getActivity()).create(EndPointInterface.class);
            JsonObject jsonObject = new JsonObject();
            if (data != null) {
                jsonObject.addProperty("bank_account", data);
            }
            String XCSRF = getPreference(getActivity(), "mCsrf_token");
            Call<ActiveBankAccountResponse> call = git.getActiveBankAccount("application/json", XCSRF, jsonObject);
            call.enqueue(new Callback<ActiveBankAccountResponse>() {
                @Override
                public void onResponse(Call<ActiveBankAccountResponse> call, Response<ActiveBankAccountResponse> response) {
                    Log.d(TAG, "onResponse: " + "><><" + new Gson().toJson(response.body()));
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    if (response != null && response.isSuccessful()) {
                        if (response.body().getResult().equals("success")) {
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            if (Utils.isInternetConnected(getActivity())) {
                                 getChangeBankAccountApi();
                            } else {
                                Toast.makeText(getActivity(), getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                @Override
                public void onFailure(Call<ActiveBankAccountResponse> call, Throwable t) {
                    Log.e("response", "error " + t.getMessage());
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                }
            });
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
        }
    }
    private void getChangeBankAccountApi() {
        try {
            pd = ProgressDialog.show(getActivity(), "Please Wait...");
            EndPointInterface git = ApiClient.getClient1(getActivity()).create(EndPointInterface.class);
            String XCSRF = getPreference(getActivity(), "mCsrf_token");
            Call<ChangeBankAccountResponse> call = git.getChangeBankAccount("application/json", XCSRF);
            call.enqueue(new Callback<ChangeBankAccountResponse>() {
                @Override
                public void onResponse(@NonNull Call<ChangeBankAccountResponse> call, @NonNull retrofit2.Response<ChangeBankAccountResponse> response) {
                    try {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        if (response != null && response.isSuccessful()) {
                            Log.d(TAG, "onResponse:" + "><Bank><" + new Gson().toJson(response.body()));
                            if (response.body().getBankAccounts().getData().size() > 0) {
                                tvNoRecord.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                adapter.clear();
                                adapter.addItems(response.body().getBankAccounts().getData());
                                    /*adapter = new ChangeBankAccountAdapter(getActivity(),ChangeBankAccountFragment.this, response.body().getBankAccounts().getData());
                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                    recyclerView.setLayoutManager(mLayoutManager);
                                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                                    recyclerView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();*/
                            } else {
                                recyclerView.setVisibility(View.GONE);
                                tvNoRecord.setVisibility(View.VISIBLE);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<ChangeBankAccountResponse> call, @NonNull Throwable t) {
                    Log.e("response", "error " + t.getMessage());
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onResume() {
        super.onResume();
        // Set title bar
        ((HomeActivity) getActivity()).setActionBarTitle(getString(R.string.change_default_bank_account));
    }
}
