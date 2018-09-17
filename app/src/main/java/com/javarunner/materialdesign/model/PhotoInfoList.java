package com.javarunner.materialdesign.model;

import com.javarunner.materialdesign.utils.Preferences;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PhotoInfoList {
    private List<PhotoInfo> data;
    private Preferences preferences;

    public PhotoInfoList(Preferences preferences, File directory, boolean favoritesOnly) {
        this.data = new ArrayList<>();
        this.preferences = preferences;

        File[] files = directory.listFiles();
        Set<String> favoriteStringSet = preferences.loadStringSet(new HashSet<String>());

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

}
