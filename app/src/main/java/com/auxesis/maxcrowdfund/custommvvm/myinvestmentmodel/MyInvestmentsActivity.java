package com.auxesis.maxcrowdfund.custommvvm.myinvestmentmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.adapter.MyInvestmentAdapter;
import com.auxesis.maxcrowdfund.constant.ProgressDialog;
import com.auxesis.maxcrowdfund.constant.Utils;
import com.auxesis.maxcrowdfund.customUtils.ApiClient;
import com.auxesis.maxcrowdfund.customUtils.EndPointInterface;
import com.google.gson.Gson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.auxesis.maxcrowdfund.constant.Utils.showToast;


public class MyInvestmentsActivity extends AppCompatActivity {
    private static final String TAG = "MyInvestmentsActivity";
    TextView tv_back_arrow, tvHeaderTitle, tv_filter;
    RecyclerView recyclerView;
    MyInvestmentAdapter adapter;
    LinearLayout lLayoutFilter;
    EditText edtCompany, edtFrom, edtTo;
    Button btn_filter, btn_clear;
    String error_msg = "";
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_investments);

        init();
    }

    private void init() {
        tv_back_arrow = findViewById(R.id.tv_back_arrow);
        tv_filter = findViewById(R.id.tv_filter);
        tv_filter.setVisibility(View.VISIBLE);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setText(R.string.menu_my_investments);
        tv_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        edtCompany = findViewById(R.id.edtCompany);
        edtFrom = findViewById(R.id.edtFrom);
        edtTo = findViewById(R.id.edtTo);

        recyclerView = findViewById(R.id.recyclerView);
        lLayoutFilter = findViewById(R.id.lLayoutFilter);
        lLayoutFilter.setVisibility(View.GONE);
        tv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lLayoutFilter.setVisibility(View.VISIBLE);
            }
        });

        if (Utils.isInternetConnected(getApplicationContext())) {
            getMyInvestment();
        } else {
            showToast(MyInvestmentsActivity.this, getResources().getString(R.string.oops_connect_your_internet));
        }

        btn_filter = findViewById(R.id.btn_filter);
        btn_clear = findViewById(R.id.btn_clear);
        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isInternetConnected(getApplicationContext())) {
                    if (Validation()) {
                        getMyInvestmentSearch();
                    }else {
                        showToast(MyInvestmentsActivity.this, error_msg);
                    }
                } else {
                    showToast(MyInvestmentsActivity.this, getResources().getString(R.string.oops_connect_your_internet));
                }
            }
        });

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtCompany.setText("");
                edtFrom.setText("");
                edtTo.setText("");
                showToast(MyInvestmentsActivity.this, "Data cleared successfully");
            }
        });
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

        EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
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
                Toast.makeText(MyInvestmentsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getMyInvestment() {
        pd = ProgressDialog.show(MyInvestmentsActivity.this, "Please Wait...");
        EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
        Call<MyInvestmentResponce> call = git.getMyInvestment();
        call.enqueue(new Callback<MyInvestmentResponce>() {
            @Override
            public void onResponse(Call<MyInvestmentResponce> call, Response<MyInvestmentResponce> response) {
                if (response != null && response.isSuccessful()) {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    if (response.body().getData().size() > 0) {
                        adapter = new MyInvestmentAdapter(MyInvestmentsActivity.this, response.body().getData());
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MyInvestmentsActivity.this);
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.addItemDecoration(new DividerItemDecoration(MyInvestmentsActivity.this, LinearLayoutManager.VERTICAL));
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<MyInvestmentResponce> call, Throwable t) {
                Log.e("response", "error " + t.getMessage());
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                Toast.makeText(MyInvestmentsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
