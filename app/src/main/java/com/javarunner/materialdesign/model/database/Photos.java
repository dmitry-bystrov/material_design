package com.javarunner.materialdesign.model.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.javarunner.materialdesign.model.database.dao.PhotoDao;
import com.javarunner.materialdesign.model.database.entity.Photo;

@Database(entities = {Photo.class}, version = 1)
public abstract class Photos extends RoomDatabase {
    public abstract PhotoDao photoDao();
}
