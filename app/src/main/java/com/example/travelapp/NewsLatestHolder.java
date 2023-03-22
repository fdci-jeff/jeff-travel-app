package com.example.travelapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsLatestHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView itemTitle;

    public NewsLatestHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.item_image);
        itemTitle = itemView.findViewById(R.id.item_title);
    }
}
