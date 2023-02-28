package com.example.travelapp.network.interceptor;

import android.content.SharedPreferences;

import com.example.travelapp.MainActivity;
import com.example.travelapp.utils.SharedPrefManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenAuthenticator implements Interceptor {
    SharedPrefManager sharedPrefManager;

    public TokenAuthenticator() {
        sharedPrefManager = new SharedPrefManager(MainActivity.getContext());
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response mainResponse = chain.proceed(chain.request());
        Request mainRequest = chain.request();

        return  mainRequest;
    }
}
