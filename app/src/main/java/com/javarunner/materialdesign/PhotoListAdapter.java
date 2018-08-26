package com.javarunner.materialdesign;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ToggleButton;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.ViewHolder>{
    PhotoInfo[] photoInfoArray;

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        ToggleButton favoriteButton;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_view);
            favoriteButton = itemView.findViewById(R.id.button_favorite);
        }
    }

    public PhotoListAdapter(PhotoInfo[] photoInfoArray) {
        this.photoInfoArray = photoInfoArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageView.setImageBitmap(PhotoUtils
                .getScaledBitmap(photoInfoArray[position].getPhotoFile().getPath(),
                        600,
                        600));
        holder.favoriteButton.setChecked(photoInfoArray[position].isFavorite());
    }

    @Override
    public int getItemCount() {
        return photoInfoArray.length;
    }
}
