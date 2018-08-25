package com.javarunner.materialdesign;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PhotoUtils {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmmss", Locale.ROOT);

    public static String getPhotoFilename() {
        return String.format("%s%s%s", "IMG_", simpleDateFormat.format(new Date()), ".jpg");
    }

    public static boolean takePhoto(Activity activity, File photoFile, int requestCode) {
        PackageManager packageManager = activity.getPackageManager();
        Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (captureImage.resolveActivity(packageManager) == null) {
            return false;
        }

        Uri uri = FileProvider.getUriForFile(activity,
                "com.javarunner.materialdesign.fileprovider",
                photoFile);
        captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        List<ResolveInfo> cameraActivities = packageManager.queryIntentActivities(captureImage,
                        PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo cameraActivity : cameraActivities) {
            activity.grantUriPermission(cameraActivity.activityInfo.packageName,
                    uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }

        activity.startActivityForResult(captureImage, requestCode);
        return true;
    }
}
