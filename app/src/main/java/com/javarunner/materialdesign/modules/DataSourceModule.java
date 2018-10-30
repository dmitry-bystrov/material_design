package com.javarunner.materialdesign.modules;

import android.arch.persistence.room.Room;

import com.javarunner.materialdesign.AppContext;
import com.javarunner.materialdesign.model.DataSource;
import com.javarunner.materialdesign.model.FileStorage;
import com.javarunner.materialdesign.model.android.AndroidDataSource;
import com.javarunner.materialdesign.model.android.AndroidFileStorage;
import com.javarunner.materialdesign.model.database.AppDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataSourceModule {
    @Provides
    @Singleton
    public FileStorage getFileStorage() {
        return new AndroidFileStorage();
    }

    @Provides
    @Singleton
    public DataSource getDataSource(AppDatabase appDatabase, FileStorage fileStorage) {
        return new AndroidDataSource(appDatabase, fileStorage);
    }

    @Provides
    @Singleton
    public AppDatabase getDataBase(AppContext appContext) {
        return Room.databaseBuilder(appContext.getApplicationContext(),
                AppDatabase.class, "database")
                .build();
    }
}
