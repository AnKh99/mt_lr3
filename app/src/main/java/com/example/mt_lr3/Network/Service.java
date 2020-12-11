package com.example.mt_lr3.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service {
    private static Service mInstance;
    private static final String BASE_URL = "https://myfakeapi.com/api/";
    private Retrofit mRetrofit;

    private Service() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Service getInstance() {
        if (mInstance == null) {
            mInstance = new Service();
        }
        return mInstance;
    }

    public Interface getJSONApi() {
        return mRetrofit.create(Interface.class);
    }
}
