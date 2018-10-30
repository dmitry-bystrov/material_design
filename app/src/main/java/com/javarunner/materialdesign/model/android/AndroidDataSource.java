package com.javarunner.materialdesign.model.android;

import com.javarunner.materialdesign.model.DataSource;
import com.javarunner.materialdesign.model.FileStorage;
import com.javarunner.materialdesign.model.database.AppDatabase;
import com.javarunner.materialdesign.model.database.dao.PhotoDao;
import com.javarunner.materialdesign.model.database.entity.Photo;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class AndroidDataSource implements DataSource {
    private PhotoDao photoDao;
    private FileStorage fileStorage;

    public AndroidDataSource(AppDatabase appDatabase, FileStorage fileStorage) {
        this.photoDao = appDatabase.photoDao();
        this.fileStorage = fileStorage;
    }

    @Override
    public Completable addPhoto(Photo photo) {
        return Completable.create(emitter -> {
            long id = photoDao.insert(photo);
            photo.setId(id);
            emitter.onComplete();
        });
    }

    @Override
    public Completable deletePhoto(Photo photo) {
        return Completable.create(emitter -> {
            fileStorage.deleteFile(photo.getFilePath());
            photoDao.delete(photo);
            emitter.onComplete();
        });
    }

    @Override
    public Completable updatePhoto(Photo photo) {
        return Completable.create(emitter -> {
            photoDao.update(photo);
            emitter.onComplete();
        });
    }

    @Override
    public Observable<List<Photo>> getAllPhotos() {
        return Observable.create(emitter -> {
            List<Photo> photos = photoDao.getAll();
            emitter.onNext(photos);
        });
    }

    @Override
    public Observable<List<Photo>> getFavoritePhotos() {
        return Observable.create(emitter -> {
            List<Photo> photos = photoDao.getFavorites();
            emitter.onNext(photos);
        });
    }
}
