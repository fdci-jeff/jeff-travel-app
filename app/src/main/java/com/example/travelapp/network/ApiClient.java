package com.example.travelapp.network;

import

import com.example.travelapp.network.interceptor.TokenAuthenticator;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

public class ApiClient {
    private static final String url = "http://localhost/";

    private  static Retrofit retrofit;

    public static Retrofit getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor)
                .addInterceptor(new TokenAuthenticator())
                .build();
    }
}
