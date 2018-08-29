package com.javarunner.materialdesign;

import android.app.Application;
import android.content.Context;

public class ApplicationClass extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
