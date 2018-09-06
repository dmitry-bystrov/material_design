package com.javarunner.materialdesign.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import com.javarunner.materialdesign.R;
import com.javarunner.materialdesign.activities.ViewPagerActivity;
import com.javarunner.materialdesign.adapters.DiffUtilCallback;
import com.javarunner.materialdesign.adapters.PhotoListAdapter;
import com.javarunner.materialdesign.models.PhotoInfo;
import com.javarunner.materialdesign.utils.CameraUtils;
import com.javarunner.materialdesign.utils.ImageFilesUtils;

import java.io.File;
import java.util.List;

public class MainFragment extends Fragment {
    private static final int REQUEST_CODE = 100;
    private File photoFile;
    private PhotoListAdapter photoListAdapter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restoreFile(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupFloatingActionButton();
    }

    private void setupRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.gallery_columns)));
        photoListAdapter = new PhotoListAdapter(ImageFilesUtils.getPhotoInfoList());
        recyclerView.setAdapter(photoListAdapter);

        photoListAdapter.setOnItemClickListener(new PhotoListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Intent intent = new Intent(getActivity(), PhotoViewActivity.class); // открыть фото в полный размер в новой активити
                Intent intent = new Intent(getActivity(), ViewPagerActivity.class); // открыть фото в полный размер во вьюпейджере
                intent.putExtra(getString(R.string.image_file_path), ImageFilesUtils.getImageFilePath(photoListAdapter.getPhotoInfoList(), position));
                startActivity(intent);
            }

            @Override
            public boolean onLongClick(View view, final int position) {
                FileDeleteDialog fileDeleteDialog = new FileDeleteDialog();
                fileDeleteDialog.setOnButtonClickListener(new FileDeleteDialog.OnButtonClickListener() {
                    @Override
                    public void onButtonClick(DialogInterface dialog, int which) {
                        String imageFilePath = ImageFilesUtils.getImageFilePath(photoListAdapter.getPhotoInfoList(), position);
                        if (ImageFilesUtils.deleteFile(imageFilePath)) {
                            dispatchUpdates(ImageFilesUtils.removeItemFromList(photoListAdapter.getPhotoInfoList(), position));
                            showSnackBar(R.string.photo_deleted);
                        }
                    }
                });

                fileDeleteDialog.show(getFragmentManager(), getString(R.string.file_delete_dialog));
                return true;
            }

            @Override
            public void onFavoriteCheckedChanged(boolean isChecked, int position) {
                if (isChecked) {
                    ImageFilesUtils.addToFavoriteList(ImageFilesUtils.getImageFilePath(photoListAdapter.getPhotoInfoList(), position));
                } else {
                    ImageFilesUtils.removeFromFavoriteList(ImageFilesUtils.getImageFilePath(photoListAdapter.getPhotoInfoList(), position));
                }
            }
        });
    }

    private void restoreFile(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String photoFilePath = savedInstanceState.getString(getString(R.string.image_file_path));
            if (photoFilePath != null) {
                photoFile = new File(photoFilePath);
            }
        }
    }

    private void setupFloatingActionButton() {
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoFile = new File(ImageFilesUtils.getFilesDir(), ImageFilesUtils.getNewFilename());
                Intent cameraIntent = CameraUtils.getCameraIntent(getActivity(), photoFile);

                if (cameraIntent == null) {
                    showSnackBar(R.string.error_camera);
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
            outState.putString(getString(R.string.image_file_path), photoFile.getPath());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            CameraUtils.revokeUriPermission(getActivity(), photoFile);
            dispatchUpdates(ImageFilesUtils.getPhotoInfoList());
            showSnackBar(R.string.photo_added);
        }
    }

    private void showSnackBar(int messageId) {
        Snackbar.make(getActivity().findViewById(R.id.coordinator_layout),
                getString(messageId),
                Snackbar.LENGTH_SHORT).show();
    }

    private void dispatchUpdates(List<PhotoInfo> newPhotoInfoList) {
        DiffUtilCallback diffUtilCallback =
                new DiffUtilCallback(photoListAdapter.getPhotoInfoList(), newPhotoInfoList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilCallback);
        photoListAdapter.setPhotoInfoList(newPhotoInfoList);
        diffResult.dispatchUpdatesTo(photoListAdapter);
    }

}
