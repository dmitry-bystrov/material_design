package com.javarunner.materialdesign;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.javarunner.materialdesign.modules.AppModule;

import java.io.File;

import timber.log.Timber;

public class AppContext extends Application {

    private static AppContext instance;
    private AppComponent appComponent;
    private static File filesDirectory;

    @Override
    public void onCreate() {
        super.onCreate();
        AppContext.instance = this;

        filesDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (filesDirectory == null) {
            filesDirectory = getFilesDir();
        }

        Timber.plant(new Timber.DebugTree());
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static AppContext getInstance() {
        return instance;
    }

    public static File getFilesDirectory() {
        return filesDirectory;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
