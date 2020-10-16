package com.example.moviesapp.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.moviesapp.Fragments.MoviesFragment;
import com.example.moviesapp.R;
import com.example.moviesapp.adapter.TopRatedMoviesAdapter;
import com.example.moviesapp.basese.Movie;

import java.util.ArrayList;
import java.util.List;

public class TopRatedMoviesActivity extends AppCompatActivity implements TopRatedMoviesAdapter.OnItemClickListener {
    RecyclerView topRatedRecycler;
    TopRatedMoviesAdapter adapter;
    List<Movie> mMovies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_rated_movies);
        mMovies= MoviesFragment.topRatedMovieList;
        topRatedRecycler = findViewById(R.id.recycler_view_all_top_rated);
        RecyclerView.LayoutManager mLayoutManager=new GridLayoutManager(this,2);
        topRatedRecycler.setLayoutManager(mLayoutManager);
        adapter = new TopRatedMoviesAdapter(mMovies);
        adapter.setOnItemClickListener(this);
        topRatedRecycler.setAdapter(adapter);
    }
    //back
    public void OnClickBackFromTopMovies(View view) {
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
