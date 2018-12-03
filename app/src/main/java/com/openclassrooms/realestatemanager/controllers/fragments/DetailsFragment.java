package com.openclassrooms.realestatemanager.controllers.fragments;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.local.immovables.Immo;
import com.openclassrooms.realestatemanager.utils.ItemClickSupport;
import com.openclassrooms.realestatemanager.viewmodels.CurrencyExchangeRateViewModel;
import com.openclassrooms.realestatemanager.viewmodels.ImmoViewModel;
import com.openclassrooms.realestatemanager.views.PageAdapter;
import com.openclassrooms.realestatemanager.views.PhotoAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import dagger.android.support.AndroidSupportInjection;

public class DetailsFragment extends BaseFragment {

    // --- DESIGN ---
    @BindView(R.id.fragment_detail_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_detail_zoom_image_view)
    ImageView zoomImageView;

    // --- DATA ---
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private ImmoViewModel immoViewModel;
    private PhotoAdapter photoAdapter;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance() { return new DetailsFragment(); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        // Init Dagger
        this.configureDagger();

        // Actions
        this.configureRecyclerView();
        this.configureViewModel();
        this.getImmoById(1);
        return view;
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_details;
    }

    private void configureDagger(){
        AndroidSupportInjection.inject(this);
    }

    // --------------------
    // ACTIONS
    // --------------------

    // - Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureRecyclerView(){
        // - Create adapter passing the list of Restaurants
        this.photoAdapter = new PhotoAdapter(Glide.with(this));
        // - Attach the adapter to the recyclerview to populate items
        this.recyclerView.setAdapter(this.photoAdapter);
        // - Set layout manager to position the items
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        this.recyclerView.setLayoutManager(horizontalLayoutManager);

        ItemClickSupport.addTo(recyclerView, R.layout.photo_list_recycle_item)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    //v.setBackgroundColor(getContext().getResources().getColor(R.color.colorAccent));

                    if(zoomImageView.getVisibility() == View.INVISIBLE){
                        zoomImageView.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.zoom_image_height);
                        zoomImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        zoomImageView.setVisibility(View.VISIBLE);
                    }
                    //zoomImageView.setImageURI();
                    Log.i("ItemClickSupport", "You click on : " + photoAdapter.getPhoto(position));
                });
    }

    private void configureViewModel(){
        immoViewModel = ViewModelProviders.of(this, viewModelFactory).get(ImmoViewModel.class);
        immoViewModel.initCurrentUser(USER_ID);
    }

    private void getImmoById(int immoId){
        this.zoomImageView.setVisibility(View.INVISIBLE);
        immoViewModel.getImmoById(immoId).observe(this, immo -> updateUI(immo));
    }

    private void updateUI(Immo immo){
        this.photoAdapter.updateData(immo.getGallery());
        // need other UI update
    }
}
