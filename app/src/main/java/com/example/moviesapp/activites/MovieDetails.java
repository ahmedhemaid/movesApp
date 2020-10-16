package com.example.moviesapp.activites;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.moviesapp.R;
import com.example.moviesapp.adapter.RecyclerAdapterActorsOfMovies;
import com.example.moviesapp.adapter.SimilarMoviesAdapter;
import com.example.moviesapp.api.VolleySingleton;
import com.example.moviesapp.basese.Actor;
import com.example.moviesapp.basese.FavoriteMovies;
import com.example.moviesapp.basese.Movie;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.moviesapp.adapter.ActionMoviesAdapter.movies_shared;
import static com.example.moviesapp.adapter.ActionMoviesAdapter.shared_preferences_movies;

public class MovieDetails extends AppCompatActivity {
    private final String TAG = "VOLLEY";
    String title;
    String postPath;
    int movieId;
    String description;
    public static ArrayList<Actor> movieActors;
    public static ArrayList<Movie> similarMovies;
    String budget;
    final Movie movie=new Movie();
    String trailerURL;
    ImageView favorite;
    RecyclerView actorsOfMovie,similarMoviesRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        movieId = getIntent().getIntExtra("movieId", 0);
        getMovieDetails();
        getMovieTrailer();
        getMovieActors();
        getSimilarMovies();
        favorite = findViewById(R.id.favorite_movie_details);
        if (is_in_shared(movieId+"")){
            favorite.setImageResource(R.drawable.favorite);
            movie.setFavorite(true);
        }
        favorite.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (movie.getFavorite() ==false) {
                                                favorite.setImageResource(R.drawable.favorite);
                                                save_movie_to_fav(movieId);
                                                movie.setFavorite(true);
                                                Toast.makeText(MovieDetails.this, "Movie added to favorite", Toast.LENGTH_SHORT).show();
                                                //saveFavoriteMovies
                                                boolean isFavorite=movie.getFavorite();
                                            }
                                            else if (movie.getFavorite()==true){
                                                favorite.setImageResource(R.drawable.unfavorite);
                                                delete_from_fav(movieId+"");
                                                movie.setFavorite(false);
                                                Toast.makeText(MovieDetails.this, "Movie deleted from favorite", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
        );
        similarMoviesRecycler=findViewById(R.id.similar_movies_recycler);
        actorsOfMovie=findViewById(R.id.actors_of_movie_list_view);
        RecyclerView.LayoutManager actorsLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        actorsOfMovie.setLayoutManager(actorsLayoutManager);
        RecyclerView.LayoutManager similarLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        similarMoviesRecycler.setLayoutManager(similarLayoutManager);
    }

    private void getMovieDetails() {
        // Get movie details
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET,  "https://api.themoviedb.org/3/movie/"+movieId+"?api_key=4a7d2c349f439f8ac8d00d0bff4b7e32&language=en-US",
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //
                                try {
                                            postPath = response.getString("poster_path");
                                            title = response.getString("title");
                                            description=response.getString("overview");
                                            budget=response.getString("budget")+"$";
                                            String backdrop_path=response.getString("backdrop_path");
                                            movie.setTitle(title);
                                            movie.setBackdrop_path(backdrop_path);
                                            movie.setPoster_path(postPath);
                                            movie.setOverview(description);
                                            movie.setBudget(budget);
                                            TextView name_tv=findViewById(R.id.movie_name_in_details);
                                            name_tv.setText(title);
                                            TextView budget_tv=findViewById(R.id.movie_budget);
                                            budget_tv.setText(budget);
                                            ImageView imageView=findViewById(R.id.profilePhoto);
                                            Picasso.get().load(movie.getPoster_path()).into(imageView);
                                            ImageView backdrop_path_imageView=findViewById(R.id.wallpaperMovie);
                                            Picasso.get().load(movie.getBackdrop_path()).into(backdrop_path_imageView);
                                            TextView descTV=findViewById(R.id.movie_description);
                                            descTV.setText(description);
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

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest, "REQUEST_TAG");
    }
    private void getMovieTrailer() {
        // Get movie details
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET,  " https://api.themoviedb.org/3/movie/"+movieId+"/videos?api_key=4a7d2c349f439f8ac8d00d0bff4b7e32&language=en-US",
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //
                                try {
                                    for (int i = 0; i < response.length(); i++) {
                                        if (response.getInt("id")==movieId) {
                                            JSONArray trailerArray = response.getJSONArray("results");
                                            JSONObject trailer =trailerArray.getJSONObject(0);
                                            trailerURL=trailer.getString("key");
                                        }
                                    }
                                    WebView videoView =findViewById(R.id.trailer);
                                    videoView.getSettings().setJavaScriptEnabled(true);
                                    videoView.setWebChromeClient(new WebChromeClient() {

                                    } );
                                    Log.d("video", "onResponse: "+trailerURL);
                                    videoView.loadData("<iframe width=\"100%\" height=\"90%\" src=\"https://www.youtube.com/embed/"+trailerURL+"?autoplay=0&showinfo=0\" allowfullscreen=\"allowfullscreen\"></iframe>", "text/html" , "utf-8");

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

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest, "REQUEST_TAG");
    }
    //similar movies
    private void getSimilarMovies() {
        similarMovies=new ArrayList<>();
        // Get movie details
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET,  "https://api.themoviedb.org/3/movie/"+movieId+"/similar?api_key=4a7d2c349f439f8ac8d00d0bff4b7e32&language=en-US",
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //
                                try {
                                    {
                                        JSONArray results = response.getJSONArray("results");
                                        for (int iResults = 0; iResults < results.length(); iResults++) {
                                            JSONObject movie = results.getJSONObject(iResults);
                                            similarMovies.add(new Movie());
                                            Log.d("actors", "actors "+similarMovies.get(iResults).getTitle());
                                            similarMovies.get(iResults).setId(movie.getInt("id"));
                                            similarMovies.get(iResults).setTitle(movie.getString("title"));
                                            similarMovies.get(iResults).setPoster_path(movie.getString("poster_path"));
                                        }
                                    }

                                    SimilarMoviesAdapter similarMoviesAdapter= new SimilarMoviesAdapter(similarMovies);
                                    similarMoviesRecycler.setAdapter(similarMoviesAdapter);
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

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest, "REQUEST_TAG");
    }
    //get actors of movie
    private void getMovieActors() {
        movieActors=new ArrayList<>();
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET,  " https://api.themoviedb.org/3/movie/"+movieId+"/credits?api_key=4a7d2c349f439f8ac8d00d0bff4b7e32&language=en-US",
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //
                                try {

                                        {
                                            JSONArray casts = response.getJSONArray("cast");
                                            for (int icast=0;icast<casts.length();icast++) {
                                                JSONObject actor=casts.getJSONObject(icast);
                                                movieActors.add(new Actor(actor.getInt("id"),actor.getString("name")+"\n("+actor.getString("character")+")",actor.getString("profile_path")));
                                                Log.d("actors", "actors "+movieActors.get(icast).getName());
                                            }
                                        }

                                    RecyclerAdapterActorsOfMovies actorsAdapter= new RecyclerAdapterActorsOfMovies(movieActors);
                                    actorsOfMovie.setAdapter(actorsAdapter);

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

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest, "REQUEST_TAG");
    }
    public void save_movie_to_fav(int id){
        SharedPreferences sharedPreferences;
        sharedPreferences=getSharedPreferences(shared_preferences_movies,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        String s =sharedPreferences.getString(movies_shared,"");
        s=s+id+",";
        editor.putString(movies_shared,s);
        editor.apply();
        Log.d("fav_movies", "save movie: "+s);
    }
    public boolean is_in_shared(String id){
        SharedPreferences sharedPreferences;
        sharedPreferences=getSharedPreferences(shared_preferences_movies,Context.MODE_PRIVATE);
        String s =sharedPreferences.getString(movies_shared,"");
        List<String> x= Arrays.asList(s.split(","));
        return x.contains(id);
    }
    public boolean delete_from_fav(String id){
        SharedPreferences sharedPreferences;
        sharedPreferences=getSharedPreferences(shared_preferences_movies,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        String s =sharedPreferences.getString(movies_shared,"");
        String[] z1=s.split(",");
        for (int i=0;i<z1.length;i++){
            if (z1[i].equals(id)){
                z1[i]="";
            }
        }
        s="";
        for (int i=0;i<z1.length;i++){
            s=s+z1[i]+",";
        }
        editor.putString(movies_shared,s);
        editor.apply();
        Log.d("fav_movies", "delete_from_fav: "+s);
        String[] z=s.split(",");
        ArrayList<String> x=new ArrayList<>(Arrays.asList(z));
        return !x.contains(id);
    }
}
