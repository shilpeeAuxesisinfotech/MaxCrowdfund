package com.auxesis.maxcrowdfund.mvvm.ui.contactinformation;

import android.content.res.Resources;
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
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.constant.APIUrl;
import com.auxesis.maxcrowdfund.constant.MaxCrowdFund;
import com.auxesis.maxcrowdfund.constant.ProgressDialog;
import com.auxesis.maxcrowdfund.constant.Utils;
import com.auxesis.maxcrowdfund.mvvm.activity.HomeActivity;
import com.auxesis.maxcrowdfund.mvvm.ui.contactinformation.contactInformationModel.ContactInfoModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.auxesis.maxcrowdfund.constant.Utils.getPreference;

public class ContactInformationFragement extends Fragment {
    private static final String TAG = "ContactFragement";
    ProgressDialog pd;
    TextView tvHeading;
    RecyclerView recyclerView;
    ContactInformationAdapter contactInformationAdapter;
    LinearLayoutManager layoutManager;
    List<ContactInfoModel> nArrayLis = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_contact_information, container, false);
        tvHeading = root.findViewById(R.id.tvHeading);
        recyclerView = root.findViewById(R.id.recyclerView);

        if (Utils.isInternetConnected(getActivity())) {
            getContactInformationApi();
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
        }
        return root;
    }

    private void getContactInformationApi() {
        pd = ProgressDialog.show(getActivity(), "Please Wait...");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, APIUrl.GER_CONTECT_INFO, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                Log.d("Contact information--", response.toString());
                JSONObject jobj = null;
                try {
                    jobj = new JSONObject(response.toString());
                    if (jobj != null) {
                        JSONObject obj = jobj.getJSONObject("contact_information");
                        tvHeading.setText(obj.getString("heading"));
                        JSONArray jsonArray = obj.getJSONArray("data");
                        nArrayLis.clear();
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                ContactInfoModel model =new ContactInfoModel();
                                model.setmTitle(jsonArray.getJSONObject(i).getString("title"));
                                model.setmValue(jsonArray.getJSONObject(i).getString("value"));
                                nArrayLis.add(model);
                            }
                            if (nArrayLis.size()>0){
                                recyclerView.setHasFixedSize(true);
                                layoutManager = new LinearLayoutManager(getActivity());
                                recyclerView.setLayoutManager(layoutManager);
                                contactInformationAdapter =new ContactInformationAdapter(getActivity(),nArrayLis);
                                recyclerView.setAdapter(contactInformationAdapter);
                                contactInformationAdapter.notifyDataSetChanged();
                            }}else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
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
                                Toast.makeText(getActivity(), mMessage, Toast.LENGTH_SHORT).show();
                            } else if (response.statusCode == 401) {
                                Toast.makeText(getActivity(), mMessage, Toast.LENGTH_SHORT).show();
                            } else if (response.statusCode == 403) {
                                Toast.makeText(getActivity(), mMessage, Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onErrorResponse: " + "statusCode:---403------" + response.statusCode + "mMessage------" + mMessage);
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
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.d(">>", "getHeaders");
                String XCSRF = getPreference(getActivity(), "mCsrf_token");
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("X-CSRF-TOKEN", XCSRF);
                if (!MaxCrowdFund.getCookie(getContext()).equals("")) {
                    String cookie = MaxCrowdFund.getCookie(getContext());
                    Log.d("Cookie to load from", cookie);
                    headers.put("Cookie", cookie);
                }
                return headers;
            }

            @Override
            protected com.android.volley.Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                Map headers = response.headers;
                String cookie = (String) headers.get("Set-Cookie");
                MaxCrowdFund.saveCookie(getContext(), cookie);
                Log.d("Cookie to save prefer--", cookie);
                return super.parseNetworkResponse(response);
            }
        };
        // Add StringRequest to the RequestQueue
        MaxCrowdFund.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);
    }
    public void onResume() {
        super.onResume();
        // Set title bar
        ((HomeActivity) getActivity()).setActionBarTitle(getString(R.string.menu_contact_information));
    }
}