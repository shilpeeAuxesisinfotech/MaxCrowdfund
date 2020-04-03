package com.auxesis.maxcrowdfund.mvvm.ui.myinvestments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.constant.MaxCrowdFund;
import com.auxesis.maxcrowdfund.mvvm.activity.LoginActivity;
import com.auxesis.maxcrowdfund.mvvm.ui.homeDetail.adapter.MyInvestmentAdapter;
import com.auxesis.maxcrowdfund.constant.ProgressDialog;
import com.auxesis.maxcrowdfund.constant.Utils;
import com.auxesis.maxcrowdfund.custommvvm.myinvestmentmodel.MyInvestmentSearchResponse;
import com.auxesis.maxcrowdfund.mvvm.activity.HomeActivity;
import com.auxesis.maxcrowdfund.mvvm.ui.myinvestments.model.MyInvestmentResponce;
import com.auxesis.maxcrowdfund.restapi.ApiClient;
import com.auxesis.maxcrowdfund.restapi.EndPointInterface;
import com.google.gson.Gson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.auxesis.maxcrowdfund.constant.Utils.getPreference;
import static com.auxesis.maxcrowdfund.constant.Utils.setPreference;

public class MyInvestmentsFragment extends Fragment {
    private static final String TAG = "MyInvestmentsFragment";
    RecyclerView recyclerView;
    MyInvestmentAdapter adapter;
    LinearLayout lLayoutFilter;
    EditText edtCompany, edtFrom, edtTo;
    Button btn_filter, btn_clear;
    String error_msg = "";
    ProgressDialog pd;
    Activity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_investments, container, false);
        mActivity = getActivity();
        edtCompany = root.findViewById(R.id.edtCompany);
        edtFrom = root.findViewById(R.id.edtFrom);
        edtTo = root.findViewById(R.id.edtTo);

        recyclerView = root.findViewById(R.id.recyclerView);
        lLayoutFilter = root.findViewById(R.id.lLayoutFilter);
        lLayoutFilter.setVisibility(View.GONE);

        if (Utils.isInternetConnected(getActivity())) {
            getMyInvestment();
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
        }
        btn_filter = root.findViewById(R.id.btn_filter);
        btn_clear = root.findViewById(R.id.btn_clear);
        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isInternetConnected(getActivity())) {
                    if (Validation()) {
                       // getMyInvestmentSearch();
                    }else {
                        Toast.makeText(getActivity(), error_msg, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*edtCompany.setText("");
                edtFrom.setText("");
                edtTo.setText("");
                Toast.makeText(getActivity(), "Data cleared successfully", Toast.LENGTH_SHORT).show();*/
            }
        });
        return root;
    }

    private boolean Validation() {
        error_msg = "";
        if (TextUtils.isEmpty(edtCompany.getText().toString().trim())) {
            error_msg = getString(R.string.enter_company);
            return false;
        } else if (TextUtils.isEmpty(edtFrom.getText().toString().trim())) {
            error_msg = getString(R.string.enter_from);
            return false;
        } else if (TextUtils.isEmpty(edtTo.getText().toString().trim())) {
            error_msg = getString(R.string.enter_to);
            return false;
        } else {
            return true;
        }
    }

    private void getMyInvestmentSearch() {
        String mCompany =edtCompany.getText().toString().trim();
        String mFrom =edtFrom.getText().toString().trim();
        String mTo =edtTo.getText().toString().trim();

        EndPointInterface git = ApiClient.getClient1(getActivity()).create(EndPointInterface.class);
        Call<MyInvestmentSearchResponse> call =git.getMyInvestmentSearch(mCompany,mFrom,mTo);
        call.enqueue(new Callback<MyInvestmentSearchResponse>() {
            @Override
            public void onResponse(Call<MyInvestmentSearchResponse> call, Response<MyInvestmentSearchResponse> response) {
                if (response != null && response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + "><s><" + new Gson().toJson(response.body()));
                }
            }
            @Override
            public void onFailure(Call<MyInvestmentSearchResponse> call, Throwable t) {
                Log.e("response", "error " + t.getMessage());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getMyInvestment() {
        pd = ProgressDialog.show(getActivity(), "Please Wait...");
        EndPointInterface git = ApiClient.getClient1(getActivity()).create(EndPointInterface.class);
        String XCSRF = getPreference(getActivity(), "mCsrf_token");
        Call<MyInvestmentResponce> call = git.getMyInvestment("application/json", XCSRF);
        call.enqueue(new Callback<MyInvestmentResponce>() {
            @Override
            public void onResponse(Call<MyInvestmentResponce> call, Response<MyInvestmentResponce> response) {
                Log.d(TAG, "onResponse: " + "><" + new Gson().toJson(response.body()));
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                if (response!=null) {
                    if (response != null && response.isSuccessful()) {
                        if (response.body().getUserLoginStatus() == 1) {
                            if (response.body().getData().size() > 0) {
                                adapter = new MyInvestmentAdapter(getActivity(), getActivity(), response.body().getData());
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }else {
                                Toast.makeText(getActivity(), getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            setPreference(getActivity(), "user_id", "");
                            setPreference(getActivity(), "mLogout_token", "");
                            MaxCrowdFund.getClearCookies(getActivity(), "cookies", "");
                            Toast.makeText(getActivity(), getResources().getString(R.string.session_expire), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                            mActivity.finish();
                        }
                    }
                }else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<MyInvestmentResponce> call, Throwable t) {
                Log.e("response", "error " + t.getMessage());
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.home, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.filter:
                lLayoutFilter.setVisibility(View.VISIBLE);
                /*btn_filter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utils.isInternetConnected(getActivity())) {
                            if (Validation()) {
                                getMyInvestmentSearch();
                            }else {
                                Toast.makeText(getActivity(), error_msg, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onResume(){
        super.onResume();
        // Set title bar
        ((HomeActivity) getActivity()).setActionBarTitle(getString(R.string.menu_my_investments));
    }
}