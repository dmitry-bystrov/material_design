package com.javarunner.materialdesign.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.io.File;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface CommonView extends MvpView {
    void takePhoto(File file);

    void revokePermissions(File file);

    void updateList();

    void showMessage(String text);

    void showMessageOnPhotoAdded();

    void showMessageOnPhotoDeleted();
}
