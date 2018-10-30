package com.javarunner.materialdesign.ui.adapter;

import android.graphics.drawable.StateListDrawable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.javarunner.materialdesign.R;
import com.javarunner.materialdesign.model.database.entity.Photo;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.ViewHolder> {
    private List<Photo> photoList;
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

            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return itemClickListener.onLongClick(v, getAdapterPosition());
                }
            });

            favoriteButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    itemClickListener.onFavoriteCheckedChanged(isChecked, getAdapterPosition());
                }
            });

            StateListDrawable scaleDrawable = (StateListDrawable) favoriteButton.getBackground();
            scaleDrawable.setLevel(5000);
        }

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        boolean onLongClick(View view, int position);

        void onFavoriteCheckedChanged(boolean isChecked, int position);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public PhotoListAdapter() {
        this.photoList = new ArrayList<>();
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {
        String filePath = photoList.get(position).getFilePath();
        holder.imageView.setTransitionName(filePath);
        Picasso.get()
                .load(new File(filePath))
                .fit()
                .centerCrop()
                .into(holder.imageView);

        holder.favoriteButton.setChecked(photoList.get(position).isFavorite());
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public void dispatchUpdates(List<Photo> newPhotoList) {
        DiffUtilCallback diffUtilCallback =
                new DiffUtilCallback(photoList, newPhotoList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilCallback);
        photoList = newPhotoList;
        diffResult.dispatchUpdatesTo(this);
    }

    public List<Photo> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<Photo> photoList) {
        this.photoList = photoList;
    }
}
