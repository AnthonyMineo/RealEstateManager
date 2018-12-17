package com.openclassrooms.realestatemanager.controllers.activities;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    public static final int ACTIVITY_MAIN_SOURCE = 0;
    public static final int ACTIVITY_EDITION_SOURCE = 1;
    public static final int ACTIVITY_MAP_SOURCE = 2;

    // - STATIC DATA FOR PICTURE
    public static final String READ_EXTERNAL_STORAGE_PERMS = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final int RC_IMAGE_PERMS = 100;
    public static final String ACCESS_FINE_LOCATION_PERMS = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final int RC_LOCATION_PERMS1 = 150;
    public static final String ACCES_COARSE_LOCATION_PERMS = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final int RC_LOCATION_PERMS2 = 200;


    // --------------------
    // CREATION
    // --------------------

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(this.getActivityLayout());
        ButterKnife.bind(this);
    }

    // --------------------
    // GETTERS
    // --------------------

    protected abstract int getActivityLayout();

}
