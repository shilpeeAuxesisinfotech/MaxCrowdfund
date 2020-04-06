package com.auxesis.maxcrowdfund.mvvm.ui.home;

import android.app.Activity;
import android.content.Intent;
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

import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.constant.MaxCrowdFund;
import com.auxesis.maxcrowdfund.constant.PaginationListener;
import com.auxesis.maxcrowdfund.mvvm.activity.LoginActivity;
import com.auxesis.maxcrowdfund.constant.APIUrl;
import com.auxesis.maxcrowdfund.constant.ProgressDialog;
import com.auxesis.maxcrowdfund.mvvm.activity.HomeActivity;
import com.auxesis.maxcrowdfund.mvvm.ui.home.homeAdapter.InvestmentOppAdapter;
import com.auxesis.maxcrowdfund.mvvm.ui.home.oppmodel.InvestmentOppModel;
import com.auxesis.maxcrowdfund.mvvm.ui.home.oppmodel.InvestmentOppRes;
import com.auxesis.maxcrowdfund.restapi.ApiClient;
import com.auxesis.maxcrowdfund.restapi.EndPointInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static com.auxesis.maxcrowdfund.constant.PaginationListener.PAGE_START;
import static com.auxesis.maxcrowdfund.constant.PaginationListener.VISIBLE_THRESHOLD;
import static com.auxesis.maxcrowdfund.constant.Utils.getPreference;
import static com.auxesis.maxcrowdfund.constant.Utils.isInternetConnected;
import static com.auxesis.maxcrowdfund.constant.Utils.setPreference;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    TextView tvNoRecordFound;
    RecyclerView recyclerView;
    InvestmentOppAdapter adapter;
    //InvestmentOppHomeAdapter adapter;
    LinearLayoutManager layoutManager;
    ProgressDialog pd;
   /* List<InvestmentOppHomeModel1> list_1 = new ArrayList<>();
    List<InvestmentOppHomeModel1> list_2 = new ArrayList<>();*/

    List<InvestmentOppModel> listArrayActive = new ArrayList<>();
    List<InvestmentOppModel> listArrayMeddle = new ArrayList<>();
    List<InvestmentOppModel> listArrayExpired = new ArrayList<>();
    List<InvestmentOppModel> listArrayTotal = new ArrayList<>();

    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 0;
    private boolean isLoading = false;

    int itemCount = 0;
    int APICount = 0;
    int mTotal = 0;

    Activity mActivity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        APICount = 0;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mActivity = getActivity();
        tvNoRecordFound = root.findViewById(R.id.tvNoRecordFound);
        recyclerView = root.findViewById(R.id.recyclerView);

      /* if (isInternetConnected(getActivity())) {
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
*/
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new InvestmentOppAdapter(getContext(), getActivity(), new ArrayList<>());
        recyclerView.setAdapter(adapter);

        APIUrl.investStatus = "active";
        if (isInternetConnected(getActivity())) {
            getInvestmentOpp();
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
        }
        // add scroll listener while user reach in bottom load more will call
        recyclerView.addOnScrollListener(new PaginationListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                // Log.d(">>>>>>>>>","Loading------currentPage-------"+String.valueOf(currentPage));
                /*if (TOTAL_PAGES == APICount) {
                    APIUrl.investStatus = "expired";
                    APICount = 0;
                }*/
                if (isInternetConnected(getActivity())) {
                    getInvestmentOpp();
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

    private void getInvestmentOpp() {
        pd = ProgressDialog.show(getActivity(), "Please Wait...");
        String XCSRF = getPreference(getActivity(), "mCsrf_token");
        EndPointInterface git = ApiClient.getClient1(getActivity()).create(EndPointInterface.class);
        Call<InvestmentOppRes> call = git.getInvestmentOppHome("json", APIUrl.investStatus, APICount, "application/json", XCSRF);
        call.enqueue(new Callback<InvestmentOppRes>() {
            @Override
            public void onResponse(Call<InvestmentOppRes> call, retrofit2.Response<InvestmentOppRes> response) {
                Log.d(TAG, "onResponse: " + "><list><" + new Gson().toJson(response.body()));
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                if (response != null) {
                    if (response != null && response.isSuccessful()) {
                        if (response.body().getUserLoginStatus() == 1) {
                            mTotal = response.body().getTotal();
                            if (mTotal != 0) {
                                tvNoRecordFound.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                listArrayActive.clear();
                                if (response.body().getData() != null && response.body().getData().size() > 0) {
                                    if (mTotal <= VISIBLE_THRESHOLD) {
                                        TOTAL_PAGES = 0;
                                        APICount = 0;
                                        Log.d(TAG, "onResponse: " + "Currentpage--" + String.valueOf(currentPage) + ">>>>>>" + String.valueOf(APICount) + "><>page<><>" + String.valueOf(TOTAL_PAGES));
                                        for (int i = 0; i < response.body().getData().size(); i++) {
                                            InvestmentOppModel myListModel = new InvestmentOppModel();
                                            myListModel.setId(response.body().getData().get(i).getId());
                                            myListModel.setmTitle(response.body().getData().get(i).getTitle());
                                            myListModel.setInterest_pa(response.body().getData().get(i).getInterestPa());
                                            myListModel.setRisk_class(response.body().getData().get(i).getRiskClass());
                                            myListModel.setAmount(response.body().getData().get(i).getAmount());
                                            myListModel.setCurrency(response.body().getData().get(i).getCurrency());
                                            myListModel.setCurrency_symbol(response.body().getData().get(i).getCurrencySymbol());
                                            myListModel.setFilled(response.body().getData().get(i).getFilled());
                                            myListModel.setNo_of_investors(response.body().getData().get(i).getNoOfInvestors());
                                            myListModel.setAmount_left(response.body().getData().get(i).getAmountLeft());
                                            myListModel.setMonths(response.body().getData().get(i).getMonths());
                                            myListModel.setType(response.body().getData().get(i).getType());
                                            myListModel.setLocation(response.body().getData().get(i).getLocation());
                                            myListModel.setLocation_flag_img(response.body().getData().get(i).getLocationFlagImg());
                                            myListModel.setInvestment_status("active");
                                            listArrayActive.add(myListModel);
                                        }
                                    } else if (mTotal > VISIBLE_THRESHOLD) {
                                        TOTAL_PAGES = (mTotal / VISIBLE_THRESHOLD);
                                        APICount++;
                                        Log.d(">><page><><", "else---" + String.valueOf(currentPage) + ">>Api>>>>" + String.valueOf(APICount));
                                        for (int i = 0; i < response.body().getData().size(); i++) {
                                            InvestmentOppModel myListModel = new InvestmentOppModel();
                                            myListModel.setId(response.body().getData().get(i).getId());
                                            myListModel.setmTitle(response.body().getData().get(i).getTitle());
                                            myListModel.setInterest_pa(response.body().getData().get(i).getInterestPa());
                                            myListModel.setRisk_class(response.body().getData().get(i).getRiskClass());
                                            myListModel.setAmount(response.body().getData().get(i).getAmount());
                                            myListModel.setCurrency(response.body().getData().get(i).getCurrency());
                                            myListModel.setCurrency_symbol(response.body().getData().get(i).getCurrencySymbol());
                                            myListModel.setFilled(response.body().getData().get(i).getFilled());
                                            myListModel.setNo_of_investors(response.body().getData().get(i).getNoOfInvestors());
                                            myListModel.setAmount_left(response.body().getData().get(i).getAmountLeft());
                                            myListModel.setMonths(response.body().getData().get(i).getMonths());
                                            myListModel.setType(response.body().getData().get(i).getType());
                                            myListModel.setLocation(response.body().getData().get(i).getLocation());
                                            myListModel.setLocation_flag_img(response.body().getData().get(i).getLocationFlagImg());
                                            myListModel.setInvestment_status("active");
                                            listArrayActive.add(myListModel);
                                        }
                                    }
                                    //manage progress view
                                    if (currentPage != PAGE_START) {
                                        adapter.removeLoading();
                                    }
                                    adapter.addItems(listArrayActive); //data added into the list
                                   // if (response.body().getFundraiserType().equals("active")) {
                                        if (TOTAL_PAGES == APICount) {
                                            Log.d("><><>", "Page--");
                                            listArrayActive.clear();
                                            listArrayMeddle.clear();
                                            InvestmentOppModel myListModel = new InvestmentOppModel();
                                            myListModel.setAverage_return(response.body().getAverageReturn());
                                            myListModel.setTotal_raised(response.body().getTotalRaised());
                                            myListModel.setActive_investors(response.body().getActiveInvestors());
                                            myListModel.setInvestment_status("expired");
                                            listArrayMeddle.add(myListModel);
                                            listArrayActive.addAll(listArrayMeddle);
                                            adapter.addItems(listArrayActive);
                                            isLastPage = true;
                                           /* APIUrl.investStatus = "expired";
                                            APICount = 0;*/
                                           // adapter.addLoading();
                                        } else {
                                            adapter.addLoading();
                                        }
                                   /* } else {
                                        Log.d("><><>", "Page-expire-");

                                    }*/
                                   /* if (currentPage < TOTAL_PAGES) {
                                        /
                                    } else {
                                        isLastPage = true;
                                    }
                                    isLoading = false;*/

                                /*    if (currentPage < TOTAL_PAGES) {
                                        Log.d(">><page><><", "lessthan" + "current-" + String.valueOf(currentPage) + String.valueOf(APICount) + "><>page<><>" + String.valueOf(TOTAL_PAGES));
                                        adapter.addLoading();
                                    } else if (currentPage == APICount) {
                                        Log.d(">><page><><", "equl" + "current---" + String.valueOf(currentPage) + String.valueOf(APICount) + "><>page<><>" + String.valueOf(TOTAL_PAGES));
                                            listArrayActive.clear();
                                            listArrayMeddle.clear();
                                            InvestmentOppModel myListModel = new InvestmentOppModel();
                                            myListModel.setAverage_return(response.body().getAverageReturn());
                                            myListModel.setTotal_raised(response.body().getTotalRaised());
                                            myListModel.setActive_investors(response.body().getActiveInvestors());
                                            myListModel.setInvestment_status("expired");
                                            listArrayMeddle.add(myListModel);
                                            listArrayActive.addAll(listArrayMeddle);
                                            adapter.addItems(listArrayActive);
                                            APIUrl.investStatus = "expired";
                                            APICount = 0;
                                           // currentPage = TOTAL_PAGES;
                                            TOTAL_PAGES = response.body().getTotal();
                                            Log.d(TAG, "onResponse:" + ">>>==>>>>" + TOTAL_PAGES);
                                            adapter.addLoading();
                                    } *//*else if (currentPage <= TOTAL_PAGES) {
                                        // mUrl = APIUrl.GER_LISTING + APIUrl.investment_status + APIUrl.investStatus + APIUrl.page + APICount;
                                        adapter.addLoading();
                                    }*//*else {
                                        isLastPage = true;
                                    }
                                    isLoading = false;*/

                                } else {
                                    Toast.makeText(getActivity(), getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                tvNoRecordFound.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            }
                        } else {
                            setPreference(getActivity(), "user_id", "");
                            setPreference(getActivity(), "mLogout_token", "");
                            MaxCrowdFund.getClearCookies(getActivity(), "cookies", "");
                            Toast.makeText(getActivity(), getResources().getString(R.string.session_expire), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                            mActivity.finish();
                        }
                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InvestmentOppRes> call, Throwable t) {
                Log.e("response", "error " + t.getMessage());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
            }
        });
    }

    /*  private void getListingApi() {
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
                            //  mTotalpage = (mTotal / VISIBLE_THRESHOLD);
                              JSONArray jsonArray = jsonObject.getJSONArray("data");
                              list_1.clear();
                              if (jsonArray.length() > 0) {
                                  for (int i = 0; i < jsonArray.length(); i++) {
                                      InvestmentOppHomeModel1 myListModel = new InvestmentOppHomeModel1();
                                      myListModel.setId(jsonArray.getJSONObject(i).getString("id"));
                                      myListModel.setmTitle(jsonArray.getJSONObject(i).getString("title"));
                                      myListModel.setInterest_pa(jsonArray.getJSONObject(i).getString("interest_pa"));
                                      myListModel.setRisk_class(jsonArray.getJSONObject(i).getString("risk_class"));
                                      myListModel.setAmount(jsonArray.getJSONObject(i).getString("amount"));
                                      myListModel.setCurrency(jsonArray.getJSONObject(i).getString("currency"));
                                      myListModel.setCurrency_symbol(jsonArray.getJSONObject(i).getString("currency_symbol"));
                                      myListModel.setFilled(jsonArray.getJSONObject(i).getInt("filled"));
                                      myListModel.setNo_of_investors(jsonArray.getJSONObject(i).getInt("no_of_investors"));
                                      // myListModel.setAmount_left(jsonArray.getJSONObject(i).getInt("amount_left"));
                                      myListModel.setAmount_left(jsonArray.getJSONObject(i).getString("amount_left"));
                                      myListModel.setMonths(jsonArray.getJSONObject(i).getString("months"));
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
                                      Log.d(TAG, "onResponse: " + "current---------" + String.valueOf(currentPage));
                                      adapter.addLoading();
                                  } else if (currentPage == totalPage) {
                                      list_1.clear();
                                      list_2.clear();
                                      InvestmentOppHomeModel1 myListModel = new InvestmentOppHomeModel1();
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
                          } else {
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

                  @Override
                  public Map getHeaders() throws AuthFailureError {
                      HashMap headers = new HashMap();
                      String mCsrfToken = getPreference(getActivity(), "mCsrf_token");
                      Log.d(TAG, "getHeaders: " + mCsrfToken);
                      if (mCsrfToken != null && !mCsrfToken.isEmpty()) {
                          headers.put("X-CSRF-TOKEN", mCsrfToken);
                      }
                      headers.put("Content-Type", "application/json");
                      return headers;
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
 */
    public void onResume() {
        super.onResume();
        ((HomeActivity) getActivity()).setActionBarTitle(getString(R.string.menu_investments_opportunity));
    }
}