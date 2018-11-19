package com.openclassrooms.realestatemanager.utils.di;
/*
Fragment Builder
 */
import com.openclassrooms.realestatemanager.controllers.fragments.ListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract ListFragment contributelistFragment();
}
