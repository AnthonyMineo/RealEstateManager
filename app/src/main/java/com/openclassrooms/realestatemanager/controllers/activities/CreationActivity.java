package com.openclassrooms.realestatemanager.controllers.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.openclassrooms.realestatemanager.R;

import butterknife.BindView;

public class CreationActivity extends BaseActivity {

    // FOR DESIGN
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.configureToolBar();
    }

    // --------------------
    // ACTIONS
    // --------------------

    // - Configure Toolbar
    private void configureToolBar() {
        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
    }


    // --------------------
    // GETTERS
    // --------------------

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_creation;
    }
}
