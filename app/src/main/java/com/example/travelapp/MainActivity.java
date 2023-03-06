package com.example.travelapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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

    private final OkHttpClient okHttpClient = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_main);

        String token = getTokenFromSharedPreferences();
        if (token != null && !token.isEmpty()) {
            checkToken(token);
        } else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Log.d("home", "home");
                    return true;
                case R.id.navigation_favourite:
                    Log.d("favorite", "favorite");
                    return true;
                case R.id.navigation_profile:
                    Log.d("profile", "profile");
                    return true;
                default:
                    return false;
            }
        });
    }

    private void checkToken(String token){

        MediaType mediaType = MediaType.parse("application/json");

        String requestBody = "" +
                "{\"token\":\"" + token + "\"}";

        RequestBody body = RequestBody.create(requestBody, mediaType);
        Log.d("test", "checkToken: " +  body);
        Log.d("test", "checkToken: " + token);
        Request $request = new Request.Builder()
                .url("http://10.0.2.2:8000/api/user/get_user")
                .post(body)
                .build();

        okHttpClient.newCall($request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                String responseBody = response.body().string();
                if (!response.isSuccessful()) {
                    Log.d("tt", "test" + responseBody);
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {

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