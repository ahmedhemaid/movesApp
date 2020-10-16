package com.example.moviesapp.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.moviesapp.Fragments.MoviesFragment;
import com.example.moviesapp.R;
import com.example.moviesapp.adapter.ComedyMoviesAdapter;
import com.example.moviesapp.adapter.RomanticMoviesAdapter;
import com.example.moviesapp.basese.Movie;

import java.util.ArrayList;
import java.util.List;

public class RomanticMoviesActivity extends AppCompatActivity implements RomanticMoviesAdapter.OnItemClickListener {

    RecyclerView romanticRecycler;
    RomanticMoviesAdapter adapter;
    List<Movie> mMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_romantic_movies);
        mMovies = MoviesFragment.romanticMovieList;
        romanticRecycler = findViewById(R.id.recycler_view_all_romantic);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        romanticRecycler.setLayoutManager(mLayoutManager);
        adapter = new RomanticMoviesAdapter(mMovies);
        adapter.setOnItemClickListener(this);
        romanticRecycler.setAdapter(adapter);
    }
//back

    public void OnClickBackFromRomanticMovies(View view) {
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