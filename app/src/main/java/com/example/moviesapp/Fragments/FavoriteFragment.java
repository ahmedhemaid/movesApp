package com.example.moviesapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviesapp.R;
import com.example.moviesapp.adapter.FavoriteActorsAdapter;
import com.example.moviesapp.adapter.FavoriteMoviesAdapter;
import com.example.moviesapp.basese.Actor;
import com.example.moviesapp.basese.Movie;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {
       RecyclerView recyclerView;
       List<Movie> movies;
       List<Actor>actors;
       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
          movies=new ArrayList<>();
          movies.add(new Movie());
           movies.add(new Movie());
           movies.add(new Movie());
           movies.add(new Movie());
           movies.add(new Movie());
           movies.add(new Movie());
           actors=new ArrayList<>();
           actors.add(new Actor());
           actors.add(new Actor());
           actors.add(new Actor());
           actors.add(new Actor());
           actors.add(new Actor());
           actors.add(new Actor());
           actors.add(new Actor());
           actors.add(new Actor());
           actors.add(new Actor());
           actors.add(new Actor());
           actors.add(new Actor());
        View rootView= inflater.inflate(R.layout.favoratrfragment, container, false);
           //first favorite recycler


        FavoriteMoviesAdapter firsAdapter = new FavoriteMoviesAdapter(rootView.getContext(), movies);
        MultiSnapRecyclerView firstRecyclerView =(MultiSnapRecyclerView) rootView.findViewById(R.id.fav_movies_recycler_view);
        LinearLayoutManager firstManager = new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        firstRecyclerView.setLayoutManager(firstManager);
        firstRecyclerView.setAdapter(firsAdapter);
           //second favorite recycler
           FavoriteActorsAdapter secondAdapter = new FavoriteActorsAdapter(rootView.getContext(), actors);
           MultiSnapRecyclerView secondRecyclerView =(MultiSnapRecyclerView) rootView.findViewById(R.id.fav_actors_recycler_view);
           LinearLayoutManager secondManager = new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.HORIZONTAL, false);
           secondRecyclerView.setLayoutManager(secondManager);
           secondRecyclerView.setAdapter(secondAdapter);
        return rootView;
    }

}


