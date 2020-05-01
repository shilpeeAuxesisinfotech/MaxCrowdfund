package com.auxesis.maxcrowdfund.constant;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;

public class MaxCrowdFund extends Application {
    private static final String MY_PREFS_NAME = "MaxCrowdfundApp";
    private static MaxCrowdFund mInstance;
    private static Context mContext;

    public MaxCrowdFund() {};

    public static synchronized MaxCrowdFund getInstance() {
        if (mInstance == null) {
            mInstance = new MaxCrowdFund();
        }
        return mInstance;
    }

    public static String getParseError(JSONObject response){
        String mError="";
        try {
            JSONObject obj=response;
             mError =obj.getString("message");
            Log.d("><><Pasrse><>",mError);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mError;
    }


    public static HashSet<String> getCookies(Context context) {
        SharedPreferences mcpPreferences = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        return (HashSet<String>) mcpPreferences.getStringSet("cookies", new HashSet<String>());
    }

    public static boolean setCookies(Context context, HashSet<String> cookies) {
        SharedPreferences mcpPreferences = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = mcpPreferences.edit();
        //editor.clear();
        return editor.putStringSet("cookies", cookies).commit();
    }

    public static void getClearCookies(Context mContext, String key, String value) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }
}
