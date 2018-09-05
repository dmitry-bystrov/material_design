package com.javarunner.materialdesign.models;

public class PhotoInfo {
    private String filePath;
    private boolean favorite;

    public PhotoInfo(String filePath) {
        this.filePath = filePath;
        this.favorite = false;
    }

    public String getFilePath() {
        return filePath;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
