package com.javarunner.materialdesign.presentation.presenter;

import com.javarunner.materialdesign.model.database.entity.Photo;
import com.javarunner.materialdesign.presentation.view.ListItemView;

import java.util.ArrayList;
import java.util.List;

public class ListPresenter {
    private List<Photo> data;

    public ListPresenter() {
        this.data = new ArrayList<>();
    }

    public void onBindListItem(ListItemView itemView, int position) {
        itemView.setPhoto(data.get(position).getFilePath());
        itemView.setFavorite(data.get(position).isFavorite());
    }

    public int getItemCount() {
        return data.size();
    }

    public List<Photo> getData() {
        return data;
    }

    public void setData(List<Photo> data) {
        this.data = data;
    }
}
