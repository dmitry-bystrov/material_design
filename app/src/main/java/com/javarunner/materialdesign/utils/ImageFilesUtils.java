package com.javarunner.materialdesign.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;

import com.javarunner.materialdesign.AppClass;
import com.javarunner.materialdesign.R;
import com.javarunner.materialdesign.models.PhotoInfo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class ImageFilesUtils {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmmss", Locale.ROOT);

    private static String getPreferenceKey() {
        return AppClass.getContext().getString(R.string.favorite_preference);
    }

    private static SharedPreferences getSharedPreferences() {
        return AppClass.getContext().getSharedPreferences(getPreferenceKey(), Context.MODE_PRIVATE);
    }

    public static String getNewFilename() {
        return String.format("%s%s%s", "IMG_", simpleDateFormat.format(new Date()), ".jpg");
    }

    public static File getFilesDir() {
        File filesDir = AppClass.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (filesDir == null) {
            filesDir = AppClass.getContext().getFilesDir();
        }

        return filesDir;
    }

    public static boolean deleteFile(String path) {
        removeFromFavoriteList(path);
        return new File(path).delete();
    }

    public static List<PhotoInfo> getPhotoInfoList() {
        File[] files = getFilesDir().listFiles();
        List<PhotoInfo> photoInfoList = new ArrayList<>();
        Set<String> favoriteStringSet = getSharedPreferences().getStringSet(getPreferenceKey(), new HashSet<String>());

        if (files != null) {
            for (File file : files) {
                boolean favorite = favoriteStringSet.contains(file.getPath());
                photoInfoList.add(new PhotoInfo(file.getPath(), favorite));
            }
        }

        return photoInfoList;
    }

    public static List<PhotoInfo> getFavoritePhotoInfoList() {
        List<PhotoInfo> photoInfoList = new ArrayList<>();
        Set<String> stringSet = getSharedPreferences().getStringSet(getPreferenceKey(), new HashSet<String>());

        for (String s : stringSet) {
            photoInfoList.add(new PhotoInfo(s, true));
        }

        return photoInfoList;
    }

    public static void addToFavoriteList(String imageFilePath) {
        Set<String> stringSet = getSharedPreferences().getStringSet(getPreferenceKey(), new HashSet<String>());
        stringSet.add(imageFilePath);
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.clear();
        editor.putStringSet(getPreferenceKey(), stringSet);
        editor.apply();
    }

    public static void removeFromFavoriteList(String imageFilePath) {
        Set<String> stringSet = getSharedPreferences().getStringSet(getPreferenceKey(), new HashSet<String>());
        stringSet.remove(imageFilePath);
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.clear();
        editor.putStringSet(getPreferenceKey(), stringSet);
        editor.apply();
    }

    public static List<PhotoInfo> removeItemFromList(List<PhotoInfo> filesList, int position) {
        if (position >= filesList.size()) {
            return filesList;
        }

        List<PhotoInfo> newList = new ArrayList<>(filesList);
        newList.remove(position);
        return newList;
    }

    public static int findItemPosition(List<PhotoInfo> filesList, String path) {
        for (int i = 0; i < filesList.size(); i++) {
            if (filesList.get(i).getFilePath().equals(path)) {
                return i;
            }
        }

        return -1;
    }

    public static String getImageFilePath(List<PhotoInfo> filesList, int position) {
        if (position >= filesList.size()) {
            return "";
        }

        return filesList.get(position).getFilePath();
    }
}
