package com.openclassrooms.realestatemanager.controllers.fragments;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.Agent;
import com.openclassrooms.realestatemanager.models.remote.CurrencyExchangeRate;
import com.openclassrooms.realestatemanager.utils.Utils;
import com.openclassrooms.realestatemanager.viewmodels.CurrencyExchangeRateViewModel;
import com.openclassrooms.realestatemanager.viewmodels.ImmoViewModel;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;


public class ListFragment extends Fragment {

    private TextView textViewMain;
    private TextView textViewQuantity;
    private TextView textViewRate;

    // --- DATA ---
    public static final String CURRENCY_1 = "USD";
    public static final  String CURRENCY_2 = "EUR";

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private CurrencyExchangeRateViewModel cerViewModel;
    private ImmoViewModel immoViewModel;

    public ListFragment() {
        // Required empty public constructor
    }

    public static ListFragment newInstance() { return new ListFragment(); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        this.textViewMain = view.findViewById(R.id.fragment_list_text_view_1);
        this.textViewQuantity = view.findViewById(R.id.fragment_list_text_view_2);
        this.textViewRate = view.findViewById(R.id.fragment_list_text_view_3);
        Log.e("tag", "ListFragnment");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.configureDagger();
        this.configureViewModel();
    }

    private void configureDagger(){
        AndroidSupportInjection.inject(this);
    }

    private void configureViewModel(){
        cerViewModel = ViewModelProviders.of(this, viewModelFactory).get(CurrencyExchangeRateViewModel.class);
        cerViewModel.initCER(CURRENCY_1, CURRENCY_2);
        cerViewModel.getCER().observe(this, cer -> updateUI(cer));

        immoViewModel = ViewModelProviders.of(this, viewModelFactory).get(ImmoViewModel.class);
        immoViewModel.initCurrentUser(1);
        immoViewModel.getCurrentUser().observe(this, agent -> updateUI2(agent));
    }

    private void updateUI(@Nullable CurrencyExchangeRate cer){
        Log.e("TAG", "AAAAAAAAAAAAA");
        if(cer != null){
            Log.e("TAG", "BBBBBBBBBBBB");
            this.textViewRate.setText(cer.getExchangeRate());
        }
    }

    private void updateUI2(@Nullable Agent agent){
        Log.e("TAG", "AAAAAAAAAAAAA");
        if(agent != null){
            Log.e("TAG", "BBBBBBBBBBBB");
            this.textViewQuantity.setText(Utils.getTodayDate());
        }
    }

}
