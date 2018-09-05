package com.javarunner.materialdesign.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.javarunner.materialdesign.models.PhotoInfo;
import com.javarunner.materialdesign.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    private List<PhotoInfo> photoInfoList;
    private Context context;

    public ViewPagerAdapter(Context context, List<PhotoInfo> photoInfoList) {
        this.context = context;
        this.photoInfoList = photoInfoList;
    }

    @Override
    public int getCount() {
        return photoInfoList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View pagerItemView = LayoutInflater.from(context)
                .inflate(R.layout.pager_item_photo, container, false);

        ImageView imageView = pagerItemView.findViewById(R.id.image_view);
        Picasso.get()
                .load(new File(photoInfoList.get(position).getFilePath()))
                .fit()
                .centerInside()
                .into(imageView);

        container.addView(pagerItemView);
        return pagerItemView;
    }

    public int findItemPosition(String path) {
        for (int i = 0; i < photoInfoList.size(); i++) {
            if (photoInfoList.get(i).getFilePath().equals(path)) {
                return i;
            }
        }

        return -1;
    }
}
