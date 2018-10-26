package com.javarunner.materialdesign.model;

import com.javarunner.materialdesign.model.android.AndroidPreferences;
import com.javarunner.materialdesign.model.database.entity.Photo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PhotoInfoList {
    private List<Photo> data;
    private AndroidPreferences preferences;

    public PhotoInfoList(AndroidPreferences preferences, File directory, boolean favoritesOnly) {
        this.data = new ArrayList<>();
        this.preferences = preferences;

//        File[] files = directory.listFiles();
//        Set<String> favoriteStringSet = preferences.loadStringSet(new HashSet<String>());
//
//        if (files != null) {
//            for (File file : files) {
//                boolean favorite = favoriteStringSet.contains(file.getPath());
//                if (favoritesOnly && !favorite) {
//                    continue;
//                }
//
//                data.add(new Photo(file.getPath(), favorite));
//            }
//        }
    }

    public int size() {
        return data.size();
    }

    public List<Photo> getList() {
        return data;
    }

    public Photo getItem(int index) {
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
