package com.javarunner.materialdesign.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.javarunner.materialdesign.AppClass;
import com.javarunner.materialdesign.R;

import java.io.File;
import java.util.List;

public class CameraUtils {

    private static final String AUTHORITY = AppClass.getContext().getString(R.string.fileprovider);

    public static Intent getCameraIntent(Activity activity, File photoFile) {
        PackageManager packageManager = activity.getPackageManager();
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (cameraIntent.resolveActivity(packageManager) == null) {
            return null;
        }

        Uri uri = FileProvider.getUriForFile(activity, AUTHORITY, photoFile);
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
        Uri uri = FileProvider.getUriForFile(activity, AUTHORITY, photoFile);
        activity.revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
    }
}
