package com.javarunner.materialdesign.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.javarunner.materialdesign.R;
import com.javarunner.materialdesign.adapters.ViewPagerAdapter;
import com.javarunner.materialdesign.models.PhotoInfoList;
import com.javarunner.materialdesign.utils.FilesUtils;
import com.javarunner.materialdesign.utils.ThemeManager;

public class ViewPagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(new ThemeManager(this).getThemeResourceId());
        setContentView(R.layout.activity_view_pager);
        supportPostponeEnterTransition();

        ViewPager viewPager = findViewById(R.id.view_pager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(new PhotoInfoList(this, FilesUtils.getFilesDir(this), false), this);
        viewPager.setAdapter(viewPagerAdapter);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String imageFilePath = bundle.getString(getString(R.string.image_file_path));
            if (imageFilePath != null) {
                int position = viewPagerAdapter.getPhotoInfoList().findItem(imageFilePath);
                if (position != -1) {
                    viewPager.setCurrentItem(position);
                }
            }
        }
    }
}
