package com.openclassrooms.realestatemanager.views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.openclassrooms.realestatemanager.controllers.activities.BaseActivity;;
import com.openclassrooms.realestatemanager.controllers.fragments.DetailsFragment;
import com.openclassrooms.realestatemanager.controllers.fragments.ListFragment;
import com.openclassrooms.realestatemanager.controllers.fragments.MapFragment;

public class PageAdapter extends FragmentPagerAdapter {

    private int source;

    // Default Constructor
    public PageAdapter(FragmentManager mgr, int source) {
        super(mgr);
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

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: //Page number 1
                return "Fil d'actualité";
            case 1: //Page number 2
                return "Profil";
            case 2: //Page number 3
                return "Paramètre";
            default:
                return null;
        }
    }
}