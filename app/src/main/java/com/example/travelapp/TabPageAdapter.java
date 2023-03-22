package com.example.travelapp;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class TabPageAdapter extends FragmentPagerAdapter {

    private final int[] TAB_ICONS = {R.drawable.latest_star, R.drawable.general, R.drawable.sports};

    private String token;

    private final OkHttpClient okHttpClient = new OkHttpClient();

    private ArrayList<String> temp_latest;

    public TabPageAdapter(FragmentManager fm, String token) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.token = token;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                LatestFragment latestFragment = new LatestFragment();
                temp_latest = new ArrayList<>();
                String query = "token=" + token + "&page=1";
                Bundle args = new Bundle();

                args.putString("token", token);

                Request request = new Request.Builder()
                        .url("http://10.0.2.2:8000/api/user/get_latest_news" + "?" + query)
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
                        try {
                            JSONObject jsonObject = new JSONObject(json);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {
                                JSONArray newsArray = jsonObject.getJSONArray("news");

                                for (int i = 0; i < newsArray.length(); i++) {
                                    JSONObject newsObject = newsArray.getJSONObject(i);
                                    if (!newsObject.isNull("image")) {
                                        String title = newsObject.getString("title");
                                        temp_latest.add(title);
                                    }
                                }

                                args.putStringArrayList("latest", temp_latest);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                Log.d("test", "getItem: " + temp_latest);
                latestFragment.setArguments(args);
                return latestFragment;// Return the Fragment for tab one
            case 1:
                return new GeneralFragment();// Return the Fragment for tab two
            case 2:
                return new SportsFragment();// Return the Fragment for tab three
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Latest"; // Set the title for tab one
            case 1:
                return "General"; // Set the title for tab two
            case 2:
                return "Sports"; // Set the title for tab three
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    private void fetchLatest() {
        String query = "token=" + token + "&page=1";

        Request request = new Request.Builder()
                .url("http://10.0.2.2:8000/api/user/get_latest_news" + "?" + query)
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
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    boolean success = jsonObject.getBoolean("success");

                    if (success) {
                        JSONArray newsArray = jsonObject.getJSONArray("news");

                        for (int i = 0; i < newsArray.length(); i++) {
                            JSONObject newsObject = newsArray.getJSONObject(i);
                            if (!newsObject.isNull("image")) {
                                String title = newsObject.getString("title");
                                temp_latest.add(title);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
