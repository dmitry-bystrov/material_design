package com.javarunner.materialdesign.modules;

import com.javarunner.materialdesign.AppContext;
import com.javarunner.materialdesign.model.Preferences;
import com.javarunner.materialdesign.model.android.AndroidPreferences;

import dagger.Module;
import dagger.Provides;

@Module
public class PreferencesModule {
    @Provides
    Preferences getPreferences(AppContext appContext) {
        return new AndroidPreferences(appContext);
    }
}
