package com.example.moviesapp.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.moviesapp.R;
import com.example.moviesapp.activites.ActorDetails;
import com.example.moviesapp.activites.MainActivity;
import com.example.moviesapp.adapter.RecyclerAdapterActors;
import com.example.moviesapp.api.TheMovieDBAPI;
import com.example.moviesapp.api.VolleySingleton;
import com.example.moviesapp.basese.Actor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActorsFragment extends Fragment implements RecyclerAdapterActors.OnItemClickListener {
    private final String TAG = "VOLLEY";
    private int pageNumber=1;
    static ArrayList<CharSequence>titles;
    RecyclerView listView;
    public static List<Actor> actorList;
    RecyclerAdapterActors adapter;
    RecyclerView recyclerView;
    View rootView;
    int page=1;
    ProgressBar progressBar;
    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView=inflater.inflate(R.layout.actorsfragment, container, false);
        // UI
        recyclerView = rootView.findViewById(R.id.grid_list_view_actors);
        listView=rootView.findViewById(R.id.grid_list_view_actors);
        //titleMovie.setText("");
        // progress bar
        progressBar=rootView.findViewById(R.id.progress_actors);
        progressBar.setVisibility(View.VISIBLE);
        // Custom Adapter (Bridge between UI & Data Set)
        // Attach the adapter to the ListView
        getActorDetails();
        Log.i("aa", "the movies are "+titles);
        // Set layout manager to position the items
//    titleMovie.setText("");
        return rootView;
    }

    private void getActorDetails() {
        final RecyclerView.LayoutManager mLayoutManager=new GridLayoutManager(this.getContext(),2);
        recyclerView.setLayoutManager(mLayoutManager);
        //pagination

        // Get popular movies
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, TheMovieDBAPI.actorsDetailsURL+page,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //
                                try {

                                    actorList = new ArrayList<Actor>();
                                    adapter = new RecyclerAdapterActors(actorList);
                                    recyclerView.setAdapter(adapter);
                                    adapter.setOnItemClickListener(ActorsFragment.this);
                                    JSONArray actorsArray = response.getJSONArray("results");
                                    for (int i = 0; i < actorsArray.length(); i++) {
                                        JSONObject actorObject = actorsArray.getJSONObject(i);
                                        String linkOfImage = actorObject.getString("profile_path");
                                        String name = actorObject.getString("name");
                                        int id =actorObject.getInt("id");
                                        Log.i("asd", name);
                                        //Toast.makeText(getContext(), title, Toast.LENGTH_SHORT).show();
                                        //Toast.makeText(getContext(), postPath, Toast.LENGTH_SHORT).show();
                                        //
                                        Actor actor = new Actor();
                                        actor.setName(name);
                                        actor.setLinkOfImage(linkOfImage);
                                        actor.setId(id);
                                        actorList.add(actor);
                                        progressBar.setVisibility(View.GONE);
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
