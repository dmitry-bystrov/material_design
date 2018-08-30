package com.javarunner.materialdesign;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;

public class MainFragment extends Fragment {

    private static final int REQUEST_CODE = 100;
    public static final String IMAGE_FILE_PATH = "photo_file_path";
    private static final String FILE_DELETE_DIALOG = "file_delete_dialog";
    private File photoFile;
    private PhotoListAdapter photoListAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        restoreFile(savedInstanceState);
        setupRecyclerView();
        setupListeners();
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = getActivity().findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        photoListAdapter = new PhotoListAdapter(ImageFileUtils.getPhotoInfoList());
        recyclerView.setAdapter(photoListAdapter);
    }

    private void restoreFile(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String photoFilePath = savedInstanceState.getString(MainFragment.IMAGE_FILE_PATH);
            if (photoFilePath != null) {
                photoFile = new File(photoFilePath);
            }
        }
    }

    private void setupListeners() {
        photoListAdapter.setOnItemClickListener(new PhotoListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Intent intent = new Intent(getActivity(), PhotoViewActivity.class);
                Intent intent = new Intent(getActivity(), ViewPagerActivity.class);
                intent.putExtra(IMAGE_FILE_PATH, photoListAdapter.getPhotoInfo(position).getImageFilePath());
                startActivity(intent);
            }

            @Override
            public boolean onLongClick(View view, final int position) {
                FileDeleteDialog fileDeleteDialog = new FileDeleteDialog();
                fileDeleteDialog.setOnButtonClickListener(new FileDeleteDialog.OnButtonClickListener() {
                    @Override
                    public void onButtonClick(DialogInterface dialog, int which) {
                        String imageFilePath = photoListAdapter.getPhotoInfo(position).getImageFilePath();
                        if (ImageFileUtils.deleteImageFile(imageFilePath)) {
                            photoListAdapter.deletePhotoFromList(position);
                            showSnackbar(R.string.photo_deleted);
                        }
                    }
                });

                fileDeleteDialog.show(getFragmentManager(), FILE_DELETE_DIALOG);
                return true;
            }
        });

        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoFile = new File(ImageFileUtils.getFilesDir(), ImageFileUtils.getNewFilename());
                Intent cameraIntent = ImageFileUtils.getCameraIntent(getActivity(), photoFile);

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
            outState.putString(IMAGE_FILE_PATH, photoFile.getPath());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            ImageFileUtils.revokeUriPermission(getActivity(), photoFile);
            photoListAdapter.addPhotoToList(photoFile.getPath());
            showSnackbar(R.string.photo_added);
        }
    }

    private void showSnackbar(int messageId) {
        Snackbar.make(getActivity().findViewById(R.id.coordinator_layout),
                getString(messageId),
                Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
}
