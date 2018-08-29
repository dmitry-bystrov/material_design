package com.javarunner.materialdesign;

import java.io.File;

public class PhotoInfo {
    private File photoFile;
    private boolean favorite;

    public PhotoInfo(File photoFile) {
        this.photoFile = photoFile;
        this.favorite = false;
    }

    public File getPhotoFile() {
        return photoFile;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
