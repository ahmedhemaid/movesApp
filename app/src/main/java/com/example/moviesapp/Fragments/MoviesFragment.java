package com.example.moviesapp.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.moviesapp.activites.ActionMoviesActivity;
import com.example.moviesapp.activites.AnimationMoviesActivity;
import com.example.moviesapp.activites.ComedyMoviesActivity;
import com.example.moviesapp.activites.DramaMoviesActivity;
import com.example.moviesapp.activites.HorrorMoviesActivity;
import com.example.moviesapp.activites.PopularMoviesActivity;
import com.example.moviesapp.activites.RecentMoviesActivity;
import com.example.moviesapp.activites.RomanticMoviesActivity;
import com.example.moviesapp.activites.TopRatedMoviesActivity;
import com.example.moviesapp.adapter.ActionMoviesAdapter;
import com.example.moviesapp.adapter.AnimationMoviesAdapter;
import com.example.moviesapp.adapter.ComedyMoviesAdapter;
import com.example.moviesapp.adapter.DramaMoviesAdapter;
import com.example.moviesapp.adapter.HorrorMoviesAdapter;
import com.example.moviesapp.adapter.RecentMoviesAdapter;
import com.example.moviesapp.adapter.RomanticMoviesAdapter;
import com.example.moviesapp.adapter.TopRatedMoviesAdapter;
import com.example.moviesapp.basese.Movie;
import com.example.moviesapp.R;
import com.example.moviesapp.adapter.PopularMoviesAdapter;
import com.example.moviesapp.api.TheMovieDBAPI;
import com.example.moviesapp.api.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MoviesFragment extends Fragment implements PopularMoviesAdapter.OnItemClickListener, TopRatedMoviesAdapter.OnItemClickListener, RecentMoviesAdapter.OnItemClickListener, ActionMoviesAdapter.OnItemClickListener, DramaMoviesAdapter.OnItemClickListener, HorrorMoviesAdapter.OnItemClickListener, RomanticMoviesAdapter.OnItemClickListener, ComedyMoviesAdapter.OnItemClickListener, AnimationMoviesAdapter.OnItemClickListener {
    private final String TAG = "VOLLEY";
    static Bundle moviesTitles;
    TextView showPopular,showTopRated,showRecent,showAction,showDrama,showHorror,showRomantic,showComedy,showAnimation;
    String s="a";
    String a="a";
    //belong to genres
    int actionnumber=0;
    int dramanumber=0;
    int romanticnumber=0;
    int horronumber=0;
    int comedynumber=0;
    int animationnumber=0;
    int actionPage=1;
    int dramaPage=1;
    int horrorPage=1;
    int romanticPage=1;
    int comedyPage=1;
    int animationPage=1;
    int moviesInPageAction =0;
    int moviesInPageDrama =0;
    int moviesInPageHorror =0;
    int moviesInPageRomantic =0;
    int moviesInPageComedy =0;
    int moviesInPageAnimation =0;
    //lists
    public static ArrayList<Movie> popularMovieList;
    public static ArrayList<Movie> topRatedMovieList;
    public static ArrayList<Movie> recentMovieList;
    //genres lists
    public static ArrayList<Movie> actionMovieList;
    public static ArrayList<Movie> horrorMovieList;
    public static ArrayList<Movie> romanticMovieList;
    public static ArrayList<Movie> dramaMovieList;
    public static ArrayList<Movie> animationMovieList;
    public static ArrayList<Movie> comedyMovieList;

    //adapters
    PopularMoviesAdapter adapter;
    RecentMoviesAdapter recentAdapter;
    TopRatedMoviesAdapter topRatedAdapter;
    //genres adapters
    ActionMoviesAdapter actionAdapter;
    DramaMoviesAdapter dramaAdapter;
    ComedyMoviesAdapter comedyAdapter;
    HorrorMoviesAdapter horroAdapter;
    RomanticMoviesAdapter romanticAdapter;
    AnimationMoviesAdapter animationAdapter;
    //recyclers
    RecyclerView recentRecyclerView, popularRecyclerView, topRatedRecyclerView,actionRecyclerView,animationRecyclerView,dramaRecyclerView,horrorRecyclerView,romanticRecyclerView,comedyRecyclerView;
    View rootView;
    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView=inflater.inflate(R.layout.moviefragment, container, false);
        actionMovieList=new ArrayList<>();
        horrorMovieList=new ArrayList<>();
        romanticMovieList=new ArrayList<>();
        dramaMovieList=new ArrayList<>();
        animationMovieList=new ArrayList<>();
        comedyMovieList=new ArrayList<>();
        // UI
        recentRecyclerView=rootView.findViewById(R.id.recent_movies_list_view);
        popularRecyclerView = rootView.findViewById(R.id.popular_movies_list_view);
        topRatedRecyclerView = rootView.findViewById(R.id.top_rated_movies_list_view);
        actionRecyclerView = rootView.findViewById(R.id.action_movies_list_view);
        dramaRecyclerView = rootView.findViewById(R.id.drama_movies_list_view);
        horrorRecyclerView = rootView.findViewById(R.id.horror_movies_list_view);
        animationRecyclerView = rootView.findViewById(R.id.animation_movies_list_view);
        romanticRecyclerView = rootView.findViewById(R.id.romantic_movies_list_view);
        comedyRecyclerView = rootView.findViewById(R.id.comedy_movies_list_view);
        //show more on click listener
        showPopular=rootView.findViewById(R.id.show_more_popular);
        showPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(rootView.getContext(), PopularMoviesActivity.class);
                startActivity(intent);
            }
        });
        showTopRated=rootView.findViewById(R.id.show_more_top_rated);
        showTopRated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(rootView.getContext(), TopRatedMoviesActivity.class);
                startActivity(intent);
            }
        });
        showRecent=rootView.findViewById(R.id.show_more_recent);
        showRecent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(rootView.getContext(), RecentMoviesActivity.class);
                startActivity(intent);
            }
        });
        showAction=rootView.findViewById(R.id.show_more_action);
        showAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(rootView.getContext(), ActionMoviesActivity.class);
                startActivity(intent);
            }
        });
        showDrama=rootView.findViewById(R.id.show_more_drama);
        showDrama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(rootView.getContext(), DramaMoviesActivity.class);
                startActivity(intent);
            }
        });
        showHorror=rootView.findViewById(R.id.show_more_horror);
        showHorror.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(rootView.getContext(), HorrorMoviesActivity.class);
                startActivity(intent);
            }
        });
        showRomantic=rootView.findViewById(R.id.show_more_romantic);
        showRomantic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(rootView.getContext(), RomanticMoviesActivity.class);
                startActivity(intent);
            }
        });
        showComedy=rootView.findViewById(R.id.show_more_comedy);
        showComedy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(rootView.getContext(), ComedyMoviesActivity.class);
                startActivity(intent);
            }
        });
        showAnimation=rootView.findViewById(R.id.show_more_animation);
        showAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(rootView.getContext(), AnimationMoviesActivity.class);
                startActivity(intent);
            }
        });
        // Attach the adapter to the ListView
        getRecentMovies();
        getPopularMovies();
        getTopRatedMovies();
        //genres call methods
        getActionMovies();
        getDramaMovies();
        getHorrorMovies();
        getComedyMovies();
        getRomanticMovies();
        getAnimationMovies();
        // Set layout manager to position the items
        return rootView;
    }
    @Override
    public void onItemClick(int position) {
    }
    //popular movies api
    private void getPopularMovies() {
        RecyclerView.LayoutManager popularLayoutManager =new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false);
        popularRecyclerView.setLayoutManager(popularLayoutManager);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, TheMovieDBAPI.popularMoviesURL,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //
                                try {
                                    moviesTitles=new Bundle();
                                    popularMovieList = new ArrayList<Movie>();
                                    adapter = new PopularMoviesAdapter(popularMovieList);
                                    popularRecyclerView.setAdapter(adapter);
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
                                        popularMovieList.add(movie);
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
    //top-Rated movies api
    private void getTopRatedMovies() {
        RecyclerView.LayoutManager topRatedLayoutManager =new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false);
        topRatedRecyclerView.setLayoutManager(topRatedLayoutManager);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, TheMovieDBAPI.topRatedMoviesURL,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //
                                try {
                                    topRatedMovieList = new ArrayList<Movie>();
                                    topRatedAdapter = new TopRatedMoviesAdapter(topRatedMovieList);
                                    topRatedRecyclerView.setAdapter(topRatedAdapter);
                                    topRatedAdapter.setOnItemClickListener(MoviesFragment.this);
                                    JSONArray moviesArray = response.getJSONArray("results");
                                    for (int i = 0; i < moviesArray.length(); i++) {
                                        JSONObject movieObject = moviesArray.getJSONObject(i);
                                        String postPath = movieObject.getString("poster_path");
                                        String title = movieObject.getString("title");
                                        int id=movieObject.getInt("id");
                                        Log.i("asd", title);
                                        Movie movie = new Movie();
                                        movie.setTitle(title);
                                        movie.setId(id);
                                        movie.setPoster_path(postPath);
                                        topRatedMovieList.add(movie);
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
    //recent movies api
    private void getRecentMovies() {
        RecyclerView.LayoutManager recentLayoutManager =new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false);
        recentRecyclerView.setLayoutManager(recentLayoutManager);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, TheMovieDBAPI.recentMoviesURL,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //
                                try {
                                    recentMovieList = new ArrayList<Movie>();
                                    recentAdapter = new RecentMoviesAdapter(recentMovieList);
                                    recentRecyclerView.setAdapter(recentAdapter);
                                    recentAdapter.setOnItemClickListener(MoviesFragment.this);
                                    JSONArray moviesArray = response.getJSONArray("results");
                                    for (int i = 0; i < moviesArray.length(); i++) {
                                        JSONObject movieObject = moviesArray.getJSONObject(i);
                                        String postPath = movieObject.getString("poster_path");
                                        String title = movieObject.getString("title");
                                        int id=movieObject.getInt("id");
                                        Log.i("asd", title);
                                        Movie movie = new Movie();
                                        movie.setTitle(title);
                                        movie.setId(id);
                                        movie.setPoster_path(postPath);
                                        recentMovieList.add(movie);
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
    //recent movies api
    private void getActionMovies() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, TheMovieDBAPI.popularMoviesURL+"&language=en-US&page="+actionPage,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //
                                try {
                                    JSONArray moviesArray = response.getJSONArray("results");
                                    for (int i = 0; i < moviesArray.length(); i++) {
                                        JSONObject movieObject = moviesArray.getJSONObject(i);
                                        int id = movieObject.getInt("id");
                                        Movie movie=new Movie();
                                        getMovieGenreAction(id,movie);
                                        moviesInPageAction++;
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
//action movies
    private void getMovieGenreAction(final int movieId, final Movie movie){
        RecyclerView.LayoutManager actionLayoutManager =new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false);
        actionRecyclerView.setLayoutManager(actionLayoutManager);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET,  "https://api.themoviedb.org/3/movie/"+movieId+"?api_key=4a7d2c349f439f8ac8d00d0bff4b7e32&language=en-US",
                            null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    //
                                    try {
                                        actionAdapter = new ActionMoviesAdapter(actionMovieList);
                                        actionRecyclerView.setAdapter(actionAdapter);
                                        actionAdapter.setOnItemClickListener(MoviesFragment.this);
                                        JSONArray moviesArray = response.getJSONArray("genres");
                                        JSONObject genres=moviesArray.getJSONObject(0);
                                        movie.setId(movieId);
                                        movie.setPoster_path(response.getString("poster_path"));
                                        movie.setTitle(response.getString("title"));
                                        movie.setGenre(genres.getString("name"));
                                        Log.d("genre", "genre: "+movie.getGenre());
                                        if (movie.getGenre().equals("Action")&&actionnumber<20) {
                                            actionMovieList.add(movie);
                                            actionnumber++;
                                        }
                                        actionAdapter.notifyDataSetChanged();
                                        Log.d("genre", "genre: "+actionMovieList.size());
                                        if (moviesInPageAction ==20&&actionMovieList.size()<20){
                                            actionPage++;
                                            getActionMovies();
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
    //drama movies
    private void getDramaMovies() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, TheMovieDBAPI.popularMoviesURL+"&language=en-US&page="+dramaPage,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //
                                try {
                                    JSONArray moviesArray = response.getJSONArray("results");
                                    for (int i = 0; i < moviesArray.length(); i++) {
                                        JSONObject movieObject = moviesArray.getJSONObject(i);
                                        int id = movieObject.getInt("id");
                                        Movie movie=new Movie();
                                        getMovieGenreDrama(id,movie);
                                        moviesInPageDrama++;
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
    private void getMovieGenreDrama(final int movieId, final Movie movie){
        RecyclerView.LayoutManager dramaLayoutManager =new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false);
        dramaRecyclerView.setLayoutManager(dramaLayoutManager);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET,  "https://api.themoviedb.org/3/movie/"+movieId+"?api_key=4a7d2c349f439f8ac8d00d0bff4b7e32&language=en-US",
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //
                                try {
                                    dramaAdapter = new DramaMoviesAdapter(dramaMovieList);
                                    dramaRecyclerView.setAdapter(dramaAdapter);
                                    dramaAdapter.setOnItemClickListener(MoviesFragment.this);
                                    JSONArray moviesArray = response.getJSONArray("genres");
                                    JSONObject genres=moviesArray.getJSONObject(0);
                                    movie.setId(movieId);
                                    movie.setPoster_path(response.getString("poster_path"));
                                    movie.setTitle(response.getString("title"));
                                    movie.setGenre(genres.getString("name"));
                                    Log.d("genre", "genre: "+movie.getGenre());
                                    if (movie.getGenre().equals("Drama")&&dramanumber<20) {
                                        dramaMovieList.add(movie);
                                        dramanumber++;
                                    }
                                    dramaAdapter.notifyDataSetChanged();
                                    Log.d("genre", "genre: "+dramaMovieList.size());
                                    if (moviesInPageDrama ==20&&dramaMovieList.size()<20){
                                        dramaPage++;
                                        getDramaMovies();
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
    //horror movies
    private void getHorrorMovies() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, TheMovieDBAPI.popularMoviesURL+"&language=en-US&page="+horrorPage,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //
                                try {
                                    JSONArray moviesArray = response.getJSONArray("results");
                                    for (int i = 0; i < moviesArray.length(); i++) {
                                        JSONObject movieObject = moviesArray.getJSONObject(i);
                                        int id = movieObject.getInt("id");
                                        Movie movie=new Movie();
                                        getMovieGenreHorror(id,movie);
                                        moviesInPageHorror++;
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
    private void getMovieGenreHorror(final int movieId, final Movie movie){
        RecyclerView.LayoutManager horrorLayoutManager =new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false);
        horrorRecyclerView.setLayoutManager(horrorLayoutManager);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET,  "https://api.themoviedb.org/3/movie/"+movieId+"?api_key=4a7d2c349f439f8ac8d00d0bff4b7e32&language=en-US",
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //
                                try {
                                    horroAdapter = new HorrorMoviesAdapter(horrorMovieList);
                                    horrorRecyclerView.setAdapter(horroAdapter);
                                    horroAdapter.setOnItemClickListener(MoviesFragment.this);
                                    JSONArray moviesArray = response.getJSONArray("genres");
                                    JSONObject genres=moviesArray.getJSONObject(0);
                                    movie.setId(movieId);
                                    movie.setPoster_path(response.getString("poster_path"));
                                    movie.setTitle(response.getString("title"));
                                    movie.setGenre(genres.getString("name"));
                                    Log.d("genre", "genre: "+movie.getGenre());
                                    if (movie.getGenre().equals("Horror")&&horronumber<20) {
                                        horrorMovieList.add(movie);
                                        horronumber++;
                                    }
                                    horroAdapter.notifyDataSetChanged();
                                    Log.d("genre", "genre: "+dramaMovieList.size());
                                    if (moviesInPageHorror ==20&&horrorMovieList.size()<20){
                                        horrorPage++;
                                        getHorrorMovies();
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
    //romantic movies
    private void getRomanticMovies() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, TheMovieDBAPI.popularMoviesURL+"&language=en-US&page="+romanticPage,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //
                                try {
                                    JSONArray moviesArray = response.getJSONArray("results");
                                    for (int i = 0; i < moviesArray.length(); i++) {
                                        JSONObject movieObject = moviesArray.getJSONObject(i);
                                        int id = movieObject.getInt("id");
                                        Movie movie=new Movie();
                                        getMovieGenreRomantic(id,movie);
                                        moviesInPageRomantic++;
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
    private void getMovieGenreRomantic(final int movieId, final Movie movie){
        RecyclerView.LayoutManager romanticLayoutManager =new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false);
        romanticRecyclerView.setLayoutManager(romanticLayoutManager);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET,  "https://api.themoviedb.org/3/movie/"+movieId+"?api_key=4a7d2c349f439f8ac8d00d0bff4b7e32&language=en-US",
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //
                                try {
                                    romanticAdapter = new RomanticMoviesAdapter(romanticMovieList);
                                    romanticRecyclerView.setAdapter(romanticAdapter);
                                    romanticAdapter.setOnItemClickListener(MoviesFragment.this);
                                    JSONArray moviesArray = response.getJSONArray("genres");
                                    JSONObject genres=moviesArray.getJSONObject(0);
                                    movie.setId(movieId);
                                    movie.setPoster_path(response.getString("poster_path"));
                                    movie.setTitle(response.getString("title"));
                                    movie.setGenre(genres.getString("name"));
                                    Log.d("genre", "genre: "+movie.getGenre());
                                    if (movie.getGenre().equals("Romance")&&romanticnumber<20) {
                                        romanticMovieList.add(movie);
                                        romanticnumber++;
                                    }
                                    romanticAdapter.notifyDataSetChanged();
                                    Log.d("genre", "genre: "+romanticMovieList.size());
                                    if (moviesInPageRomantic ==20&&romanticMovieList.size()<20){
                                        romanticPage++;
                                        getRomanticMovies();
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
    //comedy movies
    private void getComedyMovies() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, TheMovieDBAPI.popularMoviesURL+"&language=en-US&page="+comedyPage,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //
                                try {
                                    JSONArray moviesArray = response.getJSONArray("results");
                                    for (int i = 0; i < moviesArray.length(); i++) {
                                        JSONObject movieObject = moviesArray.getJSONObject(i);
                                        int id = movieObject.getInt("id");
                                        Movie movie=new Movie();
                                        getMovieGenreComedy(id,movie);
                                        moviesInPageComedy++;
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
    private void getMovieGenreComedy(final int movieId, final Movie movie){
        RecyclerView.LayoutManager comedyLayoutManager =new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false);
        comedyRecyclerView.setLayoutManager(comedyLayoutManager);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET,  "https://api.themoviedb.org/3/movie/"+movieId+"?api_key=4a7d2c349f439f8ac8d00d0bff4b7e32&language=en-US",
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //
                                try {
                                    comedyAdapter = new ComedyMoviesAdapter(comedyMovieList);
                                    comedyRecyclerView.setAdapter(comedyAdapter);
                                    comedyAdapter.setOnItemClickListener(MoviesFragment.this);
                                    JSONArray moviesArray = response.getJSONArray("genres");
                                    JSONObject genres=moviesArray.getJSONObject(0);
                                    movie.setId(movieId);
                                    movie.setPoster_path(response.getString("poster_path"));
                                    movie.setTitle(response.getString("title"));
                                    movie.setGenre(genres.getString("name"));
                                    Log.d("genre", "genre: "+movie.getGenre());
                                    if (movie.getGenre().equals("Comedy")&&comedynumber<20) {
                                        comedyMovieList.add(movie);
                                        comedynumber++;
                                    }
                                    comedyAdapter.notifyDataSetChanged();
                                    Log.d("genre", "genre: "+comedyMovieList.size());
                                    if (moviesInPageComedy ==20&&comedyMovieList.size()<20){
                                        comedyPage++;
                                        getComedyMovies();
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
    //animation movies
    private void getAnimationMovies() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, TheMovieDBAPI.popularMoviesURL+"&language=en-US&page="+animationPage,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //
                                try {
                                    JSONArray moviesArray = response.getJSONArray("results");
                                    for (int i = 0; i < moviesArray.length(); i++) {
                                        JSONObject movieObject = moviesArray.getJSONObject(i);
                                        int id = movieObject.getInt("id");
                                        Movie movie=new Movie();
                                        getMovieGenreAnimation(id,movie);
                                        moviesInPageAnimation++;
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
    private void getMovieGenreAnimation(final int movieId, final Movie movie){
        RecyclerView.LayoutManager animationLayoutManager =new LinearLayoutManager(this.getContext(),LinearLayoutManager.HORIZONTAL,false);
        animationRecyclerView.setLayoutManager(animationLayoutManager);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET,  "https://api.themoviedb.org/3/movie/"+movieId+"?api_key=4a7d2c349f439f8ac8d00d0bff4b7e32&language=en-US",
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //
                                try {
                                    animationAdapter = new AnimationMoviesAdapter(animationMovieList);
                                    animationRecyclerView.setAdapter(animationAdapter);
                                    animationAdapter.setOnItemClickListener(MoviesFragment.this);
                                    JSONArray moviesArray = response.getJSONArray("genres");
                                    JSONObject genres=moviesArray.getJSONObject(0);
                                    movie.setId(movieId);
                                    movie.setPoster_path(response.getString("poster_path"));
                                    movie.setTitle(response.getString("title"));
                                    movie.setGenre(genres.getString("name"));
                                    Log.d("genre", "genre: "+movie.getGenre());
                                    if (movie.getGenre().equals("Animation")&&animationnumber<20) {
                                        animationMovieList.add(movie);
                                        animationnumber++;
                                    }
                                    animationAdapter.notifyDataSetChanged();
                                    Log.d("genre", "list size: "+animationnumber);
                                    if (moviesInPageAnimation ==20){
                                        if (animationnumber!=20){
                                            animationPage++;
                                            getAnimationMovies();
                                        }
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
}