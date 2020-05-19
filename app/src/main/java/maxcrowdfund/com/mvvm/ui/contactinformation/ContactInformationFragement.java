package maxcrowdfund.com.mvvm.ui.contactinformation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import maxcrowdfund.com.R;
import maxcrowdfund.com.constant.MaxCrowdFund;
import maxcrowdfund.com.constant.ProgressDialog;
import maxcrowdfund.com.constant.Utils;
import maxcrowdfund.com.databinding.FragmentContactInformationBinding;
import maxcrowdfund.com.mvvm.activity.HomeActivity;
import maxcrowdfund.com.mvvm.activity.LoginActivity;
import maxcrowdfund.com.mvvm.ui.contactinformation.contactInformationModel.ContactInfoModel;
import maxcrowdfund.com.mvvm.ui.contactinformation.contactInformationModel.ContactInfoResponse;
import maxcrowdfund.com.restapi.ApiClient;
import maxcrowdfund.com.restapi.EndPointInterface;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactInformationFragement extends Fragment {
    private static final String TAG = "ContactFragement";
    ProgressDialog pd;
    ContactInformationAdapter contactInformationAdapter;
    LinearLayoutManager layoutManager;
    List<ContactInfoModel> nArrayLis = new ArrayList<>();
    Activity mActivity;
    FragmentContactInformationBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentContactInformationBinding.inflate(inflater, container, false);
        mActivity = getActivity();
        if (Utils.isInternetConnected(getActivity())) {
            getContactInformationApi();
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
        }
        return binding.getRoot();
    }

    private void getContactInformationApi() {
        pd = ProgressDialog.show(getActivity(), "Please Wait...");
        String XCSRF = Utils.getPreference(getActivity(), "mCsrf_token");
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
                    if (response.code() == 200) {
                        if (response != null && response.isSuccessful()) {
                            if (response.body().getUserLoginStatus() == 1) {
                                if (response.body().getContactInformation() != null) {
                                    binding.tvHeading.setText(response.body().getContactInformation().getHeading());
                                    if (response.body().getContactInformation().getData() != null) {
                                        nArrayLis.clear();
                                        if (response.body().getContactInformation().getData().size() > 0) {
                                            binding.tvNoDataFound.setVisibility(View.GONE);
                                            binding.recyclerView.setVisibility(View.VISIBLE);
                                            for (int i = 0; i < response.body().getContactInformation().getData().size(); i++) {
                                                ContactInfoModel model = new ContactInfoModel();
                                                model.setmTitle(response.body().getContactInformation().getData().get(i).getTitle());
                                                model.setmValue(response.body().getContactInformation().getData().get(i).getValue());
                                                nArrayLis.add(model);
                                            }
                                            if (nArrayLis.size() > 0) {
                                                binding.recyclerView.setHasFixedSize(true);
                                                layoutManager = new LinearLayoutManager(getActivity());
                                                binding.recyclerView.setLayoutManager(layoutManager);
                                                contactInformationAdapter = new ContactInformationAdapter(getActivity(), nArrayLis);
                                                binding.recyclerView.setAdapter(contactInformationAdapter);
                                                contactInformationAdapter.notifyDataSetChanged();
                                            }
                                        } else {
                                            binding.tvNoDataFound.setVisibility(View.VISIBLE);
                                            binding.recyclerView.setVisibility(View.GONE);
                                        }
                                    }
                                }
                            } else {
                                Utils.setPreference(getActivity(), "user_id", "");
                                Utils.setPreference(getActivity(), "mLogout_token", "");
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