package com.javarunner.materialdesign.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.javarunner.materialdesign.model.PhotoInfo;

import java.util.List;

public interface PhotoListView extends MvpView {
    void showFileDeleteMessage();
    void updatePhotoListAdapter(List<PhotoInfo> newData);
}
