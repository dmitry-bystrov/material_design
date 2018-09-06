package com.javarunner.materialdesign.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.javarunner.materialdesign.AppClass;
import com.javarunner.materialdesign.R;

public class ThemeUtils {

    private static final String THEME_PREFERENCE = "theme_preference";
    private static final String FILE_NAME = "shared_preferences";

    enum Theme {
        DEFAULT(0, R.style.AppTheme, R.id.rb_default_theme),
        BROWN(1, R.style.AppTheme_BrownCyanTheme, R.id.rb_brown_cyan_theme),
        CYAN(2, R.style.AppTheme_CyanDeepOrangeTheme, R.id.rb_cyan_deep_orange_theme),
        GREEN(3, R.style.AppTheme_BlueGreyOrangeTheme, R.id.rb_blue_grey_orange_theme);

        private final int themeID;
        private final int themeStyleResourceID;
        private final int themeButtonResourceID;

        Theme(int themeID, int themeStyleResourceID, int themeButtonResourceID) {
            this.themeID = themeID;
            this.themeStyleResourceID = themeStyleResourceID;
            this.themeButtonResourceID = themeButtonResourceID;
        }

        public int getThemeID() {
            return themeID;
        }

        public int getThemeStyleResourceID() {
            return themeStyleResourceID;
        }

        public int getThemeButtonResourceID() {
            return themeButtonResourceID;
        }

        public static Theme getThemeByID(int themeID) {
            for (Theme o: Theme.values()) {
                if (o.getThemeID() == themeID) return o;
            }

            return Theme.DEFAULT;
        }

        public static Theme getThemeByButtonResourceID(int themeButtonResourceID) {
            for (Theme o: Theme.values()) {
                if (o.getThemeButtonResourceID() == themeButtonResourceID) return o;
            }

            return Theme.DEFAULT;
        }
    }

    private static SharedPreferences getSharedPreferences() {
        return AppClass.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public static void saveSelectedTheme(int radioButtonResourceID) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(THEME_PREFERENCE, Theme.getThemeByButtonResourceID(radioButtonResourceID).getThemeID());
        editor.apply();
    }

    private static int getSavedThemeID() {
        return getSharedPreferences().getInt(THEME_PREFERENCE, 0);
    }

    public static int getSelectedButtonResourceID() {
        return Theme.getThemeByID(getSavedThemeID()).getThemeButtonResourceID();
    }

    public static int getThemeStyleResourceID() {
        return Theme.getThemeByID(getSavedThemeID()).getThemeStyleResourceID();
    }
}
