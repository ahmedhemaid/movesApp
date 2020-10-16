package com.example.moviesapp.activites;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviesapp.Fragments.ActorsFragment;
import com.example.moviesapp.Fragments.EmptyFragment;
import com.example.moviesapp.Fragments.FavoriteFragment;
import com.example.moviesapp.Fragments.MoviesFragment;
import com.example.moviesapp.Fragments.SearchFragment;
import com.example.moviesapp.R;
import com.example.moviesapp.adapter.FavoriteMoviesAdapter;
import com.example.moviesapp.basese.Actor;
import com.example.moviesapp.basese.Movie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.moviesapp.Fragments.MoviesFragment.actionMovieList;
import static com.example.moviesapp.Fragments.MoviesFragment.animationMovieList;
import static com.example.moviesapp.Fragments.MoviesFragment.comedyMovieList;
import static com.example.moviesapp.Fragments.MoviesFragment.dramaMovieList;
import static com.example.moviesapp.Fragments.MoviesFragment.horrorMovieList;
import static com.example.moviesapp.Fragments.MoviesFragment.romanticMovieList;

public class MainActivity extends AppCompatActivity  {
    static boolean isConnected;
    public static ArrayList<Movie> favoriteArrayList = new ArrayList<>();

    //onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Network connection
        isConnected=false;
        checkNetworkConnectionStatus();
        //shared preferences

        //NavigationBar
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //LoadFragment
        if (isConnected) {
            Fragment fragment = new MoviesFragment();
            loadFragment(fragment);
        }
    }
    //shared preferences
    //favorite movies methods
    // TODO: 2/20/2020 favorite movies
    //favorite actors methods
    // TODO: dont forget to make actor favorite methods

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
                        actionMovieList.clear();
                        horrorMovieList.clear();
                        romanticMovieList.clear();
                        dramaMovieList.clear();
                        animationMovieList.clear();
                        comedyMovieList.clear();
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
                case R.id.search_icon:
                    fragment = new SearchFragment();
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
    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_layout, fragment);
        fragmentTransaction.commit();
    }


/////////////////////////////////////////////////////////////////////////////

}
