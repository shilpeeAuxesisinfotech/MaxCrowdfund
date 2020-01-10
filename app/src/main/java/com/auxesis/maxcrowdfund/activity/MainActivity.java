package com.auxesis.maxcrowdfund.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
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
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.adapter.MyListAdapter;
import com.auxesis.maxcrowdfund.constant.APIUrl;
import com.auxesis.maxcrowdfund.constant.PaginationListener;
import com.auxesis.maxcrowdfund.constant.ProgressDialog;
import com.auxesis.maxcrowdfund.constant.Utils;
import com.auxesis.maxcrowdfund.custommvvm.myinvestmentmodel.MyInvestmentsActivity;
import com.auxesis.maxcrowdfund.model.MyListModel;
import com.auxesis.maxcrowdfund.mvvm.HomeActivity;
import com.google.android.material.navigation.NavigationView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.auxesis.maxcrowdfund.constant.PaginationListener.PAGE_START;
import static com.auxesis.maxcrowdfund.constant.PaginationListener.VISIBLE_THRESHOLD;
import static com.auxesis.maxcrowdfund.constant.Utils.getPreference;
import static com.auxesis.maxcrowdfund.constant.Utils.isInternetConnected;
import static com.auxesis.maxcrowdfund.constant.Utils.setPreference;
import static com.auxesis.maxcrowdfund.constant.Utils.showToast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    boolean doubleBackToExitPressedOnce = false;
    TextView tv_user_name;
    RecyclerView recyclerView;
    MyListAdapter adapter;
    ProgressDialog pd;
    List<MyListModel> list_1 = new ArrayList<>();
    List<MyListModel> list_2 = new ArrayList<>();
    private int currentPage = PAGE_START;
  //  private int currentNPage = 0;
    private int pageExpiredStart = 0;
    private boolean isLastPage = false;
    private int totalPage = 2;
    private int mTotalpage = 0;
    // private int totalPage = 10;
    private boolean isLoading = false;
    LinearLayoutManager layoutManager;
    int APICount = 0;
    int mTotal = 0;
    boolean isPageExpiredStart=false;

    String logOutUrl = "";
    String message = "";
    String logoutToken = "";
    String mUrl = "";
    boolean isScrolled = false;
    private int mFTotal = 0;
    // total no. of pages to load. Initial load is page 0, after which 2 more pages will load.
    private int TOTAL_PAGES = 0;
    private static int mItemCount = 0;
    SearchView searchCiew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(R.string.investments_opportunity);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View v = navigationView.getHeaderView(0);
        tv_user_name = (TextView) v.findViewById(R.id.tv_user_name);

        String mName = getPreference(MainActivity.this, "mName").toLowerCase();
        if (mName != null && !mName.isEmpty()) {
            String userName = mName.substring(0, 1).toUpperCase() + mName.substring(1);
            tv_user_name.setText(userName);
        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        init();
    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerView);
        if (isInternetConnected(getApplicationContext())) {
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new MyListAdapter(MainActivity.this, new ArrayList<>());
            recyclerView.setAdapter(adapter);
            logoutToken = getPreference(MainActivity.this, "mLogout_token");
            APIUrl.investStatus = "active";
            getListingApi();
        } else {
            showToast(MainActivity.this, getResources().getString(R.string.oops_connect_your_internet));
        }

        /**
         * add scroll listener while user reach in bottom load more will call
         */
        recyclerView.addOnScrollListener(new PaginationListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                pageExpiredStart++;
                if (isInternetConnected(getApplicationContext())) {
                     getListingApi();
                } else {
                    showToast(MainActivity.this, getResources().getString(R.string.oops_connect_your_internet));
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
    }


  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_investment_opp) {

        } else if (id == R.id.nav_dashboard) {
            startActivity(new Intent(MainActivity.this, DashboardActivity.class));
            // overridePendingTransition(R.anim.enter, R.anim.exit);
        } else if (id == R.id.nav_my_investments) {
            startActivity(new Intent(MainActivity.this, MyInvestmentsActivity.class));
            // overridePendingTransition(R.anim.enter, R.anim.exit);
        } else if (id == R.id.nav_my_profile) {
            startActivity(new Intent(MainActivity.this, MyProfileActivity.class));
            // overridePendingTransition(R.anim.enter, R.anim.exit);
        } else if (id == R.id.nav_contact_information) {
            startActivity(new Intent(MainActivity.this, ContactInformationActivity.class));
            // overridePendingTransition(R.anim.enter, R.anim.exit);
        } else if (id == R.id.nav_demo) {
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
        }  else if (id == R.id.nav_logout) {
            if (isInternetConnected(getApplicationContext())) {
                getCheckUser();
            } else {
                showToast(MainActivity.this, getResources().getString(R.string.oops_connect_your_internet));
            }
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getListingApi() {
        Utils.hideKeyboard(this);
        pd = ProgressDialog.show(MainActivity.this, "Please Wait...");
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
                        mTotal = jsonObject.getInt("total");
                        mTotalpage = (mTotal / VISIBLE_THRESHOLD);
                        Log.d(TAG, "onResponse: " + "><>mTotalpage<><" + mTotalpage + "><>mTotal<>" + mTotal);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        list_1.clear();
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
                                list_1.add(myListModel);
                            }

                            Log.d(TAG, "onResponse: " + "><>list_1.size<><" + list_1.size());
                            if (currentPage != PAGE_START) {
                                adapter.removeLoading();
                            }
                            adapter.addItems(list_1);
                            Log.d(TAG, "onResponse: " + ">currentPage>>>>>>>" + String.valueOf(currentPage));
                            // check weather is last page or not if (currentPage < totalPage)
                           // if (currentPage < mTotalpage) {

                           /* if (APIUrl.investStatus.equals("active")){*/

                                if (currentPage < totalPage) {
                                    adapter.addLoading();
                                    Log.d(TAG, "onResponse: " + ">IF<currentPage><><" + String.valueOf(currentPage) + "><mTotalpage><><" + String.valueOf(mTotalpage));
                                    //} else if (currentPage == mTotalpage) {
                                } else if (currentPage == totalPage) {
                                    Log.d(TAG, "onResponse: " + "Current Page------------" + currentPage);
                                    list_2.clear();
                                    MyListModel myListModel = new MyListModel();
                                    myListModel.setAverage_return(jsonObject.getInt("average_return"));
                                    myListModel.setTotal_raised(jsonObject.getInt("total_raised"));
                                    myListModel.setActive_investors(jsonObject.getInt("active_investors"));
                                    myListModel.setInvestment_status("expired");
                                    list_2.add(myListModel);
                                    list_1.addAll(list_2);
                                    adapter.addItems(list_1);
                                    APIUrl.investStatus = "expired";
                                   // isPageExpiredStart =true;
                                   // APICount = APICount - mTotalpage;
                                    APICount = 0;
                                    mUrl = APIUrl.GER_LISTING + APIUrl.investment_status + APIUrl.investStatus + APIUrl.page +APICount;
                                    Log.d(TAG, "onResponse: " + "AddLoading" + "><mUrl><" + mUrl + "><><>" + String.valueOf(mTotalpage) + ">>" + currentPage);
                                    adapter.addLoading();
                                } else if (currentPage <= 3) {
                                    //APIUrl.investStatus = "expired";
                                   // APICount = APICount - mTotalpage;
                                    mUrl = APIUrl.GER_LISTING + APIUrl.investment_status + APIUrl.investStatus + APIUrl.page +APICount;
                                    Log.d(TAG, "onResponse: " + "AddLoading3**********" + "><mUrl><" + mUrl +"<CurrentPage<<<<<<"+ currentPage);
                                    adapter.addLoading();
                                } else {
                                    isLastPage = true;
                                }
                                isLoading = false;


                          /*  }else {

                                APICount = APICount - mTotalpage;

                                mUrl = APIUrl.GER_LISTING + APIUrl.investment_status + APIUrl.investStatus + APIUrl.page + APICount;
                                Log.d(TAG, "onResponse: " + "AddLoading" + "><mUrl><" + mUrl + "><><>" + String.valueOf(mTotalpage) + ">>" + currentPage);
                                adapter.addLoading();
                            }*/

                            /*if (currentPage < totalPage) {
                                adapter.addLoading();
                                Log.d(TAG, "onResponse: " + ">IF<currentPage><><" + String.valueOf(currentPage) + "><mTotalpage><><" + String.valueOf(mTotalpage));

                            //} else if (currentPage == mTotalpage) {
                            } else if (currentPage == totalPage) {
                                Log.d(TAG, "onResponse: " + "Current Page------------" + currentPage);
                                list_2.clear();
                                MyListModel myListModel = new MyListModel();
                                myListModel.setAverage_return(jsonObject.getInt("average_return"));
                                myListModel.setTotal_raised(jsonObject.getInt("total_raised"));
                                myListModel.setActive_investors(jsonObject.getInt("active_investors"));
                                myListModel.setInvestment_status("expired");
                                list_2.add(myListModel);
                                list_1.addAll(list_2);
                                adapter.addItems(list_1);

                                 APIUrl.investStatus = "expired";
                                APICount = APICount - mTotalpage;

                                mUrl = APIUrl.GER_LISTING + APIUrl.investment_status + APIUrl.investStatus + APIUrl.page + APICount;
                                Log.d(TAG, "onResponse: " + "AddLoading" + "><mUrl><" + mUrl + "><><>" + String.valueOf(mTotalpage) + ">>" + currentPage);
                                adapter.addLoading();

                            }  else {
                                isLastPage = true;
                            }
                            isLoading = false;*/
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
                                showToast(MainActivity.this, getResources().getString(R.string.something_went));
                            } else if (response.statusCode == 401) {

                            } else if (response.statusCode == 422) {
                                //  json = trimMessage(new String(response.data));
                                if (json != "" && json != null) {
                                    // displayMessage(json);
                                } else {
                                    showToast(MainActivity.this, getResources().getString(R.string.please_try_again));
                                }
                            } else if (response.statusCode == 503) {
                                showToast(MainActivity.this, getResources().getString(R.string.server_down));
                            } else {
                                showToast(MainActivity.this, getResources().getString(R.string.please_try_again));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (error instanceof NoConnectionError) {
                            showToast(MainActivity.this, getResources().getString(R.string.oops_connect_your_internet));
                        } else if (error instanceof NetworkError) {
                            showToast(MainActivity.this, getResources().getString(R.string.oops_connect_your_internet));
                        } else if (error instanceof TimeoutError) {
                            try {
                                if (error.networkResponse == null) {
                                    if (error.getClass().equals(TimeoutError.class)) {
                                        // Show timeout error message
                                        showToast(MainActivity.this, getResources().getString(R.string.timed_out));
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
            RequestQueue queue = Volley.newRequestQueue(this);
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

    private void getCheckUser() {
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, APIUrl.GER_CHECK_USER, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "onResponse:" + response.toString());
                    String status = response.toString();
                    Log.d(TAG, response.toString() + "getCheckUser");
                    Log.d(TAG, "getCheckUser--->>" + status);

                    if (status.equals("1")) {
                        Log.d(TAG, "onResponse: " + status); //is logged in
                        if (isInternetConnected(getApplicationContext())) {
                            getLogOut();
                        } else {
                            showToast(MainActivity.this, getResources().getString(R.string.oops_connect_your_internet));
                        }
                    } else {
                        Log.d(TAG, "onResponse: " + status);
                        setPreference(MainActivity.this, "user_id", "");
                        setPreference(MainActivity.this, "mLogout_token", "");
                        showToast(MainActivity.this, "Logout Successfully..");
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        Log.d(TAG, "onErrorResponse: " + error.getMessage());
                        Log.d(TAG, "" + error.getMessage() + "," + error.toString());
                        NetworkResponse response = error.networkResponse;
                        // String json = null;
                        String mMessage = "";
                        Log.d(TAG, "onErrorResponse: " + response.statusCode);
                        if (response != null && response.data != null) {
                            try {
                                String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                // Now you can use any deserializer to make sense of data
                                JSONObject obj = new JSONObject(res);
                                mMessage = obj.getString("message");
                                Log.d(TAG, "onErrorResponse: " + res);
                                Log.d(TAG, "onErrorResponse: " + obj.getString("message"));
                                showToast(MainActivity.this, mMessage);
                                //use this json as you want
                            } catch (UnsupportedEncodingException e1) {
                                // Couldn't properly decode data to string
                                e1.printStackTrace();
                            } catch (JSONException e2) {
                                // returned data is not JSONObject?
                                e2.printStackTrace();
                            }
                            if (response.statusCode == 404) {
                                showToast(MainActivity.this, mMessage);
                            } else if (response.statusCode == 400 || response.statusCode == 405 || response.statusCode == 500) {
                                Log.d(TAG, "onErrorResponse: " + "statusCode:--400-------" + response.statusCode + "mMessage-------" + mMessage);
                                showToast(MainActivity.this, mMessage);
                            } else if (response.statusCode == 401) {
                                Log.d(TAG, "onErrorResponse: " + "statusCode:---401------" + response.statusCode);
                                showToast(MainActivity.this, mMessage);
                            } else if (response.statusCode == 403) {
                                showToast(MainActivity.this, mMessage);
                                Log.d(TAG, "onErrorResponse: " + "statusCode:---403------" + response.statusCode + "mMessage------" + mMessage);
                            } else if (response.statusCode == 422) {
                                showToast(MainActivity.this, mMessage);
                                Log.d(TAG, "onErrorResponse: " + "statusCode:---422---String---" + new String(response.data));
                            } else if (response.statusCode == 503) {
                                showToast(MainActivity.this, getResources().getString(R.string.server_down));
                            } else if (response.statusCode == 204) {
                                Log.d(TAG, "onErrorResponse: " + "statusCode:---204---String---");
                            } else if (response.statusCode == 200) {
                                Log.d(TAG, "onErrorResponse: " + "statusCode:---200---String---");
                            } else {
                                showToast(MainActivity.this, getResources().getString(R.string.please_try_again));
                            }
                        } else {
                            if (error instanceof NoConnectionError) {
                                showToast(MainActivity.this, getResources().getString(R.string.oops_connect_your_internet));
                            } else if (error instanceof NetworkError) {
                                showToast(MainActivity.this, getResources().getString(R.string.oops_connect_your_internet));
                            } else if (error instanceof TimeoutError) {
                                try {
                                    if (error.networkResponse == null) {
                                        if (error.getClass().equals(TimeoutError.class)) {
                                            // Show timeout error message
                                            showToast(MainActivity.this, getResources().getString(R.string.timed_out));
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
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("_format", "json");
                    return params;
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getLogOut() {
        Utils.hideKeyboard(this);
        pd = ProgressDialog.show(MainActivity.this, "Please Wait...");
        try {
            if (logoutToken != null && !logoutToken.isEmpty()) {
                logOutUrl = APIUrl.GER_LOG_OUT + logoutToken;
                Log.d(TAG, "getLogOut: " + logOutUrl);
            }
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, logOutUrl, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    Log.d("getLogOut RESPONCE:::", response.toString());
                    JSONObject jobj = null;
                    try {
                        //==================For Creating JSONObject =============================
                        jobj = new JSONObject(response.toString());
                        if (jobj != null) {
                            message = jobj.getString("message");
                            if (message.equalsIgnoreCase("'csrf_token' URL query argument is invalid.")) {
                                showToast(MainActivity.this, message);
                            }
                        } else {
                            setPreference(MainActivity.this, "isRememberMe", "");
                            setPreference(MainActivity.this, "user_id", "");
                            setPreference(MainActivity.this, "mLogout_token", "");
                            showToast(MainActivity.this, "Logout Successfully..");
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } catch (Exception e) {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        Log.d(TAG, "onErrorResponse: " + error.getMessage());
                        String json = null;
                        String mMessage = "";
                        NetworkResponse response = error.networkResponse;
                        if (response != null && response.data != null) {
                            try {
                                JSONObject errorObj = new JSONObject(new String(response.data));
                                mMessage = errorObj.getString("message");
                                if (response.statusCode == 400 || response.statusCode == 405 || response.statusCode == 500) {
                                    Log.d(TAG, "onErrorResponse: " + "statusCode:--400-------" + response.statusCode + "mMessage-------" + mMessage);
                                    showToast(MainActivity.this, mMessage);
                                } else if (response.statusCode == 401) {
                                    Log.d(TAG, "onErrorResponse: " + "statusCode:---401------" + response.statusCode);
                                    showToast(MainActivity.this, mMessage);
                                } else if (response.statusCode == 403) {
                                    showToast(MainActivity.this, mMessage);
                                    Log.d(TAG, "onErrorResponse: " + "statusCode:---403------" + response.statusCode + "mMessage------" + mMessage);
                                } else if (response.statusCode == 422) {
                                    showToast(MainActivity.this, mMessage);
                                    Log.d(TAG, "onErrorResponse: " + "statusCode:---422---String---" + new String(response.data));
                                    //  json = trimMessage(new String(response.data));
                                    if (json != "" && json != null) {
                                        // displayMessage(json);
                                    } else {
                                        showToast(MainActivity.this, getResources().getString(R.string.please_try_again));
                                    }
                                } else if (response.statusCode == 503) {
                                    showToast(MainActivity.this, getResources().getString(R.string.server_down));
                                } else if (response.statusCode == 204) {
                                    showToast(MainActivity.this, mMessage);
                                    Log.d(TAG, "onErrorResponse: " + "statusCode:---204---String---");
                                } else {
                                    showToast(MainActivity.this, getResources().getString(R.string.please_try_again));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            if (error instanceof NoConnectionError) {
                                showToast(MainActivity.this, getResources().getString(R.string.oops_connect_your_internet));
                            } else if (error instanceof NetworkError) {
                                showToast(MainActivity.this, getResources().getString(R.string.oops_connect_your_internet));
                            } else if (error instanceof TimeoutError) {
                                try {
                                    if (error.networkResponse == null) {
                                        if (error.getClass().equals(TimeoutError.class)) {
                                            // Show timeout error message
                                            showToast(MainActivity.this, getResources().getString(R.string.timed_out));
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
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json";
                }
            };
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue queue = Volley.newRequestQueue(this);
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

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}



