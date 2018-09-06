package com.javarunner.materialdesign.utils;

import android.os.Environment;

import com.javarunner.materialdesign.AppClass;
import com.javarunner.materialdesign.models.PhotoInfo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ImageFilesUtils {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmmss", Locale.ROOT);

    public static String getNewFilename() {
        return String.format("%s%s%s", "IMG_", simpleDateFormat.format(new Date()), ".jpg");
    }

    public static File getFilesDir() {
        File filesDir = AppClass.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (filesDir == null) {
            filesDir = AppClass.getContext().getFilesDir();
        }

        return filesDir;
    }

    public static boolean deleteFile(String path) {
        return new File(path).delete();
    }


    public static List<PhotoInfo> getPhotoInfoList() {
        File[] files = getFilesDir().listFiles();
        List<PhotoInfo> photoInfoList = new ArrayList<>();

        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                photoInfoList.add(new PhotoInfo(files[i].getPath()));
            }
        }

        return photoInfoList;
    }

    public static List<PhotoInfo> removeItemFromList(List<PhotoInfo> filesList, int position) {
        if (position >= filesList.size()) {
            return filesList;
        }

        List<PhotoInfo> newList = new ArrayList<>(filesList);
        newList.remove(position);
        return newList;
    }

    public static int findItemPosition(List<PhotoInfo> filesList, String path) {
        for (int i = 0; i < filesList.size(); i++) {
            if (filesList.get(i).getFilePath().equals(path)) {
                return i;
            }
        }

        return -1;
    }

    public static String getImageFilePath(List<PhotoInfo> filesList, int position) {
        if (position >= filesList.size()) {
            return "";
        }

        return filesList.get(position).getFilePath();
    }
}
