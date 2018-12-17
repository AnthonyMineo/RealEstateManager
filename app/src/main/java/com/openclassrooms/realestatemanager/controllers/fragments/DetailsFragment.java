package com.openclassrooms.realestatemanager.controllers.fragments;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.BuildConfig;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.local.immovables.Immo;
import com.openclassrooms.realestatemanager.utils.ItemClickSupport;
import com.openclassrooms.realestatemanager.utils.LocalStorageHelper;
import com.openclassrooms.realestatemanager.utils.Utils;
import com.openclassrooms.realestatemanager.viewmodels.ImmoViewModel;
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
    @BindView(R.id.sold_image_view)
    ImageView soldImageView;
    @BindView(R.id.fragment_detail_description_text)
    TextView descriptionTextView;
    @BindView(R.id.detail_fragment_surface_text_view)
    TextView surfaceTextView;
    @BindView(R.id.detail_fragment_rooms_text_view)
    TextView roomsTextView;
    @BindView(R.id.detail_fragment_bath_text_view)
    TextView bathTextView;
    @BindView(R.id.detail_fragment_bed_text_view)
    TextView bedTextView;
    @BindView(R.id.detail_fragment_address_text_view)
    TextView addressTextView;
    @BindView(R.id.detail_fragment_cpt_address_text_view)
    TextView cptAddressTextView;
    @BindView(R.id.detail_fragment_city_text_view)
    TextView cityTextView;
    @BindView(R.id.detail_fragment_postal_code_text_view)
    TextView postalCodeTextView;
    @BindView(R.id.detail_fragment_country_text_view)
    TextView countryTextView;
    @BindView(R.id.static_map_image_view)
    ImageView staticMapImageView;

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
        this.photoAdapter = new PhotoAdapter(DETAILS_FRAGMENT_SOURCE);
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
                        zoomImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        zoomImageView.setVisibility(View.VISIBLE);
                    }
                    zoomImageView.setImageURI(Uri.fromFile(LocalStorageHelper.createOrGetFile(getContext().getFilesDir(), photoAdapter.getPhoto(position).getFileName())));
                    Log.i("ItemClickSupport", "You click on : " + photoAdapter.getPhoto(position));
                });
    }

    private void configureViewModel(){
        immoViewModel = ViewModelProviders.of(this, viewModelFactory).get(ImmoViewModel.class);
        this.zoomImageView.setVisibility(View.INVISIBLE);
        immoViewModel.getSelectedImmo().observe(this, immo -> updateUI(immo));
    }

    private void updateUI(Immo immo){
        if(immo != null){

            this.photoAdapter.updateData(immo.getGallery());
            if(zoomImageView.getVisibility() == View.VISIBLE){
                zoomImageView.getLayoutParams().height = 0;
                zoomImageView.setVisibility(View.INVISIBLE);
            }

            // first layout informations
            if(immo.getSurface() != -1)
                this.surfaceTextView.setText(String.valueOf(immo.getSurface()));
            else
                this.surfaceTextView.setText(R.string.no_text_yet);
            if(immo.getPieceNumber() != -1)
                this.roomsTextView.setText(String.valueOf(immo.getPieceNumber()));
            else
                this.roomsTextView.setText(R.string.no_text_yet);
            if(immo.getBathNumber() != -1)
                this.bathTextView.setText(String.valueOf(immo.getBathNumber()));
            else
                this.bathTextView.setText(R.string.no_text_yet);
            if(immo.getBedNumber() != -1)
                this.bedTextView.setText(String.valueOf(immo.getBedNumber()));
            else
                this.bedTextView.setText(R.string.no_text_yet);
            if(!immo.getDescription().equals(""))
                this.descriptionTextView.setText(immo.getDescription());
            else
                this.descriptionTextView.setText(R.string.no_text_yet);

            // second layout informations - location
            this.addressTextView.setText(immo.getVicinity().getAddress());
            this.cptAddressTextView.setText(immo.getVicinity().getCptAddress());
            this.cityTextView.setText(immo.getVicinity().getCity());
            this.postalCodeTextView.setText(immo.getVicinity().getPostalCode());
            this.countryTextView.setText(immo.getVicinity().getCountry());

            if(immo.getSellingDate() != -1){
                soldImageView.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.sold_image_height);
            } else {
                soldImageView.getLayoutParams().height = 0;
            }

            try{
                String addressFormatted = Utils.convertVicinityToMapsStaticAPIFormat(immo.getVicinity());
                String url = "https://maps.googleapis.com/maps/api/staticmap?size=500x500&markers=" + addressFormatted + "&key=" + BuildConfig.Maps_ApiKey;
                Glide.with(this).load(url).into(staticMapImageView);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
