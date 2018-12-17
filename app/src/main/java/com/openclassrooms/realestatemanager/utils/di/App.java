package com.openclassrooms.realestatemanager.utils.di;

import android.app.Activity;
import android.app.Application;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class App extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate(){
        super.onCreate();
        this.intDagger();
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector(){
        return dispatchingAndroidInjector;
    }

    // Binding our application instance to our Dagger graph.
    private void intDagger(){
        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this);
    }
}
