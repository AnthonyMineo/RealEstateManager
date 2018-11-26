package com.openclassrooms.realestatemanager.controllers.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;


import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controllers.fragments.ListFragment;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    private static int CURRENT_USER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.configureDagger();
        this.showFragment(savedInstanceState);

    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    private void showFragment(Bundle savedInstanceState){
        if (savedInstanceState == null) {
            ListFragment fragment = new ListFragment();
            Bundle bundle = new Bundle();
            bundle.putString(ListFragment.CURRENCY_1, ListFragment.CURRENCY_2);
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, null)
                    .commit();
        }
    }
    private void configureDagger(){
        AndroidInjection.inject(this);
    }
}
