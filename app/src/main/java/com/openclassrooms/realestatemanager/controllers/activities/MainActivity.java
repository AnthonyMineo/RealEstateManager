package com.openclassrooms.realestatemanager.controllers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
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

import butterknife.BindView;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends BaseActivity implements HasSupportFragmentInjector, NavigationView.OnNavigationItemSelectedListener, ListFragment.OnItemSelectedListener {

    // FOR DESIGN
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.activity_main_drawer_layout) DrawerLayout drawerLayout;
    @BindView(R.id.activity_main_nav_view) NavigationView navigationView;
    @BindView(R.id.activity_main_view_pager) ViewPager viewPager;
    private MenuItem addMenu;
    private MenuItem editMenu;
    private MenuItem searchMenu;

    // FOR DATA
    private PageAdapter pagerAdapter;
    private ListFragment listFragment;
    private DetailsFragment detailsFragment;
    private SearchView searchView;

    // FOR INJECTION
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init Dagger
        this.configureDagger();

        // Actions
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

    // - Configure Toolbar
    private void configureToolBar() {
        toolbar.setTitle(getResources().getString(R.string.activity_main_title));
        setSupportActionBar(toolbar);
    }

    // - Configure Drawer Layout
    private void configureDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // - Configure NavigationView
    private void configureNavigationView() {
        navigationView.setNavigationItemSelectedListener(this);
    }

    // - Configure ViewPager
    private void configureViewPager() {
        pagerAdapter = new PageAdapter(getSupportFragmentManager());
        // - Set Adapter PageAdapter and glue it together
        viewPager.setAdapter(pagerAdapter);
    }

    // Listener init
    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof ListFragment) {
            ListFragment lf = (ListFragment) fragment;
            lf.setOnItemSelectedListener(this);
        }
    }

    // - Show first fragment
    private void showFirstFragment(){
        Fragment visibleFragment = getSupportFragmentManager().findFragmentById(R.id.activity_main_view_pager);
        if (visibleFragment == null){
            // - Show ListFragment
            if(this.listFragment == null){
                this.listFragment = ListFragment.newInstance();
            }
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
                Intent intentAdd = new Intent(MainActivity.this, EditionActivity.class);
                intentAdd.putExtra("editionMode", 0);
                startActivity(intentAdd);
                viewPager.setCurrentItem(0);
                return true;
            case R.id.toolbar_menu_edit:
                // - start immo edition activity
                Intent intentEdit = new Intent(MainActivity.this, EditionActivity.class);
                intentEdit.putExtra("editionMode", 1);
                startActivity(intentEdit);
                viewPager.setCurrentItem(0);
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

    // Implement ListFragment interface
    @Override
    public void onItemSelected() {
        viewPager.setCurrentItem(1);
    }
}
