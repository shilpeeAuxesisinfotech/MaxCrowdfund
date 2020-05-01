package com.auxesis.maxcrowdfund.mvvm.ui.contactinformation;

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
import com.auxesis.maxcrowdfund.constant.ProgressDialog;
import com.auxesis.maxcrowdfund.constant.Utils;
import com.auxesis.maxcrowdfund.mvvm.activity.HomeActivity;
import com.auxesis.maxcrowdfund.mvvm.activity.LoginActivity;
import com.auxesis.maxcrowdfund.mvvm.ui.contactinformation.contactInformationModel.ContactInfoModel;
import com.auxesis.maxcrowdfund.mvvm.ui.contactinformation.contactInformationModel.ContactInfoResponse;
import com.auxesis.maxcrowdfund.restapi.ApiClient;
import com.auxesis.maxcrowdfund.restapi.EndPointInterface;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.auxesis.maxcrowdfund.constant.Utils.getPreference;
import static com.auxesis.maxcrowdfund.constant.Utils.setPreference;

public class ContactInformationFragement extends Fragment {
    private static final String TAG = "ContactFragement";
    ProgressDialog pd;
    TextView tvHeading;
    RecyclerView recyclerView;
    ContactInformationAdapter contactInformationAdapter;
    LinearLayoutManager layoutManager;
    List<ContactInfoModel> nArrayLis = new ArrayList<>();
    Activity mActivity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_contact_information, container, false);
        mActivity = getActivity();
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
        String XCSRF = getPreference(getActivity(), "mCsrf_token");
        EndPointInterface git = ApiClient.getClient1(getActivity()).create(EndPointInterface.class);
        Call<ContactInfoResponse> call = git.getContactInformation("application/json", XCSRF);
        call.enqueue(new Callback<ContactInfoResponse>() {
            @Override
            public void onResponse(Call<ContactInfoResponse> call, Response<ContactInfoResponse> response) {
                Log.d(TAG, "onResponse: " + "><Contax><" + new Gson().toJson(response.body()));
                try {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    if (response.code()==200) {
                        if (response != null && response.isSuccessful()) {
                            if (response.body().getUserLoginStatus() == 1) {
                                if (response.body().getContactInformation() != null) {
                                    tvHeading.setText(response.body().getContactInformation().getHeading());
                                    if (response.body().getContactInformation().getData() != null) {
                                        nArrayLis.clear();
                                        if (response.body().getContactInformation().getData().size() > 0) {
                                            for (int i = 0; i < response.body().getContactInformation().getData().size(); i++) {
                                                ContactInfoModel model = new ContactInfoModel();
                                                model.setmTitle(response.body().getContactInformation().getData().get(i).getTitle());
                                                model.setmValue(response.body().getContactInformation().getData().get(i).getValue());
                                                nArrayLis.add(model);
                                            }
                                            if (nArrayLis.size() > 0) {
                                                recyclerView.setHasFixedSize(true);
                                                layoutManager = new LinearLayoutManager(getActivity());
                                                recyclerView.setLayoutManager(layoutManager);
                                                contactInformationAdapter = new ContactInformationAdapter(getActivity(), nArrayLis);
                                                recyclerView.setAdapter(contactInformationAdapter);
                                                contactInformationAdapter.notifyDataSetChanged();
                                            }
                                        } else {
                                            Toast.makeText(getActivity(), getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }else {
                                setPreference(getActivity(), "user_id", "");
                                setPreference(getActivity(), "mLogout_token", "");
                                MaxCrowdFund.getInstance().getClearCookies(getActivity(), "cookies", "");
                                Toast.makeText(getActivity(), getResources().getString(R.string.session_expire), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                                mActivity.finish();
                            }
                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        Toast.makeText(getActivity(), getResources().getString(R.string.something_went), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ContactInfoResponse> call, Throwable t) {
                Log.e("response", "error " + t.getMessage());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
            }
        });
    }

    public void onResume() {
        super.onResume();
        ((HomeActivity) getActivity()).setActionBarTitle(getString(R.string.menu_contact_information));
    }
}