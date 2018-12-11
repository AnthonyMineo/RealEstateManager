package com.openclassrooms.realestatemanager.controllers.activities;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    public final long USER_ID = 1;

    // - STATIC DATA FOR PICTURE
    public static final String PERMS = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final int RC_IMAGE_PERMS = 100;

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
