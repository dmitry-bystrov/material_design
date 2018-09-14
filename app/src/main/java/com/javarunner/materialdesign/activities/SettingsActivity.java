package com.javarunner.materialdesign.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;

import com.javarunner.materialdesign.R;
import com.javarunner.materialdesign.utils.ThemeManager;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    ThemeManager themeManager;
    List<RadioButton> radioButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeManager = new ThemeManager(this);
        setTheme(themeManager.getThemeResourceId());
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        radioButtons = new ArrayList<>();
        radioButtons.add((RadioButton) findViewById(R.id.rb_default_theme));
        radioButtons.add((RadioButton) findViewById(R.id.rb_brown_cyan_theme));
        radioButtons.add((RadioButton) findViewById(R.id.rb_cyan_deep_orange_theme));
        radioButtons.add((RadioButton) findViewById(R.id.rb_blue_grey_orange_theme));

        for (int i = 0; i < radioButtons.size(); i++) {
            radioButtons.get(i).setOnClickListener(this);
            radioButtons.get(i).setChecked(themeManager.getThemeIndex() == i);
        }
    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < radioButtons.size(); i++) {
            if (radioButtons.get(i).equals(v)) {
                themeManager.saveThemeIndex(i);
                break;
            }
        }

        startActivity(new Intent(this, getClass()));
        finish();
    }
}
