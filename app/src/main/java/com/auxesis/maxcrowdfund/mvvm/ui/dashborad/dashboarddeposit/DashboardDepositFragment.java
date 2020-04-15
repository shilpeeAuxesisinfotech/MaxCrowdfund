package com.auxesis.maxcrowdfund.mvvm.ui.dashborad.dashboarddeposit;

import android.app.Activity;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.constant.ProgressDialog;
import com.auxesis.maxcrowdfund.mvvm.activity.HomeActivity;
import com.auxesis.maxcrowdfund.restapi.ApiClient;
import com.auxesis.maxcrowdfund.restapi.EndPointInterface;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.auxesis.maxcrowdfund.constant.Utils.getPreference;
import static com.auxesis.maxcrowdfund.constant.Utils.isInternetConnected;

public class DashboardDepositFragment extends Fragment {
    private static final String TAG = "DashboardDeposit";
    ProgressDialog pd;
   /* Button btn_ideal;
    int logged_in_user_id = 0;
    String active_account_type = "";
    int active_account_id = 0;
    String country_code = "";
    String address_line1 = "";
    String address_line2 = "";
    int postal_code = 0;
    String locality = "";
    String email = "";
    String last_name = "";
    String first_name = "";
    String mobile_phone = "";
    String mAmount = "";*/

    WebView mWebview;
    String mTrustlyUrl = "";
    String error_msg = "";
    String mAmount = "";
    Button btn_trustly;
    EditText edt_trustly;
    CardView cv_trustly;
    WebView webView;
    TextView tvTrustly;
    Activity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard_deposit, container, false);
        mActivity = getActivity();
        tvTrustly = root.findViewById(R.id.tvTrustly);
        edt_trustly = root.findViewById(R.id.edt_trustly);
        btn_trustly = root.findViewById(R.id.btn_trustly);
        cv_trustly = root.findViewById(R.id.cv_trustly);
        webView = root.findViewById(R.id.webView);



        if (getPreference(getActivity(), "totalBalance") != null && !getPreference(getActivity(), "totalBalance").isEmpty()) {
            tvTrustly.setText(getPreference(getActivity(), "totalBalance"));
        }
        cv_trustly.setVisibility(View.VISIBLE);
        webView.setVisibility(View.GONE);
        btn_trustly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetConnected(getActivity())) {
                    if (Validation()) {
                        getDeposit();
                    } else {
                        Toast.makeText(getActivity(), error_msg, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return root;
    }


    private void getDeposit() {
        pd = ProgressDialog.show(getActivity(), "Please Wait...");
        String XCSRF = getPreference(getActivity(), "mCsrf_token");
        EndPointInterface endPointInterface = ApiClient.getClient1(getActivity()).create(EndPointInterface.class);
        mAmount = edt_trustly.getText().toString().trim();
        JsonObject jsonObject = new JsonObject();
        if (jsonObject != null) {
            jsonObject.addProperty("amount_deposite", mAmount);
        }
        Log.d("><><>deposit<", jsonObject.toString());

        Call<DashboardDepositResponse> call = endPointInterface.getDashboardDeposit("application/json", XCSRF, jsonObject);
        call.enqueue(new Callback<DashboardDepositResponse>() {
            @Override
            public void onResponse(Call<DashboardDepositResponse> call, Response<DashboardDepositResponse> response) {
                try {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    if (response != null) {
                        if (response != null && response.isSuccessful()) {
                            Log.d(TAG, "onResponse: " + "><><" + new Gson().toJson(response.body()));
                            if (response.body().getResult().equals("success")) {
                                mTrustlyUrl = response.body().getTrustlyUrl().toString();
                                Log.d("><>mTrustlyUrl", mTrustlyUrl);
                                if (mTrustlyUrl != null) {
                                    edt_trustly.setText("");
                                    cv_trustly.setVisibility(View.GONE);
                                    webView.setVisibility(View.VISIBLE);
                                    webView.loadUrl(mTrustlyUrl);
                                    webView.getSettings().setJavaScriptEnabled(true);
                                    webView.setWebViewClient(new WebClient());

                                  /* mWebview.setWebViewClient(new WebClient());
                                   mWebview.loadUrl(mTrustlyUrl);
                                   mWebview.getSettings().setJavaScriptEnabled(true);*/
                                }
                            }
                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DashboardDepositResponse> call, Throwable t) {
                Log.e("", "error " + t.getMessage());
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                edt_trustly.setText("");
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private class WebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }

    }


    /* private void getDashboardDetail() {
         EndPointInterface endPointInterface = ApiClient.getClient1(getActivity()).create(EndPointInterface.class);
         Call<DashboardDetailModelResponce> call = endPointInterface.DashboardDetail();
         call.enqueue(new Callback<DashboardDetailModelResponce>() {
             @Override
             public void onResponse(Call<DashboardDetailModelResponce> call, Response<DashboardDetailModelResponce> response) {
                 Log.d(TAG, "onResponse: " + "><><" + new Gson().toJson(response.body()));
                 try {
                     DashboardDetailModelResponce detailModelResponce = response.body();
                     if (detailModelResponce != null && detailModelResponce.getActiveAccount() != null) {
                         logged_in_user_id = detailModelResponce.getActiveAccount().getData().getLoggedInUserId();
                         active_account_type = detailModelResponce.getActiveAccount().getData().getActiveAccountType();
                         active_account_id = detailModelResponce.getActiveAccount().getData().getActiveAccountId();
                         country_code = detailModelResponce.getActiveAccount().getData().getCountryCode();
                         address_line1 = detailModelResponce.getActiveAccount().getData().getAddressLine1();
                         address_line2 = detailModelResponce.getActiveAccount().getData().getAddressLine2();
                         postal_code = detailModelResponce.getActiveAccount().getData().getPostalCode();
                         locality = detailModelResponce.getActiveAccount().getData().getLocality();
                         email = detailModelResponce.getActiveAccount().getData().getEmail();
                         last_name = detailModelResponce.getActiveAccount().getData().getLastName();
                         first_name = detailModelResponce.getActiveAccount().getData().getFirstName();
                         mobile_phone = detailModelResponce.getActiveAccount().getData().getMobilePhone();
                     }
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             }

             @Override
             public void onFailure(Call<DashboardDetailModelResponce> call, Throwable t) {
                 Log.d("response", "error " + t.getMessage());
                 Toast.makeText(DashboardDepositActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
             }
         });
     }
 */
    /*public void getTrustly() {
        mAmount = edt_trustly.getText().toString().trim();
        if (mAmount != null && !mAmount.isEmpty()) {
            try {
                JSONObject jobjMain = new JSONObject();
                JSONArray array = new JSONArray();
                JSONObject jobj = new JSONObject();
                jobj.put("SuccessURL", "https://test.maxcrowdfund.com/deposit-amount/success");
                jobj.put("FailURL", "https://test.maxcrowdfund.com/profile/'.$uid");
                jobj.put("Currency", "EUR");
                jobj.put("Firstname", first_name);
                jobj.put("Lastname", last_name);
                jobj.put("Email", email);
                jobj.put("Country", country_code);
                jobj.put("Amount", mAmount);
                jobj.put("Locale", locality);
                jobj.put("MobilePhone", mobile_phone);
                array.put(jobj);
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                if (active_account_type.equals("company")) {
                    jsonObject.put("Partytype", "ORGANISATION");
                } else {
                    jsonObject.put("Partytype", "PERSON");
                }
                jsonObject.put("Firstname", first_name);
                jsonObject.put("Lastname", last_name);
                jsonObject.put("CountryCode", country_code);
                jsonObject.put("CustomerID", String.valueOf(active_account_id));
                jsonObject.put("Address", address_line1);
                jsonObject.put("EndUserID", "23");
                jsonObject.put("MessageID", "26-12-2019");
                jsonObject.put("NotificationURL", "https://test.maxcrowdfund.com/notification");
                jsonObject.put("Password", "78faffbe-a3f3-4a22-8b78-f975d50f2499");
                jsonObject.put("Username", "maxcrowdfund");
                jsonArray.put(jsonObject);
                jobj.put("RecipientInformation", jsonArray);
                jobjMain.put("Attributes", array);
                Log.d(TAG, "getTrustly: " + jobjMain);

                if (isInternetConnected(getApplicationContext())) {
                    //we will load it asynchronously from server in this method
                    getDashboardSignature(jobjMain);
                } else {
                    Toast.makeText(this, getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "getTrustly: " + e.getMessage());
            }
        }
    }
*/
    /*private void getDashboardSignature(JSONObject jobjMain) {
        String XCSRF = getPreference(DashboardDepositActivity.this, "mCsrf_token");
        Log.d(TAG, "getTrustly: " + "<><XCSRF><><" + XCSRF);
        EndPointInterface endPointInterface = ApiClient.getClient1(DashboardDepositActivity.this).create(EndPointInterface.class);
        Call<DashboardSignatureResponce> call = endPointInterface.DashboardDetailSignature("application/json", XCSRF, mMethod_deposit, getRandomNO(), jobjMain);
        call.enqueue(new Callback<DashboardSignatureResponce>() {
            @Override
            public void onResponse(Call<DashboardSignatureResponce> call, Response<DashboardSignatureResponce> response) {
                Log.d(TAG, "onResponse: " + "><Signaature><" + new Gson().toJson(response.body()));
                DashboardSignatureResponce responce = response.body();
                if (responce != null && responce.getSignature() != null) {
                    String signature = responce.getSignature();
                    Log.d(TAG, "onResponse: " + "signature-------" + signature);
                    try {
                        //For data posting

                        JSONArray array = new JSONArray();
                        JSONObject jobj = new JSONObject();
                        jobj.put("method", mMethod_deposit);
                        jobj.put("version", "1.1");

                        JSONObject jsn = new JSONObject();
                        JSONArray mArray = new JSONArray();
                        JSONObject mjobj = new JSONObject();
                        mjobj.put("Signature", responce.getSignature());
                        mjobj.put("UUID", getRandomNO());
                        mjobj.put("Data", jobjMain);
                        mArray.put(mjobj);

                        jobj.put("params", mArray);
                        array.put(jobj);

                        Log.d(TAG, "onResponse: " + "><><><" + array);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<DashboardSignatureResponce> call, Throwable t) {
                Log.d("response", "error " + t.getMessage());
                //showToast(DashboardDepositActivity.this, t.getMessage());
            }
        });
    }
*/
    private boolean Validation() {
        error_msg = "";
        if (TextUtils.isEmpty(edt_trustly.getText().toString().trim())) {
            error_msg = getString(R.string.enter_amount);
            return false;
        } else {
            return true;
        }
    }

    public void onResume() {
        super.onResume();
        // Set title bar
        ((HomeActivity) getActivity()).setActionBarTitle(getString(R.string.deposit));
    }

}
