package com.openclassrooms.realestatemanager.utils.di;
/*
Activity Builder
 */
import com.openclassrooms.realestatemanager.controllers.activities.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();
}
