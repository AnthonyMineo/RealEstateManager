package com.openclassrooms.realestatemanager.controllers.activities;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controllers.fragments.DetailsFragment;
import com.openclassrooms.realestatemanager.controllers.fragments.ListFragment;
import com.openclassrooms.realestatemanager.models.local.immovables.Immo;
import com.openclassrooms.realestatemanager.viewmodels.ImmoViewModel;
import com.openclassrooms.realestatemanager.views.PageAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
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
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private ImmoViewModel immoViewModel;
    private PageAdapter pagerAdapter;
    private ListFragment listFragment;
    private DetailsFragment detailsFragment;
    private static final int SEARCH_RESULT_REQUEST_CODE = 50;

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
        this.configureViewModel();
        this.showFirstFragment();
        this.checkForPermission();
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

    private void checkForPermission(){
        if (!EasyPermissions.hasPermissions(this, ACCESS_FINE_LOCATION_PERMS)) {
            EasyPermissions.requestPermissions(this, getString(R.string.popup_title_permission), RC_LOCATION_PERMS1, ACCESS_FINE_LOCATION_PERMS);
        }
        if (!EasyPermissions.hasPermissions(this, ACCES_COARSE_LOCATION_PERMS)) {
            EasyPermissions.requestPermissions(this, getString(R.string.popup_title_permission), RC_LOCATION_PERMS2, ACCES_COARSE_LOCATION_PERMS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 2 - Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
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
        pagerAdapter = new PageAdapter(getSupportFragmentManager(), ACTIVITY_MAIN_SOURCE, this);
        // - Set Adapter PageAdapter and glue it together
        viewPager.setAdapter(pagerAdapter);
    }

    private void configureViewModel(){
        immoViewModel = ViewModelProviders.of(this, viewModelFactory).get(ImmoViewModel.class);
        immoViewModel.initCurrentUser(USER_ID);
        immoViewModel.getSelectedImmo().observe(this, immo -> updateMenu(immo));
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
        this.editMenu.setVisible(false);
        this.searchMenu = menu.findItem(R.id.toolbar_menu_search);

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
                return true;
            case R.id.toolbar_menu_edit:
                // - start immo edition activity
                Intent intentEdit = new Intent(MainActivity.this, EditionActivity.class);
                intentEdit.putExtra("editionMode", 1);
                startActivity(intentEdit);
                return true;
            case R.id.toolbar_menu_search:
                Intent intentSearch = new Intent(MainActivity.this, SearchActivity.class);
                startActivityForResult(intentSearch, SEARCH_RESULT_REQUEST_CODE);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SEARCH_RESULT_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getExtras() != null) {

            Bundle result = data.getExtras();

            int minPrice = result.getInt("minPrice");
            int maxPrice = result.getInt("maxPrice");
            int minSurface = result.getInt("minSurface");
            int maxSurface = result.getInt("maxSurface");
            String city = result.getString("city");
            int minPhotoNumber = result.getInt("minPhotoNumber");
            ArrayList<String> poi = result.getStringArrayList("poi");
            int enterDate =  result.getInt("enterDate");
            int sellingDate = result.getInt("sellingDate");

            this.listFragment = (ListFragment) this.pagerAdapter.getFragment(0);
            this.listFragment.getSearchImmos(minPrice, maxPrice, minSurface, maxSurface, city, minPhotoNumber, poi, enterDate, sellingDate);
        }
    }

    // - Handle Navigation Item Click in the Navigation Drawer
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.menu_drawer_map :
                Intent intentMap = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intentMap);
                // - Show google maps activity center to the current user
                break;
            case R.id.menu_drawer_settings:
                // - Show the settings of the current user
                break;
            case R.id.menu_drawer_log_out:
                // - Run off the activity
                this.finish();
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

    private void updateMenu(Immo immo){
        if(this.editMenu != null){
            if(immo != null){
                this.editMenu.setVisible(true);
            } else {
                this.editMenu.setVisible(false);
            }
        }
    }

    // Implement ListFragment interface
    @Override
    public void onItemSelected() {
        viewPager.setCurrentItem(1);
    }

}
