package com.openclassrooms.realestatemanager.utils.di;
/*
Activity Builder
 */
import com.openclassrooms.realestatemanager.controllers.activities.EditionActivity;
import com.openclassrooms.realestatemanager.controllers.activities.LogginActivity;
import com.openclassrooms.realestatemanager.controllers.activities.MainActivity;
import com.openclassrooms.realestatemanager.controllers.activities.MapActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract LogginActivity contributeLogginActivity();

    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector
    abstract EditionActivity contributeCreationActivity();

    @ContributesAndroidInjector
    abstract MapActivity contributeMapActivity();
}
