package com.javarunner.materialdesign.adapters;

import android.support.v7.util.DiffUtil;

import com.javarunner.materialdesign.model.PhotoInfo;

import java.util.List;

public class DiffUtilCallback extends DiffUtil.Callback {
    private List<PhotoInfo> oldList;
    private List<PhotoInfo> newList;

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
        return oldList.get(oldItemPosition).getFilePath().equals(newList.get(newItemPosition).getFilePath());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getFilePath().equals(newList.get(newItemPosition).getFilePath());
    }
}
