package com.example.moviesapp.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.moviesapp.activites.MovieDetails;
import com.example.moviesapp.basese.Movie;
import com.example.moviesapp.R;
import com.example.moviesapp.adapter.RecyclerAdapterMovies;
import com.example.moviesapp.api.TheMovieDBAPI;
import com.example.moviesapp.api.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MoviesFragment extends Fragment  implements RecyclerAdapterMovies.OnItemClickListener {
    private final String TAG = "VOLLEY";
    static Bundle moviesTitles;
    static String a;
    static ArrayList<CharSequence>titles;
    MovieDetails detailsFragment =new MovieDetails();
    RecyclerView listView;
    static List<Movie> movieList;
    RecyclerAdapterMovies adapter;
    RecyclerView recyclerView;
    TextView titleMovie ;
    ImageView imageViewMovie;
    View rootView;
    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView=inflater.inflate(R.layout.moviefragment, container, false);
        // UI
        recyclerView = rootView.findViewById(R.id.grid_list_view_movies);
        listView=rootView.findViewById(R.id.grid_list_view_movies);

        //titleMovie.setText("");
        // Data Set


        // Custom Adapter (Bridge between UI & Data Set)
                // Attach the adapter to the ListView
        getPopularMovies();
        Log.i("aa", "the movies are "+titles);
        // Set layout manager to position the items
//    titleMovie.setText("");
        return rootView;
    }

    @Override
    public void onItemClick(int position) {
    }
    private void getPopularMovies() {
        GridLayoutManager mLayoutManager=new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(mLayoutManager);
        // Get popular movies
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, TheMovieDBAPI.popularMoviesURL,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //
                                try {
                                    moviesTitles=new Bundle();
                                    movieList = new ArrayList<Movie>();
                                    adapter = new RecyclerAdapterMovies(movieList);
                                    recyclerView.setAdapter(adapter);
                                    adapter.setOnItemClickListener(MoviesFragment.this);
                                    JSONArray moviesArray = response.getJSONArray("results");
                                    for (int i = 0; i < moviesArray.length(); i++) {
                                        JSONObject movieObject = moviesArray.getJSONObject(i);
                                        String postPath = movieObject.getString("poster_path");
                                        String title = movieObject.getString("title");
                                        int id=movieObject.getInt("id");
                                        Log.i("asd", title);
                                        //Toast.makeText(getContext(), title, Toast.LENGTH_SHORT).show();
                                        //Toast.makeText(getContext(), postPath, Toast.LENGTH_SHORT).show();
                                        //
                                        moviesTitles.putString(""+i,title);
                                        Log.d(TAG, "onResponse: Title: " + title);
                                        Movie movie = new Movie();
                                        movie.setTitle(title);
                                        movie.setId(id);
                                        movie.setPoster_path(postPath);
                                        movieList.add(movie);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, "onErrorResponse: " + error.toString());
                                Log.d(TAG, "onErrorResponse: " + error.getMessage());
                            }
                        });

        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest, "REQUEST_TAG");
    }

    private void getTopMovies() {
        //
    }

}