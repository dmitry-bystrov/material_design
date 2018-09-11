package com.javarunner.materialdesign.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    private Context context;
    private String fileName;

    public Preferences(Context context, int fileNameStringID) {
        this.context = context;
        this.fileName = context.getString(fileNameStringID);
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    public void saveInteger(int keyStringID, int value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.clear();
        editor.putInt(context.getString(keyStringID), value);
        editor.apply();
    }

    public int loadInteger(int keyStringID, int defValue) {
        return getSharedPreferences().getInt(context.getString(keyStringID), defValue);
    }
}
