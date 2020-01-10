package com.auxesis.maxcrowdfund.mvvm.ui.dashborad;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
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
import com.auxesis.maxcrowdfund.activity.DashboardActivity;
import com.auxesis.maxcrowdfund.adapter.AccountBalanceAdapter;
import com.auxesis.maxcrowdfund.adapter.PortFolioAdapter;
import com.auxesis.maxcrowdfund.constant.ProgressDialog;
import com.auxesis.maxcrowdfund.constant.Utils;
import com.auxesis.maxcrowdfund.databinding.FragmentDashboardBinding;
import com.auxesis.maxcrowdfund.model.AccountBalanceModel;
import com.auxesis.maxcrowdfund.model.PortfolioModel;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import static com.auxesis.maxcrowdfund.constant.APIUrl.GER_ACCOUNT_BALANCE;
import static com.auxesis.maxcrowdfund.constant.APIUrl.GER_PORTFOLIO;
import static com.auxesis.maxcrowdfund.constant.Utils.showToast;

public class DashboardFragment extends Fragment {
    private static final String TAG = "DashboardFragment";
    private DashboardViewModel dashboardViewModel;
    ProgressDialog pd;
    FragmentDashboardBinding binding;
    AccountBalanceAdapter accountbalanceadapter;
    PortFolioAdapter portFolioAdapter;
    List<AccountBalanceModel> accountlist = new ArrayList<>();
    List<AccountBalanceModel> depositedlist = new ArrayList<>();
    List<AccountBalanceModel> withdrawnlist = new ArrayList<>();
    List<AccountBalanceModel> investedlist = new ArrayList<>();
    List<AccountBalanceModel> pendinglist = new ArrayList<>();
    List<AccountBalanceModel> feeslist = new ArrayList<>();
    List<AccountBalanceModel> repaidlist = new ArrayList<>();
    List<AccountBalanceModel> interest_paidlist = new ArrayList<>();
    List<AccountBalanceModel> reservedlist = new ArrayList<>();
    List<AccountBalanceModel> written_offlist = new ArrayList<>();
    List<AccountBalanceModel> mpg_purchaselist = new ArrayList<>();

    List<PortfolioModel> Portfoliolist = new ArrayList<>();
    List<PortfolioModel> no_arrears = new ArrayList<>();
    List<PortfolioModel> arrears_1 = new ArrayList<>();
    List<PortfolioModel> arrears_2 = new ArrayList<>();
    List<PortfolioModel> arrears_3 = new ArrayList<>();
    List<PortfolioModel> arrears_4 = new ArrayList<>();
    List<PortfolioModel> reserved = new ArrayList<>();



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        binding = DataBindingUtil.inflate( inflater,R.layout.fragment_dashboard, container, false);
        View root = binding.getRoot();
         //here data must be an instance of the class DashboardViewModel
        binding.setDashboard(dashboardViewModel);
        binding.setLifecycleOwner(this);
      //  binding.recyViewAccBalance.setLayoutManager(new LinearLayoutManager(getActivity()));
      //  binding.recyViewAccBalance.setHasFixedSize(true);

       /* dashboardViewModel.getAccountBalance().observe(this, new Observer<AccountBalanceResponse>() {
            @Override
            public void onChanged(AccountBalanceResponse accountBalanceResponse) {
                Log.d(TAG, "onChanged: "+accountBalanceResponse.getBalance().getHeading());
               *//* deposited.sadd(accountBalanceResponse.getBalance().getData().getDeposited());
                arrayList.addAll(deposited);
                accountbalanceadapter =new DashboardAccBalanceAdapter(getActivity(),arrayList);
                binding.recyViewAccBalance.setAdapter(accountbalanceadapter);*//*
               // accountbalanceadapter.setAccountBalanceList(accountBalanceResponse.getBalance().getData().getDeposited());

            }
        });
*/

        if (Utils.isInternetConnected(getActivity())) {
            getAccountBalance();
            getPortFolio();
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
        }

        return root;


    }
    private void getAccountBalance() {
        pd = ProgressDialog.show(getActivity(), "Please Wait...");
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, GER_ACCOUNT_BALANCE, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        Log.d(TAG, "onResponse: " + response);

                        JSONObject jsonObjMain = new JSONObject(response.toString());
                        if (jsonObjMain != null) {
                            JSONObject jsonObject = jsonObjMain.getJSONObject("balance");
                            accountlist.clear();
                            depositedlist.clear();
                            withdrawnlist.clear();
                            investedlist.clear();
                            pendinglist.clear();
                            feeslist.clear();
                            repaidlist.clear();
                            interest_paidlist.clear();
                            reservedlist.clear();
                            written_offlist.clear();
                            mpg_purchaselist.clear();

                            //For deposit
                            if (jsonObject != null) {
                                AccountBalanceModel balanceModel = new AccountBalanceModel();
                                balanceModel.setmTitle(jsonObject.getString("heading"));
                                balanceModel.setmValue("");
                                balanceModel.setmType("");
                                accountlist.add(balanceModel);
                            }
                            JSONObject jsonData = jsonObject.getJSONObject("data");
                            JSONObject jsondeposited = jsonData.getJSONObject("deposited");
                            //For deposit
                            if (jsondeposited != null) {
                                AccountBalanceModel accountBalanceModel = new AccountBalanceModel();
                                accountBalanceModel.setmTitle(jsondeposited.getString("title"));
                                accountBalanceModel.setmValue(jsondeposited.getString("value"));
                                accountBalanceModel.setmType(jsondeposited.getString("type"));
                                depositedlist.add(accountBalanceModel);
                            }
                            //For withdrawn
                            JSONObject jsonwithdrawn = jsonData.getJSONObject("withdrawn");
                            if (jsonwithdrawn != null) {
                                AccountBalanceModel withdrawnModel = new AccountBalanceModel();
                                withdrawnModel.setmTitle(jsonwithdrawn.getString("title"));
                                withdrawnModel.setmValue(jsonwithdrawn.getString("value"));
                                withdrawnModel.setmType(jsonwithdrawn.getString("type"));
                                withdrawnlist.add(withdrawnModel);
                            }

                            //For invested
                            JSONObject jsoninvested = jsonData.getJSONObject("invested");
                            if (jsoninvested != null) {
                                AccountBalanceModel investedModel = new AccountBalanceModel();
                                investedModel.setmTitle(jsoninvested.getString("title"));
                                investedModel.setmValue(jsoninvested.getString("value"));
                                investedModel.setmType(jsoninvested.getString("type"));
                                investedlist.add(investedModel);
                            }
                            //For pending
                            JSONObject jsonpendinglist = jsonData.getJSONObject("pending");
                            if (jsonpendinglist != null) {
                                AccountBalanceModel pendinglistModel = new AccountBalanceModel();
                                pendinglistModel.setmTitle(jsonpendinglist.getString("title"));
                                pendinglistModel.setmValue(jsonpendinglist.getString("value"));
                                pendinglistModel.setmType(jsonpendinglist.getString("type"));
                                pendinglist.add(pendinglistModel);
                            }

                            //For fees
                            JSONObject jsonfeeslist = jsonData.getJSONObject("fees");
                            if (jsonfeeslist != null) {
                                AccountBalanceModel feeslistModel = new AccountBalanceModel();
                                feeslistModel.setmTitle(jsonfeeslist.getString("title"));
                                feeslistModel.setmValue(jsonfeeslist.getString("value"));
                                feeslistModel.setmType(jsonfeeslist.getString("type"));
                                feeslist.add(feeslistModel);
                            }

                            //For repaidlist
                            JSONObject jsonrepaidlist = jsonData.getJSONObject("repaid");
                            if (jsonrepaidlist != null) {
                                AccountBalanceModel repaidModel = new AccountBalanceModel();
                                repaidModel.setmTitle(jsonrepaidlist.getString("title"));
                                repaidModel.setmValue(jsonrepaidlist.getString("value"));
                                repaidModel.setmType(jsonrepaidlist.getString("type"));
                                repaidlist.add(repaidModel);
                            }

                            //For invested
                            JSONObject jsoninterest_paid = jsonData.getJSONObject("interest_paid");
                            if (jsoninterest_paid != null) {
                                AccountBalanceModel interest_paidModel = new AccountBalanceModel();
                                interest_paidModel.setmTitle(jsoninterest_paid.getString("title"));
                                interest_paidModel.setmValue(jsoninterest_paid.getString("value"));
                                interest_paidModel.setmType(jsoninterest_paid.getString("type"));
                                interest_paidlist.add(interest_paidModel);
                            }

                            //For reserved
                            JSONObject jsonreserved = jsonData.getJSONObject("reserved");
                            if (jsonreserved != null) {
                                AccountBalanceModel reservedModel = new AccountBalanceModel();
                                reservedModel.setmTitle(jsonreserved.getString("title"));
                                reservedModel.setmValue(jsonreserved.getString("value"));
                                reservedModel.setmType(jsonreserved.getString("type"));
                                reservedlist.add(reservedModel);
                            }


                            //For reserved
                            JSONObject jsonwritten_off = jsonData.getJSONObject("written_off");
                            if (jsonwritten_off != null) {
                                AccountBalanceModel written_offModel = new AccountBalanceModel();
                                written_offModel.setmTitle(jsonwritten_off.getString("title"));
                                written_offModel.setmValue(jsonwritten_off.getString("value"));
                                written_offModel.setmType(jsonwritten_off.getString("type"));
                                written_offlist.add(written_offModel);
                            }

                            //For reserved
                            JSONObject jsonmpg_purchase = jsonData.getJSONObject("mpg_purchase");
                            if (jsonmpg_purchase != null) {
                                AccountBalanceModel mpg_purchaseModel = new AccountBalanceModel();
                                mpg_purchaseModel.setmTitle(jsonmpg_purchase.getString("title"));
                                mpg_purchaseModel.setmValue(jsonmpg_purchase.getString("value"));
                                mpg_purchaseModel.setmType(jsonmpg_purchase.getString("type"));
                                mpg_purchaselist.add(mpg_purchaseModel);
                            }

                            accountlist.addAll(depositedlist);
                            accountlist.addAll(withdrawnlist);
                            accountlist.addAll(investedlist);
                            accountlist.addAll(pendinglist);
                            accountlist.addAll(feeslist);
                            accountlist.addAll(repaidlist);
                            accountlist.addAll(interest_paidlist);
                            accountlist.addAll(reservedlist);
                            accountlist.addAll(written_offlist);
                            accountlist.addAll(mpg_purchaselist);

                            if (accountlist.size() > 0) {
                                accountbalanceadapter = new AccountBalanceAdapter(getActivity(), accountlist);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                binding.recyViewAccBalance.setLayoutManager(mLayoutManager);
                                binding.recyViewAccBalance.setItemAnimator(new DefaultItemAnimator());
                                binding.recyViewAccBalance.setAdapter(accountbalanceadapter);
                                accountbalanceadapter.notifyDataSetChanged();
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
                    //showToast(MyInvestmentDetailActivity.this, getResources().getString(R.string.something_went));
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

    private void getPortFolio() {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, GER_PORTFOLIO, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Log.d(TAG, "onResponse: " + response);
                        JSONObject jsonObjMain = new JSONObject(response.toString());
                        if (jsonObjMain != null) {
                            JSONObject jsonObject = jsonObjMain.getJSONObject("balance");
                            Portfoliolist.clear();
                            no_arrears.clear();
                            arrears_1.clear();
                            arrears_2.clear();
                            arrears_3.clear();
                            arrears_4.clear();
                            reserved.clear();
                            //For Portfolio
                            if (jsonObject != null) {
                                PortfolioModel portfolioModel = new PortfolioModel();
                                portfolioModel.setmPortfolioTitle(jsonObject.getString("heading"));
                                portfolioModel.setmPortfolioValue("");
                                Portfoliolist.add(portfolioModel);
                            }

                            JSONObject jsonData = jsonObject.getJSONObject("data");
                            JSONObject jsonno_arrears = jsonData.getJSONObject("no_arrears");
                            //For no_arrears
                            if (jsonno_arrears != null) {
                                PortfolioModel no_arreresModel = new PortfolioModel();
                                no_arreresModel.setmPortfolioTitle(jsonno_arrears.getString("title"));
                                no_arreresModel.setmPortfolioValue(jsonno_arrears.getString("value"));
                                no_arrears.add(no_arreresModel);
                            }

                            //For arrears4589
                            JSONObject jsonarrears_1  = jsonData.getJSONObject("arrears4589");
                            if (jsonarrears_1 != null) {
                                PortfolioModel no_arreresModel_1 = new PortfolioModel();
                                no_arreresModel_1.setmPortfolioTitle(jsonarrears_1.getString("title"));
                                no_arreresModel_1.setmPortfolioValue(jsonarrears_1.getString("value"));
                                arrears_1.add(no_arreresModel_1);
                            }

                            //For arrears90179
                            JSONObject jsonarrears_2 = jsonData.getJSONObject("arrears90179");
                            if (jsonarrears_2 != null) {
                                PortfolioModel no_arreresModel_2 = new PortfolioModel();
                                no_arreresModel_2.setmPortfolioTitle(jsonarrears_2.getString("title"));
                                no_arreresModel_2.setmPortfolioValue(jsonarrears_2.getString("value"));
                                arrears_2.add(no_arreresModel_2);
                            }
                            //For arrears180364
                            JSONObject jsonarrears_3 = jsonData.getJSONObject("arrears180364");
                            if (jsonarrears_3 != null) {
                                PortfolioModel no_arreresModel_3 = new PortfolioModel();
                                no_arreresModel_3.setmPortfolioTitle(jsonarrears_3.getString("title"));
                                no_arreresModel_3.setmPortfolioValue(jsonarrears_3.getString("value"));
                                arrears_3.add(no_arreresModel_3);
                            }

                            //For fees
                            JSONObject jsonarrears_4 = jsonData.getJSONObject("arrears365");
                            if (jsonarrears_4 != null) {
                                PortfolioModel no_arreresModel_4 = new PortfolioModel();
                                no_arreresModel_4.setmPortfolioTitle(jsonarrears_4.getString("title"));
                                no_arreresModel_4.setmPortfolioValue(jsonarrears_4.getString("value"));
                                arrears_4.add(no_arreresModel_4);
                            }

                            //For repaidlist
                            JSONObject jsonreserved = jsonData.getJSONObject("reserved");
                            if (jsonreserved != null) {
                                PortfolioModel reservedModel = new PortfolioModel();
                                reservedModel.setmPortfolioTitle(jsonreserved.getString("title"));
                                reservedModel.setmPortfolioValue(jsonreserved.getString("value"));
                                reserved.add(reservedModel);
                            }

                            Portfoliolist.addAll(no_arrears);
                            Portfoliolist.addAll(arrears_1);
                            Portfoliolist.addAll(arrears_2);
                            Portfoliolist.addAll(arrears_3);
                            Portfoliolist.addAll(arrears_4);
                            Portfoliolist.addAll(reserved);
                            if (Portfoliolist.size() > 0) {
                                portFolioAdapter = new PortFolioAdapter(getActivity(), Portfoliolist);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                                binding.recyViewPortFolio.setLayoutManager(mLayoutManager);
                                binding.recyViewPortFolio.setItemAnimator(new DefaultItemAnimator());
                                binding.recyViewPortFolio.setAdapter(portFolioAdapter);
                                portFolioAdapter.notifyDataSetChanged();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    showToast(getActivity(), getResources().getString(R.string.something_went));
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
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}