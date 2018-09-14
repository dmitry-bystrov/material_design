package com.javarunner.materialdesign.utils;

import android.content.Context;

import com.javarunner.materialdesign.R;

import java.util.ArrayList;
import java.util.List;

public class ThemeManager {

    private Preferences preferences;
    private int themeIndex;
    private List<Integer> themes;

    public ThemeManager(Context context) {
        this.themes = new ArrayList<>();
        this.themes.add(R.style.AppTheme);
        this.themes.add(R.style.AppTheme_BrownCyanTheme);
        this.themes.add(R.style.AppTheme_CyanDeepOrangeTheme);
        this.themes.add(R.style.AppTheme_BlueGreyOrangeTheme);
        this.preferences = new Preferences(context, R.string.theme_preference);
        loadThemeIndex();
    }

    private void loadThemeIndex() {
        this.themeIndex = preferences.loadInteger(R.string.theme_preference, 0);
    }

    public void saveThemeIndex(int themeIndex) {
        this.themeIndex = themeIndex;
        preferences.saveInteger(R.string.theme_preference, themeIndex);
    }

    public boolean themeChanged() {
        int index = themeIndex;
        loadThemeIndex();
        return index != themeIndex;
    }

    public int getThemeIndex() {
        return themeIndex;
    }

    public int getThemeResourceId() {
        if (themes.size() > themeIndex) {
            return themes.get(themeIndex);
        } else {
            return themes.get(0);
        }
    }
}
