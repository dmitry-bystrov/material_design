package com.javarunner.materialdesign.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.javarunner.materialdesign.AppClass;
import com.javarunner.materialdesign.models.PhotoInfo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FileUtils {

    private static final String FILEPROVIDER_AUTHORITY = "com.javarunner.materialdesign.fileprovider";
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

    public static boolean deleteImageFile(String path) {
        return new File(path).delete();
    }

    public static Intent getCameraIntent(Activity activity, File photoFile) {
        PackageManager packageManager = activity.getPackageManager();
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (cameraIntent.resolveActivity(packageManager) == null) {
            return null;
        }

        Uri uri = FileProvider.getUriForFile(activity, FILEPROVIDER_AUTHORITY, photoFile);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        List<ResolveInfo> cameraActivities = packageManager.queryIntentActivities(cameraIntent,
                PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo cameraActivity : cameraActivities) {
            activity.grantUriPermission(cameraActivity.activityInfo.packageName,
                    uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }

        return cameraIntent;
    }

    public static void revokeUriPermission(Activity activity, File photoFile) {
        Uri uri = FileProvider.getUriForFile(activity, FILEPROVIDER_AUTHORITY, photoFile);
        activity.revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
    }
}
