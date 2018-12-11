package com.openclassrooms.realestatemanager.controllers.fragments;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.openclassrooms.realestatemanager.R;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment{

    public final String CURRENCY_1 = "USD";
    public final String CURRENCY_2 = "EUR";
    public final long USER_ID = 1;
    // - STATIC DATA FOR PICTURE
    public static final String PERMS = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final int RC_IMAGE_PERMS = 100;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(this.getFragmentLayout(), container, false);
        ButterKnife.bind(this, view); //Configure Butterknife
        return view;
    }

    // --------------------
    // GETTERS
    // --------------------

    public abstract int getFragmentLayout();

}
