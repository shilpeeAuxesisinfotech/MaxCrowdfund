package com.auxesis.maxcrowdfund.restapi;

import android.content.Context;
import com.auxesis.maxcrowdfund.constant.APIUrl;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient1(Context context) {
        if (retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(interceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
                    .addInterceptor(new AddCookiesInterceptor(context))
                    .addInterceptor(new ReceivedCookiesInterceptor(context))
                    .connectTimeout(2, TimeUnit.MINUTES)
                    .writeTimeout(2, TimeUnit.MINUTES)
                    .readTimeout(2, TimeUnit.MINUTES)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(APIUrl.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}
