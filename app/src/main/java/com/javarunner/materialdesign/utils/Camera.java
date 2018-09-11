package com.javarunner.materialdesign.utils;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;

import com.javarunner.materialdesign.R;

import java.io.File;
import java.util.List;

public class Camera {

    private Fragment fragment;
    private Intent intent;

    public Camera(Fragment fragment) {
        this.fragment = fragment;
        this.intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    }

    public boolean takePicture(File file, int requestCode) {
        if (intent.resolveActivity(fragment.getActivity().getPackageManager()) == null) {
            return false;
        }

        Uri uri = FileProvider.getUriForFile(fragment.getContext(),
                fragment.getString(R.string.fileprovider),
                file);
        grantUriPermission(uri);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        fragment.startActivityForResult(intent, requestCode);
        return true;
    }

    private void grantUriPermission(Uri uri) {
        List<ResolveInfo> cameraActivities = fragment.getActivity().getPackageManager().queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo cameraActivity : cameraActivities) {
            fragment.getActivity().grantUriPermission(cameraActivity.activityInfo.packageName,
                    uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
    }

    public void revokeUriPermission(File photoFile) {
        Uri uri = FileProvider.getUriForFile(fragment.getContext(), fragment.getString(R.string.fileprovider), photoFile);
        fragment.getActivity().revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
    }
}
