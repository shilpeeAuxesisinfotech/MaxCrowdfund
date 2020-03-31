package com.auxesis.maxcrowdfund.restapi;
import android.content.Context;
import android.util.Log;

import com.auxesis.maxcrowdfund.constant.MaxCrowdFund;

import java.io.IOException;
import java.util.HashSet;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
/**
 * This interceptor put all the Cookies in Preferences in the Request.
 * Your implementation on how to get the Preferences may vary.
 */
public class AddCookiesInterceptor implements Interceptor {
    private Context context;

    public AddCookiesInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        HashSet<String> preferences = MaxCrowdFund.getCookies(context);
        if (preferences != null) {
            for (String cookie : preferences) {
                builder.addHeader("Cookie", cookie);
                /*if (cookie.contains("expires")){
                    Log.v("OkHttp", "Adding Header: Exif" + cookie);
                    preferences.clear();
                }else {
                    builder.addHeader("Cookie", cookie);
                    Log.v("OkHttp", "Adding Header: add " + cookie);
                }*/
                Log.v("OkHttp", "Adding Header: " + cookie); // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
            }
        }
        return chain.proceed(builder.build());
    }
}

