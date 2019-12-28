package com.example.moviesapp.activites;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviesapp.Fragments.ActorsFragment;
import com.example.moviesapp.Fragments.EmptyFragment;
import com.example.moviesapp.Fragments.FavoriteFragment;
import com.example.moviesapp.Fragments.MoviesFragment;
import com.example.moviesapp.R;
import com.example.moviesapp.genres.ActionMovies;
import com.example.moviesapp.genres.AnimationMovies;
import com.example.moviesapp.genres.ComedyMovies;
import com.example.moviesapp.genres.DramaMovies;
import com.example.moviesapp.genres.HorrorMovies;
import com.example.moviesapp.genres.RomanticMovies;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
static boolean isConnected;
    private ActionBarDrawerToggle mToggle;
    //onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Network connection
        isConnected=false;
        checkNetworkConnectionStatus();
        //NavigationDrawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //NavigationBar
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //LoadFragment
        if (isConnected) {
            Fragment fragment = new MoviesFragment();
            loadFragment(fragment);
        }
    }
public void checkNetworkConnectionStatus(){
        boolean wifiConnected;
        boolean mobileDataConnected;
    ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activityInfo=connectivityManager.getActiveNetworkInfo();
    if(activityInfo!=null&&activityInfo.isConnected()) {
        wifiConnected = activityInfo.getType() == ConnectivityManager.TYPE_WIFI;
        mobileDataConnected = activityInfo.getType() == ConnectivityManager.TYPE_MOBILE;
        if (wifiConnected) {
            isConnected=true;
        } else if (mobileDataConnected) {
            isConnected=true;
            Toast.makeText(this, "Take care you are working on the MobileData", Toast.LENGTH_LONG).show();
        }
    }
        else {
            isConnected=false;

            new AlertDialog.Builder(this).setIcon(R.drawable.nowifi).setTitle("connection error").setMessage("This app needs an internet connection , please check the wifi connection").setPositiveButton("Go to Favorite", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which){
               loadFragment(new FavoriteFragment());
            }}).show();
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        }
    }
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.action) {
            checkNetworkConnectionStatus();
            if (isConnected) {
                Intent intent = new Intent(this, ActionMovies.class);
                startActivity(intent);
            }
        } else if (id == R.id.drama) {
            checkNetworkConnectionStatus();
            if (isConnected) {
                Intent intent = new Intent(this, DramaMovies.class);
                startActivity(intent);
            }
        } else if (id == R.id.horror) {
            checkNetworkConnectionStatus();
            if (isConnected) {
                Intent intent = new Intent(this, HorrorMovies.class);
                startActivity(intent);
            }

        } else if (id == R.id.romantic) {
            checkNetworkConnectionStatus();
            if (isConnected) {
                Intent intent = new Intent(this, RomanticMovies.class);
                startActivity(intent);
            }
        } else if (id == R.id.comedy) {
            checkNetworkConnectionStatus();
            if (isConnected) {
                Intent intent = new Intent(this, ComedyMovies.class);
                startActivity(intent);
            }
        } else if (id == R.id.animation) {
            checkNetworkConnectionStatus();
            if (isConnected) {
                Intent intent = new Intent(this, AnimationMovies.class);
                startActivity(intent);
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Navigation bar settings
    private TextView mTextMessage;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.movies_icon:
                    checkNetworkConnectionStatus();
                    if (isConnected) {
                        fragment = new MoviesFragment();
                        loadFragment(fragment);
                    }else {
                        loadFragment(new EmptyFragment());
                    }
                    return true;
                case R.id.favorite_icon:
                        fragment = new FavoriteFragment();
                        loadFragment(fragment);
                    return true;
                case R.id.actor_icon:
                    checkNetworkConnectionStatus();
                    if (isConnected){
                    fragment = new ActorsFragment();
                    loadFragment(fragment);
                    }else {
                        loadFragment(new EmptyFragment());
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        getMenuInflater().inflate(R.menu.sort,menu);
        return true;
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

/////////////////////////////////////////////////////////////////////////////

}
