package com.javarunner.materialdesign.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FilesUtils {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmmss", Locale.ROOT);

    public static String getNewFilename() {
        return String.format("%s%s%s", "IMG_", simpleDateFormat.format(new Date()), ".jpg");
    }

    public static File getFilesDir(Context context) {
        File filesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (filesDir == null) {
            filesDir = context.getFilesDir();
        }

        return filesDir;
    }
}
