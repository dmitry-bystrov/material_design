package com.javarunner.materialdesign.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.javarunner.materialdesign.ApplicationContext;

import java.util.Set;

public class Preferences {
    private static final String INTEGER_VALUE = "integer_value";
    private static final String STRING_SET = "string_set";
    private SharedPreferences sharedPreferences;

    public Preferences(String fileName) {
        this.sharedPreferences = ApplicationContext.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    public void saveInteger(int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt(INTEGER_VALUE, value);
        editor.apply();
    }

    public void saveStringSet(Set<String> value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putStringSet(STRING_SET, value);
        editor.apply();
    }

    public int loadInteger(int defValue) {
        return sharedPreferences.getInt(INTEGER_VALUE, defValue);
    }

    public Set<String> loadStringSet(Set<String> defValue) {
        return sharedPreferences.getStringSet(STRING_SET, defValue);
    }
}
