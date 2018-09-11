package com.javarunner.materialdesign.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.javarunner.materialdesign.R;

import java.io.File;
import java.util.List;

public class Camera {

    private Context context;
    private Intent intent;

    public Camera(Context context) {
        this.context = context;
        this.intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    }

    public boolean takePicture(File file) {
        if (intent.resolveActivity(context.getPackageManager()) == null) {
            return false;
        }

        Uri uri = FileProvider.getUriForFile(context,
                context.getString(R.string.fileprovider),
                file);
        grantUriPermission(uri);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

        return true;
    }

    private void grantUriPermission(Uri uri) {
        List<ResolveInfo> cameraActivities = context.getPackageManager().queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo cameraActivity : cameraActivities) {
            context.grantUriPermission(cameraActivity.activityInfo.packageName,
                    uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
    }

    public void revokeUriPermission(File photoFile) {
        Uri uri = FileProvider.getUriForFile(context, context.getString(R.string.fileprovider), photoFile);
        context.revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
    }
}
