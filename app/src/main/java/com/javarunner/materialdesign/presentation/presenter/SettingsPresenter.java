package com.javarunner.materialdesign.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.javarunner.materialdesign.model.ThemeSettings;
import com.javarunner.materialdesign.presentation.view.SettingsView;

import javax.inject.Inject;

import timber.log.Timber;

@InjectViewState
public class SettingsPresenter extends MvpPresenter<SettingsView> {
    @Inject
    ThemeSettings themeSettings;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        switch (themeSettings.getThemeId()) {
            case 0:
                getViewState().setDefaultThemeButtonChecked();
                break;
            case 1:
                getViewState().setBrownThemeButtonChecked();
                break;
            case 2:
                getViewState().setCyanThemeButtonChecked();
                break;
            case 3:
                getViewState().setBlueThemeButtonChecked();
                break;
        }
    }

    public void defaultThemeSelected() {
        themeSettings.setThemeId(0);
        getViewState().restartActivity();
    }

    public void brownThemeSelected() {
        themeSettings.setThemeId(1);
        getViewState().restartActivity();
    }

    public void cyanThemeSelected() {
        themeSettings.setThemeId(2);
        getViewState().restartActivity();
    }

    public void blueThemeSelected() {
        themeSettings.setThemeId(3);
        getViewState().restartActivity();
    }
}
