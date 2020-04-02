package com.auxesis.maxcrowdfund.mvvm.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

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

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.auxesis.maxcrowdfund.constant.APIUrl;
import com.auxesis.maxcrowdfund.constant.MaxCrowdFund;
import com.auxesis.maxcrowdfund.constant.ProgressDialog;
import com.auxesis.maxcrowdfund.constant.Utils;
import com.auxesis.maxcrowdfund.restapi.ApiClient;
import com.auxesis.maxcrowdfund.restapi.EndPointInterface;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

import static com.auxesis.maxcrowdfund.constant.Utils.getPreference;
import static com.auxesis.maxcrowdfund.constant.Utils.isInternetConnected;
import static com.auxesis.maxcrowdfund.constant.Utils.setPreference;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private AppBarConfiguration mAppBarConfiguration;
    TextView tv_user_name;
    ProgressDialog pd;
    String logOutUrl = "";
    String message = "";
    String logoutToken = "";
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        View v = navigationView.getHeaderView(0);
        tv_user_name = (TextView) v.findViewById(R.id.tv_user_name);
        String mName = getPreference(HomeActivity.this, "mName").toLowerCase();
        if (mName != null && !mName.isEmpty()) {
            String userName = mName.substring(0, 1).toUpperCase() + mName.substring(1);
            tv_user_name.setText(userName);
        }

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_investment_opp, R.id.nav_dashboard, R.id.nav_my_investments,
                R.id.nav_my_profile, R.id.nav_contact_information, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        //For nav item click listner
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.nav_investment_opp) {
                    setActionBarTitle(getString(R.string.menu_investments_opportunity));
                } else if (id == R.id.nav_dashboard) {
                    setActionBarTitle(getString(R.string.menu_dashboard));
                } else if (id == R.id.nav_my_investments) {
                    setActionBarTitle(getString(R.string.menu_my_investments));
                } else if (id == R.id.nav_my_profile) {
                    setActionBarTitle(getString(R.string.menu_my_profile));
                } else if (id == R.id.nav_contact_information) {
                    setActionBarTitle(getString(R.string.menu_contact_information));
                } else if (id == R.id.nav_logout) {
                    if (isInternetConnected(getApplicationContext())) {
                       // getCheckUserApi();
                       getCheckUser();
                    } else {
                        Toast.makeText(HomeActivity.this, getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
                    }
                }
                //This is for maintaining the behavior of the Navigation view
                NavigationUI.onNavDestinationSelected(menuItem, navController);
                //This is for closing the drawer after acting on it
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void getCheckUserApi() {
        pd = ProgressDialog.show(HomeActivity.this, "Please Wait...");
        EndPointInterface git = ApiClient.getClient1(HomeActivity.this).create(EndPointInterface.class);
        Call<?> call = git.getCheckUser("json");
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, retrofit2.Response response) {
                Log.d(TAG, "onResponse: " + "><Logout><" + new Gson().toJson(response.body()));
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("response", "error " + t.getMessage());
                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void getCheckUser() {
        Log.d("GER_CHECK_USER", APIUrl.GER_CHECK_USER);
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, APIUrl.GER_CHECK_USER, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "onResponse:" + "Logout ----"+response.toString());
                    String status = response.toString();
                    if (status.equals("1")) {
                        if (isInternetConnected(getApplicationContext())) {
                            getLogOut();
                        } else {
                            Toast.makeText(HomeActivity.this, getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.d(TAG, "onResponse: " + status);
                        setPreference(HomeActivity.this, "user_id", "");
                        setPreference(HomeActivity.this, "mLogout_token", "");
                        MaxCrowdFund.getClearCookies(HomeActivity.this, "cookies", "");
                        Toast.makeText(HomeActivity.this, getResources().getString(R.string.logout_succ), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
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
                        NetworkResponse response = error.networkResponse;
                        String mMessage = "";
                        if (response != null && response.data != null) {
                            /*try {
                                String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                JSONObject obj = new JSONObject(res);
                                mMessage = obj.getString("message");
                                Toast.makeText(HomeActivity.this, mMessage, Toast.LENGTH_SHORT).show();
                            } catch (UnsupportedEncodingException e1) {
                                e1.printStackTrace();
                            } catch (JSONException e2) {
                                e2.printStackTrace();
                            }*/
                            if (response.statusCode == 404) {
                                Toast.makeText(HomeActivity.this, mMessage, Toast.LENGTH_SHORT).show();
                            } else if (response.statusCode == 400 || response.statusCode == 405 || response.statusCode == 500) {
                                Toast.makeText(HomeActivity.this, mMessage, Toast.LENGTH_SHORT).show();
                            } else if (response.statusCode == 401) {
                                Toast.makeText(HomeActivity.this, mMessage, Toast.LENGTH_SHORT).show();
                            } else if (response.statusCode == 403) {
                                Toast.makeText(HomeActivity.this, mMessage, Toast.LENGTH_SHORT).show();
                            } else if (response.statusCode == 422) {
                                Toast.makeText(HomeActivity.this, mMessage, Toast.LENGTH_SHORT).show();
                            } else if (response.statusCode == 503) {
                                Toast.makeText(HomeActivity.this, getResources().getString(R.string.server_down), Toast.LENGTH_SHORT).show();
                            } else if (response.statusCode == 204) {
                            } else if (response.statusCode == 200) {
                            } else {
                                Toast.makeText(HomeActivity.this, getResources().getString(R.string.please_try_again), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (error instanceof NoConnectionError) {
                                Toast.makeText(HomeActivity.this, getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
                            } else if (error instanceof NetworkError) {
                                Toast.makeText(HomeActivity.this, getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
                            } else if (error instanceof TimeoutError) {
                                try {
                                    if (error.networkResponse == null) {
                                        if (error.getClass().equals(TimeoutError.class)) {
                                            Toast.makeText(HomeActivity.this, getResources().getString(R.string.timed_out), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else if (error instanceof AuthFailureError) {
                                Log.d(TAG, "onErrorResponse: " + "AuthFailureError" + AuthFailureError.class);
                            } else if (error instanceof ServerError) {
                                Log.d(TAG, "onErrorResponse: " + "ServerError" + ServerError.class);
                            } else if (error instanceof ParseError) {
                                Log.d(TAG, "onErrorResponse: " + "ParseError" + ParseError.class);
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

                /** Passing some request headers* */
                @Override
                public Map getHeaders() throws AuthFailureError {
                    HashMap headers = new HashMap();
                    String mCsrfToken = getPreference(HomeActivity.this, "mCsrf_token");
                    if (mCsrfToken != null && !mCsrfToken.isEmpty()) {
                        headers.put("X-CSRF-TOKEN", mCsrfToken);
                    }
                    headers.put("Content-Type", "application/json");
                    return headers;
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
        pd = ProgressDialog.show(HomeActivity.this, "Please Wait...");
        try {
            if (logoutToken != null && !logoutToken.isEmpty()) {
                logOutUrl = APIUrl.GER_LOG_OUT + logoutToken;
            }
            Log.d("logOutUrl", logOutUrl);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, logOutUrl, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    JSONObject jobj = null;
                    try {
                        //==================For Creating JSONObject =============================
                        jobj = new JSONObject(response.toString());
                        if (jobj != null) {
                            message = jobj.getString("message");
                            if (message.equalsIgnoreCase("'csrf_token' URL query argument is invalid.")) {
                                Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            setPreference(HomeActivity.this, "isRememberMe", "");
                            setPreference(HomeActivity.this, "user_id", "");
                            setPreference(HomeActivity.this, "mLogout_token", "");
                            MaxCrowdFund.getClearCookies(HomeActivity.this, "cookies", "");
                            Toast.makeText(HomeActivity.this, getResources().getString(R.string.logout_succ), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
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
                        String json = null;
                        String mMessage = "";
                        NetworkResponse response = error.networkResponse;
                        if (response != null && response.data != null) {
                            try {
                                JSONObject errorObj = new JSONObject(new String(response.data));
                                mMessage = errorObj.getString("message");
                                if (response.statusCode == 400 || response.statusCode == 405 || response.statusCode == 500) {
                                    Toast.makeText(HomeActivity.this, mMessage, Toast.LENGTH_SHORT).show();
                                } else if (response.statusCode == 401) {
                                    Toast.makeText(HomeActivity.this, mMessage, Toast.LENGTH_SHORT).show();
                                } else if (response.statusCode == 403) {
                                    Toast.makeText(HomeActivity.this, mMessage, Toast.LENGTH_SHORT).show();
                                } else if (response.statusCode == 422) {
                                    Toast.makeText(HomeActivity.this, mMessage, Toast.LENGTH_SHORT).show();
                                } else if (response.statusCode == 503) {
                                    Toast.makeText(HomeActivity.this, getResources().getString(R.string.server_down), Toast.LENGTH_SHORT).show();
                                } else if (response.statusCode == 204) {
                                    Toast.makeText(HomeActivity.this, mMessage, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(HomeActivity.this, getResources().getString(R.string.please_try_again), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            if (error instanceof NoConnectionError) {
                                Toast.makeText(HomeActivity.this, getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
                            } else if (error instanceof NetworkError) {
                                Toast.makeText(HomeActivity.this, getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
                            } else if (error instanceof TimeoutError) {
                                try {
                                    if (error.networkResponse == null) {
                                        if (error.getClass().equals(TimeoutError.class)) {
                                            Toast.makeText(HomeActivity.this, getResources().getString(R.string.timed_out), Toast.LENGTH_SHORT).show();
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
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json";
                }

                @Override
                public Map getHeaders() throws AuthFailureError {
                    HashMap headers = new HashMap();
                    String mCsrfToken = getPreference(HomeActivity.this, "mCsrf_token");
                    if (mCsrfToken != null && !mCsrfToken.isEmpty()) {
                        headers.put("X-CSRF-TOKEN", mCsrfToken);
                    }
                    headers.put("Content-Type", "application/json");
                    return headers;
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

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
