package com.javarunner.materialdesign.model.android;

import android.content.Context;
import android.content.SharedPreferences;

import com.javarunner.materialdesign.AppContext;
import com.javarunner.materialdesign.model.Preferences;

import java.util.Set;


public class AndroidPreferences implements Preferences {
    private SharedPreferences sharedPreferences;

    public AndroidPreferences(AppContext appContext) {
        this.sharedPreferences = appContext.getSharedPreferences(appContext.getPackageName(), Context.MODE_PRIVATE);
    }

    @Override
    public void saveInteger(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.putInt(key, value);
        editor.apply();
    }

    @Override
    public void saveStringSet(String key, Set<String> value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.putStringSet(key, value);
        editor.apply();
    }

    @Override
    public int loadInteger(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    @Override
    public Set<String> loadStringSet(String key, Set<String> defValue) {
        return sharedPreferences.getStringSet(key, defValue);
    }
}
