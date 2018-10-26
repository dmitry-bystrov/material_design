package com.javarunner.materialdesign.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.io.File;


@StateStrategyType(OneExecutionStateStrategy.class)
public interface CommonView extends MvpView {
    @StateStrategyType(SkipStrategy.class)
    void takePhoto(File file);

    @StateStrategyType(SkipStrategy.class)
    void revokePermissions(File file);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void updateList();

    void showMessage(String text);

    void showMessageOnPhotoAdded();

    void showMessageOnPhotoDeleted();
}
