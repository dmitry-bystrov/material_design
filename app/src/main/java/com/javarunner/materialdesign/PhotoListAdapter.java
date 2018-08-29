package com.javarunner.materialdesign;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.ViewHolder> {
    private List<PhotoInfo> photoInfoList;
    private OnItemClickListener itemClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        ToggleButton favoriteButton;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_view);
            favoriteButton = itemView.findViewById(R.id.button_favorite);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
        }

    }
    public interface OnItemClickListener {

        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public PhotoListAdapter(List<PhotoInfo> photoInfoList) {
        this.photoInfoList = photoInfoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.get()
                .load(new File(photoInfoList.get(position).getPhotoFilePath()))
                .fit()
                .centerCrop()
                .into(holder.imageView);

        holder.favoriteButton.setChecked(photoInfoList.get(position).isFavorite());
    }

    @Override
    public int getItemCount() {
        return photoInfoList.size();
    }

    public PhotoInfo getPhotoInfo(int position) {
        return photoInfoList.get(position);
    }

    public void addPhotoToList(String path) {
        if (photoInfoList.size() > 0
                && !photoInfoList.get(photoInfoList.size() - 1).getPhotoFilePath().equals(path)) {

            List<PhotoInfo> newPhotoInfoList = new ArrayList<>(photoInfoList);
            newPhotoInfoList.add(new PhotoInfo(path));
            DiffUtilCallback diffUtilCallback =
                    new DiffUtilCallback(photoInfoList, newPhotoInfoList);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilCallback);

            photoInfoList = newPhotoInfoList;
            diffResult.dispatchUpdatesTo(this);
        }
    }
}
