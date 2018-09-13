package com.javarunner.materialdesign.fragments;

import android.app.Activity;
import android.app.ActivityOptions;
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

import com.javarunner.materialdesign.R;
import com.javarunner.materialdesign.activities.ViewPagerActivity;
import com.javarunner.materialdesign.adapters.PhotoListAdapter;
import com.javarunner.materialdesign.models.PhotoInfo;
import com.javarunner.materialdesign.models.PhotoInfoList;
import com.javarunner.materialdesign.utils.Camera;
import com.javarunner.materialdesign.utils.FilesUtils;

import java.io.File;

public class MainFragment extends Fragment {
    private static final int REQUEST_CODE = 100;
    private File filesDir;
    private File photoFile;
    private Camera camera;
    private PhotoListAdapter photoListAdapter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restoreFile(savedInstanceState);
        camera = new Camera(this);
        filesDir = FilesUtils.getFilesDir(getContext());
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
        photoListAdapter = new PhotoListAdapter(new PhotoInfoList(getContext(), filesDir, false));
        recyclerView.setAdapter(photoListAdapter);

        photoListAdapter.setOnItemClickListener(new PhotoListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String filePath = photoListAdapter.getPhotoInfoList().getItem(position).getFilePath();
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                        view, filePath);
                Intent intent = new Intent(getActivity(), ViewPagerActivity.class);
                intent.putExtra(getString(R.string.image_file_path), filePath);
                startActivity(intent, options.toBundle());
            }

            @Override
            public boolean onLongClick(View view, final int position) {
                FileDeleteDialog fileDeleteDialog = new FileDeleteDialog();
                fileDeleteDialog.setOnButtonClickListener(new FileDeleteDialog.OnButtonClickListener() {
                    @Override
                    public void onButtonClick(DialogInterface dialog, int which) {
                        PhotoInfoList photoInfoList = photoListAdapter.getPhotoInfoList();
                        PhotoInfo photoInfo = photoInfoList.getItem(position);

                        if (photoInfoList.deleteFile(position)) {
                            if (photoInfo.isFavorite()) {
                                photoInfoList.setFavorite(position, false);
                            }

                            photoListAdapter.dispatchUpdates(new PhotoInfoList(getContext(), filesDir, false));
                            Snackbar.make(findCoordinatorLayout(), getString(R.string.photo_deleted), Snackbar.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });

                fileDeleteDialog.show(getFragmentManager(), getString(R.string.file_delete_dialog));
                return true;
            }

            @Override
            public void onFavoriteCheckedChanged(boolean isChecked, int position) {
                photoListAdapter.getPhotoInfoList().setFavorite(position, isChecked);
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
                photoFile = new File(filesDir, FilesUtils.getNewFilename());
                if (!camera.takePicture(photoFile, REQUEST_CODE)) {
                    Snackbar.make(findCoordinatorLayout(), getString(R.string.error_camera), Snackbar.LENGTH_SHORT)
                            .show();
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
            camera.revokeUriPermission(photoFile);
            photoListAdapter.dispatchUpdates(new PhotoInfoList(getContext(), filesDir, false));
            Snackbar.make(findCoordinatorLayout(), getString(R.string.photo_added), Snackbar.LENGTH_SHORT)
                    .show();
        }
    }

    private View findCoordinatorLayout() {
        return getActivity().findViewById(R.id.coordinator_layout);
    }
}
