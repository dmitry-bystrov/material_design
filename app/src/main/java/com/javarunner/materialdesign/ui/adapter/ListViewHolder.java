package com.javarunner.materialdesign.ui.adapter;

import android.graphics.drawable.StateListDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxCompoundButton;
import com.javarunner.materialdesign.R;
import com.javarunner.materialdesign.presentation.view.ListItemView;
import com.squareup.picasso.Picasso;

import java.io.File;

import io.reactivex.subjects.PublishSubject;

public class ListViewHolder extends RecyclerView.ViewHolder implements ListItemView {

    private ImageView imageView;
    private ToggleButton favoriteButton;

    public ListViewHolder(View itemView, PublishSubject<ListEvent> events) {
        super(itemView);
        imageView = itemView.findViewById(R.id.image_view);
        favoriteButton = itemView.findViewById(R.id.button_favorite);

        RxView.clicks(imageView)
                .map(o -> new ListEvent(ListEvent.EventType.CLICK, getAdapterPosition()))
                .subscribe(events);

        RxView.longClicks(imageView)
                .map(o -> new ListEvent(ListEvent.EventType.LONG_CLICK, getAdapterPosition()))
                .subscribe(events);

        RxCompoundButton.checkedChanges(favoriteButton)
                .map(o -> new ListEvent(ListEvent.EventType.TOGGLE, getAdapterPosition()))
                .subscribe(events);

        StateListDrawable scaleDrawable = (StateListDrawable) favoriteButton.getBackground();
        scaleDrawable.setLevel(5000);
    }

    @Override
    public void setPhoto(String path) {
        imageView.setTransitionName(path);
        Picasso.get()
                .load(new File(path))
                .fit()
                .centerCrop()
                .into(imageView);
    }

    @Override
    public void setFavorite(boolean favorite) {
        favoriteButton.setChecked(favorite);
    }
}
