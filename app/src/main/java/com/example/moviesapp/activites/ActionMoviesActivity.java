package com.example.moviesapp.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.moviesapp.Fragments.MoviesFragment;
import com.example.moviesapp.R;
import com.example.moviesapp.adapter.ActionMoviesAdapter;
import com.example.moviesapp.adapter.RecentMoviesAdapter;
import com.example.moviesapp.basese.Movie;

import java.util.ArrayList;
import java.util.List;

public class ActionMoviesActivity extends AppCompatActivity implements ActionMoviesAdapter.OnItemClickListener {

    RecyclerView actionRecycler;
    ActionMoviesAdapter adapter;
    List<Movie> mMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_movies);
        mMovies = MoviesFragment.actionMovieList;
        actionRecycler = findViewById(R.id.recycler_view_all_action);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        actionRecycler.setLayoutManager(mLayoutManager);
        adapter = new ActionMoviesAdapter(mMovies);
        adapter.setOnItemClickListener(this);
        actionRecycler.setAdapter(adapter);
    }
    //back

    public void OnClickBackFromActionMovies(View view) {
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