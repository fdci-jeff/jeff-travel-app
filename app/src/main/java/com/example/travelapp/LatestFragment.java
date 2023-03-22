package com.example.travelapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LatestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LatestFragment extends Fragment {

    private RecyclerView recyclerView;

    private final OkHttpClient okHttpClient = new OkHttpClient();

    private NewsLatestAdapter newsLatestAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<String> temp_data;

    private List<NewsLatestModel> news_data = new ArrayList<>();

    public LatestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LatestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LatestFragment newInstance(String param1, String param2) {
        LatestFragment fragment = new LatestFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_latest, container, false);
        recyclerView = view.findViewById(R.id.myRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setNestedScrollingEnabled(false);
        newsLatestAdapter = new NewsLatestAdapter(getData());
        recyclerView.setAdapter(newsLatestAdapter);
        // Inflate the layout for this fragment
        return view;
    }


    private List<NewsLatestModel> getData() {
        ArrayList<String> temp_latest = getArguments().getStringArrayList("latest");
        Log.d("fragment", "getData: " + temp_latest);
        news_data = new ArrayList<>();
        news_data.add(new NewsLatestModel("title1"));
        news_data.add(new NewsLatestModel("title1"));
        news_data.add(new NewsLatestModel("title1"));
        news_data.add(new NewsLatestModel("title1"));

        return news_data;
    }
}