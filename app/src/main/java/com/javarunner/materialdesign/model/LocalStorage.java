package com.javarunner.materialdesign.model;

import com.javarunner.materialdesign.model.database.entity.Photo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class LocalStorage implements DataSource {

    private File directory;

    public LocalStorage(File directory) {
        this.directory = directory;
    }

    @Override
    public Observable<List<Photo>> getPhotos() {
        return Observable.fromCallable(() -> {
            List<Photo> data = new ArrayList<>();

            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    boolean favorite = false;
                    data.add(new Photo(0, file.getPath(), favorite));
                }
            }

            return data;
        });
    }
}
