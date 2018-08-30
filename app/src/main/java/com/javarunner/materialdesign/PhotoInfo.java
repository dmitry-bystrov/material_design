package com.javarunner.materialdesign;

public class PhotoInfo {
    private String photoFilePath;
    private boolean favorite;

    public PhotoInfo(String photoFilePath) {
        this.photoFilePath = photoFilePath;
        this.favorite = false;
    }

    public String getImageFilePath() {
        return photoFilePath;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
