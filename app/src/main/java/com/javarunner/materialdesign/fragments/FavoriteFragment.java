package com.javarunner.materialdesign.fragments;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.javarunner.materialdesign.R;
import com.javarunner.materialdesign.activities.PhotoViewActivity;
import com.javarunner.materialdesign.adapters.DiffUtilCallback;
import com.javarunner.materialdesign.adapters.PhotoListAdapter;
import com.javarunner.materialdesign.models.PhotoInfo;
import com.javarunner.materialdesign.utils.ImageFilesUtils;
import com.javarunner.materialdesign.utils.SnackBar;

import java.util.List;

public class FavoriteFragment extends Fragment {
    private PhotoListAdapter photoListAdapter;
    private ImageView imageView;
    private SnackBar snackBar;

    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        snackBar = new SnackBar(getContext(), getActivity().findViewById(R.id.coordinator_layout));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView(view);
        imageView = view.findViewById(R.id.image_view);

        if (photoListAdapter.getItemCount() > 0) {
            imageView.setVisibility(View.GONE);
        }
    }

    private void setupRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.gallery_columns)));
        photoListAdapter = new PhotoListAdapter(ImageFilesUtils.getFavoritePhotoInfoList());
        recyclerView.setAdapter(photoListAdapter);

        photoListAdapter.setOnItemClickListener(new PhotoListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String filePath = ImageFilesUtils.getImageFilePath(photoListAdapter.getPhotoInfoList(), position);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                        view, filePath);
                Intent intent = new Intent(getActivity(), PhotoViewActivity.class);
                intent.putExtra(getString(R.string.image_file_path), filePath);
                startActivity(intent, options.toBundle());
            }

            @Override
            public boolean onLongClick(View view, final int position) {
                FileDeleteDialog fileDeleteDialog = new FileDeleteDialog();
                fileDeleteDialog.setOnButtonClickListener(new FileDeleteDialog.OnButtonClickListener() {
                    @Override
                    public void onButtonClick(DialogInterface dialog, int which) {
                        String imageFilePath = ImageFilesUtils.getImageFilePath(photoListAdapter.getPhotoInfoList(), position);
                        if (ImageFilesUtils.deleteFile(imageFilePath)) {
                            photoListAdapter.dispatchUpdates(ImageFilesUtils.getFavoritePhotoInfoList());
                            snackBar.show(R.string.photo_deleted);
                        }
                    }
                });

                fileDeleteDialog.show(getFragmentManager(), getString(R.string.file_delete_dialog));
                return true;
            }

            @Override
            public void onFavoriteCheckedChanged(boolean isChecked, int position) {
                if (!isChecked) {
                    ImageFilesUtils.removeFromFavoriteList(ImageFilesUtils.getImageFilePath(photoListAdapter.getPhotoInfoList(), position));
                    photoListAdapter.dispatchUpdates(ImageFilesUtils.getFavoritePhotoInfoList());

                    if (photoListAdapter.getItemCount() == 0) {
                        imageView.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

}
