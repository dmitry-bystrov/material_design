package com.javarunner.materialdesign.adapters;

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
import com.javarunner.materialdesign.models.PhotoInfoList;
import com.squareup.picasso.Picasso;

import java.io.File;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.ViewHolder> {
    private PhotoInfoList photoInfoList;
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

    public PhotoListAdapter(PhotoInfoList photoInfoList) {
        this.photoInfoList = photoInfoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String filePath = photoInfoList.getItem(position).getFilePath();
        holder.imageView.setTransitionName(filePath);
        Picasso.get()
                .load(new File(filePath))
                .fit()
                .centerCrop()
                .into(holder.imageView);

        holder.favoriteButton.setChecked(photoInfoList.getItem(position).isFavorite());
    }

    @Override
    public int getItemCount() {
        return photoInfoList.size();
    }

    public PhotoInfoList getPhotoInfoList() {
        return photoInfoList;
    }

    public void dispatchUpdates(PhotoInfoList newPhotoInfoList) {
        DiffUtilCallback diffUtilCallback =
                new DiffUtilCallback(photoInfoList.getList(), newPhotoInfoList.getList());
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilCallback);
        photoInfoList = newPhotoInfoList;
        diffResult.dispatchUpdatesTo(this);
    }
}
