package com.openclassrooms.realestatemanager.controllers.activities;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controllers.fragments.DetailsFragment;
import com.openclassrooms.realestatemanager.controllers.fragments.ListFragment;
import com.openclassrooms.realestatemanager.views.PageAdapter;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends BaseActivity implements HasSupportFragmentInjector, NavigationView.OnNavigationItemSelectedListener {

    // FOR DESIGN
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private PageAdapter pagerAdapter;
    private ViewPager viewPager;
    private ListFragment listFragment;
    private DetailsFragment detailsFragment;
    private MenuItem addMenu;
    private MenuItem editMenu;
    private MenuItem searchMenu;
    private SearchView searchView;

    // FOR DATA

    // FOR INJECTION
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.configureDagger();
        this.attachView();

        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();
        this.configureViewPager();
        this.showFirstFragment();

    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    // --------------------
    // GETTERS
    // --------------------

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_main;
    }

    // --------------------
    // ACTIONS
    // --------------------

    private void configureDagger(){
        AndroidInjection.inject(this);
    }

    // find by view
    private void attachView(){

    }

    // - Configure Toolbar
    private void configureToolBar() {
        this.toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);
    }

    // - Configure Drawer Layout
    private void configureDrawerLayout() {
        this.drawerLayout = findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // - Configure NavigationView
    private void configureNavigationView() {
        this.navigationView = findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    // - Configure ViewPager
    private void configureViewPager() {
        // - Get ViewPager from layout
        viewPager = findViewById(R.id.activity_main_view_pager);
        pagerAdapter = new PageAdapter(getSupportFragmentManager());
        // - Set Adapter PageAdapter and glue it together
        viewPager.setAdapter(pagerAdapter);

    }

    // - Show first fragment
    private void showFirstFragment(){
        Fragment visibleFragment = getSupportFragmentManager().findFragmentById(R.id.activity_main_view_pager);
        if (visibleFragment == null){
            // - Show ListFragment
            if(this.listFragment == null)
                this.listFragment = ListFragment.newInstance();
            viewPager.setCurrentItem(0);
        }
    }

    // --------------------
    // MENUS
    // --------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // - Inflate the menu and add it to the Toolbar
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_tools, menu);
        this.addMenu = menu.findItem(R.id.toolbar_menu_add);
        this.editMenu = menu.findItem(R.id.toolbar_menu_edit);
        this.searchMenu = menu.findItem(R.id.toolbar_menu_search);
        this.searchView = (SearchView) searchMenu.getActionView();
        this.searchView.setQueryHint(getResources().getString(R.string.search_query_hint));
        this.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // doMySearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    // - Handle actions on menu items
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_menu_add:
                // - start immo creation activity
                return true;
            case R.id.toolbar_menu_edit:
                // - start immo edition activity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // - Handle Navigation Item Click in the Navigation Drawer
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.menu_drawer_map :
                // - Show google maps activity center to the current user
                break;
            case R.id.menu_drawer_settings:
                // - Show the settings of the current user
                break;
            case R.id.menu_drawer_log_out:
                // - Run off the activity
                break;
            default:
                break;
        }
        this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // - Handle back click to close menu
    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
