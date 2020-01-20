package com.auxesis.maxcrowdfund.mvvm.ui.contactinformation;

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
import com.auxesis.maxcrowdfund.constant.ProgressDialog;
import com.auxesis.maxcrowdfund.constant.Utils;
import com.auxesis.maxcrowdfund.mvvm.activity.HomeActivity;
import com.auxesis.maxcrowdfund.mvvm.ui.contactinformation.contactInformationModel.ContactInformationResponse;
import com.auxesis.maxcrowdfund.restapi.ApiClient;
import com.auxesis.maxcrowdfund.restapi.EndPointInterface;
import com.google.gson.Gson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactInformationFragement extends Fragment {
    private static final String TAG = "ContactFragement";
    ProgressDialog pd;
    TextView tvHeading;
    RecyclerView recyclerView;
    ContactInformationAdapter contactInformationAdapter;
    LinearLayoutManager layoutManager;

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
            getContactInformation();
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
        }
        return root;
    }

    private void getContactInformation() {
        pd = ProgressDialog.show(getActivity(), "Please Wait...");
        EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
        Call<ContactInformationResponse> call = git.getContactInformation("application/json");
        call.enqueue(new Callback<ContactInformationResponse>() {
            @Override
            public void onResponse(Call<ContactInformationResponse> call, Response<ContactInformationResponse> response) {
                Log.d(TAG, "onResponse: " + "><><" + new Gson().toJson(response.body()));
                try {
                    if (response != null && response.isSuccessful()) {
                        if (pd != null && pd.isShowing()) {
                            pd.dismiss();
                        }
                        tvHeading.setText(response.body().getContactInformation().getHeading());
                        if (response.body().getContactInformation().getData().size()>0){
                            recyclerView.setHasFixedSize(true);
                            layoutManager = new LinearLayoutManager(getActivity());
                            recyclerView.setLayoutManager(layoutManager);
                            contactInformationAdapter =new ContactInformationAdapter(getActivity(),response.body().getContactInformation().getData());
                            recyclerView.setAdapter(contactInformationAdapter);
                            contactInformationAdapter.notifyDataSetChanged();
                        }else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ContactInformationResponse> call, Throwable t) {
                Log.e("", "error " + t.getMessage());
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onResume() {
        super.onResume();
        // Set title bar
        ((HomeActivity) getActivity()).setActionBarTitle(getString(R.string.menu_contact_information));
    }
}