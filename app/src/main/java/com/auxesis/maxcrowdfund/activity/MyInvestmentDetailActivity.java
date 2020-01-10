package com.auxesis.maxcrowdfund.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.activity.customInterface.OnDownloadClickListener;
import com.auxesis.maxcrowdfund.adapter.InvestmentDocumentAdapter;
import com.auxesis.maxcrowdfund.adapter.MyInvestmentDetailAdapter;
import com.auxesis.maxcrowdfund.adapter.MyInvestmentDetailRepayAdapter;
import com.auxesis.maxcrowdfund.constant.ProgressDialog;
import com.auxesis.maxcrowdfund.constant.Utils;
import com.auxesis.maxcrowdfund.customUtils.ApiClient;
import com.auxesis.maxcrowdfund.customUtils.EndPointInterface;
import com.auxesis.maxcrowdfund.custommvvm.myinvestmentmodel.myinvestmentdetail.MyInvestmentDetailResponse;
import com.google.gson.Gson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.auxesis.maxcrowdfund.constant.Utils.showToast;


public class MyInvestmentDetailActivity extends AppCompatActivity implements OnDownloadClickListener {
    private static final String TAG = "MyInvestmentDetailActiv";
    TextView tv_back_arrow, tvHeaderTitle, tv_arrow_document, tv_arrow_repayment_schedule, tvInvestment, tv_investment_amount,
            tvInvested, tv_invested_amount, tv_document,tv_repayment_schedule;
    RelativeLayout rl_document_click, rl_repayment_schedule_click;
    LinearLayout ll_contant_document, ll_repayment_schedule_content;
    ProgressDialog pd;
    RecyclerView recyclerView, recyclerViewDocment,recyclerViewRepayment;

    MyInvestmentDetailAdapter adapter;
    InvestmentDocumentAdapter documentadapter;
    MyInvestmentDetailRepayAdapter repaymentadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_investment_detail);
        init();
    }

    private void init() {
        tv_back_arrow = findViewById(R.id.tv_back_arrow);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);
        tvHeaderTitle.setText(R.string.max_property_group);
        tv_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rl_document_click = findViewById(R.id.rl_document_click);
        rl_repayment_schedule_click = findViewById(R.id.rl_repayment_schedule_click);

        ll_contant_document = findViewById(R.id.ll_contant_document);
        ll_repayment_schedule_content = findViewById(R.id.ll_repayment_schedule_content);

        tv_arrow_document = findViewById(R.id.tv_arrow_document);
        tv_arrow_repayment_schedule = findViewById(R.id.tv_arrow_repayment_schedule);

        ll_contant_document.setVisibility(View.GONE);
        ll_repayment_schedule_content.setVisibility(View.GONE);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerViewDocment = findViewById(R.id.recyclerViewDocment);
        recyclerViewRepayment = findViewById(R.id.recyclerViewRepayment);

        tvInvestment = findViewById(R.id.tvInvestment);
        tv_investment_amount = findViewById(R.id.tv_investment_amount);
        tvInvested = findViewById(R.id.tvInvested);
        tv_invested_amount = findViewById(R.id.tv_invested_amount);
        tv_document = findViewById(R.id.tv_document);
        tv_repayment_schedule = findViewById(R.id.tv_repayment_schedule);

        if (Utils.isInternetConnected(getApplicationContext())) {
            getMyInvestmentDetail();
        } else {
            showToast(MyInvestmentDetailActivity.this, getResources().getString(R.string.oops_connect_your_internet));
        }

        rl_document_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ll_contant_document.isShown()) {
                    Utils.slide_up(MyInvestmentDetailActivity.this, ll_contant_document);
                    tv_arrow_document.setBackgroundResource(R.drawable.ic_arrow_down);
                    ll_contant_document.setVisibility(View.GONE);
                } else {
                    ll_contant_document.setVisibility(View.VISIBLE);
                    Utils.slide_down(MyInvestmentDetailActivity.this, ll_contant_document);
                    tv_arrow_document.setBackgroundResource(R.drawable.ic_arrow_up);
                }
            }
        });

        rl_repayment_schedule_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ll_repayment_schedule_content.isShown()) {
                    Utils.slide_up(MyInvestmentDetailActivity.this, ll_repayment_schedule_content);
                    tv_arrow_repayment_schedule.setBackgroundResource(R.drawable.ic_arrow_down);
                    ll_repayment_schedule_content.setVisibility(View.GONE);
                } else {
                    ll_repayment_schedule_content.setVisibility(View.VISIBLE);
                    Utils.slide_down(MyInvestmentDetailActivity.this, ll_repayment_schedule_content);
                    tv_arrow_repayment_schedule.setBackgroundResource(R.drawable.ic_arrow_up);
                }
            }
        });
    }

    private void getMyInvestmentDetail() {
        pd = ProgressDialog.show(MyInvestmentDetailActivity.this, "Please Wait...");
        EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
        Call<MyInvestmentDetailResponse> call = git.getMyInvestmentDetail();
        call.enqueue(new Callback<MyInvestmentDetailResponse>() {
            @Override
            public void onResponse(Call<MyInvestmentDetailResponse> call, Response<MyInvestmentDetailResponse> response) {
                if (response != null && response.isSuccessful()) {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    Log.d(TAG, "onResponse: " + "><s><" + new Gson().toJson(response.body()));
                    tvInvestment.setText(response.body().getDetails().getMainheading().getTitle());
                    tv_investment_amount.setText(response.body().getDetails().getMainheading().getData());
                    tvInvested.setText(response.body().getDetails().getInvested().getTitle());
                    tv_invested_amount.setText(response.body().getDetails().getInvested().getData());
                    tv_document.setText(response.body().getDetails().getDocuments().getHeading());

                    if (response.body().getDetails().getLoanTerms().getData().size()>0) {
                        adapter = new MyInvestmentDetailAdapter(getApplicationContext(),  response.body().getDetails().getLoanTerms().getData());
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MyInvestmentDetailActivity.this, LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                    //for document
                    if (response.body().getDetails().getDocuments().getData().size()>0) {
                        documentadapter = new InvestmentDocumentAdapter(getApplicationContext(), MyInvestmentDetailActivity.this, response.body().getDetails().getDocuments().getData());
                        RecyclerView.LayoutManager mdLayoutManager = new LinearLayoutManager(MyInvestmentDetailActivity.this, LinearLayoutManager.VERTICAL, false);
                        recyclerViewDocment.setLayoutManager(mdLayoutManager);
                        recyclerViewDocment.setItemAnimator(new DefaultItemAnimator());
                        recyclerViewDocment.setAdapter(documentadapter);
                        documentadapter.notifyDataSetChanged();
                    }
                    //For repayment_schedule
                    tv_repayment_schedule.setText(response.body().getDetails().getRepaymentSchedule().getHeading());
                    if (response.body().getDetails().getRepaymentSchedule().getData().size()>0) {
                        repaymentadapter = new MyInvestmentDetailRepayAdapter(getApplicationContext(),  response.body().getDetails().getRepaymentSchedule().getData());
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MyInvestmentDetailActivity.this, LinearLayoutManager.VERTICAL, false);
                        recyclerViewRepayment.setLayoutManager(mLayoutManager);
                        recyclerViewRepayment.setItemAnimator(new DefaultItemAnimator());
                        recyclerViewRepayment.setAdapter(repaymentadapter);
                        repaymentadapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<MyInvestmentDetailResponse> call, Throwable t) {
                Log.e("response", "error " + t.getMessage());
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                Toast.makeText(MyInvestmentDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onMyDownloadClick(String mdownload) {
        //Toast.makeText(this, "hiiiii", Toast.LENGTH_SHORT).show();
    }
}
