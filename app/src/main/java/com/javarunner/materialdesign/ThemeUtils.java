package com.javarunner.materialdesign;

import android.content.Context;
import android.content.SharedPreferences;

public class ThemeUtils {

    private static final String THEME_PREFERENCE = "theme_preference";
    private static final String FILE_NAME = "shared_preferences";

    private static SharedPreferences getSharedPreferences() {
        return ApplicationClass.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public static void saveSelectedTheme(int theme) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(THEME_PREFERENCE, theme);
        editor.apply();
    }

    public static int getSelectedTheme() {
        return getSharedPreferences().getInt(THEME_PREFERENCE, R.id.rb_default_theme);
    }

    public static int getThemeResourceId() {
        switch (getSelectedTheme()) {
            case R.id.rb_brown_cyan_theme:
                return R.style.AppTheme_BrownCyanTheme;
            case R.id.rb_cyan_deep_orange_theme:
                return R.style.AppTheme_CyanDeepOrangeTheme;
            default:
                return R.id.rb_default_theme;
        }
    }
}
