package com.javarunner.materialdesign;

import com.javarunner.materialdesign.modules.AppModule;
import com.javarunner.materialdesign.modules.DataSourceModule;
import com.javarunner.materialdesign.modules.PreferencesModule;
import com.javarunner.materialdesign.modules.SettingsModule;
import com.javarunner.materialdesign.presentation.presenter.CommonPresenter;
import com.javarunner.materialdesign.presentation.presenter.SettingsPresenter;
import com.javarunner.materialdesign.ui.activity.MainActivity;
import com.javarunner.materialdesign.ui.activity.PhotoViewActivity;
import com.javarunner.materialdesign.ui.activity.SettingsActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        PreferencesModule.class,
        DataSourceModule.class,
        SettingsModule.class
})
public interface AppComponent {
    void inject(MainActivity mainActivity);

    void inject(CommonPresenter commonPresenter);

    void inject(PhotoViewActivity photoViewActivity);

    void inject(SettingsPresenter settingsPresenter);

    void inject(SettingsActivity settingsActivity);
}
