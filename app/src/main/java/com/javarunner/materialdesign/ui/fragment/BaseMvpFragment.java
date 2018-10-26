package com.javarunner.materialdesign.ui.fragment;

import android.support.v4.app.FragmentActivity;

import com.arellomobile.mvp.MvpAppCompatFragment;

public class BaseMvpFragment extends MvpAppCompatFragment {
    protected FragmentActivity activity() {
        FragmentActivity activity = super.getActivity();
        if (activity == null) {
            throw new RuntimeException("getActivity() return null");
        } else {
            return super.getActivity();
        }
    }
}
