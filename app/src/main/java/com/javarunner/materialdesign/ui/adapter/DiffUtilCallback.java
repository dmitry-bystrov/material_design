package com.javarunner.materialdesign.ui.adapter;

import android.support.v7.util.DiffUtil;

import com.javarunner.materialdesign.model.database.entity.Photo;

import java.util.List;

public class DiffUtilCallback extends DiffUtil.Callback {
    private List<Photo> oldList;
    private List<Photo> newList;

    public DiffUtilCallback(List<Photo> oldList, List<Photo> newList) {
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
