package com.openclassrooms.realestatemanager.controllers.activities;

import android.os.Bundle;

import com.openclassrooms.realestatemanager.R;

public class SecondActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }


    // --------------------
    // GETTERS
    // --------------------

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_second;
    }
}
