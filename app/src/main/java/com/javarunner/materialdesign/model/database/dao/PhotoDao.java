package com.javarunner.materialdesign.model.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.javarunner.materialdesign.model.database.entity.Photo;

import java.util.List;

@Dao
public interface PhotoDao {
    @Query("SELECT * FROM photo")
    List<Photo> getAll();

    @Query("SELECT * FROM photo WHERE favorite = 1")
    List<Photo> getFavorites();

    @Query("DELETE FROM photo")
    void deleteAll();

    @Query("SELECT * FROM photo WHERE id = :id")
    Photo getById(long id);

    @Insert
    long insert(Photo photo);

    @Update
    void update(Photo photo);

    @Delete
    void delete(Photo photo);
}
