package com.auxesis.maxcrowdfund.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
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
import com.auxesis.maxcrowdfund.constant.APIUrl;
import com.auxesis.maxcrowdfund.constant.ProgressDialog;
import com.auxesis.maxcrowdfund.constant.Utils;
import org.json.JSONException;
import org.json.JSONObject;
import static com.auxesis.maxcrowdfund.constant.Utils.hideKeyboard;
import static com.auxesis.maxcrowdfund.constant.Utils.isInternetConnected;
import static com.auxesis.maxcrowdfund.constant.Utils.setPreference;
import static com.auxesis.maxcrowdfund.constant.Utils.showToast;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    EditText edt_email, edt_pssword;
    Button btn_login;
    TextView tv_max_crowd;
    boolean doubleBackToExitPressedOnce = false;
    String error_msg = "";
    ProgressDialog pd;
    String message = "";
    CheckBox checkBoxRMe;
    boolean isRememberMe = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        hideKeyboard(LoginActivity.this);
        init();
    }

    private void init() {
        edt_email = findViewById(R.id.edt_email);
        edt_pssword = findViewById(R.id.edt_pssword);
        tv_max_crowd = findViewById(R.id.tv_max_crowd);
        btn_login = findViewById(R.id.btn_login);
        checkBoxRMe = findViewById(R.id.checkBoxRMe);

        checkBoxRMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBoxRMe.isChecked()) {
                    isRememberMe = checkBoxRMe.isChecked();
                    Log.d(TAG, "onCheckedChanged: " + isRememberMe);
                } else {
                    isRememberMe = checkBoxRMe.isChecked();
                    Log.d(TAG, "onCheckedChanged: " + "Else-------" + isRememberMe);
                }
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetConnected(getApplicationContext())) {
                    if (Validation()) {
                        getLogin();
                    } else {
                        showToast(LoginActivity.this, error_msg);
                    }
                } else {
                    showToast(LoginActivity.this, getResources().getString(R.string.oops_connect_your_internet));
                }
            }
        });


        tv_max_crowd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://maxcrowdfund.com"));
                startActivity(intent);
            }
        });
    }

    private void getLogin() {
        Utils.hideKeyboard(this);
        pd = ProgressDialog.show(LoginActivity.this, "Please Wait...");
        String loginUrl = APIUrl.GER_LOGIN;
        try {
            JSONObject mainJson = new JSONObject();
            try {
                mainJson.put("name", edt_email.getText().toString().trim());
                mainJson.put("pass", edt_pssword.getText().toString().trim());
                Log.d(TAG, "FINAL JSON" + String.valueOf(mainJson));
                Log.d(TAG, "getLogin: " + loginUrl);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, loginUrl, mainJson, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    Log.d("Login RESPONCE:::", response.toString());
                    JSONObject jobj = null;
                    try {
                        //==================For Creating JSONObject =============================
                        jobj = new JSONObject(response.toString());
                        if (jobj != null) {
                            message = jobj.getString("message");
                            if (message.equals("Succesfully Logged In")) {
                                String mSattus = jobj.getString("status");
                                if (mSattus.equals("200")) {
                                    JSONObject obj = jobj.getJSONObject("current_user");
                                    String uid = obj.getString("uid");
                                    String name = obj.getString("name");
                                    String csrf_token = obj.getString("csrf_token");
                                    String logout_token = obj.getString("logout_token");
                                    setPreference(LoginActivity.this, "isRememberMe", String.valueOf(isRememberMe));
                                    setPreference(LoginActivity.this, "user_id", uid);
                                    setPreference(LoginActivity.this, "mName", name);
                                    setPreference(LoginActivity.this, "mCsrf_token", csrf_token);
                                    setPreference(LoginActivity.this, "mLogout_token", logout_token);
                                    showToast(LoginActivity.this, message);
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    overridePendingTransition(R.anim.enter, R.anim.exit);
                                    edt_email.setText("");
                                    edt_pssword.setText("");
                                    finish();
                                }
                            }
                        } else {
                            showToast(LoginActivity.this, message);
                        }
                    } catch (Exception e) {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        showToast(LoginActivity.this, getResources().getString(R.string.something_went));
                        Log.d(TAG, "onResponse: " + e.getMessage());
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
                                    showToast(LoginActivity.this, mMessage);
                                } else if (response.statusCode == 401) {
                                    Log.d(TAG, "onErrorResponse: " + "statusCode:---401------" + response.statusCode);
                                    showToast(LoginActivity.this, mMessage);
                                } else if (response.statusCode == 403) {
                                    showToast(LoginActivity.this, mMessage);
                                    Log.d(TAG, "onErrorResponse: " + "statusCode:---403------" + response.statusCode + "mMessage------" + mMessage);
                                } else if (response.statusCode == 422) {
                                    showToast(LoginActivity.this, mMessage);
                                    Log.d(TAG, "onErrorResponse: " + "statusCode:---422---String---" + new String(response.data));
                                    //  json = trimMessage(new String(response.data));
                                    if (json != "" && json != null) {
                                        // displayMessage(json);
                                    } else {
                                        showToast(LoginActivity.this, getResources().getString(R.string.please_try_again));
                                    }
                                } else if (response.statusCode == 503) {
                                    showToast(LoginActivity.this, getResources().getString(R.string.server_down));
                                } else {
                                    showToast(LoginActivity.this, getResources().getString(R.string.please_try_again));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            if (error instanceof NoConnectionError) {
                                showToast(LoginActivity.this, getResources().getString(R.string.oops_connect_your_internet));
                            } else if (error instanceof NetworkError) {
                                showToast(LoginActivity.this, getResources().getString(R.string.oops_connect_your_internet));
                            } else if (error instanceof TimeoutError) {
                                try {
                                    if (error.networkResponse == null) {
                                        if (error.getClass().equals(TimeoutError.class)) {
                                            // Show timeout error message
                                            showToast(LoginActivity.this, getResources().getString(R.string.timed_out));
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

    private boolean Validation() {
        error_msg = "";
        if (TextUtils.isEmpty(edt_email.getText().toString().trim())) {
            error_msg = getString(R.string.enter_email);
            return false;
        } else if (TextUtils.isEmpty(edt_pssword.getText().toString().trim())) {
            error_msg = getString(R.string.enter_password);
            return false;
        } else {
            return true;
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

// overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

   /* private void getLogin() {
        Utils.hideKeyboard(this);
        pd = ProgressDialog.show(LoginActivity.this, "Please Wait...");
        String loginUrl = APIUrl.GER_LOGIN;
        try {
            JSONObject mainJson = new JSONObject();
            try {
                mainJson.put("name", edt_email.getText().toString().trim());
                mainJson.put("pass", edt_pssword.getText().toString().trim());
                Log.d(TAG, "FINAL JSON" + String.valueOf(mainJson));
                Log.d(TAG, "getLogin: " + loginUrl);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, loginUrl, mainJson, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    Log.d("Login RESPONCE:::", response.toString());
                    JSONObject jobj = null;
                    try {
                        //==================For Creating JSONObject =============================
                        jobj = new JSONObject(response.toString());
                        if (jobj != null) {
                            message = jobj.getString("message");
                            if (message.equals("Succesfully Logged In")) {
                                String mSattus = jobj.getString("status");
                                if (mSattus.equals("200")) {
                                    JSONObject obj = jobj.getJSONObject("current_user");
                                    String uid = obj.getString("uid");
                                    JSONArray jsonArray = obj.getJSONArray("roles");
                                    String name = obj.getString("name");
                                    String csrf_token = obj.getString("csrf_token");
                                    String logout_token = obj.getString("logout_token");

                                    Log.d(TAG, "onResponse: " + name);
                                    Log.d(TAG, "onResponse: " + uid);
                                    Log.d(TAG, "onResponse: " + csrf_token);
                                    Log.d(TAG, "onResponse: " + logout_token);

                                    setPreference(LoginActivity.this, "user_id", uid);
                                    setPreference(LoginActivity.this, "mName", name);
                                    setPreference(LoginActivity.this, "mCsrf_token", csrf_token);
                                    setPreference(LoginActivity.this, "mLogout_token", logout_token);

                                    showToast(LoginActivity.this, message);
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    overridePendingTransition(R.anim.enter, R.anim.exit);
                                    edt_email.setText("");
                                    edt_pssword.setText("");
                                    finish();
                                }
                            } else if (message.equalsIgnoreCase("Sorry, unrecognized username or password.")) {
                                showToast(LoginActivity.this, message);
                            }
                        } else {
                            showToast(LoginActivity.this, message);
                        }
                    } catch (Exception e) {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        showToast(LoginActivity.this, getResources().getString(R.string.something_went));
                        Log.d(TAG, "onResponse: " + e.getMessage());
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
                        // showToast(LoginActivity.this, getResources().getString(R.string.something_went));
                        String json = null;
                        String mMessage = "";
                        NetworkResponse response = error.networkResponse;
                        Log.d(TAG, "onErrorResponse: " + response.statusCode);
                        Log.d(TAG, "onErrorResponse: " + response.data.toString());
                        Log.d(TAG, "onErrorResponse: " + response.headers);
                        Log.d(TAG, "onErrorResponse: " + response.headers.get("Set-Cookie"));

                        if (response != null && response.data != null) {
                            try {
                                JSONObject errorObj = new JSONObject(new String(response.data));
                               *//* String body=new String(response.data);
                                Log.d(TAG, "onErrorResponse: " + "NetworkResponse-body--------" + body);
                                *//*
 *//* try {
                                    String jsond = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                  *//*

                                mMessage=errorObj.getString("message");
                                Log.d(TAG, "onErrorResponse: " + "NetworkResponse---------" + errorObj.getString("message"));
                                Log.d(TAG, "onErrorResponse: " + "statusCode:---------" + response.statusCode);

                                if (response.statusCode == 400 || response.statusCode == 405 || response.statusCode == 500) {
                                    Log.d(TAG, "onErrorResponse: " + "statusCode:--400-------" + response.statusCode+"mMessage-------"+mMessage);
                                    showToast(LoginActivity.this, mMessage);
                                    // showToast(LoginActivity.this, getResources().getString(R.string.something_went));
                                } else if (response.statusCode == 401) {
                                    Log.d(TAG, "onErrorResponse: " + "statusCode:---401------" + response.statusCode);
                                    showToast(LoginActivity.this, mMessage);
                                } else if (response.statusCode == 403) {
                                    showToast(LoginActivity.this, mMessage);
                                    Log.d(TAG, "onErrorResponse: " + "statusCode:---403------" + response.statusCode+ "mMessage------"+mMessage);
                                } else if (response.statusCode == 422) {
                                    showToast(LoginActivity.this, mMessage);
                                    Log.d(TAG, "onErrorResponse: " + "statusCode:---422------" + response.statusCode);
                                    Log.d(TAG, "onErrorResponse: " + "statusCode:---422------" + new String(response.data));
                                    //  json = trimMessage(new String(response.data));
                                    if (json != "" && json != null) {
                                        // displayMessage(json);
                                    } else {
                                        showToast(LoginActivity.this, getResources().getString(R.string.please_try_again));
                                    }
                                } else if (response.statusCode == 503) {
                                    showToast(LoginActivity.this, getResources().getString(R.string.server_down));
                                } else {
                                    showToast(LoginActivity.this, getResources().getString(R.string.please_try_again));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            if (error instanceof NoConnectionError) {
                                showToast(LoginActivity.this, getResources().getString(R.string.oops_connect_your_internet));
                            } else if (error instanceof NetworkError) {
                                showToast(LoginActivity.this, getResources().getString(R.string.oops_connect_your_internet));
                            } else if (error instanceof TimeoutError) {
                                try {
                                    if (error.networkResponse == null) {
                                        if (error.getClass().equals(TimeoutError.class)) {
                                            // Show timeout error message
                                            showToast(LoginActivity.this, getResources().getString(R.string.timed_out));
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
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
    }*/