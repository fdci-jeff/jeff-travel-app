package com.example.travelapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewsLatestAdapter extends RecyclerView.Adapter<NewsLatestHolder> {

    private List<NewsLatestModel> data;

    public NewsLatestAdapter(List<NewsLatestModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public NewsLatestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_latest_list, parent, false);
        return  new NewsLatestHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsLatestHolder holder, int position) {
        NewsLatestModel item = data.get(position);
        holder.itemTitle.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
