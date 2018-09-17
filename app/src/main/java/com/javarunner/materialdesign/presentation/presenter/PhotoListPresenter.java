package com.javarunner.materialdesign.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.javarunner.materialdesign.model.PhotoInfo;
import com.javarunner.materialdesign.presentation.view.PhotoListView;
import com.javarunner.materialdesign.utils.FilesUtils;
import com.javarunner.materialdesign.utils.Preferences;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@InjectViewState
public class PhotoListPresenter extends MvpPresenter<PhotoListView> {
    private static final String FAVORITE_PREFERENCE = "favorite_preference";
    private List<PhotoInfo> data;
    boolean favoritesOnly;
    private Preferences preferences;

    public PhotoListPresenter(boolean favoritesOnly) {
        this.data = new ArrayList<>();
        this.favoritesOnly = favoritesOnly;
        this.preferences = new Preferences(FAVORITE_PREFERENCE);
    }

    public void onFileDeleteCommand(int position) {
        PhotoInfo photoInfo = data.get(position);

        if (new File(photoInfo.getFilePath()).delete()) {
            if (photoInfo.isFavorite()) {
                setFavorite(position, false);
            }

            getViewState().updatePhotoListAdapter(getPhotoInfoList());
            getViewState().showFileDeleteMessage();
        }
    }

    private List<PhotoInfo> getPhotoInfoList() {
        List<PhotoInfo> photoInfoList = new ArrayList<>();
        File[] files = FilesUtils.getFilesDir().listFiles();
        Set<String> favoriteStringSet = preferences.loadStringSet(new HashSet<String>());

        if (files != null) {
            for (File file : files) {
                boolean favorite = favoriteStringSet.contains(file.getPath());
                if (favoritesOnly && !favorite) {
                    continue;
                }

                data.add(new PhotoInfo(file.getPath(), favorite));
            }
        }

        return photoInfoList;
    }

    private void setFavorite(int index, boolean favorite) {
        Set<String> stringSet = preferences.loadStringSet(new HashSet<String>());

        if (favorite) {
            stringSet.add(data.get(index).getFilePath());
        } else {
            stringSet.remove(data.get(index).getFilePath());
        }

        preferences.saveStringSet(stringSet);
        data.get(index).setFavorite(favorite);
    }
}
