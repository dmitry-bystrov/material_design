package com.javarunner.materialdesign.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.javarunner.materialdesign.R;
import com.javarunner.materialdesign.utils.ThemeUtils;
import com.squareup.picasso.Picasso;

import java.io.File;

public class PhotoViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(ThemeUtils.getThemeStyleResourceID());
        setContentView(R.layout.activity_photo_view);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String photoFilePath = bundle.getString(getString(R.string.image_file_path));
            if (photoFilePath != null) {
                ImageView imageView = findViewById(R.id.image_view);
                imageView.setTransitionName(photoFilePath);
                Picasso.get()
                        .load(new File(photoFilePath))
                        .fit()
                        .centerInside()
                        .into(imageView);
            }
        }
    }
}
