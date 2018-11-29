package com.openclassrooms.realestatemanager.controllers.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.realestatemanager.R;

public class DetailsFragment extends BaseFragment {


    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance() { return new DetailsFragment(); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        return view;

    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_details;
    }

}
