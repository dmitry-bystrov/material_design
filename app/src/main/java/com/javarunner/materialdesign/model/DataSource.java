package com.javarunner.materialdesign.model;

import com.javarunner.materialdesign.model.database.entity.Photo;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface DataSource {
    Completable addPhoto(Photo photo);
    Completable deletePhoto(Photo photo);
    Completable updatePhoto(Photo photo);
    Observable<List<Photo>> getAllPhotos();
    Observable<List<Photo>> getFavoritePhotos();
}
