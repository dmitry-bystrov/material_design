package com.javarunner.materialdesign.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.javarunner.materialdesign.R;
import com.javarunner.materialdesign.utils.ThemeManager;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;

public class PhotoViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(new ThemeManager(this).getThemeResourceId());
        setContentView(R.layout.activity_photo_view);
        supportPostponeEnterTransition();

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
                        .into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                supportStartPostponedEnterTransition();
                            }

                            @Override
                            public void onError(Exception e) {
                                supportStartPostponedEnterTransition();
                            }
                        });
            }
        }
    }
}
