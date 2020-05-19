package maxcrowdfund.com.mvvm.ui.myinvestments;

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
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import maxcrowdfund.com.R;
import maxcrowdfund.com.constant.MaxCrowdFund;
import maxcrowdfund.com.mvvm.activity.LoginActivity;
import maxcrowdfund.com.mvvm.ui.home.homeDetail.adapter.MyInvestmentAdapter;
import maxcrowdfund.com.constant.ProgressDialog;
import maxcrowdfund.com.constant.Utils;
import maxcrowdfund.com.mvvm.activity.HomeActivity;
import maxcrowdfund.com.mvvm.ui.myinvestments.model.MyInvestmentResponce;
import maxcrowdfund.com.restapi.ApiClient;
import maxcrowdfund.com.restapi.EndPointInterface;
import com.google.gson.Gson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyInvestmentsFragment extends Fragment {
    private static final String TAG = "MyInvestmentsFragment";
    RecyclerView recyclerView;
    MyInvestmentAdapter adapter;
    LinearLayout lLayoutFilter;
    EditText edtCompany, edtFrom, edtTo;
    Button btn_filter, btn_clear;
    String error_msg = "";
    TextView tvNoDataFound;
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
        tvNoDataFound = root.findViewById(R.id.tvNoDataFound);
        edtCompany = root.findViewById(R.id.edtCompany);
        edtFrom = root.findViewById(R.id.edtFrom);
        edtTo = root.findViewById(R.id.edtTo);
        recyclerView = root.findViewById(R.id.recyView);
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
                    } else {
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

    private void getMyInvestment() {
        pd = ProgressDialog.show(getActivity(), "Please Wait...");
        EndPointInterface git = ApiClient.getClient1(getActivity()).create(EndPointInterface.class);
        String XCSRF = Utils.getPreference(getActivity(), "mCsrf_token");
        Call<MyInvestmentResponce> call = git.getMyInvestment("application/json", XCSRF);
        call.enqueue(new Callback<MyInvestmentResponce>() {
            @Override
            public void onResponse(Call<MyInvestmentResponce> call, Response<MyInvestmentResponce> response) {
                Log.d(TAG, "onResponse: " + "><" + new Gson().toJson(response.body()));
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                if (response.code() == 200) {
                    if (response != null && response.isSuccessful()) {
                        if (response.body().getUserLoginStatus() == 1) {
                            if (response.body().getTotal() != 0) {
                                tvNoDataFound.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                if (response.body().getData().size() > 0) {
                                    adapter = new MyInvestmentAdapter(getActivity(), getActivity(), response.body().getData());
                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                    recyclerView.setLayoutManager(mLayoutManager);
                                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                                    recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                                    recyclerView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                }
                            } else {
                                tvNoDataFound.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
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
                    }
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.something_went), Toast.LENGTH_SHORT).show();
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

    public void onResume() {
        super.onResume();
        // Set title bar
        ((HomeActivity) getActivity()).setActionBarTitle(getString(R.string.menu_my_investments));
    }
}