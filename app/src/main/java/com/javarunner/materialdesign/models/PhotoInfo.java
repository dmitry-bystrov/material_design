package com.javarunner.materialdesign.models;

public class PhotoInfo {
    private String filePath;
    private boolean favorite;


    PhotoInfo(String filePath, boolean favorite) {
        this.filePath = filePath;
        this.favorite = favorite;
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
