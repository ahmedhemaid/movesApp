package com.example.moviesapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviesapp.R;
import com.example.moviesapp.activites.ActorDetails;
import com.example.moviesapp.activites.MovieDetails;
import com.example.moviesapp.basese.FavoriteMovies;
import com.example.moviesapp.basese.Movie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class ActorMoviesAdapter extends RecyclerView.Adapter<ActorMoviesAdapter.ViewHolder> {
    public static List<FavoriteMovies>favoriteArrayList=new ArrayList<>();
    public static List<FavoriteMovies> favorite;
    private Context mContext;
    private OnItemClickListener mListener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }
    // Store a member variable for the contacts
    private List<String>namesOfMovies;
    public static List<Movie> mMovies;

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    // Pass in the contact array into the constructor
    public ActorMoviesAdapter(List<Movie> movies) {
        mMovies = movies;
    }
    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ActorMoviesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the custom layout
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rootView = inflater.inflate(R.layout.row_of_movie, parent, false);
        mContext=rootView.getContext();
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(rootView);
        // Return the completed view to render on screen
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull final ActorMoviesAdapter.ViewHolder holder, final int position) {
        // Get the data model based on position
        Movie movie = mMovies.get(position);

        // Set item views based on your views and data model
        final ImageView imageViewPicture;
        TextView textViewName;
        textViewName=holder.itemView.findViewById(R.id.movieTitle);
        textViewName.setText(mMovies.get(position).getTitle()+"");
        imageViewPicture=holder.itemView.findViewById(R.id.image_view_movie);
        Picasso.get().load(mMovies.get(position).getPoster_path()).into(imageViewPicture);
        final ImageView favorite;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null){
                    int position1=position;
                    if(position1!=RecyclerView.NO_POSITION){
                        mListener.onItemClick(position1);
                    }
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.itemView.getContext(), MovieDetails.class);
                intent.putExtra("movieId",mMovies.get(position).getId());
                holder.itemView.getContext().startActivity(intent);

            }
        });
        favorite = holder.itemView.findViewById(R.id.favorite);
        favorite.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            FavoriteMovies movie ;
                                            int id=mMovies.get(position).getId();
                                            String name=mMovies.get(position).getTitle();
                                            String profile_path=mMovies.get(position).getPoster_path();
                                            boolean isFavorite= mMovies.get(position).getFavorite();

                                            if (mMovies.get(position).getFavorite() ==false) {
                                                favorite.setImageResource(R.drawable.favorite);
                                                mMovies.get(position).setFavorite(true);

                                                //saveFavoriteMovies
                                                isFavorite=mMovies.get(position).getFavorite();
                                               favoriteArrayList.add(new FavoriteMovies(id,id,name,profile_path,isFavorite));
                                                Log.d("favoriteList", favoriteArrayList.toString());
                                                Log.d("favoriteList", id+"--"+name+"  "+position);
                                            saveFavoriteMovies();
                                            }
                                            else if (mMovies.get(position).getFavorite()==true){
                                                favorite.setImageResource(R.drawable.unfavorite);
                                                mMovies.get(position).setFavorite(false);
                                                isFavorite=mMovies.get(position).getFavorite();
                                                //unsaveFavoriteMovie
                                                favoriteArrayList.remove(mMovies.get(position));
                                                Log.d("favoriteList", id+"--"+name+"  "+position);
                                                Log.d("favoriteList", favoriteArrayList.toString());
                                            }
                                        }
                                    }
        );
    }
    public void saveFavoriteMovies(){
        SharedPreferences sharedPreferences=this.mContext.getSharedPreferences("favorite movies", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        String json =gson.toJson(favoriteArrayList);
        editor.putString("taskList",json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences("favorite movies", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("taskList", null);
        Type type = new TypeToken<ArrayList<FavoriteMovies>>() {
        }.getType();
        favoriteArrayList = gson.fromJson(json, type);
        if (favoriteArrayList == null) {
            favoriteArrayList = new ArrayList<>();
        }
        Log.d("shared", favoriteArrayList.size()+"");

    }
    // Returns the total count of items in the list
    public int getItemCount() {
        return mMovies.size();
    }
    public void filterList(ArrayList<Movie> filteredList) {
        mMovies = filteredList;
        notifyDataSetChanged();
    }
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View rootView) {

            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(rootView);

        }
    }
    public void updateList(List<String> newList){
        namesOfMovies=new ArrayList<>();
        namesOfMovies.addAll(newList);
        notifyDataSetChanged();
    }
}
