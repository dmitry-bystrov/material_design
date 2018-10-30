package com.javarunner.materialdesign.model;

import io.reactivex.Completable;

public interface FileStorage {
    Completable deleteFile(String path);
}
