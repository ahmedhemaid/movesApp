package com.example.moviesapp.activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.moviesapp.R;
import com.example.moviesapp.api.TheMovieDBAPI;
import com.example.moviesapp.api.VolleySingleton;
import com.example.moviesapp.basese.Movie;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieDetails extends AppCompatActivity {
    private final String TAG = "VOLLEY";
    int id;
    String title;
    String postPath;
    String description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        id = getIntent().getIntExtra("id", 0);
        getPopularMovies();
    }

    private void getPopularMovies() {
        final Movie movie=new Movie();
        // Get popular movies
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, TheMovieDBAPI.popularMoviesURL,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //
                                try {

                                    JSONArray moviesArray = response.getJSONArray("results");
                                    for (int i = 0; i < moviesArray.length(); i++) {
                                        JSONObject movieObject = moviesArray.getJSONObject(i);
                                        if (id == movieObject.getInt("id")) {

                                            postPath = movieObject.getString("poster_path");
                                            title = movieObject.getString("title");
                                            description=movieObject.getString("overview");
                                            String backdrop_path=movieObject.getString("backdrop_path");
                                            movie.setTitle(title);
                                            movie.setBackdrop_path(backdrop_path);
                                            movie.setPoster_path(postPath);
                                            movie.setOverview(description);
                                            TextView textView=findViewById(R.id.movie_name_in_details);
                                            textView.setText(title);
                                            ImageView imageView=findViewById(R.id.profilePhoto);
                                            Picasso.get().load(movie.getPoster_path()).into(imageView);
                                            ImageView backdrop_path_imageView=findViewById(R.id.wallpaperMovie);
                                            Picasso.get().load(movie.getBackdrop_path()).into(backdrop_path_imageView);
                                            TextView descTV=findViewById(R.id.movie_description);
                                            descTV.setText(description);

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

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest, "REQUEST_TAG");
    }

}
