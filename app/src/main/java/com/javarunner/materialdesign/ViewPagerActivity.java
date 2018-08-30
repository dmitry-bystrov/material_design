package com.javarunner.materialdesign;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class ViewPagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(ThemeUtils.getThemeResourceId());
        setContentView(R.layout.activity_view_pager);

        ViewPager viewPager = findViewById(R.id.view_pager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, ImageFileUtils.getPhotoInfoList());
        viewPager.setAdapter(viewPagerAdapter);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String imageFilePath = bundle.getString(MainFragment.IMAGE_FILE_PATH);
            if (imageFilePath != null) {
                int position = viewPagerAdapter.findItemPosition(imageFilePath);
                if (position != -1) {
                    viewPager.setCurrentItem(position);
                }
            }
        }
    }
}
