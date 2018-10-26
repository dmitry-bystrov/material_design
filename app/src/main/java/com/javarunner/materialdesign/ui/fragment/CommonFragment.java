package com.javarunner.materialdesign.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.jakewharton.rxbinding2.view.RxView;
import com.javarunner.materialdesign.AppContext;
import com.javarunner.materialdesign.R;
import com.javarunner.materialdesign.presentation.presenter.CommonPresenter;
import com.javarunner.materialdesign.presentation.view.CommonView;
import com.javarunner.materialdesign.ui.adapter.ListAdapter;
import com.javarunner.materialdesign.ui.adapter.ListEvent;

import java.io.File;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class CommonFragment extends BaseMvpFragment implements CommonView {
    private static final int REQUEST_CODE = 100;
    private CompositeDisposable d;
    private RecyclerView recyclerView;

    @InjectPresenter
    CommonPresenter presenter;

    @ProvidePresenter
    public CommonPresenter provideCommonListPresenter() {
        presenter = new CommonPresenter(AndroidSchedulers.mainThread(),
                AppContext.getFilesDirectory());
        return presenter;
    }

    public static CommonFragment newInstance() {
        return new CommonFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        d = new CompositeDisposable();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        d.dispose();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        d.add(RxView.clicks(activity().findViewById(R.id.fab)).subscribe(o -> presenter.onTakePhotoButtonCLick()));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.gallery_columns)));
        ListAdapter adapter = new ListAdapter(presenter.getListPresenter());
        recyclerView.setAdapter(adapter);

        d.add(adapter.getEvents().subscribe(event -> {
            if (event.getEventType() == ListEvent.EventType.CLICK) {
                presenter.onItemClick(event.getPosition());
            }
            if (event.getEventType() == ListEvent.EventType.LONG_CLICK) {
                presenter.onItemLongClick(event.getPosition());
            }
            if (event.getEventType() == ListEvent.EventType.TOGGLE) {
                presenter.onFavoriteToggle(event.getPosition());
            }
        }));
    }

//    private void setupRecyclerView(View view) {
//        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.gallery_columns)));
//        photoListAdapter = new PhotoListAdapter();
//        recyclerView.setAdapter(photoListAdapter);
//
//        photoListAdapter.setOnItemClickListener(new PhotoListAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                String filePath = photoListAdapter.getPhotoList().get(position).getFilePath();
//                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),
//                        view, filePath);
//                Intent intent = new Intent(getActivity(), ViewPagerActivity.class);
//                intent.putExtra(getString(R.string.image_file_path), filePath);
//                startActivity(intent, options.toBundle());
//            }
//
//            @Override
//            public boolean onLongClick(View view, final int position) {
//                FileDeleteDialog fileDeleteDialog = new FileDeleteDialog();
//                fileDeleteDialog.setOnButtonClickListener(new FileDeleteDialog.OnButtonClickListener() {
//                    @Override
//                    public void onButtonClick(DialogInterface dialog, int which) {
//                        presenter.onFileDeleteCommand(position);
//                    }
//                });
//
//                FragmentManager fragmentManager = getFragmentManager();
//                if (fragmentManager != null) {
//                    fileDeleteDialog.show(getFragmentManager(), getString(R.string.file_delete_dialog));
//                }
//
//                return true;
//            }
//
//            @Override
//            public void onFavoriteCheckedChanged(boolean isChecked, int position) {
//                photoListAdapter.getPhotoList().setFavorite(position, isChecked);
//            }
//        });
//    }
//

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            presenter.onPhotoTaken();
        }
    }

    @Override
    public void takePhoto(File file) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        PackageManager packageManager = activity().getPackageManager();
        if (intent.resolveActivity(packageManager) == null) return;

        Uri uri = FileProvider.getUriForFile(activity(), getString(R.string.fileprovider), file);
        List<ResolveInfo> cameraActivities = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);

        for (ResolveInfo cameraActivity : cameraActivities) {
            activity().grantUriPermission(cameraActivity.activityInfo.packageName,
                    uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }

        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void revokePermissions(File file) {
        Uri uri = FileProvider.getUriForFile(activity(), getString(R.string.fileprovider), file);
        activity().revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
    }

    @Override
    public void updateList() {
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showMessage(String text) {
        Snackbar.make(activity().findViewById(R.id.coordinator_layout), text, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showMessageOnPhotoAdded() {
        showMessage(getString(R.string.photo_added));
    }

    @Override
    public void showMessageOnPhotoDeleted() {
        showMessage(getString(R.string.photo_deleted));
    }
}
