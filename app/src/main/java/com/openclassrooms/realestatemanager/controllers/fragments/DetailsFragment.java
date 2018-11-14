package com.openclassrooms.realestatemanager.controllers.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.realestatemanager.R;

public class DetailsFragment extends BaseFragment {


    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

}
