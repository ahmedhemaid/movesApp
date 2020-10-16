package com.example.moviesapp.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.moviesapp.Fragments.MoviesFragment;
import com.example.moviesapp.R;
import com.example.moviesapp.adapter.ComedyMoviesAdapter;
import com.example.moviesapp.adapter.DramaMoviesAdapter;
import com.example.moviesapp.basese.Movie;

import java.util.ArrayList;
import java.util.List;

public class DramaMoviesActivity extends AppCompatActivity implements DramaMoviesAdapter.OnItemClickListener {

    RecyclerView dramaRecycler;
    DramaMoviesAdapter adapter;
    List<Movie> mMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drama_movies);
        mMovies = MoviesFragment.dramaMovieList;
        dramaRecycler = findViewById(R.id.recycler_view_all_drama);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        dramaRecycler.setLayoutManager(mLayoutManager);
        adapter = new DramaMoviesAdapter(mMovies);
        adapter.setOnItemClickListener(this);
        dramaRecycler.setAdapter(adapter);
    }
//back

    public void OnClickBackFromDramaMovies(View view) {
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