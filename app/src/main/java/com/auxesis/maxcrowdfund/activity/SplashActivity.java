package com.auxesis.maxcrowdfund.activity;

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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import com.auxesis.maxcrowdfund.R;
import com.auxesis.maxcrowdfund.constant.Utils;
import java.util.HashMap;
import java.util.Map;
import static com.auxesis.maxcrowdfund.constant.Utils.getPreference;
import static com.auxesis.maxcrowdfund.constant.Utils.isInternetConnected;


public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    private static int SPLASH_SCREEN_TIME_OUT = 1500; //3000
    Animation zoom_in;
    TextView tvMax, tv_crowdfund, tvVersion;
    String versionName = "";
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        zoom_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        init();
    }

    private void init() {
        tvMax = findViewById(R.id.tvMax);
        tv_crowdfund = findViewById(R.id.tv_crowdfund);
        tvVersion = findViewById(R.id.tvVersion);
        tvMax.startAnimation(zoom_in);
        tv_crowdfund.startAnimation(zoom_in);

        if (isInternetConnected(getApplicationContext())) {
            if (Utils.checkRequestPermiss(getApplicationContext(), SplashActivity.this)) {
                Log.d(TAG, "onClick: " + "permission already granted");
                doPermissionGranted();
            }
        } else {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(SplashActivity.this);
            alertBuilder.setCancelable(true);
            alertBuilder.setTitle(R.string.no_internet);
            //alertBuilder.setMessage("Internet not available, Cross check your internet connectivity and try again");
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
                if (getPreference(SplashActivity.this, "user_id") != null && !getPreference(SplashActivity.this, "user_id").isEmpty()) {
                    if (getPreference(SplashActivity.this, "isRememberMe") != null && !getPreference(SplashActivity.this, "isRememberMe").isEmpty()) {
                        if (getPreference(SplashActivity.this, "isRememberMe").equals("true")) {
                            Log.d(TAG, "run: " + getPreference(SplashActivity.this, "isRememberMe"));
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.enter, R.anim.exit);
                            finish();
                        } else {
                            Log.d(TAG, "run: " + "<><><><" + getPreference(SplashActivity.this, "isRememberMe"));
                            Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                            startActivity(mainIntent);
                            overridePendingTransition(R.anim.enter, R.anim.exit);
                            finish();
                        }
                    }
                } else {
                    Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(mainIntent);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                    finish();
                }
            }
        }, SPLASH_SCREEN_TIME_OUT);
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
                        startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("com.auxesis.maxcrowdfund")));
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
}
