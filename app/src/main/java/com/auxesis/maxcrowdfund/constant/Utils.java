package com.auxesis.maxcrowdfund.constant;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.auxesis.maxcrowdfund.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;
import static com.auxesis.maxcrowdfund.constant.APIUrl.uUid;

public class Utils {
    private static final String MY_PREFS_NAME = "MaxCrowdfund";
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    public static boolean isInternetConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE) ;

        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnectedOrConnecting()) {
            return true;
        }

        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnectedOrConnecting()) {
            return true;
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public static void setPreference(Context mContext, String key, String value) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getPreference(Context mContext, String key) {
        SharedPreferences prefs = mContext.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        return prefs.getString(key, "");
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View f = activity.getCurrentFocus();
        if (null != f && null != f.getWindowToken() && EditText.class.isAssignableFrom(f.getClass()))
            imm.hideSoftInputFromWindow(f.getWindowToken(), 0);
        else
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public static void slide_down(Context ctx, View v) {
        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_down);
        if (a != null) {
            a.reset();
            if (v != null) {
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }

    public static void slide_up(Context ctx, View v) {
        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_down);
        if (a != null) {
            a.reset();
            if (v != null) {
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }

    public static int getFilledData(int total_amount, String amount) throws ArithmeticException {
        int mAmountR = 0;
        if (amount == null) {
            mAmountR = 0;
        } else {
            try {
                mAmountR = ((total_amount * 100) / Integer.valueOf(amount));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return mAmountR;
    }


    // for replace <br>
    public static String getCustomRepalce(String[] message) {
        String mMessage = "";
        //match HTML tags
        String strRegEx = "<br>";
        //replace them with empty string to remove them
        for (String str : message) {
            mMessage = str.replaceAll(strRegEx, "");
            System.out.println("<><><><" + mMessage);
        }
        return mMessage;
    }


    public static String getCustomReplaceFormat(int number){
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(0);
        format.setCurrency(Currency.getInstance("EUR"));
        String moneyString =format.format(number);
        return moneyString;
    }

    public static String getRandomNO() {
        Random random = new Random();
        String mRandom = "";
        for (int i = 0; i <=11; i++) {
            mRandom = mRandom + String.valueOf(random.nextInt(9));
        }
        return (uUid+mRandom);
    }

    public static boolean checkRequestPermiss(Context mContext, Activity mActivity) {
        int cameraPer = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA);
        int writePer = ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int read = ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (cameraPer != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (writePer != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (read != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(mActivity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

}
