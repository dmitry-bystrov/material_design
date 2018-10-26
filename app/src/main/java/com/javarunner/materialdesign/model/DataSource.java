package com.javarunner.materialdesign.model;

import com.javarunner.materialdesign.model.database.entity.Photo;

import java.util.List;

import io.reactivex.Observable;

public interface DataSource {
    Observable<List<Photo>> getPhotos();
}
