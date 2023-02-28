package com.example.travelapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    public static final String SP_LOGIN_APP = "spLoginApp";

    public static final String NAME = "Name";
    public static final String Email = "Email";
    public static final String TOKEN = "Token";

    public static final String SP_LOGIN = "spJeffLogin";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences(SP_LOGIN_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
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

    public Boolean getLogin(){
        return sp.getBoolean(SP_LOGIN, false);
    }
}
