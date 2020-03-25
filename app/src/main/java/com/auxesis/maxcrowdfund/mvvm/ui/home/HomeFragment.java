package com.auxesis.maxcrowdfund.mvvm.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.mvvm.ui.home.homeAdapter.InvestmentOppHomeAdapter;
import com.auxesis.maxcrowdfund.constant.APIUrl;
import com.auxesis.maxcrowdfund.constant.PaginationListener;
import com.auxesis.maxcrowdfund.constant.ProgressDialog;
import com.auxesis.maxcrowdfund.mvvm.ui.home.homemodel.InvestmentOppHomeModel;
import com.auxesis.maxcrowdfund.mvvm.activity.HomeActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import static com.auxesis.maxcrowdfund.constant.PaginationListener.PAGE_START;
import static com.auxesis.maxcrowdfund.constant.PaginationListener.VISIBLE_THRESHOLD;
import static com.auxesis.maxcrowdfund.constant.Utils.isInternetConnected;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    TextView tvNoRecordFound;
    RecyclerView recyclerView;
    InvestmentOppHomeAdapter adapter;
    ProgressDialog pd;
    List<InvestmentOppHomeModel> list_1 = new ArrayList<>();
    List<InvestmentOppHomeModel> list_2 = new ArrayList<>();
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage = 2;
    private int mTotalpage = 0;
    private boolean isLoading = false;
    LinearLayoutManager layoutManager;
    int APICount = 0;
    int mTotal = 0;
    String mUrl = "";
    private int TOTAL_PAGES = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        APICount = 0;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        tvNoRecordFound = root.findViewById(R.id.tvNoRecordFound);
        recyclerView = root.findViewById(R.id.recyclerView);

        if (isInternetConnected(getActivity())) {
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            adapter = new InvestmentOppHomeAdapter(getContext(), getActivity(), new ArrayList<>());
            recyclerView.setAdapter(adapter);
            APIUrl.investStatus = "active";
            getListingApi();
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
        }

        // add scroll listener while user reach in bottom load more will call
        recyclerView.addOnScrollListener(new PaginationListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                if (isInternetConnected(getActivity())) {
                    getListingApi();
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        return root;
    }

    private void getListingApi() {
        pd = ProgressDialog.show(getActivity(), "Please Wait...");
        mUrl = APIUrl.GER_LISTING + APIUrl.investment_status + APIUrl.investStatus + APIUrl.page + APICount;
        Log.d(TAG, "getListingApi--" + mUrl);
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, mUrl, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        Log.d(TAG, "onResponse: " + "APICount---->>>>" + APICount);
                        APICount++;
                        JSONObject jsonObject = new JSONObject(response.toString());
                        if (jsonObject != null) {
                            mTotal = jsonObject.getInt("total");
                            mTotalpage = (mTotal / VISIBLE_THRESHOLD);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            list_1.clear();
                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    InvestmentOppHomeModel myListModel = new InvestmentOppHomeModel();
                                    myListModel.setId(jsonArray.getJSONObject(i).getInt("id"));
                                    myListModel.setmTitle(jsonArray.getJSONObject(i).getString("title"));
                                    myListModel.setInterest_pa(jsonArray.getJSONObject(i).getString("interest_pa"));
                                    myListModel.setRisk_class(jsonArray.getJSONObject(i).getString("risk_class"));
                                    myListModel.setAmount(jsonArray.getJSONObject(i).getInt("amount"));
                                    myListModel.setCurrency(jsonArray.getJSONObject(i).getString("currency"));
                                    myListModel.setCurrency_symbol(jsonArray.getJSONObject(i).getString("currency_symbol"));
                                    myListModel.setFilled(jsonArray.getJSONObject(i).getInt("filled"));
                                    myListModel.setNo_of_investors(jsonArray.getJSONObject(i).getInt("no_of_investors"));
                                    myListModel.setAmount_left(jsonArray.getJSONObject(i).getInt("amount_left"));
                                    myListModel.setMonths(jsonArray.getJSONObject(i).getInt("months"));
                                    myListModel.setType(jsonArray.getJSONObject(i).getString("type"));
                                    myListModel.setLocation(jsonArray.getJSONObject(i).getString("location"));
                                    myListModel.setLocation_flag_img(jsonArray.getJSONObject(i).getString("location_flag_img"));
                                    myListModel.setInvestment_status(jsonArray.getString(i));
                                    myListModel.setInvestment_status("active");
                                    list_1.add(myListModel);
                                }
                                if (currentPage != PAGE_START) {
                                    adapter.removeLoading();
                                }
                                adapter.addItems(list_1);
                                if (currentPage < totalPage) {
                                    Log.d(TAG, "onResponse: "+"current---------"+String.valueOf(currentPage));
                                    adapter.addLoading();
                                } else if (currentPage == totalPage) {
                                    list_1.clear();
                                    list_2.clear();
                                    InvestmentOppHomeModel myListModel = new InvestmentOppHomeModel();
                                    myListModel.setAverage_return(jsonObject.getInt("average_return"));
                                    myListModel.setTotal_raised(jsonObject.getInt("total_raised"));
                                    myListModel.setActive_investors(jsonObject.getInt("active_investors"));
                                    myListModel.setInvestment_status("expired");
                                    list_2.add(myListModel);
                                    list_1.addAll(list_2);
                                    adapter.addItems(list_1);
                                    APIUrl.investStatus = "expired";
                                    APICount = 0;
                                    mUrl = APIUrl.GER_LISTING + APIUrl.investment_status + APIUrl.investStatus + APIUrl.page + APICount;
                                    adapter.addLoading();
                                } else if (currentPage <= 3) {
                                    mUrl = APIUrl.GER_LISTING + APIUrl.investment_status + APIUrl.investStatus + APIUrl.page + APICount;
                                    adapter.addLoading();
                                } else {
                                    isLastPage = true;
                                }
                                isLoading = false;
                            } else {
                                Toast.makeText(getActivity(), getResources().getString(R.string.something_went), Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            tvNoRecordFound.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    String json = null;
                    String Message;
                    NetworkResponse response = error.networkResponse;
                    if (response != null && response.data != null) {
                        try {
                            JSONObject errorObj = new JSONObject(new String(response.data));
                            if (response.statusCode == 400 || response.statusCode == 405 || response.statusCode == 500) {
                                Toast.makeText(getActivity(), getResources().getString(R.string.something_went), Toast.LENGTH_SHORT).show();
                            } else if (response.statusCode == 401) {

                            } else if (response.statusCode == 422) {
                                Toast.makeText(getActivity(), getResources().getString(R.string.please_try_again), Toast.LENGTH_SHORT).show();
                            } else if (response.statusCode == 503) {
                                Toast.makeText(getActivity(), getResources().getString(R.string.server_down), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), getResources().getString(R.string.please_try_again), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (error instanceof NoConnectionError) {
                            Toast.makeText(getActivity(), getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(getActivity(), getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
                        } else if (error instanceof TimeoutError) {
                            try {
                                if (error.networkResponse == null) {
                                    if (error.getClass().equals(TimeoutError.class)) {
                                        // Show timeout error message
                                        Toast.makeText(getActivity(), getResources().getString(R.string.timed_out), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (error instanceof AuthFailureError) {
                        } else if (error instanceof ServerError) {
                        } else if (error instanceof ParseError) {
                        }
                    }
                    error.printStackTrace();
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json";
                }
            };
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            queue.add(jsonObjectRequest);
        } catch (Error e) {
            if (pd != null && pd.isShowing()) {
                pd.dismiss();
            }
            e.printStackTrace();
        } catch (Exception e) {
            if (pd != null && pd.isShowing()) {
                pd.dismiss();
            }
            e.printStackTrace();
        }
    }

    public void onResume() {
        super.onResume();
        // Set title bar
        ((HomeActivity) getActivity()).setActionBarTitle(getString(R.string.menu_investments_opportunity));
    }
}