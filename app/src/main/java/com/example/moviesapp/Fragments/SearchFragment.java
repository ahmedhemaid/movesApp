package com.example.moviesapp.Fragments;

import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.moviesapp.R;
import com.example.moviesapp.adapter.SearchActorAdapter;
import com.example.moviesapp.adapter.SearchMoviesAdapter;
import com.example.moviesapp.api.VolleySingleton;
import com.example.moviesapp.basese.Actor;
import com.example.moviesapp.basese.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements SearchMoviesAdapter.OnItemClickListener, SearchActorAdapter.OnItemClickListener {
    private final String TAG = "VOLLEY";
    public static List<Movie> moviesResults;
    public static List<Actor> actorsResults;
    EditText ET_Result;
    Drawable clicked_button;
    String searchResult="";
    SearchMoviesAdapter moviesAdapter;
    SearchActorAdapter actorAdapter;
    Button B_movies, B_actors;
    boolean movie_clicked,actor_clicked;
    RecyclerView recyclerView;
    ImageView clear;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView= inflater.inflate(R.layout.search_fragment, container, false);
        moviesResults=new ArrayList<>();
        actorsResults=new ArrayList<>();
        moviesResults=MoviesFragment.popularMovieList;
        recyclerView = rootView.findViewById(R.id.recycler_view_search_results);
        moviesAdapter = new SearchMoviesAdapter(moviesResults);
        actorAdapter=new SearchActorAdapter(actorsResults);
        ET_Result=rootView.findViewById(R.id.search_all);
        B_actors=rootView.findViewById(R.id.is_actor);
        B_movies=rootView.findViewById(R.id.is_movie);
        B_movies.setBackgroundResource(R.drawable.clicked_button);
        B_actors.setBackgroundResource(R.drawable.not_clicked_button);
        movie_clicked=true;
        B_actors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actor_clicked=true;
                movie_clicked=false;
                searchResult=ET_Result.getText().toString();
                getSearchResultActors(searchResult);
                B_actors.setBackgroundResource(R.drawable.clicked_button);
                B_movies.setBackgroundResource(R.drawable.not_clicked_button);
            }
        });
        B_movies.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                movie_clicked=true;
                actor_clicked=false;
                searchResult=ET_Result.getText().toString();
                getSearchResultMovies(searchResult);
                B_movies.setBackgroundResource(R.drawable.clicked_button);
                B_actors.setBackgroundResource(R.drawable.not_clicked_button);
            }
        });
        searchResult=ET_Result.getText().toString();
        clear=rootView.findViewById(R.id.clear_search);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ET_Result.setText("");
                moviesResults.clear();
                actorsResults.clear();
                moviesAdapter.notifyDataSetChanged();
                actorAdapter.notifyDataSetChanged();
            }
        });
        ET_Result.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (searchResult==""){
                    moviesResults.clear();
                    actorsResults.clear();
                    actorAdapter.notifyDataSetChanged();
                    moviesAdapter.notifyDataSetChanged();
                }
                if (movie_clicked) {
                    getSearchResultMovies(s.toString());
                }
                else if(actor_clicked) {
                    getSearchResultActors(s.toString());
                }
            }
        });

        return rootView;
    }
    //search result movies api
    private void getSearchResultMovies(String r) {
        RecyclerView.LayoutManager LayoutManager =new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(LayoutManager);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, "https://api.themoviedb.org/3/search/movie?query="+r+"&api_key=4a7d2c349f439f8ac8d00d0bff4b7e32&language=en-US&page=1&include_adult=false",
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //
                                try {
                                    moviesResults = new ArrayList<Movie>();
                                    moviesAdapter = new SearchMoviesAdapter(moviesResults);
                                    recyclerView.setAdapter(moviesAdapter);
                                    moviesAdapter.setOnItemClickListener(SearchFragment.this);
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
                                        Movie movie = new Movie();
                                        movie.setTitle(title);
                                        movie.setId(id);
                                        movie.setPoster_path(postPath);
                                        moviesResults.add(movie);
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

    //search result actors api
    private void getSearchResultActors(String r) {
        RecyclerView.LayoutManager LayoutManager =new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(LayoutManager);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, "https://api.themoviedb.org/3/search/person?query="+r+"&api_key=4a7d2c349f439f8ac8d00d0bff4b7e32&language=en-US&page=1&include_adult=false",
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //
                                try {
                                    actorsResults = new ArrayList<Actor>();
                                    actorAdapter = new SearchActorAdapter(actorsResults);
                                    recyclerView.setAdapter(actorAdapter);
                                    actorAdapter.setOnItemClickListener(SearchFragment.this);
                                    JSONArray actorsArray = response.getJSONArray("results");
                                    for (int i = 0; i < actorsArray.length(); i++) {
                                        JSONObject actorObject = actorsArray.getJSONObject(i);
                                        String postPath = actorObject.getString("profile_path");
                                        String title = actorObject.getString("name");
                                        int id= actorObject.getInt("id");
                                        Log.i("asd", title);
                                        //Toast.makeText(getContext(), title, Toast.LENGTH_SHORT).show();
                                        //Toast.makeText(getContext(), postPath, Toast.LENGTH_SHORT).show();
                                        //
                                        Actor actor = new Actor();
                                        actor.setName(title);
                                        actor.setId(id);
                                        actor.setLinkOfImage(postPath);
                                        actorsResults.add(actor);
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


    @Override
    public void onItemClick(int position) {

    }
}


