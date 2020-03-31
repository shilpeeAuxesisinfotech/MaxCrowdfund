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

   /* public static Retrofit getinstance(Context context) {
        OkHttpClient client = new OkHttpClient();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new AddCookiesInterceptor(context)); // VERY VERY IMPORTANT
        builder.addInterceptor(new ReceivedCookiesInterceptor(context)); // VERY VERY IMPORTANT
        builder.addInterceptor(logging);
        client = builder.build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(APIUrl.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
   */

    /*public static Retrofit getClient() {
        if (retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(interceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
                   // .addInterceptor(new AddCookiesInterceptor1())
                   // .addInterceptor(new ReceivedCookiesInterceptor1())
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    *//*.connectTimeout(2, TimeUnit.MINUTES)
                    .writeTimeout(2, TimeUnit.MINUTES)
                    .readTimeout(2, TimeUnit.MINUTES)*//*
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(APIUrl.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
*/
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
