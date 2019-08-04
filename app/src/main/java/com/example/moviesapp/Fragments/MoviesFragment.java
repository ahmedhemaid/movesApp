package com.example.moviesapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.moviesapp.Movie;
import com.example.moviesapp.R;
import com.example.moviesapp.RecyclerAdapterMovies;

import java.util.ArrayList;
import java.util.List;

public class MoviesFragment extends Fragment  implements RecyclerAdapterMovies.OnItemClickListener {

    MovieDetailsFragment detailsFragment =new MovieDetailsFragment();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.moviefragment, container, false);
        // UI
        RecyclerView recyclerView = rootView.findViewById(R.id.grid_list_view_movies);
        ListView listView=rootView.findViewById(R.id.grid_list_view_actors);
        // Data Set
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("asdsd","asdas"));
        movies.add(new Movie("asdsd","asdas"));
        movies.add(new Movie("asdsd","asdas"));
        movies.add(new Movie("asdsd","asdas"));
        movies.add(new Movie("asdsd","asdas"));
        movies.add(new Movie("asdsd","asdas"));
        movies.add(new Movie("asdsd","asdas"));
        movies.add(new Movie("asdsd","asdas"));
        movies.add(new Movie("asdsd","asdas"));
        movies.add(new Movie("asdsd","asdas"));
        movies.add(new Movie("asdsd","asdas"));
        movies.add(new Movie("asdsd","asdas"));
        GridLayoutManager mLayoutManager=new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(mLayoutManager);
        // Custom Adapter (Bridge between UI & Data Set)
        RecyclerAdapterMovies adapter = new RecyclerAdapterMovies(movies);
                // Attach the adapter to the ListView
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(MoviesFragment.this);
        // Set layout manager to position the items
        return rootView;
    }

    @Override
    public void onItemClick(int position) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,detailsFragment);
        fragmentTransaction.commit();
    }
}