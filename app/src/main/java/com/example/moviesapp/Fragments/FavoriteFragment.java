package com.example.moviesapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.moviesapp.R;
import com.example.moviesapp.activites.MainActivity;
import com.example.moviesapp.adapter.FavoriteActorsAdapter;
import com.example.moviesapp.adapter.FavoriteMoviesAdapter;
import com.example.moviesapp.adapter.PopularMoviesAdapter;
import com.example.moviesapp.api.VolleySingleton;
import com.example.moviesapp.basese.Actor;
import com.example.moviesapp.basese.FavoriteMovies;
import com.example.moviesapp.basese.Movie;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FavoriteFragment extends Fragment {
       List<Movie> movies;
       int movieId=0;
       Movie movie=new Movie();
       List<Actor>actors;
       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
           movies=MainActivity.favoriteArrayList;
           HashSet hs = new HashSet();
           hs.addAll(movies);
           movies.clear();
           movies.addAll(hs);
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
        RecyclerView firstRecyclerView = rootView.findViewById(R.id.fav_movies_recycler_view);
        LinearLayoutManager firstManager = new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        firstRecyclerView.setLayoutManager(firstManager);
        firstRecyclerView.setAdapter(firsAdapter);
           //second favorite recycler
           FavoriteActorsAdapter secondAdapter = new FavoriteActorsAdapter(rootView.getContext(), actors);
           RecyclerView secondRecyclerView = rootView.findViewById(R.id.fav_actors_recycler_view);
           LinearLayoutManager secondManager = new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.HORIZONTAL, false);
           secondRecyclerView.setLayoutManager(secondManager);
           secondRecyclerView.setAdapter(secondAdapter);
        return rootView;
    }
//    private void getMovieDetails() {
//        // Get movie details
//        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
//                (Request.Method.GET,  "https://api.themoviedb.org/3/movie/"+movieId+"?api_key=4a7d2c349f439f8ac8d00d0bff4b7e32&language=en-US",
//                        null,
//                        new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                //
//                                try {
//                                    String title = response.getString("title");
//                                    movie.setTitle(title);
//                                    movie.setPoster_path(postPath);
//                                    Picasso.get().load(movie.getPoster_path()).into(imageView);
//                                    ImageView backdrop_path_imageView=findViewById(R.id.wallpaperMovie);
//                                    Picasso.get().load(movie.getBackdrop_path()).into(backdrop_path_imageView);
//                                    TextView descTV=findViewById(R.id.movie_description);
//                                    descTV.setText(description);
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        },
//                        new Response.ErrorListener() {
//
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Log.d(TAG, "onErrorResponse: " + error.toString());
//                                Log.d(TAG, "onErrorResponse: " + error.getMessage());
//                            }
//                        });
//
//        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest, "REQUEST_TAG");
//    }

}


