package com.javarunner.materialdesign.models;

import android.content.Context;

import com.javarunner.materialdesign.R;
import com.javarunner.materialdesign.utils.Preferences;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PhotoInfoList {
    private List<PhotoInfo> data;
    private Preferences preferences;
    private final int prefKey = R.string.favorite_preference;

    public PhotoInfoList(Context context, File directory, boolean favoritesOnly) {
        this.data = new ArrayList<>();
        this.preferences = new Preferences(context, prefKey);

        File[] files = directory.listFiles();
        Set<String> favoriteStringSet = preferences.loadStringSet(prefKey, new HashSet<String>());

        if (files != null) {
            for (File file : files) {
                boolean favorite = favoriteStringSet.contains(file.getPath());
                if (favoritesOnly && !favorite) {
                    continue;
                }

                data.add(new PhotoInfo(file.getPath(), favorite));
            }
        }
    }

    public int size() {
        return data.size();
    }

    public List<PhotoInfo> getList() {
        return data;
    }

    public PhotoInfo getItem(int index) {
        return data.get(index);
    }

    public int findItem(String path) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getFilePath().equals(path)) {
                return i;
            }
        }

        return -1;
    }

    public boolean deleteFile(int index) {
        return new File(data.get(index).getFilePath()).delete();
    }

    public void setFavorite(int index, boolean favorite) {
        Set<String> stringSet = preferences.loadStringSet(prefKey, new HashSet<String>());

        if (favorite) {
            stringSet.add(data.get(index).getFilePath());
        } else {
            stringSet.remove(data.get(index).getFilePath());
        }

        preferences.saveStringSet(prefKey, stringSet);
        data.get(index).setFavorite(favorite);
    }
}
