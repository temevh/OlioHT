package com.example.httesti;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;



import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static MainActivity instance;

    public static MainActivity getInstance() {
        return instance;
    }


    private DrawerLayout Drawer;
    private Toolbar toolbar;
    private NavigationView nav;
    private ActionBarDrawerToggle drawerToggle;
    private Menu menu;



    placesClass lp = placesClass.getInstance();
    Fragment mainFragment = new MainFragment();
    Fragment accFragment = new accFragment();
    Fragment placeFrag;
    FragmentManager fragmentManager;

    private User currentUser = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Start on the "Home" screen
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, mainFragment).commit();
        setTitle("Home");


        // Setup the sliding drawer
        Drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();

        // Setup toggle to display hamburger icon with nice animation
        drawerToggle.setDrawerIndicatorEnabled(true);

        // Setup the NavView and the menu with it
        nav = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nav);
        menu = nav.getMenu();

        Drawer.addDrawerListener(drawerToggle);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Drawer.openDrawer(GravityCompat.START);
                return true;
        }
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment;
        // switch the fragment based on which menu item is clicked/selected
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragment = mainFragment;
                break;

            case R.id.nav_register:
                fragment = new RegisterFragment();
                break;

            case R.id.nav_profile:
                fragment = accFragment;
                break;
            case R.id.nav_logout:
                fragment = mainFragment;
                currentUser = null;
                break;
            case R.id.nav_favorites:
                fragment = new FavouritesFragment();
                break;
            case R.id.nav_weather:
                fragment = new WeatherFragment();
                break;
            default:
                fragment = mainFragment;
        }
        //Load the selected fragment
        loadFragment(fragment, currentUser);
        // Highlight the selected item
        menuItem.setChecked(true);
        // Close the navigation drawer
        Drawer.closeDrawers();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, Drawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }


    // this method is used when a placeItem in the GridLayout is clicked
    public void onItemClicked(View v, String placeName){


        activityPlace placeInfo = lp.selection(placeName);
        // if we are at favourites, select the info from current users favourites
        if(placeInfo == null){
            for(activityPlace ap : currentUser.getFavourites()){
                if(ap.getName().equals(placeName)){
                    placeInfo = ap;
                }
            }
        }
        // set relevant info to the bundle
        Bundle bundle = new Bundle();
        bundle.putSerializable("placeinfo", placeInfo);

        bundle.putSerializable("user", currentUser);
        placeFrag = new PlaceFragment();
        placeFrag.setArguments(bundle);

        // Load the new placeFragment i.e. show all info of the said place
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, placeFrag).commit();

    }


    // This method is used for loading new fragments
    public void loadFragment(Fragment fragment, User currentUser){
        // keep track of the current user
        this.currentUser = currentUser;

        // show different menu items based on if there's a user logged in or not
        if(this.currentUser != null){
            menu.findItem(R.id.nav_logout).setVisible(true);
            menu.findItem(R.id.nav_register).setVisible(false);
            menu.findItem(R.id.nav_profile).setVisible(true);
            menu.findItem(R.id.nav_favorites).setVisible(true);
        }else {
            menu.findItem(R.id.nav_logout).setVisible(false);
            menu.findItem(R.id.nav_register).setVisible(true);
            menu.findItem(R.id.nav_profile).setVisible(false);
            menu.findItem(R.id.nav_favorites).setVisible(false);
        }

        // loads the wanted fragment and passes the currentUser with it
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", currentUser);
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

    }



}

