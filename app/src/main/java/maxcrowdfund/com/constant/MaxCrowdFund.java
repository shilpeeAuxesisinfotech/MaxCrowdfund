package maxcrowdfund.com.constant;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;

public class MaxCrowdFund extends Application {
    private static final String MY_PREFS_NAME = "MaxCrowdfundApp";
    private static MaxCrowdFund mInstance;
    public MaxCrowdFund() {};

    public static synchronized MaxCrowdFund getInstance() {
        if (mInstance == null) {
            mInstance = new MaxCrowdFund();
        }
        return mInstance;
    }

    public static HashSet<String> getCookies(Context context) {
        SharedPreferences mcpPreferences = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        return (HashSet<String>) mcpPreferences.getStringSet("cookies", new HashSet<String>());
    }

    public static boolean setCookies(Context context, HashSet<String> cookies) {
        SharedPreferences mcpPreferences = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = mcpPreferences.edit();
        return editor.putStringSet("cookies", cookies).commit();
    }

    public static void getClearCookies(Context mContext, String key, String value) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }
}
