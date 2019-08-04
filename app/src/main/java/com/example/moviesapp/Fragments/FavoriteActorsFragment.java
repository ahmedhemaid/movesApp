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

import com.example.moviesapp.Actor;
import com.example.moviesapp.R;
import com.example.moviesapp.RecyclerAdapterActors;

import java.util.ArrayList;

public class FavoriteActorsFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.actorsfragment, container, false);
        // UI
        RecyclerView recyclerView = rootView.findViewById(R.id.grid_list_view_actors);
        // Data Set
        ArrayList<Actor> actors = new ArrayList<>();
        actors.add(new Actor(""));
        actors.add(new Actor(""));
        actors.add(new Actor(""));
        actors.add(new Actor(""));
        actors.add(new Actor(""));
        actors.add(new Actor(""));
        actors.add(new Actor(""));
        actors.add(new Actor(""));
        actors.add(new Actor(""));
        actors.add(new Actor(""));
        actors.add(new Actor(""));
        actors.add(new Actor(""));
        GridLayoutManager mLayoutManager=new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(mLayoutManager);
        // Custom Adapter (Bridge between UI & Data Set)
        RecyclerAdapterActors adapter = new RecyclerAdapterActors(actors);

        // Attach the adapter to the ListView
        recyclerView.setAdapter(adapter);

        // Set layout manager to position the items
        return rootView;
    }
}
