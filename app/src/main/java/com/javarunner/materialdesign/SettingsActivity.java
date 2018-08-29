package com.javarunner.materialdesign;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(ThemeUtils.getThemeResourceId());
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.rb_default_theme).setOnClickListener(this);
        findViewById(R.id.rb_brown_cyan_theme).setOnClickListener(this);
        findViewById(R.id.rb_cyan_deep_orange_theme).setOnClickListener(this);

        RadioButton radioButton = findViewById(ThemeUtils.getSelectedTheme());

        if (radioButton != null) {
            radioButton.setChecked(true);
        }
    }

    @Override
    public void onClick(View v) {
        ThemeUtils.saveSelectedTheme(v.getId());
        startActivity(new Intent(this, getClass()));
        finish();
    }
}
