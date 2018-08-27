package com.javarunner.materialdesign;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private static final int REQUEST_CODE = 100;
    private File filesDir;
    private File photoFile;
    private PhotoListAdapter photoListAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        filesDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        RecyclerView recyclerView = getActivity().findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        photoListAdapter = new PhotoListAdapter(PhotoUtils.getPhotoInfoList(filesDir));
        recyclerView.setAdapter(photoListAdapter);

        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoFile = new File(filesDir, PhotoUtils.getPhotoFilename());

                Intent cameraIntent = PhotoUtils.getCameraIntent(getActivity(), photoFile);

                if (cameraIntent == null) {
                    Snackbar.make(view, getString(R.string.error_camera), Snackbar.LENGTH_LONG).show();
                } else {
                    startActivityForResult(cameraIntent, REQUEST_CODE);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            PhotoUtils.revokeUriPermission(getActivity(), photoFile);

            List<PhotoInfo> newPhotoInfoList = new ArrayList<>(photoListAdapter.getData());
            newPhotoInfoList.add(new PhotoInfo(photoFile));

            DiffUtilCallback diffUtilCallback =
                    new DiffUtilCallback(photoListAdapter.getData(), newPhotoInfoList);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilCallback);

            photoListAdapter.setData(newPhotoInfoList);
            diffResult.dispatchUpdatesTo(photoListAdapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
}
