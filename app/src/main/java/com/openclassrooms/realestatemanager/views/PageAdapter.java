package com.openclassrooms.realestatemanager.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.openclassrooms.realestatemanager.controllers.activities.BaseActivity;;
import com.openclassrooms.realestatemanager.controllers.fragments.DetailsFragment;
import com.openclassrooms.realestatemanager.controllers.fragments.ListFragment;
import com.openclassrooms.realestatemanager.controllers.fragments.MapFragment;

public class PageAdapter extends FragmentStatePagerAdapter {

    // - A SparseArray that will contain our fragment
    private final SparseArray<Fragment> instantiatedFragments = new SparseArray<>();
    private Context context;
    private int source;

    // Default Constructor
    public PageAdapter(FragmentManager mgr, int source, Context context) {
        super(mgr);
        this.context = context;
        this.source = source;
    }

    @Override
    public int getCount() {
        return(2);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: //Page number 1
                if(source == BaseActivity.ACTIVITY_MAIN_SOURCE)
                    return ListFragment.newInstance();
                else if(source == BaseActivity.ACTIVITY_MAP_SOURCE)
                    return MapFragment.newInstance();
            case 1: //Page number 2
                return DetailsFragment.newInstance();
            default:
                return null;
        }
    }


    // - Store the fragment instance to the SparseArray
    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final Fragment fragment = (Fragment) super.instantiateItem(container, position);
        instantiatedFragments.put(position, fragment);
        return fragment;
    }

    // - Suppress a fragment from the SparseArray
    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        instantiatedFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    // - Fragment Getter
    // - It will allow us to perform method's call from the fragment it return directly into our activity
    @Nullable
    public Fragment getFragment(final int position) {
        Log.i("PAGE ADAPTER", "GET FRAGMENT");
        return instantiatedFragments.get(position);

    }
}