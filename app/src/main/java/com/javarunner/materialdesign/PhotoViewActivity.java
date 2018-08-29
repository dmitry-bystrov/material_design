package com.javarunner.materialdesign;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

public class PhotoViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(ThemeUtils.getThemeResourceId());
        setContentView(R.layout.activity_photo_view);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String photoFilePath = bundle.getString(MainFragment.PHOTO_FILE_PATH);
            if (photoFilePath != null) {
                ImageView imageView = findViewById(R.id.image_view);
                Picasso.get().load(new File(photoFilePath)).fit().centerCrop().into(imageView);
            }
        }
    }
}
