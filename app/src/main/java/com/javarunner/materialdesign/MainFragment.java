package com.javarunner.materialdesign;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;

public class MainFragment extends Fragment {

    private static final int REQUEST_CODE_FOR_PHOTO = 100;
    private File filesDir;
    private File photoFile;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        filesDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoFile = new File(filesDir, PhotoUtils.getPhotoFilename());

                if (!PhotoUtils.takePhoto(getActivity(), photoFile, REQUEST_CODE_FOR_PHOTO)) {
                    Snackbar.make(view, getString(R.string.error_camera), Snackbar.LENGTH_LONG).show();
                }
            }
        });

        File[] files = filesDir.listFiles();
        PhotoInfo[] photoInfoArray = new PhotoInfo[files.length];
        for (int i = 0; i < files.length; i++) {
            photoInfoArray[i] = new PhotoInfo(files[i]);
        }

        RecyclerView recyclerView = getActivity().findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(new PhotoListAdapter(photoInfoArray));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
}
