package com.openclassrooms.realestatemanager.controllers.activities;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controllers.fragments.DetailsFragment;
import com.openclassrooms.realestatemanager.controllers.fragments.MapFragment;
import com.openclassrooms.realestatemanager.viewmodels.ImmoViewModel;
import com.openclassrooms.realestatemanager.views.PageAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;


public class MapActivity extends BaseActivity implements HasSupportFragmentInjector, MapFragment.OnItemSelectedListener {

    // FOR DESIGN
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    // FOR DATA
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private ImmoViewModel immoViewModel;
    private PageAdapter pagerAdapter;
    private MapFragment mapFragment;
    private DetailsFragment detailsFragment;
    private ViewPager viewPager;

    // FOR INJECTION
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.configureDagger();

        this.configureToolBar();
        this.configureViewPagerOrFragment();
        this.configureViewModel();
        this.showFirstFragment();
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_map;
    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    private void configureDagger(){
        AndroidInjection.inject(this);
    }

    // - Configure Toolbar
    private void configureToolBar() {
        toolbar.setTitle(getResources().getString(R.string.activity_map_title));
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    // - Configure ViewPager
    private void configureViewPagerOrFragment() {
        if(getResources().getBoolean(R.bool.isTablet)) {
            if (mapFragment == null && findViewById(R.id.frame_layout_map) != null) {
                mapFragment = MapFragment.newInstance();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.frame_layout_map, mapFragment)
                        .commit();
            }
            if (detailsFragment == null && findViewById(R.id.frame_layout_details) != null) {
                detailsFragment = DetailsFragment.newInstance();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.frame_layout_details, detailsFragment)
                        .commit();
            }
        } else {
            viewPager = findViewById(R.id.activity_map_view_pager);
            pagerAdapter = new PageAdapter(getSupportFragmentManager(), ACTIVITY_MAP_SOURCE, this);
            // - Set Adapter PageAdapter and glue it together
            viewPager.setAdapter(pagerAdapter);
        }
    }

    // - Show first fragment
    private void showFirstFragment(){
        if(!getResources().getBoolean(R.bool.isTablet)) {
            Fragment visibleFragment = getSupportFragmentManager().findFragmentById(R.id.activity_map_view_pager);
            if (visibleFragment == null) {
                // - Show ListFragment
                if (this.mapFragment == null) {
                    this.mapFragment = mapFragment.newInstance();
                }
                viewPager.setCurrentItem(0);
            }
        }
    }

    private void configureViewModel(){
        immoViewModel = ViewModelProviders.of(this, viewModelFactory).get(ImmoViewModel.class);
        immoViewModel.setSelectedImmo(null);
    }

    // Listener init
    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof MapFragment) {
            MapFragment mf = (MapFragment) fragment;
            mf.setOnItemSelectedListener(this);
        }
    }

    // Implement DetFragment interface
    @Override
    public void onItemSelected() {
        viewPager.setCurrentItem(1);
    }
}
