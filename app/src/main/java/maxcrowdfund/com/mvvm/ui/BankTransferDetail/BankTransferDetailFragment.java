package maxcrowdfund.com.mvvm.ui.BankTransferDetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import maxcrowdfund.com.R;
import maxcrowdfund.com.constant.MaxCrowdFund;
import maxcrowdfund.com.constant.ProgressDialog;
import maxcrowdfund.com.constant.Utils;
import maxcrowdfund.com.mvvm.activity.HomeActivity;
import maxcrowdfund.com.mvvm.activity.LoginActivity;
import maxcrowdfund.com.mvvm.ui.BankTransferDetail.model.BankTransferDetailMailResponse;
import maxcrowdfund.com.mvvm.ui.BankTransferDetail.model.BankTransferDetailResponse;
import maxcrowdfund.com.restapi.ApiClient;
import maxcrowdfund.com.restapi.EndPointInterface;
import com.google.gson.Gson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static android.text.Html.fromHtml;

public class BankTransferDetailFragment extends Fragment {
    private static final String TAG = "BankTransferDetailFragm";
    ProgressDialog pd;
    Activity mActivity;
    Button btnResend;
    String XCSRF = "";
    TextView tvShortMessage, tvAccountNumberTittle, tvAccountNubValue, tvBenifisaryTittle, tvtvBenifisaryValue, tvbiscSwTittle, tvbiscSwValue, tvrRreferenceTittle, tvrRreferenceValue, tvLongMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_bank_transfer_detail, container, false);
        mActivity = getActivity();

        XCSRF = Utils.getPreference(getActivity(), "mCsrf_token");
        tvShortMessage = root.findViewById(R.id.tvShortMessage);
        tvAccountNumberTittle = root.findViewById(R.id.tvAccountNumberTittle);
        tvAccountNubValue = root.findViewById(R.id.tvAccountNubValue);
        tvBenifisaryTittle = root.findViewById(R.id.tvBenifisaryTittle);
        tvtvBenifisaryValue = root.findViewById(R.id.tvtvBenifisaryValue);
        tvbiscSwTittle = root.findViewById(R.id.tvbiscSwTittle);

        tvbiscSwValue = root.findViewById(R.id.tvbiscSwValue);
        tvrRreferenceTittle = root.findViewById(R.id.tvrRreferenceTittle);
        tvrRreferenceValue = root.findViewById(R.id.tvrRreferenceValue);
        tvLongMessage = root.findViewById(R.id.tvLongMessage);
        btnResend = root.findViewById(R.id.btnResend);

       /* BankViewModel bankViewModel = ViewModelProviders.of(this).get(BankViewModel.class);
        bankViewModel.getResponseMutableLiveData().observe(this, new Observer<BankTransferDetailResponse>() {
            @Override
            public void onChanged(BankTransferDetailResponse bankTransferDetailResponse) {
                Log.d(">>>>>>>>>>",bankTransferDetailResponse.getBankTransferDetail().getHeading());
            }
        });*/

        if (Utils.isInternetConnected(getActivity())) {
            if (XCSRF.length() > 0) {
                getBankTransferDetail();
            }
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
        }

        btnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isInternetConnected(getActivity())) {
                    getBankTransferDetailMail();
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.oops_connect_your_internet), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return root;
    }

    private void getBankTransferDetail() {
        pd = ProgressDialog.show(getActivity(), "Please Wait...");
        EndPointInterface git = ApiClient.getClient1(getActivity()).create(EndPointInterface.class);
        Call<BankTransferDetailResponse> call = git.getBankTransferDetail("application/json", XCSRF);
        call.enqueue(new Callback<BankTransferDetailResponse>() {
            @Override
            public void onResponse(Call<BankTransferDetailResponse> call, Response<BankTransferDetailResponse> response) {
                Log.d(TAG, "onResponse: " + "><getBankTranfer><" + new Gson().toJson(response.body()));
                try {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    if (response.code() == 200) {
                        if (response != null && response.isSuccessful()) {
                            if (response.body().getUserLoginStatus() == 1) {
                                if (response.body().getBankTransferDetail() != null) {
                                    if (response.body().getBankTransferDetail().getData() != null) {
                                        if (response.body().getBankTransferDetail().getData().getShortMessage() != null) {
                                            tvShortMessage.setText(fromHtml(response.body().getBankTransferDetail().getData().getShortMessage().getValue()));
                                        }
                                        if (response.body().getBankTransferDetail().getData().getAccountNumber() != null) {
                                            tvAccountNumberTittle.setText(response.body().getBankTransferDetail().getData().getAccountNumber().getTitle());
                                            tvAccountNubValue.setText(response.body().getBankTransferDetail().getData().getAccountNumber().getValue());
                                        }

                                        if (response.body().getBankTransferDetail().getData().getBenificiaryName() != null) {
                                            tvBenifisaryTittle.setText(response.body().getBankTransferDetail().getData().getBenificiaryName().getTitle());
                                            tvtvBenifisaryValue.setText(response.body().getBankTransferDetail().getData().getBenificiaryName().getValue());
                                        }
                                        if (response.body().getBankTransferDetail().getData().getBicswift() != null) {
                                            tvbiscSwTittle.setText(response.body().getBankTransferDetail().getData().getBicswift().getTitle());
                                            tvbiscSwValue.setText(response.body().getBankTransferDetail().getData().getBicswift().getValue());
                                        }
                                        if (response.body().getBankTransferDetail().getData().getReference() != null) {
                                            tvrRreferenceTittle.setText(response.body().getBankTransferDetail().getData().getReference().getTitle());
                                            tvrRreferenceValue.setText(response.body().getBankTransferDetail().getData().getReference().getValue());
                                        }
                                        if (response.body().getBankTransferDetail().getData().getLongMessage() != null) {
                                            tvLongMessage.setText(fromHtml(response.body().getBankTransferDetail().getData().getLongMessage().getValue()));
                                        }
                                    }
                                } else {
                                    Toast.makeText(mActivity, getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getActivity(), getResources().getString(R.string.something_went), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BankTransferDetailResponse> call, Throwable t) {
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
        ((HomeActivity) getActivity()).setActionBarTitle(getString(R.string.bank_transfer_detail));
    }

    private void getBankTransferDetailMail() {
        pd = ProgressDialog.show(getActivity(), "Please Wait...");
        EndPointInterface git = ApiClient.getClient1(getActivity()).create(EndPointInterface.class);
        Call<BankTransferDetailMailResponse> call = git.getBankTransferDetailMail("application/json", XCSRF);
        call.enqueue(new Callback<BankTransferDetailMailResponse>() {
            @Override
            public void onResponse(Call<BankTransferDetailMailResponse> call, Response<BankTransferDetailMailResponse> response) {
                try {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    if (response.code() == 200) {
                        if (response != null && response.isSuccessful()) {
                            if (response.body().getUserLoginStatus() == 1) {
                                if (response.body().getBankTransferDetailMail() != null) {
                                    if (response.body().getBankTransferDetailMail().getStatus().equals("success")) {
                                        Toast.makeText(mActivity, response.body().getBankTransferDetailMail().getStatus(), Toast.LENGTH_SHORT).show();
                                        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                                        navController.navigateUp();
                                    }
                                } else {
                                    Toast.makeText(mActivity, getResources().getString(R.string.no_data_found), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getActivity(), getResources().getString(R.string.something_went), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BankTransferDetailMailResponse> call, Throwable t) {
                Log.e("response", "error " + t.getMessage());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
            }
        });
    }
}
