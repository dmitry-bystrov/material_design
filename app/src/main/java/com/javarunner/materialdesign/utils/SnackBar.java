package com.javarunner.materialdesign.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

public class SnackBar {

    private Context context;
    private View view;

    public SnackBar(Context context, View view) {
        this.context = context;
        this.view = view;
    }

    public void show(int messageID) {
        Snackbar.make(view, context.getString(messageID), Snackbar.LENGTH_SHORT)
                .show();
    }
}
