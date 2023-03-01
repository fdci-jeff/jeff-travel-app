package com.example.travelapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    public static final String SP_LOGIN_APP = "spLoginApp";

    public static final String NAME = "name";
    public static final String Email = "email";
    public static final String TOKEN = "token";

    public static final String SP_LOGIN = "spJeffLogin";

    private Context mCtx;

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

//    public SharedPrefManager(Context context){
//        sp = context.getSharedPreferences(SP_LOGIN_APP, Context.MODE_PRIVATE);
//        spEditor = sp.edit();
//        spEditor.apply();
//    }

    public SharedPrefManager(Context mCtx) {
        this.mCtx = mCtx;
    }

    public void saveSPString(String keySP, String value){
        sp = mCtx.getSharedPreferences(SP_LOGIN_APP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(keySP, value);
        editor.apply();
    }

    public void saveSPInt(String keySP, int value){
        sp = mCtx.getSharedPreferences(SP_LOGIN_APP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(keySP, value);
        editor.apply();
    }

    public void saveSPBoolean(String keySP, boolean value){
        sp = mCtx.getSharedPreferences(SP_LOGIN_APP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(keySP, value);
        editor.apply();
    }

    public String getName(){
        return sp.getString(NAME, "");
    }

    public String getEmail(){
        return sp.getString(Email, "");
    }

    public String getToken(){
        return sp.getString(TOKEN, "");
    }

    public boolean getLogin(){
        sp = mCtx.getSharedPreferences(SP_LOGIN_APP, Context.MODE_PRIVATE);
        return sp.getBoolean(SP_LOGIN_APP, false);
    }
}
