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
                Bundle args = new Bundle();
                args.putString("token", token);
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
}
