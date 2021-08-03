package com.social.quotesapp.Api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {  // retrofit object and in api interface we declare end points of our api

    public static Retrofit RETROFIT=null;

    public static Retrofit getclient()
    {
        if(RETROFIT==null) {               // we dont want to make object of okkhttp and gson again again. so it will make only first time when retrofit is null
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();  // it will generate the requests
            Gson gson = new GsonBuilder().create();                           // gson object
            RETROFIT = new Retrofit.Builder()
                    .baseUrl("http://192.168.0.110/poetryapis/").client(okHttpClient)  // base url contain the similar address of all api
                    .addConverterFactory(GsonConverterFactory.create(gson)).build();
        }

        return  RETROFIT;
    }
}
