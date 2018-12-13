package com.openclassrooms.realestatemanager.utils.di;
/*
Fragment Builder
 */
import com.openclassrooms.realestatemanager.controllers.fragments.DetailsFragment;
import com.openclassrooms.realestatemanager.controllers.fragments.ListFragment;
import com.openclassrooms.realestatemanager.controllers.fragments.MapFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract ListFragment contributelistFragment();

    @ContributesAndroidInjector
    abstract DetailsFragment contributeDetailFragment();

    @ContributesAndroidInjector
    abstract MapFragment contributeMapFragment();
}
