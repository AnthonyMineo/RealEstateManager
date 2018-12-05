package com.openclassrooms.realestatemanager.controllers.fragments;


import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controllers.activities.BaseActivity;
import com.openclassrooms.realestatemanager.controllers.activities.MainActivity;
import com.openclassrooms.realestatemanager.models.local.immovables.Immo;
import com.openclassrooms.realestatemanager.models.remote.CurrencyExchangeRate;
import com.openclassrooms.realestatemanager.utils.ItemClickSupport;
import com.openclassrooms.realestatemanager.viewmodels.CurrencyExchangeRateViewModel;
import com.openclassrooms.realestatemanager.viewmodels.ImmoViewModel;
import com.openclassrooms.realestatemanager.views.ImmoAdapter;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import dagger.android.support.AndroidSupportInjection;


public class ListFragment extends BaseFragment {

    // --- DESIGN ---
    @BindView(R.id.fragment_list_recycler_view) RecyclerView recyclerView;

    // --- DATA ---
    public OnItemSelectedListener callback;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private CurrencyExchangeRateViewModel cerViewModel;
    private ImmoViewModel immoViewModel;
    private ImmoAdapter immoAdapter;

    public void setOnItemSelectedListener(MainActivity activity){
        callback =  activity;
    }

    public interface OnItemSelectedListener{
        void onItemSelected();
    }

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
        this.getAllImmos();

        return view;
    }

    @Override
    public int getFragmentLayout() { return R.layout.fragment_list; }

    private void configureDagger(){
        AndroidSupportInjection.inject(this);
    }


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
                    //v.setBackgroundColor(getContext().getResources().getColor(R.color.colorAccent));
                    callback.onItemSelected();
                    Log.i("ItemClickSupport", "You click on : " + String.valueOf(immoAdapter.getImmo(position).getId()));
                });
    }

    private void configureViewModel(){
        cerViewModel = ViewModelProviders.of(this, viewModelFactory).get(CurrencyExchangeRateViewModel.class);
        cerViewModel.initCER(CURRENCY_1, CURRENCY_2);

        immoViewModel = ViewModelProviders.of(this, viewModelFactory).get(ImmoViewModel.class);
        immoViewModel.initCurrentUser(USER_ID);
    }

    private void getCER(){
        cerViewModel.getCER().observe(this, cer -> updateUI(cer));
    }

    private void getAllImmos(){
        immoViewModel.getAllImmos().observe(this, immos -> updateListImmo(immos));
    }

    private void updateListImmo(List<Immo> immos){
        Log.i("ListFragment", "immo update");
        this.immoAdapter.updateData(immos);
    }

    private void updateUI(@Nullable CurrencyExchangeRate cer){
        Log.i("ListFragment", "CER change");
    }

}
