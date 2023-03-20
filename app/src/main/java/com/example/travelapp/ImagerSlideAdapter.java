package com.example.travelapp;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ImagerSlideAdapter extends PagerAdapter {
    private ArrayList<String> imageUrls;
    private Context context;
    private LayoutInflater mInflater;

    public ImagerSlideAdapter(Context context, ArrayList<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        Picasso.get().load(imageUrls.get(position)).into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
