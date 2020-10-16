package com.example.moviesapp.activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.moviesapp.Fragments.MoviesFragment;
import com.example.moviesapp.R;
import com.example.moviesapp.adapter.AnimationMoviesAdapter;
import com.example.moviesapp.adapter.ComedyMoviesAdapter;
import com.example.moviesapp.basese.Movie;

import java.util.ArrayList;
import java.util.List;

public class ComedyMoviesActivity extends AppCompatActivity implements ComedyMoviesAdapter.OnItemClickListener {

    RecyclerView comedyRecycler;
    ComedyMoviesAdapter adapter;
    List<Movie> mMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comedy_movies);
        mMovies = MoviesFragment.comedyMovieList;
        comedyRecycler = findViewById(R.id.recycler_view_all_comedy);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        comedyRecycler.setLayoutManager(mLayoutManager);
        adapter = new ComedyMoviesAdapter(mMovies);
        adapter.setOnItemClickListener(this);
        comedyRecycler.setAdapter(adapter);
    }
//back

    public void OnClickBackFromComedyMovies(View view) {
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