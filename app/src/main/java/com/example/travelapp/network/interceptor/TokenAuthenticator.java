package com.example.travelapp.network.interceptor;

import android.content.Intent;
import android.content.SharedPreferences;

import com.example.travelapp.LoginActivity;
import com.example.travelapp.MainActivity;
import com.example.travelapp.network.ApiClient;
import com.example.travelapp.network.ApiInterface;
import com.example.travelapp.network.response.UserResponse;
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
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        if (mainResponse.code() == 401 || mainResponse.code() == 403) {
            String token = sharedPrefManager.getToken();
            retrofit2.Response<UserResponse> refreshToken = apiInterface.refreshToken(token).execute();
            if (refreshToken.isSuccessful()) {
                sharedPrefManager.saveSPString(SharedPrefManager.TOKEN, "Bearer " + refreshToken.body().getToken());

                Request.Builder builder = mainRequest.newBuilder().header("Authorization", sharedPrefManager.getToken())
                        .method(mainRequest.method(), mainRequest.body());
                mainResponse = chain.proceed(builder.build());
            }
        } else if (mainResponse.code() == 500) {
            sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_LOGIN, false);
            Intent i = new Intent(MainActivity.getContext(), LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MainActivity.getContext().startActivity(i);
        }
        return  mainResponse;
    }
}
