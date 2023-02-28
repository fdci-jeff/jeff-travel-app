package com.example.travelapp.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;


public class ApiClient {
    static InputStream is = null;
    static JSONObject jObj = null;
    static JSONArray jArr = null;
    static String json = "";
    static String error = "";

    public ApiClient() {
    }

    public JSONObject makeHttpRequest(String url, String method, ArrayList params) {
        return jObj;
    }
}
