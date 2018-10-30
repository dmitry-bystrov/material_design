package com.javarunner.materialdesign.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.javarunner.materialdesign.model.DataSource;
import com.javarunner.materialdesign.model.database.entity.Photo;
import com.javarunner.materialdesign.presentation.view.CommonView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@InjectViewState
public class CommonPresenter extends MvpPresenter<CommonView> {
    private Scheduler uiScheduler;
    private File file;
    private File filesDir;
    private boolean favorites;
    private ListPresenter listPresenter;
    private CompositeDisposable d;

    @Inject
    DataSource dataSource;

    public CommonPresenter(Scheduler uiScheduler, File filesDir, boolean favorites) {
        this.uiScheduler = uiScheduler;
        this.filesDir = filesDir;
        this.favorites = favorites;
        this.listPresenter = new ListPresenter();
        this.d = new CompositeDisposable();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadData();
    }

    private void loadData() {
        if (favorites) {
            loadFavoritePhotos();
        } else {
            loadAllPhotos();
        }
    }

    private void loadAllPhotos() {
        d.add(dataSource.getAllPhotos()
                .subscribeOn(Schedulers.io())
                .observeOn(uiScheduler)
                .subscribe(this::setData));
    }

    private void loadFavoritePhotos() {
        d.add(dataSource.getFavoritePhotos()
                .subscribeOn(Schedulers.io())
                .observeOn(uiScheduler)
                .subscribe(this::setData));
    }

    private void setData(List<Photo> list) {
        listPresenter.setData(list);
        getViewState().updateList();
    }

    public void onTakePhotoButtonCLick() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmmss", Locale.ROOT);
        String newFileName = String.format("%s%s%s", "IMG_", simpleDateFormat.format(new Date()), ".jpg");
        file = new File(filesDir, newFileName);
        getViewState().takePhoto(file);
    }

    public void onPhotoTaken() {
        Photo newPhoto = new Photo(file.getPath(), false);
        d.add(dataSource.addPhoto(newPhoto)
                .subscribeOn(Schedulers.io())
                .observeOn(uiScheduler)
                .subscribe(() -> {
                    listPresenter.getData().add(newPhoto);
                    getViewState().revokePermissions(file);
                    getViewState().showMessageOnPhotoAdded();
                    getViewState().updateList();
                }));
    }

    public ListPresenter getListPresenter() {
        return listPresenter;
    }

    public void onItemClick(int position) {
        Timber.d("onItemClick(%d)", position);
    }

    public void onItemLongClick(int position) {
        Timber.d("onItemLongClick(%d)", position);
    }

    public void onFavoriteToggle(int position) {
        Photo photo = listPresenter.getData().get(position);
        photo.setFavorite(!photo.isFavorite());
        d.add(dataSource.updatePhoto(photo)
                .subscribeOn(Schedulers.io())
                .observeOn(uiScheduler)
                .subscribe(() -> {
                    if (favorites) {
                        loadData();
                    }
                }));
    }

    //    private static final String FAVORITE_PREFERENCE = "favorite_preference";
//    }
//        data.get(index).setFavorite(favorite);
//        preferences.saveStringSet(stringSet);
//
//        }
//            stringSet.remove(data.get(index).getFilePath());
//        } else {
//            stringSet.add(data.get(index).getFilePath());
//        if (favorite) {
//
//        Set<String> stringSet = preferences.loadStringSet(new HashSet<String>());
//    private void setFavorite(int index, boolean favorite) {
//
//    }
//        return photoInfoList;
//
//        }
//            }
//                data.add(new Photo(file.getPath(), favorite));
//
//                }
//                    continue;
//                if (favoritesOnly && !favorite) {
//                boolean favorite = favoriteStringSet.contains(file.getPath());
//            for (File file : files) {
//        if (files != null) {
//
//        Set<String> favoriteStringSet = preferences.loadStringSet(new HashSet<String>());
//        File[] files = FilesUtils.getFilesDir().listFiles();
//        List<Photo> photoInfoList = new ArrayList<>();
//    private List<Photo> getPhotoList() {
//
//    }
//        }
//            getViewState().showMessagePhotoDeleted();
//            getViewState().updateList(getPhotoList());
//
//            }
//                setFavorite(position, false);
//            if (photoInfo.isFavorite()) {
//        if (new File(photoInfo.getFilePath()).delete()) {
//
//        Photo photoInfo = data.get(position);
//    public void onFileDeleteCommand(int position) {
//
//    }
//        this.preferences = new AndroidPreferences(FAVORITE_PREFERENCE);
//        this.favoritesOnly = favoritesOnly;
//        this.data = new ArrayList<>();
//    public CommonPresenter(boolean favoritesOnly) {
//
//    private AndroidPreferences preferences;
//    boolean favoritesOnly;
//    private List<Photo> data;

    @Override
    public void onDestroy() {
        super.onDestroy();
        d.dispose();
    }
}
