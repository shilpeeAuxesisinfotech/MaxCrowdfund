package com.auxesis.maxcrowdfund.mvvm.ui.contactinformation;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
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
import com.auxesis.maxcrowdfund.adapter.MyListAdapter;
import com.auxesis.maxcrowdfund.constant.APIUrl;
import com.auxesis.maxcrowdfund.constant.PaginationListener;
import com.auxesis.maxcrowdfund.constant.ProgressDialog;
import com.auxesis.maxcrowdfund.model.MyListModel;
import com.auxesis.maxcrowdfund.mvvm.ui.home.homeAdapter.InvestmentOppAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.auxesis.maxcrowdfund.constant.PaginationListener.PAGE_START;
import static com.auxesis.maxcrowdfund.constant.PaginationListener.VISIBLE_THRESHOLD;
import static com.auxesis.maxcrowdfund.constant.Utils.isInternetConnected;
import static com.auxesis.maxcrowdfund.constant.Utils.showToast;


public class ContactInformation extends Fragment {
    private static final String TAG = "ContactInformation";
    private ContactInformationViewModel shareViewModel;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ProgressDialog pd;

    private int currentPage = PAGE_START;
    private int totalPage = 2;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    int APICount = 0;
    int mTotal = 0;
    String mUrl = "";

    List<MyListModel> arrayListM = new ArrayList<>();
    List<MyListModel> arrayListData = new ArrayList<>();
    List<MyListModel> mergedList = new ArrayList<>();

    CustomAdapter customAdapter;
    private int mTotalpage = 0;
    private int TOTAL_PAGES = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        shareViewModel = ViewModelProviders.of(this).get(ContactInformationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_contact_information, container, false);
        recyclerView = root.findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

      /*  if (isInternetConnected(getActivity())) {
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            customAdapter = new CustomAdapter(getActivity(), getActivity(), new ArrayList<>());
            recyclerView.setAdapter(customAdapter);
            APIUrl.investStatus = "active";
            APICount = 0;
            getListingApi();
        } else {
            showToast(getActivity(), getResources().getString(R.string.oops_connect_your_internet));
        }

        recyclerView.addOnScrollListener(new PaginationListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                if (isInternetConnected(getActivity())) {
                    getListingApi();
                } else {
                    showToast(getActivity(), getResources().getString(R.string.oops_connect_your_internet));
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
*/
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

                        APICount++;

                        JSONObject jsonObject = new JSONObject(response.toString());
                        if (jsonObject != null) {
                            mTotal = jsonObject.getInt("total");

                            mTotalpage = (mTotal / VISIBLE_THRESHOLD);

                           /* arrayListM.clear();
                            MyListModel listModel = new MyListModel();
                            listModel.setAverage_return(jsonObject.getInt("average_return"));
                            listModel.setTotal_raised(jsonObject.getInt("total_raised"));
                            listModel.setActive_investors(jsonObject.getInt("active_investors"));
                            listModel.setInvestment_status("expired");
                            arrayListM.add(listModel);*/

                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            arrayListData.clear();
                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    MyListModel myListModel = new MyListModel();
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
                                    myListModel.setInvestment_status("active");
                                    arrayListData.add(myListModel);
                                }
                                mergedList.clear();
                                mergedList.addAll(arrayListM);
                                mergedList.addAll(arrayListData);

                                Log.d(TAG, "onResponse: " + "size=======" + String.valueOf(mergedList.size()));

                                if (currentPage != PAGE_START) {
                                    customAdapter.removeLoading();
                                }

                                customAdapter.addItems(mergedList);
                                if (currentPage < mTotalpage) {
                                    customAdapter.addLoading();
                                } else if (currentPage == mTotalpage) {

                                    arrayListM.clear();
                                    MyListModel listModel = new MyListModel();
                                    listModel.setAverage_return(jsonObject.getInt("average_return"));
                                    listModel.setTotal_raised(jsonObject.getInt("total_raised"));
                                    listModel.setActive_investors(jsonObject.getInt("active_investors"));
                                    listModel.setInvestment_status("expired");
                                    arrayListM.add(listModel);


                                    // arrayListData.addAll(list_2);
                                    //customAdapter.addItems(arrayListData);


                                    APIUrl.investStatus = "expired";
                                    APICount = 0;
                                    mUrl = APIUrl.GER_LISTING + APIUrl.investment_status + APIUrl.investStatus + APIUrl.page + APICount;
                                    customAdapter.addLoading();
                                } else if (currentPage <= 3) {
                                    mUrl = APIUrl.GER_LISTING + APIUrl.investment_status + APIUrl.investStatus + APIUrl.page + APICount;
                                    customAdapter.addLoading();
                                } else {
                                    isLastPage = true;
                                }
                                isLoading = false;
                            } else {
                                Toast.makeText(getActivity(), getResources().getString(R.string.something_went), Toast.LENGTH_SHORT).show();
                            }
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
                    //showToast(MainActivity.this, getResources().getString(R.string.something_went));
                    String json = null;
                    String Message;
                    NetworkResponse response = error.networkResponse;
                    if (response != null && response.data != null) {
                        try {
                            JSONObject errorObj = new JSONObject(new String(response.data));
                            if (response.statusCode == 400 || response.statusCode == 405 || response.statusCode == 500) {
                                showToast(getActivity(), getResources().getString(R.string.something_went));
                            } else if (response.statusCode == 401) {

                            } else if (response.statusCode == 422) {
                                //  json = trimMessage(new String(response.data));
                                if (json != "" && json != null) {
                                    // displayMessage(json);
                                } else {
                                    showToast(getActivity(), getResources().getString(R.string.please_try_again));
                                }
                            } else if (response.statusCode == 503) {
                                showToast(getActivity(), getResources().getString(R.string.server_down));
                            } else {
                                showToast(getActivity(), getResources().getString(R.string.please_try_again));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (error instanceof NoConnectionError) {
                            showToast(getActivity(), getResources().getString(R.string.oops_connect_your_internet));
                        } else if (error instanceof NetworkError) {
                            showToast(getActivity(), getResources().getString(R.string.oops_connect_your_internet));
                        } else if (error instanceof TimeoutError) {
                            try {
                                if (error.networkResponse == null) {
                                    if (error.getClass().equals(TimeoutError.class)) {
                                        // Show timeout error message
                                        showToast(getActivity(), getResources().getString(R.string.timed_out));
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (error instanceof AuthFailureError) {
                            Log.d(TAG, "onErrorResponse: " + "AuthFailureError" + AuthFailureError.class);
                        } else if (error instanceof ServerError) {
                            Log.d(TAG, "onErrorResponse: " + "ServerError" + ServerError.class);
                            //Indicates that the server responded with a error response
                        } else if (error instanceof ParseError) {
                            Log.d(TAG, "onErrorResponse: " + "ParseError" + ParseError.class);
                            // Indicates that the server response could not be parsed
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



    /*private void getMydata() {
        pd = ProgressDialog.show(getActivity(), "Please Wait...");
        EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
        Call<InvestmentOppResponse> call = git.getMyInvestmentOpp("json", investment_status, pageCount);
        call.enqueue(new Callback<InvestmentOppResponse>() {
            @Override
            public void onResponse(Call<InvestmentOppResponse> call, retrofit2.Response<InvestmentOppResponse> response) {
                if (response != null && response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + "><s><" + new Gson().toJson(response.body()));
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    pageCount++;
                    if (currentPage != PAGE_START) {
                        investmentOppAdapter.removeLoading();
                    }
                    if (response.body().getData().size() > 0) {
                        investmentOppAdapter.addItems(response.body().getData());

                        if (currentPage < totalPage) {
                            investmentOppAdapter.addLoading();
                            Log.d(TAG, "onResponse: " + ">IF<currentPage><><" + String.valueOf(currentPage) + "><mTotalpage><><" + String.valueOf(mTotalpage));
                        } else if (currentPage == totalPage) {
                            Log.d(TAG, "onResponse: " + "Current Page------------" + currentPage);
                            investmentOppAdapter.addItems(response.body().getData());

                            pageCount = 0;
                            investment_status = "expired";
                            investmentOppAdapter.addLoading();
                        } else if (currentPage <= 3) {
                            investmentOppAdapter.addLoading();
                            // APICount = 0;
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<InvestmentOppResponse> call, Throwable t) {
                Log.e("response", "error " + t.getMessage());
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/
}