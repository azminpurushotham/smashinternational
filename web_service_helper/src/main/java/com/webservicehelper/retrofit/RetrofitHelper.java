package com.webservicehelper.retrofit;

import android.content.Context;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.webservicehelper.retrofit.APIs.BASE_URL;


/**
 * Created by azmin on 20/1/16.
 */
public class RetrofitHelper {
    static APIs apis;

    public RetrofitHelper() {
        initRestAdapter();
    }

    public static APIs getApis() {
        return apis;
    }

    public static void setApis(APIs apis) {
        RetrofitHelper.apis = apis;
    }

    public RetrofitHelper(Context mContext) {
        initRestAdapter();
    }

    public void initRestAdapter() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        setApis(restAdapter.create(APIs.class));
    }
}
