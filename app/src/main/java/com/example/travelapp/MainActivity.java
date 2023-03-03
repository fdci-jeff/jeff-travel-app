package com.example.travelapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private OkHttpClient okHttpClient = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String token = getTokenFromSharedPreferences();
        if (token != null && !token.isEmpty()) {
            checkToken(token);
        } else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void checkToken(String token){

        MediaType mediaType = MediaType.parse("application/json");

        String requestBody = "" +
                "{\"token\":\"" + token + "\"}";

        RequestBody body = RequestBody.create(requestBody, mediaType);
        Log.d("test", "checkToken: " + body);
        Log.d("test", "checkToken: " + token);
        Request $request = new Request.Builder()
                .url("http://10.0.2.2:8000/api/user/get_user")
                .method("GET", null)
                .addHeader("Authorization", "Bearer " + token)
                .build();

        okHttpClient.newCall($request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                Log.d("test", "onResponse: " + response);
                if (!response.isSuccessful()) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    String responseBody = response.body().string();
                    Log.d("test", "response" + responseBody);
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody);
                        String userId = jsonObject.getString("id");
                        Log.d("test", "response" + userId);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private String getTokenFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return  sharedPreferences.getString("token", null);
    }
}