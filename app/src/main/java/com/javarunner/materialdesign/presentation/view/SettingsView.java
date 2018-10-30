package com.javarunner.materialdesign.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface SettingsView extends MvpView {
    void setDefaultThemeButtonChecked();

    void setBrownThemeButtonChecked();

    void setCyanThemeButtonChecked();

    void setBlueThemeButtonChecked();

    void restartActivity();
}
