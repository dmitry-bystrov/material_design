package com.javarunner.materialdesign;

import android.app.Application;
import android.content.Context;

public class ApplicationContext extends Application {

    private static ApplicationContext instance;

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationContext.instance = this;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }
}
