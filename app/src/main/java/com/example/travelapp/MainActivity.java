package com.example.travelapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private final OkHttpClient okHttpClient = new OkHttpClient();

    private ViewPager mViewPager;
    private ImagerSlideAdapter mImageSliderAdapter;
    private ArrayList<String> imageUrls = new ArrayList<>();

    private ArrayList<String> imageTexts = new ArrayList<>();

    private Handler slideHandler = new Handler();
    private int currentImageIndex = 0;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_main);

        mViewPager = findViewById(R.id.view_pager);

        String token = getTokenFromSharedPreferences();
        if (token != null && !token.isEmpty()) {
            checkToken(token);
        } else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        fetchImages(token);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Log.d("home", "home");
                    return true;
                case R.id.navigation_search:
                    Log.d("searc", "search");
                    return true;
                case R.id.navigation_profile:
                    Log.d("profile", "profile");
                    return true;
                case R.id.navigation_bookmark:
                    Log.d("bookmark", "bookmark");
                    return true;
                default:
                    return false;
            }
        });
    }

    private void fetchImages(String token) {

        String query = "token=" + token + "&page=1";
        Request request = new Request.Builder()
                .url("http://10.0.2.2:8000/api/user/get_news" + "?" + query)
                .get()
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String json = response.body().string();
                Log.d("test", json);
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        JSONArray newsArray = jsonObject.getJSONArray("news");

                        for (int i = 0; i < newsArray.length(); i++) {
                            JSONObject newsObject = newsArray.getJSONObject(i);
                            if (!newsObject.isNull("image")) {
                                String imageUrl = newsObject.getString("image");
                                String title = newsObject.getString("title");
                                imageUrls.add(imageUrl);
                                imageTexts.add(title);
                            }
                        }
                        Log.d("test", "response" + imageUrls);
                        showImages(imageUrls, imageTexts);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void showImages(final ArrayList<String> imageUrls, final ArrayList<String> imageTexts){
        runOnUiThread(() -> {
            mViewPager.setAdapter(new ImagerSlideAdapter(MainActivity.this, imageUrls, imageTexts));
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
//                        String userId = jsonObject.getJSONArray('user');
                        Log.d("test", "response" + jsonObject);
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

    private Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
            int currentPage = mViewPager.getCurrentItem();
            int nextPage = currentPage + 1;

            ImagerSlideAdapter adapter = new ImagerSlideAdapter(MainActivity.this, imageUrls, imageTexts);

            if (nextPage >= adapter.getCount()) {
                nextPage = 0;
            }

            mViewPager.setCurrentItem(nextPage);

            slideHandler.postDelayed(this, 3000);
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        slideHandler.postDelayed(slideRunnable, 3000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        slideHandler.removeCallbacks(slideRunnable);
    }
}