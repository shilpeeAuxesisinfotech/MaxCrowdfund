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
import com.auxesis.maxcrowdfund.constant.ProgressDialog;
import com.auxesis.maxcrowdfund.constant.Utils;
import com.google.android.material.navigation.NavigationView;
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
import static com.auxesis.maxcrowdfund.constant.Utils.getPreference;
import static com.auxesis.maxcrowdfund.constant.Utils.isInternetConnected;
import static com.auxesis.maxcrowdfund.constant.Utils.setPreference;
import static com.auxesis.maxcrowdfund.constant.Utils.showToast;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private AppBarConfiguration mAppBarConfiguration;
    TextView tv_user_name;
    MenuItem item;
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
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
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

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
       // item = menu.findItem(R.id.filter).setVisible(false);
        return true;
    }
*/
   /* @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.filter:
               // newGame();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
       *//* switch (item.getItemId()) {
            case R.id.filter:
                Toast.makeText(HomeActivity.this, "Menu Item with clicked: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);*//*
    }*/



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void getCheckUser() {
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, APIUrl.GER_CHECK_USER, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "onResponse:" + response.toString());
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
                        showToast(HomeActivity.this, "Logout Successfully..");
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
                                showToast(HomeActivity.this, mMessage);
                                //use this json as you want
                            } catch (UnsupportedEncodingException e1) {
                                // Couldn't properly decode data to string
                                e1.printStackTrace();
                            } catch (JSONException e2) {
                                // returned data is not JSONObject?
                                e2.printStackTrace();
                            }
                            if (response.statusCode == 404) {
                                showToast(HomeActivity.this, mMessage);
                            } else if (response.statusCode == 400 || response.statusCode == 405 || response.statusCode == 500) {
                                Log.d(TAG, "onErrorResponse: " + "statusCode:--400-------" + response.statusCode + "mMessage-------" + mMessage);
                                showToast(HomeActivity.this, mMessage);
                            } else if (response.statusCode == 401) {
                                Log.d(TAG, "onErrorResponse: " + "statusCode:---401------" + response.statusCode);
                                showToast(HomeActivity.this, mMessage);
                            } else if (response.statusCode == 403) {
                                showToast(HomeActivity.this, mMessage);
                                Log.d(TAG, "onErrorResponse: " + "statusCode:---403------" + response.statusCode + "mMessage------" + mMessage);
                            } else if (response.statusCode == 422) {
                                showToast(HomeActivity.this, mMessage);
                                Log.d(TAG, "onErrorResponse: " + "statusCode:---422---String---" + new String(response.data));
                            } else if (response.statusCode == 503) {
                                showToast(HomeActivity.this, getResources().getString(R.string.server_down));
                            } else if (response.statusCode == 204) {
                                Log.d(TAG, "onErrorResponse: " + "statusCode:---204---String---");
                            } else if (response.statusCode == 200) {
                                Log.d(TAG, "onErrorResponse: " + "statusCode:---200---String---");
                            } else {
                                showToast(HomeActivity.this, getResources().getString(R.string.please_try_again));
                            }
                        } else {
                            if (error instanceof NoConnectionError) {
                                showToast(HomeActivity.this, getResources().getString(R.string.oops_connect_your_internet));
                            } else if (error instanceof NetworkError) {
                                showToast(HomeActivity.this, getResources().getString(R.string.oops_connect_your_internet));
                            } else if (error instanceof TimeoutError) {
                                try {
                                    if (error.networkResponse == null) {
                                        if (error.getClass().equals(TimeoutError.class)) {
                                            // Show timeout error message
                                            showToast(HomeActivity.this, getResources().getString(R.string.timed_out));
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
        pd = ProgressDialog.show(HomeActivity.this, "Please Wait...");
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
                                showToast(HomeActivity.this, message);
                            }
                        } else {
                            setPreference(HomeActivity.this, "isRememberMe", "");
                            setPreference(HomeActivity.this, "user_id", "");
                            setPreference(HomeActivity.this, "mLogout_token", "");
                            showToast(HomeActivity.this, "Logout Successfully..");
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
                                    showToast(HomeActivity.this, mMessage);
                                } else if (response.statusCode == 401) {
                                    Log.d(TAG, "onErrorResponse: " + "statusCode:---401------" + response.statusCode);
                                    showToast(HomeActivity.this, mMessage);
                                } else if (response.statusCode == 403) {
                                    showToast(HomeActivity.this, mMessage);
                                    Log.d(TAG, "onErrorResponse: " + "statusCode:---403------" + response.statusCode + "mMessage------" + mMessage);
                                } else if (response.statusCode == 422) {
                                    showToast(HomeActivity.this, mMessage);
                                    Log.d(TAG, "onErrorResponse: " + "statusCode:---422---String---" + new String(response.data));
                                    //  json = trimMessage(new String(response.data));
                                    if (json != "" && json != null) {
                                        // displayMessage(json);
                                    } else {
                                        showToast(HomeActivity.this, getResources().getString(R.string.please_try_again));
                                    }
                                } else if (response.statusCode == 503) {
                                    showToast(HomeActivity.this, getResources().getString(R.string.server_down));
                                } else if (response.statusCode == 204) {
                                    showToast(HomeActivity.this, mMessage);
                                    Log.d(TAG, "onErrorResponse: " + "statusCode:---204---String---");
                                } else {
                                    showToast(HomeActivity.this, getResources().getString(R.string.please_try_again));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            if (error instanceof NoConnectionError) {
                                showToast(HomeActivity.this, getResources().getString(R.string.oops_connect_your_internet));
                            } else if (error instanceof NetworkError) {
                                showToast(HomeActivity.this, getResources().getString(R.string.oops_connect_your_internet));
                            } else if (error instanceof TimeoutError) {
                                try {
                                    if (error.networkResponse == null) {
                                        if (error.getClass().equals(TimeoutError.class)) {
                                            // Show timeout error message
                                            showToast(HomeActivity.this, getResources().getString(R.string.timed_out));
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

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
