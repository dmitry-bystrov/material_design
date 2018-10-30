package com.javarunner.materialdesign.model.android;

import com.javarunner.materialdesign.model.FileStorage;

import java.io.File;

import io.reactivex.Completable;

public class AndroidFileStorage implements FileStorage {
    @Override
    public Completable deleteFile(String path) {
        return Completable.create(emitter -> {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }

            emitter.onComplete();
        });
    }
}
