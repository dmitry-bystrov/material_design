package com.javarunner.materialdesign;

import android.app.Application;
import android.content.Context;

public class AppClass extends Application {
    private static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static Context getContext() {
        return application.getApplicationContext();
    }
}
