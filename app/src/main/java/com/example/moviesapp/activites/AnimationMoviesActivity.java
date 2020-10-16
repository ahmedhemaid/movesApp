package com.example.moviesapp.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.moviesapp.Fragments.MoviesFragment;
import com.example.moviesapp.R;
import com.example.moviesapp.adapter.ActionMoviesAdapter;
import com.example.moviesapp.adapter.AnimationMoviesAdapter;
import com.example.moviesapp.basese.Movie;

import java.util.ArrayList;
import java.util.List;

public class AnimationMoviesActivity extends AppCompatActivity implements  AnimationMoviesAdapter.OnItemClickListener {

        RecyclerView animationRecycler;
        AnimationMoviesAdapter adapter;
        List<Movie> mMovies;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_movies);
        mMovies = MoviesFragment.animationMovieList;
        animationRecycler = findViewById(R.id.recycler_view_all_animation);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        animationRecycler.setLayoutManager(mLayoutManager);
        adapter = new AnimationMoviesAdapter(mMovies);
        adapter.setOnItemClickListener(this);
        animationRecycler.setAdapter(adapter);
        }
//back

public void OnClickBackFromAnimationMovies(View view) {
        finish();
        }

private void filter(String text) {
        ArrayList<Movie> filteredList = new ArrayList<>();

        for (Movie item : mMovies) {
        if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
        filteredList.add(item);
        }
        }
        adapter.filterList(filteredList);
        }

@Override
public void onItemClick(int position) {

        }

        }