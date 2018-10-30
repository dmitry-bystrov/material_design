package com.javarunner.materialdesign.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;

public class ViewPagerAdapter extends PagerAdapter {
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return false;
    }
//    private PhotoInfoList photoInfoList;
//    private AppCompatActivity activity;
//
//    public ViewPagerAdapter(PhotoInfoList photoInfoList, AppCompatActivity activity) {
//        this.photoInfoList = photoInfoList;
//        this.activity = activity;
//    }
//
//    @Override
//    public int getCount() {
//        return photoInfoList.size();
//    }
//
//    @Override
//    public boolean isViewFromObject(View view, Object object) {
//        return view.equals(object);
//    }
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView((View) object);
//    }
//
//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        View pagerItemView = LayoutInflater.from(activity)
//                .inflate(R.layout.pager_item_photo, container, false);
//
//        ImageView imageView = pagerItemView.findViewById(R.id.image_view);
//        String filePath = photoInfoList.getItem(position).getFilePath();
//        imageView.setTransitionName(filePath);
//        Picasso.get()
//                .load(new File(filePath))
//                .fit()
//                .centerInside()
//                .into(imageView, new Callback() {
//                    @Override
//                    public void onSuccess() {
//                        activity.supportStartPostponedEnterTransition();
//                    }
//
//                    @Override
//                    public void onError(Exception e) {
//                        activity.supportStartPostponedEnterTransition();
//                    }
//                });
//
//        container.addView(pagerItemView);
//        return pagerItemView;
//    }
//
//    public PhotoInfoList getPhotoInfoList() {
//        return photoInfoList;
//    }
}
