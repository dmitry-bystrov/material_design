package com.javarunner.materialdesign.modules;

import com.javarunner.materialdesign.AppContext;
import com.javarunner.materialdesign.model.Preferences;
import com.javarunner.materialdesign.model.ThemeSettings;
import com.javarunner.materialdesign.model.android.AndroidThemeSettings;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SettingsModule {
    @Provides
    @Singleton
    public ThemeSettings themeSettings(AppContext appContext, Preferences preferences) {
        return new AndroidThemeSettings(appContext, preferences);
    }
}
