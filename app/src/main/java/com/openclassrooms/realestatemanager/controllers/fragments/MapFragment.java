package com.openclassrooms.realestatemanager.controllers.fragments;


import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controllers.activities.BaseActivity;
import com.openclassrooms.realestatemanager.controllers.activities.MapActivity;
import com.openclassrooms.realestatemanager.models.local.immovables.Immo;
import com.openclassrooms.realestatemanager.utils.Utils;

import com.openclassrooms.realestatemanager.viewmodels.ImmoViewModel;

import java.util.List;


import javax.inject.Inject;

import butterknife.BindView;
import dagger.android.support.AndroidSupportInjection;
import pub.devrel.easypermissions.EasyPermissions;

public class MapFragment extends BaseFragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener  {

    // FOR DESIGN
    @BindView(R.id.map)
    MapView mMapView;

    // FOR DATA
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private ImmoViewModel immoViewModel;
    public OnItemSelectedListener callback;
    private static final String TAG = "Map_Fragment"; // - Map Fragment ID for log
    // Maps Related
    private FusedLocationProviderClient mFusedLocationClient;
    private GoogleMap mMap;
    private MarkerOptions markerOptions;

    public void setOnItemSelectedListener(MapActivity activity){
        callback =  activity;
    }

    public interface OnItemSelectedListener{
        void onItemSelected();
    }

    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance() { return new MapFragment(); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        this.configureDagger();

        this.configureViewModel();

        // Maps related
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        return view;
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.fragment_map;
    }

    private void configureDagger(){
        AndroidSupportInjection.inject(this);
    }

    private void configureViewModel(){
        immoViewModel = ViewModelProviders.of(this, viewModelFactory).get(ImmoViewModel.class);
        immoViewModel.initCurrentUser(USER_ID);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;

        this.mMap.setOnMarkerClickListener(this);

        // - Check for permission
        if (EasyPermissions.hasPermissions(getContext(), BaseActivity.ACCESS_FINE_LOCATION_PERMS)) {
            this.mMap.setMyLocationEnabled(true);

            this.mFusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                // Got last known location. In some rare situations this can be null.
                double lat = location.getLatitude();
                double lng = location.getLongitude();
                LatLng currentPosition = new LatLng(lat, lng);
                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(currentPosition,12);
                this.mMap.animateCamera(update);
            });
        }

        immoViewModel.getAllImmos().observe(this, immos -> updateUI(immos));
    }

    public void updateUI(List<Immo> immos){
        for(Immo immo : immos){
            LatLng immoLocation = null;
            String addressForLocation = immo.getVicinity().getAddress() + ", " + immo.getVicinity().getCity();
            try{
                immoLocation = Utils.getLocationFromAddress(getContext(), addressForLocation);
            } finally {
                if(immoLocation != null){
                    markerOptions = new MarkerOptions();
                    // Position of Marker on Map
                    markerOptions.position(immoLocation);
                    markerOptions.snippet(String.valueOf(immo.getId()));
                    Log.e(TAG, String.valueOf(immo.getId()));
                    // Adding Marker to the Camera.
                    Marker m = mMap.addMarker(markerOptions);
                }
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        immoViewModel.getImmoById(Long.parseLong(marker.getSnippet())).observe(this, immo -> updateSelectedImmo(immo));
        return false;
    }

    private void updateSelectedImmo(Immo selectedImmo){
        immoViewModel.setSelectedImmo(selectedImmo);
        if(!getResources().getBoolean(R.bool.isTablet)) {
            callback.onItemSelected();
        }
    }

    // --------------------
    // LIFE CYCLE
    // --------------------

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMapView != null)
            mMapView.onSaveInstanceState(outState);
    }

    // Docs suggest to override them
    @Override
    public void onResume() {
        super.onResume();
        if (mMapView != null) {
            mMapView.onResume();
        }
        Log.i(TAG, "onResume");
    }

    @Override
    public void onPause() {
        if (mMapView != null) {
            mMapView.onPause();
        }
        Log.i(TAG, "onPause");
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (mMapView != null) {
            try {
                mMapView.onDestroy();
            } catch (NullPointerException e) {
                Log.i("Error","Error while attempting MapView.onDestroy(), ignoring exception", e);
            }
        }
        Log.i(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mMapView != null) {
            mMapView.onLowMemory();
        }
        Log.i(TAG, "onLowMemory");
    }

}
