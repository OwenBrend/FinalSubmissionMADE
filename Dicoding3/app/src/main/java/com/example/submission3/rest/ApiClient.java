package com.example.submission3.rest;

import com.example.submission3.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {

    private static Retrofit sRetrofit = null;

    public static Retrofit getClient(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if(sRetrofit == null){
            sRetrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return sRetrofit;
    }
}
