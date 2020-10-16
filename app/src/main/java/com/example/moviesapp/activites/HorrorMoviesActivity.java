package com.example.moviesapp.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.moviesapp.Fragments.MoviesFragment;
import com.example.moviesapp.R;
import com.example.moviesapp.adapter.ComedyMoviesAdapter;
import com.example.moviesapp.adapter.HorrorMoviesAdapter;
import com.example.moviesapp.basese.Movie;

import java.util.ArrayList;
import java.util.List;

public class HorrorMoviesActivity extends AppCompatActivity implements HorrorMoviesAdapter.OnItemClickListener {

    RecyclerView horrorRecycler;
    HorrorMoviesAdapter adapter;
    List<Movie> mMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horror_movies);
        mMovies = MoviesFragment.horrorMovieList;
        horrorRecycler = findViewById(R.id.recycler_view_all_horror);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        horrorRecycler.setLayoutManager(mLayoutManager);
        adapter = new HorrorMoviesAdapter(mMovies);
        adapter.setOnItemClickListener(this);
        horrorRecycler.setAdapter(adapter);
    }
//back

    public void OnClickBackFromHorrorMovies(View view) {
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