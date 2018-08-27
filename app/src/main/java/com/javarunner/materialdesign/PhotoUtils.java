package com.javarunner.materialdesign;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PhotoUtils {

    private static final String FILEPROVIDER_AUTHORITY = "com.javarunner.materialdesign.fileprovider";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmmss", Locale.ROOT);

    public static String getPhotoFilename() {
        return String.format("%s%s%s", "IMG_", simpleDateFormat.format(new Date()), ".jpg");
    }

    public static List<PhotoInfo> getPhotoInfoList(File filesDir) {
        File[] files = filesDir.listFiles();
        List<PhotoInfo> photoInfoList = new ArrayList<>();

        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                photoInfoList.add(new PhotoInfo(files[i]));
            }
        }

        return photoInfoList;
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

    public static Bitmap getScaledBitmap(String path, int destWidth, int destHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;

        int inSampleSize = 1;
        if (srcHeight > destHeight || srcWidth > destWidth) {
            float heightScale = srcHeight / destHeight;
            float widthScale = srcWidth / destWidth;
                    inSampleSize = Math.round(heightScale > widthScale ? heightScale : widthScale);
        }

        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;

        return BitmapFactory.decodeFile(path, options);
    }

    public static Bitmap getScaledBitmap(String path, Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay()
                .getSize(size);
        return getScaledBitmap(path, size.x, size.y);
    }
}
