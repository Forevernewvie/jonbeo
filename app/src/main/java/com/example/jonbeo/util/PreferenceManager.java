package com.example.jonbeo.util;

import android.content.Context;

import android.content.SharedPreferences;


public class PreferenceManager {

    public static final String PREFERENCES_NAME = "rebuild_preference";
    private static final int DEFAULT_VALUE_INT = -1;

    private static SharedPreferences getPreferences(Context context) {

        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

    }

    public static void setInt(Context context, String key, int value) {

        SharedPreferences prefs = getPreferences(context);

        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt(key, value);

        editor.commit();

    }

    public static int getInt(Context context, String key) {

        SharedPreferences prefs = getPreferences(context);

        int value = prefs.getInt(key, DEFAULT_VALUE_INT);

        return value;

    }

    public static void removeKey(Context context, String key) {

        SharedPreferences prefs = getPreferences(context);

        SharedPreferences.Editor edit = prefs.edit();

        edit.remove(key);

        edit.commit();

    }


    public static void clear(Context context) {

        SharedPreferences prefs = getPreferences(context);

        SharedPreferences.Editor edit = prefs.edit();

        edit.clear();

        edit.commit();

    }




}
