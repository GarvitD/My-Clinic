package com.example.myclinic.Api;

import android.content.res.Resources;

import com.example.myclinic.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {

    private static final String API_BASE_URL = "https://bigohealthdevtest.herokuapp.com";
    private static RetroClient retroClientInstance;
    private Retrofit retrofit;

    private RetroClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetroClient getInstance() {
        if(retroClientInstance == null) {
            retroClientInstance = new RetroClient();
        }
        return retroClientInstance;
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }
}
