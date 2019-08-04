package com.example.moviesapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviesapp.Movie;
import com.example.moviesapp.R;
import com.example.moviesapp.RecyclerAdapterMovies;

import java.util.ArrayList;
import java.util.List;

public class DramaFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.actorsfragment, container, false);
        // UI
        RecyclerView recyclerView = rootView.findViewById(R.id.drama_list_view);
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

        return rootView;
    }
}
