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

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controllers.activities.MainActivity;
import com.openclassrooms.realestatemanager.models.local.immovables.Immo;
import com.openclassrooms.realestatemanager.models.remote.CurrencyExchangeRate;
import com.openclassrooms.realestatemanager.utils.ItemClickSupport;
import com.openclassrooms.realestatemanager.viewmodels.CurrencyExchangeRateViewModel;
import com.openclassrooms.realestatemanager.viewmodels.ImmoViewModel;
import com.openclassrooms.realestatemanager.views.ImmoAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import dagger.android.support.AndroidSupportInjection;


public class ListFragment extends BaseFragment {

    // --- DESIGN ---
    @BindView(R.id.fragment_list_recycler_view)
    RecyclerView recyclerView;

    // --- DATA ---
    public OnItemSelectedListener callback;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private CurrencyExchangeRateViewModel cerViewModel;
    private ImmoViewModel immoViewModel;
    private ImmoAdapter immoAdapter;

    public void setOnItemSelectedListener(MainActivity activity) {
        callback = activity;
    }

    public interface OnItemSelectedListener {
        void onItemSelected();
    }

    public ListFragment() {
        // Required empty public constructor
    }

    public static ListFragment newInstance() {
        return new ListFragment();
    }

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
    public int getFragmentLayout() {
        return R.layout.fragment_list;
    }

    private void configureDagger() {
        AndroidSupportInjection.inject(this);
    }


    // --------------------
    // ACTIONS
    // --------------------

    // - Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureRecyclerView() {
        // - Create adapter passing the list of Restaurants
        this.immoAdapter = new ImmoAdapter();
        // - Attach the adapter to the recyclerview to populate items
        this.recyclerView.setAdapter(this.immoAdapter);
        // - Set layout manager to position the items
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ItemClickSupport.addTo(recyclerView, R.layout.immo_list_recycle_item)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    //v.setBackgroundColor(getContext().getResources().getColor(R.color.colorAccent));
                    immoViewModel.setSelectedImmo(immoAdapter.getImmo(position));
                    if (!getResources().getBoolean(R.bool.isTablet)) {
                        callback.onItemSelected();
                    }
                });
    }

    private void configureViewModel() {
        cerViewModel = ViewModelProviders.of(this, viewModelFactory).get(CurrencyExchangeRateViewModel.class);
        cerViewModel.initCER(CURRENCY_1, CURRENCY_2);
        immoViewModel = ViewModelProviders.of(this, viewModelFactory).get(ImmoViewModel.class);
    }

    private void getCER() {
        cerViewModel.getCER().observe(this, cer -> updateUI(cer));
    }

    private void getAllImmos() {
        immoViewModel.getAllImmos().observe(this, immos -> updateListImmo(immos));
    }

    public void getSearchImmos(int minPrice, int maxPrice, int minSurface, int maxSurface, String city, int minPhotoNumber, ArrayList<String> poi, int enterDate, int sellingDate) {
        Log.i("LIST FRAGMENT", "GET SEARCH IMMOS !!!");
        immoViewModel.getSearchImmos(minPrice, maxPrice, minSurface, maxSurface, enterDate, sellingDate)
                .observe(this, immos -> updateListImmoBySearch(immos, city, minPhotoNumber, poi));
    }

    private void updateListImmo(List<Immo> immos) {
        Log.i("ListFragment", "immo update");
        this.immoAdapter.updateData(immos);
    }

    private void updateListImmoBySearch(List<Immo> immos, String city, int minPhotoNumber, ArrayList<String> poi) {
        Boolean similarPOI = true;
        List<Immo> tempList = new ArrayList<>();
        for (Immo immo : immos) {

            // if city is same and gallery is not init or superior
            if (immo.getVicinity().getCity().equals(city) && immo.getGallery().size() >= minPhotoNumber) {
                for (String p : poi) {
                    if (immo.getPointsOfInterest().contains(p)) {
                        similarPOI = true;
                    } else {
                        similarPOI = false;
                    }
                }
                if (similarPOI) {
                    tempList.add(immo);
                }
            } else if (city.equals("") && immo.getGallery().size() >= minPhotoNumber) {
                // if city is not init and gallery is not init or superior
                for (String p : poi) {
                    if (immo.getPointsOfInterest().contains(p)) {
                        similarPOI = true;
                    } else {
                        similarPOI = false;
                    }
                }
                if (similarPOI) {
                    tempList.add(immo);
                }
            }
        }
        this.immoAdapter.updateData(tempList);
    }

    private void updateUI(@Nullable CurrencyExchangeRate cer) {
        Log.i("ListFragment", "CER change");
    }

}
