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
import com.example.moviesapp.basese.Actor;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ActorDetails extends AppCompatActivity {
    private final String TAG = "VOLLEY";
    int id;
    String title;
    String postPath;
    String description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actor_detail);
        id = getIntent().getIntExtra("idActor", 0);
        //Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
        getPopularMovies();
    }
    private void getPopularMovies() {
        final Actor actor=new Actor();
        // Get popular movies
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, TheMovieDBAPI.actorsDetailsURL,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //
                                try {

                                    JSONArray actorsArray = response.getJSONArray("results");
                                    for (int i = 0; i < actorsArray.length(); i++) {
                                        JSONObject actorObject = actorsArray.getJSONObject(i);
                                        if (id == actorObject.getInt("id")) {

                                            postPath = actorObject.getString("profile_path");
                                            title = actorObject.getString("name");
                                            //description=actorObject.getString("biography");
                                           // String backdrop_path=actorObject.getString("backdrop_path");
                                            actor.setName(title);
                                            actor.setLinkOfImage(postPath);
                                            actor.setDescription(description);
                                            TextView textView=findViewById(R.id.actor_name_in_details);
                                            textView.setText(title);
                                            ImageView imageView=findViewById(R.id.profileActorPhoto);
                                            Picasso.get().load(actor.getLinkOfImage()).into(imageView);
                                            //ImageView backdrop_path_imageView=findViewById(R.id.wallpaperActor);
                                           // Picasso.get().load(movie.getBackdrop_path()).into(backdrop_path_imageView);
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
