package com.stackflow.app.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.io.Serializable;
import java.lang.reflect.Type;

public class SharedPrefUtil {
    private static SharedPrefUtil sharedPrefUtil = null;

    private SharedPreferences sharedPreferences;
    public static final String PREF_NAME = "stackoverflow";
    public static final String FIRST_TIME = "firstTime";
    public static final String ACCESS_TOKEN = "accessToken";
    public static final String ACCESS_KEY = "accessKey";
    public static final String CLIENT_ID = "clientId";
    public static final String USER_PROFILE = "userProfile";


    private SharedPrefUtil(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPrefUtil set(Context context) {
        if (sharedPrefUtil == null) {
            sharedPrefUtil = new SharedPrefUtil(context);
        }
        return sharedPrefUtil;
    }

    public static SharedPrefUtil instance() {
        return sharedPrefUtil;
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public int getInteger(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public Serializable getSerializable(String key, Class clazz) {
        Gson gson = new Gson();
        String json = getString(key);
        return gson.fromJson(json, (Type) clazz);
    }

    public void set(String key, Object value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (value instanceof String) {
            editor.putString(key, String.valueOf(value));
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Serializable) {
            Gson gson = new Gson();
            String json = gson.toJson(value);
            editor.putString(key, json);
        }
        editor.apply();
    }

}
