package com.javarunner.materialdesign.ui.fragment;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.javarunner.materialdesign.R;
import com.javarunner.materialdesign.adapters.PhotoListAdapter;
import com.javarunner.materialdesign.model.PhotoInfo;
import com.javarunner.materialdesign.model.PhotoInfoList;
import com.javarunner.materialdesign.presentation.presenter.PhotoListPresenter;
import com.javarunner.materialdesign.presentation.view.PhotoListView;
import com.javarunner.materialdesign.ui.activity.ViewPagerActivity;
import com.javarunner.materialdesign.utils.Camera;
import com.javarunner.materialdesign.utils.FilesUtils;

import java.io.File;
import java.util.List;

public class MainFragment extends MvpAppCompatFragment implements PhotoListView {
    private static final int REQUEST_CODE = 100;
    private File photoFile;
    private Camera camera;
    private PhotoListAdapter photoListAdapter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @InjectPresenter
    PhotoListPresenter photoListPresenter;

    @ProvidePresenter
    public PhotoListPresenter providePhotoListPresenter() {
        photoListPresenter = new PhotoListPresenter(false);
        return photoListPresenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restoreFile(savedInstanceState);
        camera = new Camera(this);
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
        photoListAdapter = new PhotoListAdapter();
        recyclerView.setAdapter(photoListAdapter);

        photoListAdapter.setOnItemClickListener(new PhotoListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String filePath = photoListAdapter.getPhotoInfoList().get(position).getFilePath();
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
                        photoListPresenter.onFileDeleteCommand(position);
                    }
                });

                FragmentManager fragmentManager = getFragmentManager();
                if (fragmentManager != null) {
                    fileDeleteDialog.show(getFragmentManager(), getString(R.string.file_delete_dialog));
                }

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

    @Nullable
    private View findCoordinatorLayout() {
        Activity activity = getActivity();
        if (activity != null) {
            return getActivity().findViewById(R.id.coordinator_layout);
        } else {
            return null;
        }
    }

    @Override
    public void showFileDeleteMessage() {
        View coordinatorLayout = findCoordinatorLayout();
        if (coordinatorLayout != null) {
            Snackbar.make(coordinatorLayout, getString(R.string.photo_deleted), Snackbar.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void updatePhotoListAdapter(List<PhotoInfo> newData) {
        photoListAdapter.dispatchUpdates(newData);
    }
}
