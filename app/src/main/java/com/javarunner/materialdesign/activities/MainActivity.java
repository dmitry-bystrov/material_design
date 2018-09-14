package com.javarunner.materialdesign.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.javarunner.materialdesign.R;
import com.javarunner.materialdesign.fragments.DatabaseFragment;
import com.javarunner.materialdesign.fragments.FavoriteFragment;
import com.javarunner.materialdesign.fragments.MainFragment;
import com.javarunner.materialdesign.fragments.NetworkFragment;
import com.javarunner.materialdesign.utils.ThemeManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String SELECTED_ITEM_ID = "selected_item_id";

    enum FragmentTag {
        COMMON("fragment_common", R.id.action_common, true),
        DATABASE("fragment_database", R.id.action_database, false),
        NETWORK("fragment_network", R.id.action_network, false),
        FAVORITE("fragment_favorite", R.id.action_favorite, false);

        private final String tag;
        private final int menuItemID;
        private boolean showFloatActionButton;

        FragmentTag(String tag, int menuItemID, boolean showFloatActionButton) {
            this.tag = tag;
            this.menuItemID = menuItemID;
            this.showFloatActionButton = showFloatActionButton;
        }

        public String getTag() {
            return tag;
        }

        public int getMenuItemID() {
            return menuItemID;
        }

        public boolean showFloatActionButton() {
            return showFloatActionButton;
        }

        public static FragmentTag findByMenuItemID(int menuItemID) {
            for (FragmentTag o : FragmentTag.values()) {
                if (o.getMenuItemID() == menuItemID) return o;
            }

            return COMMON;
        }
    }

    private DrawerLayout drawer;
    private ThemeManager themeManager;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeManager = new ThemeManager(this);
        setTheme(themeManager.getThemeResourceId());
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                fragmentSelection(FragmentTag.findByMenuItemID(item.getItemId()));
                return true;
            }
        });

        if (savedInstanceState != null) {
            bottomNavigationView.setSelectedItemId(savedInstanceState.getInt(SELECTED_ITEM_ID));
        }

        fragmentSelection(FragmentTag.findByMenuItemID(bottomNavigationView.getSelectedItemId()));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_ITEM_ID, bottomNavigationView.getSelectedItemId());
    }

    private void fragmentSelection(FragmentTag fragmentTag) {
        Fragment fragment = null;
        switch (fragmentTag) {
            case COMMON:
                fragment = getSupportFragmentManager().findFragmentByTag(fragmentTag.getTag());
                if (fragment == null) {
                    fragment = MainFragment.newInstance();
                }

                break;
            case DATABASE:
                fragment = getSupportFragmentManager().findFragmentByTag(fragmentTag.getTag());
                if (fragment == null) {
                    fragment = DatabaseFragment.newInstance();
                }

                break;
            case NETWORK:
                fragment = getSupportFragmentManager().findFragmentByTag(fragmentTag.getTag());
                if (fragment == null) {
                    fragment = NetworkFragment.newInstance();
                }

                break;
            case FAVORITE:
                fragment = getSupportFragmentManager().findFragmentByTag(fragmentTag.getTag());
                if (fragment == null) {
                    fragment = FavoriteFragment.newInstance();
                }

                break;
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fragment, fragmentTag.getTag());
        transaction.commit();

        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        if (fragmentTag.showFloatActionButton()) {
            floatingActionButton.show();
        } else {
            floatingActionButton.hide();
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (themeManager.themeChanged()) {
            startActivity(new Intent(this, getClass()));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_main) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.menu_settings) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
