package com.example.moviesapp.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.moviesapp.Fragments.ActorsFragment;
import com.example.moviesapp.Fragments.MoviesFragment;
import com.example.moviesapp.R;
import com.example.moviesapp.adapter.PopularMoviesAdapter;
import com.example.moviesapp.adapter.RecyclerAdapterActors;
import com.example.moviesapp.basese.Movie;

import java.util.ArrayList;
import java.util.List;

public class PopularMoviesActivity extends AppCompatActivity implements PopularMoviesAdapter.OnItemClickListener {
    RecyclerView popularRecycler;
    PopularMoviesAdapter adapter;
    List<Movie> mMovies;
    EditText searchEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies);
        mMovies= MoviesFragment.popularMovieList;
        popularRecycler = findViewById(R.id.recycler_view_all_popular);
        RecyclerView.LayoutManager mLayoutManager=new GridLayoutManager(this,2);
        popularRecycler.setLayoutManager(mLayoutManager);
        adapter = new PopularMoviesAdapter(mMovies);
        adapter.setOnItemClickListener(this);
        popularRecycler.setAdapter(adapter);
        //search filter

    }
    //back
    public void OnClickBackFromNotebooksToHome(View view) {
        finish();
    }


    @Override
    public void onItemClick(int position) {

    }
}
