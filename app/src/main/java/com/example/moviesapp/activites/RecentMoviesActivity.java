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
import com.example.moviesapp.adapter.RecentMoviesAdapter;
import com.example.moviesapp.adapter.RecyclerAdapterActors;
import com.example.moviesapp.basese.Movie;

import java.util.ArrayList;
import java.util.List;

public class RecentMoviesActivity extends AppCompatActivity implements RecentMoviesAdapter.OnItemClickListener {
    RecyclerView recentRecycler;
    RecentMoviesAdapter adapter;
    List<Movie> mMovies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_movies);
        mMovies= MoviesFragment.recentMovieList;
        recentRecycler = findViewById(R.id.recycler_view_all_recent);
        RecyclerView.LayoutManager mLayoutManager=new GridLayoutManager(this,2);
        recentRecycler.setLayoutManager(mLayoutManager);
        adapter = new RecentMoviesAdapter(mMovies);
        adapter.setOnItemClickListener(this);
        recentRecycler.setAdapter(adapter);
    }
    //back

    public void OnClickBackFromRecentMovies(View view) {
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
