package com.javarunner.materialdesign;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private static final int REQUEST_CODE = 100;
    public static final String PHOTO_FILE_PATH = "photo_file_path";
    private View fragmentView;
    private File filesDir;
    private File photoFile;
    private PhotoListAdapter photoListAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        filesDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (savedInstanceState != null) {
            String photoFilePath = savedInstanceState.getString(MainFragment.PHOTO_FILE_PATH);
            if (photoFilePath != null) {
                photoFile = new File(photoFilePath);
            }
        }

        RecyclerView recyclerView = getActivity().findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        photoListAdapter = new PhotoListAdapter(PhotoUtils.getPhotoInfoList(filesDir));
        recyclerView.setAdapter(photoListAdapter);

        photoListAdapter.setOnItemClickListener(new PhotoListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), PhotoViewActivity.class);
                intent.putExtra(PHOTO_FILE_PATH, photoListAdapter.getPhotoInfo(position).getPhotoFilePath());
                startActivity(intent);
            }
        });

        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoFile = new File(filesDir, PhotoUtils.getPhotoFilename());
                Intent cameraIntent = PhotoUtils.getCameraIntent(getActivity(), photoFile);

                if (cameraIntent == null) {
                    showSnackbar(R.string.error_camera);
                } else {
                    startActivityForResult(cameraIntent, REQUEST_CODE);
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (photoFile != null) {
            outState.putString(PHOTO_FILE_PATH, photoFile.getPath());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            PhotoUtils.revokeUriPermission(getActivity(), photoFile);
            photoListAdapter.addPhotoToList(photoFile.getPath());
            showSnackbar(R.string.photo_added);
        }
    }

    private void showSnackbar(int messageId) {
        Snackbar.make(fragmentView,
                getString(messageId),
                Snackbar.LENGTH_LONG).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_main, container, false);
        return fragmentView;
    }
}
