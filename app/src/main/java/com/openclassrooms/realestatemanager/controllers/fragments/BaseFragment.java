package com.openclassrooms.realestatemanager.controllers.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment{

    public final String CURRENCY_1 = "USD";
    public final String CURRENCY_2 = "EUR";
    public final long USER_ID = 1;
    public static final int DETAILS_FRAGMENT_SOURCE = 10;
    // - STATIC DATA FOR PICTURE



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
