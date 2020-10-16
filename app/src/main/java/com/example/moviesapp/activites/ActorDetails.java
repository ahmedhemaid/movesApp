package com.example.moviesapp.activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.moviesapp.R;
import com.example.moviesapp.adapter.ActorMoviesAdapter;
import com.example.moviesapp.api.VolleySingleton;
import com.example.moviesapp.basese.Actor;
import com.example.moviesapp.basese.FavoriteMovies;
import com.example.moviesapp.basese.Movie;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActorDetails extends AppCompatActivity {
    private final String TAG = "VOLLEY";
    final Actor actor=new Actor();
    ActorMoviesAdapter moviesAdapter;
    int id;
    ArrayList<Movie>actorMovies=new ArrayList<>();
    String title="";
    String postPath="";
    String description="";
    String birthday="";
    String knownOfDepartment="";
    String gender="";
    String popularity="";
    String place_of_birth="";
    RecyclerView actorMoviesRecycler;
    ImageView favorite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actor_detail);
        id = getIntent().getIntExtra("idActor", 0);
        actorMovies.clear();
        actorMoviesRecycler=findViewById(R.id.actor_movies);
        RecyclerView.LayoutManager actorsLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        actorMoviesRecycler.setLayoutManager(actorsLayoutManager);
        getActorDetails();
        getActorMovies();
        favorite = findViewById(R.id.favorite_actor_on_details);
        favorite.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            FavoriteMovies movie ;
                                            if (actor.isFavorite() ==false) {
                                                favorite.setImageResource(R.drawable.favorite);
                                                actor.setFavorite(true);
                                                Toast.makeText(ActorDetails.this, "Actor added to favorite", Toast.LENGTH_SHORT).show();
                                                //saveFavoriteMovies
                                                boolean isFavorite=actor.isFavorite();
                                                }
                                            else if (actor.isFavorite()==true){
                                                favorite.setImageResource(R.drawable.unfavorite);
                                                Toast.makeText(ActorDetails.this, "Actor deleted from favorite", Toast.LENGTH_SHORT).show();
                                                actor.setFavorite(false);
                                                }
                                        }
                                    }
        );
    }
    //get actor details
    private void getActorDetails() {
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, "https://api.themoviedb.org/3/person/"+id+"?api_key=4a7d2c349f439f8ac8d00d0bff4b7e32",
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //
                                try {
                                            postPath = response.getString("profile_path");
                                            title = response.getString("name");
                                            if (response.getString("popularity")!=null)
                                            popularity="Popularity : "+response.getString("popularity");
                                            if (response.getString("birthday")!=null)
                                            birthday="Birthday : "+response.getString("birthday");
                                            if (response.getString("known_for_department")!=null)
                                            knownOfDepartment="Department : "+response.getString("known_for_department");
                                            int gender_=response.getInt("gender");
                                            if (gender_==2)
                                                gender="gender : male";
                                            else if (gender_==1)
                                                gender="gender : female";
                                            else {
                                            gender="gender : non-determent";
                                            }
                                            if (!response.getString("place_of_birth").equals(null)) {
                                                place_of_birth = "place of birth : " + response.getString("place_of_birth");
                                            }
                                            if (response.getString("biography")!=null)
                                            description=response.getString("biography");
                                            actor.setName(title);
                                            actor.setLinkOfImage(postPath);
                                            actor.setDescription(description);
                                            actor.setGender(gender);
                                            actor.setKnownOfDepartment(knownOfDepartment);
                                            actor.setPopularity(popularity);
                                            actor.setBirthday(birthday);
                                            actor.setPlace_of_birth(place_of_birth);
                                            TextView actorName=findViewById(R.id.actor_name_in_details);
                                            actorName.setText(title);
                                            ImageView actorImageView=findViewById(R.id.profileActorPhoto);
                                            Picasso.get().load(actor.getLinkOfImage()).into(actorImageView);
                                            TextView actorDesc=findViewById(R.id.actor_description);
                                            actorDesc.setText(description);
                                            TextView actorPop=findViewById(R.id.actor_popularity);
                                            actorPop.setText(popularity);
                                            TextView actorBirthDay=findViewById(R.id.actor_birth_day);
                                            actorBirthDay.setText(birthday);
                                            TextView actorKnownOfDepartment=findViewById(R.id.actor_known_of_department);
                                            actorKnownOfDepartment.setText(knownOfDepartment);
                                            TextView actorGender=findViewById(R.id.actor_gender);
                                            actorGender.setText(gender);
                                            TextView actorPlaceOfBirth=findViewById(R.id.actor_place_of_birth);
                                            actorPlaceOfBirth.setText(place_of_birth);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.d("exception", e.getMessage());
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
    private void getActorMovies() {
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET,  " https://api.themoviedb.org/3/person/"+id+"/movie_credits?api_key=4a7d2c349f439f8ac8d00d0bff4b7e32&language=en-US",
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //
                                try {

                                    {
                                        JSONArray casts = response.getJSONArray("cast");
                                        for (int icast = 0; icast < casts.length(); icast++) {
                                            JSONObject movie = casts.getJSONObject(icast);
                                            actorMovies.add(new Movie(movie.getInt("id"), movie.getString("title"), movie.getString("poster_path")));
                                        }
                                        actor.famousMovies=actorMovies;
                                    }


                                    moviesAdapter= new ActorMoviesAdapter(actorMovies);
                                    actorMoviesRecycler.setAdapter(moviesAdapter);
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
