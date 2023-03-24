package com.example.travelapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImagerSlideAdapter extends PagerAdapter {
    private final ArrayList<String> imageUrls;
    private final LayoutInflater mInflater;

    private final ArrayList<String> imageTexts;

    private int currentPosition = Integer.MAX_VALUE / 2;

    public ImagerSlideAdapter(Context context, ArrayList<String> imageUrls, ArrayList<String> imageTexts) {
        this.imageUrls = imageUrls;
        this.mInflater = LayoutInflater.from(context);
        this.imageTexts = imageTexts;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        View view = mInflater.inflate(R.layout.image_slide_item, container, false);
        ImageView imageView = view.findViewById(R.id.image_view);
        TextView textView = view.findViewById(R.id.image_text_view);
        Picasso.get().load(imageUrls.get(position % imageUrls.size())).fit().centerInside().into(imageView);
        textView.setText(imageTexts.get(position % imageUrls.size()));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
