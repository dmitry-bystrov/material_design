package com.javarunner.materialdesign.modules;

import com.javarunner.materialdesign.AppContext;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private AppContext appContext;

    public AppModule(AppContext appContext) {
        this.appContext = appContext;
    }

    @Provides
    public AppContext getAppContext() {
        return appContext;
    }
}
