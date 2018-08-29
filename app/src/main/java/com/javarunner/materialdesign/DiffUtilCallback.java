package com.javarunner.materialdesign;

import android.support.v7.util.DiffUtil;

import java.util.List;

public class DiffUtilCallback extends DiffUtil.Callback {
    List<PhotoInfo> oldList;
    List<PhotoInfo> newList;

    public DiffUtilCallback(List<PhotoInfo> oldList, List<PhotoInfo> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getPhotoFile().equals(newList.get(newItemPosition).getPhotoFile());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getPhotoFile().equals(newList.get(newItemPosition).getPhotoFile());
    }
}