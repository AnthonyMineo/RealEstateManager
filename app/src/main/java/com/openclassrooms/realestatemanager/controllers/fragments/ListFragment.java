package com.openclassrooms.realestatemanager.controllers.fragments;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.local.immovables.Immo;
import com.openclassrooms.realestatemanager.models.remote.CurrencyExchangeRate;
import com.openclassrooms.realestatemanager.utils.ItemClickSupport;
import com.openclassrooms.realestatemanager.viewmodels.CurrencyExchangeRateViewModel;
import com.openclassrooms.realestatemanager.viewmodels.ImmoViewModel;
import com.openclassrooms.realestatemanager.views.ImmoAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import dagger.android.support.AndroidSupportInjection;


public class ListFragment extends BaseFragment {

    // --- DESIGN ---
    @BindView(R.id.fragment_list_recycler_view) RecyclerView recyclerView;

    // --- DATA ---
    public static final String CURRENCY_1 = "USD";
    public static final  String CURRENCY_2 = "EUR";

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private CurrencyExchangeRateViewModel cerViewModel;
    private ImmoViewModel immoViewModel;
    private ImmoAdapter immoAdapter;

    public ListFragment() {
        // Required empty public constructor
    }

    public static ListFragment newInstance() { return new ListFragment(); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        this.configureDagger();

        this.configureRecyclerView();
        this.configureViewModel();
        this.getCER();
        this.getImmosByAgent(1);

        return view;
    }

    @Override
    public int getFragmentLayout() { return R.layout.fragment_list; }

    // --------------------
    // ACTIONS
    // --------------------

    // - Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureRecyclerView(){
        // - Create adapter passing the list of Restaurants
        this.immoAdapter = new ImmoAdapter(Glide.with(this));
        // - Attach the adapter to the recyclerview to populate items
        this.recyclerView.setAdapter(this.immoAdapter);
        // - Set layout manager to position the items
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ItemClickSupport.addTo(recyclerView, R.layout.immo_list_recycle_item)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    Log.i("ItemClickSupport", "You click on : " + immoAdapter.getImmo(position));
                });
    }

    private void configureDagger(){
        AndroidSupportInjection.inject(this);
    }

    private void configureViewModel(){
        cerViewModel = ViewModelProviders.of(this, viewModelFactory).get(CurrencyExchangeRateViewModel.class);
        cerViewModel.initCER(CURRENCY_1, CURRENCY_2);

        immoViewModel = ViewModelProviders.of(this, viewModelFactory).get(ImmoViewModel.class);
        immoViewModel.initCurrentUser(1);
    }

    private void getCER(){
        cerViewModel.getCER().observe(this, cer -> updateUI(cer));
    }

    private void getImmosByAgent(int i){
        immoViewModel.getImmosByAgent(i).observe(this, immo -> updateListImmo(immo));
    }

    private void updateListImmo(List<Immo> immo){
        Log.e("TAG", "immo update");
        this.immoAdapter.updateData(immo);
    }

    private void updateUI(@Nullable CurrencyExchangeRate cer){
        Log.e("TAG", "CER change");
    }

}
