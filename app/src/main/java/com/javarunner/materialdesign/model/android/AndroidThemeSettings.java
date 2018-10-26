package com.javarunner.materialdesign.model.android;

import android.content.res.TypedArray;

import com.javarunner.materialdesign.AppContext;
import com.javarunner.materialdesign.R;
import com.javarunner.materialdesign.model.Preferences;
import com.javarunner.materialdesign.model.ThemeSettings;

import timber.log.Timber;

public class AndroidThemeSettings implements ThemeSettings {
    private static final String KEY = "theme";
    private Preferences preferences;
    private int[] themes;
    private int themeId;


    public AndroidThemeSettings(AppContext appContext, Preferences preferences) {
        this.preferences = preferences;
        this.themes = getThemes(appContext);
        loadThemeId();

        Timber.d("New AndroidThemeSettings created");
    }

    @Override
    public int getThemeResourceId() {
        return themes[themeId];
    }

    @Override
    public int getThemeId() {
        return themeId;
    }

    @Override
    public void setThemeId(int themeId) {
        this.themeId = themeId;
        saveThemeId();
    }

    private int[] getThemes(AppContext appContext) {
        TypedArray typedArray = appContext.getResources().obtainTypedArray(R.array.themes);

        int[] array = new int[typedArray.length()];
        for (int i = 0; i < typedArray.length(); i++) {
            array[i] = typedArray.getResourceId(i, R.style.AppTheme);
        }

        typedArray.recycle();
        return array;
    }

    private void loadThemeId() {
        this.themeId = preferences.loadInteger(KEY, 0);
    }

    private void saveThemeId() {
        preferences.saveInteger(KEY, themeId);
    }
}
