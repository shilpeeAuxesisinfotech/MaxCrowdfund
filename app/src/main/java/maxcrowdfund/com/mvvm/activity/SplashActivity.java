package maxcrowdfund.com.mvvm.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import maxcrowdfund.com.BuildConfig;
import maxcrowdfund.com.R;
import maxcrowdfund.com.constant.Utils;
import maxcrowdfund.com.databinding.ActivitySplashBinding;
import maxcrowdfund.com.mvvm.ui.uiTokens.UITokenResponse;
import maxcrowdfund.com.restapi.ApiClient;
import maxcrowdfund.com.restapi.EndPointInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.HashMap;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    ActivitySplashBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }
    private void init() {
        binding.tvVersion.setText("Version " + BuildConfig.VERSION_CODE + "( " + BuildConfig.VERSION_NAME + " )");
        if (Utils.isInternetConnected(getApplicationContext())) {
            if (Utils.checkRequestPermiss(getApplicationContext(), SplashActivity.this)) {
                Log.d(TAG, "onClick: " + "permission already granted");
                doPermissionGranted();
            }
        } else {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(SplashActivity.this);
            alertBuilder.setCancelable(true);
            alertBuilder.setTitle(R.string.oops_connect_your_internet);
            alertBuilder.setMessage("Please turn on internet connection to continue");
            alertBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            AlertDialog alert = alertBuilder.create();
            alert.show();
        }
    }
    private void doPermissionGranted() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Utils.isInternetConnected(getApplicationContext())) {
                    getUIToken();
                }else {
                    Toast.makeText(SplashActivity.this, Utils.oops_connect_your_internet, Toast.LENGTH_SHORT).show();
                }
            }
        }, 1500);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<>();
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);

                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "CAMERA  permission granted");
                        doPermissionGranted();
                    } else {
                        Log.d(TAG, "Camera Permission are not granted ask again ");
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            showDialogOK("Camera Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    Utils.checkRequestPermiss(getApplicationContext(), SplashActivity.this);
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        } else {
                            explain("Go to settings and enable permissions");
                        }
                    }
                }
            }
        }
    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    private void explain(String msg) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(msg)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("maxcrowdfund.com")));
                        // startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        finish();
                    }
                });
        dialog.show();
    }

    private void getUIToken() {
        EndPointInterface git = ApiClient.getClientUI(SplashActivity.this).create(EndPointInterface.class);
        Call<UITokenResponse> call = git.getUiToken("json","application/json");
        call.enqueue(new Callback<UITokenResponse>() {
            @Override
            public void onResponse(Call<UITokenResponse> call, Response<UITokenResponse> response) {
                if (response.code() == 200) {
                    if (response != null && response.isSuccessful()) {
                        if (response.body().getUiLoginTokens() != null) {
                            Utils.user_login_description = response.body().getUiLoginTokens().getUserLoginDescription();
                            Utils.user_login_email_placeholder = response.body().getUiLoginTokens().getUserLoginEmailPlaceholder();
                            Utils.user_login_password_placehplder = response.body().getUiLoginTokens().getUserLoginPasswordPlacehplder();
                            Utils.user_login_btn_login = response.body().getUiLoginTokens().getUserLoginBtnLogin();
                        }
                        if (response.body().getProfilePageTitleTokens()!=null){
                            Utils.profilePageTitle = response.body().getProfilePageTitleTokens().getProfilePageTitle();
                        }

                        if (Utils.getPreference(SplashActivity.this, "user_id") != null && !Utils.getPreference(SplashActivity.this, "user_id").isEmpty()) {
                            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.enter, R.anim.exit);
                            finish();
                        } else {
                            Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                            startActivity(mainIntent);
                            overridePendingTransition(R.anim.enter, R.anim.exit);
                            finish();
                        }

                    }
                }else {
                    //For mentince
                }
            }
            @Override
            public void onFailure(Call<UITokenResponse> call, Throwable t) {
                Log.e("response", "error " + t.getMessage());
                Toast.makeText(SplashActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
