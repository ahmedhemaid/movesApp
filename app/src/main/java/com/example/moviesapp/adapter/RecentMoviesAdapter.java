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
import android.widget.Toast;

import com.example.moviesapp.R;
import com.example.moviesapp.activites.MainActivity;
import com.example.moviesapp.activites.MovieDetails;
import com.example.moviesapp.basese.FavoriteMovies;
import com.example.moviesapp.basese.Movie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.moviesapp.activites.MainActivity.favoriteArrayList;
import static com.example.moviesapp.adapter.ActionMoviesAdapter.movies_shared;
import static com.example.moviesapp.adapter.ActionMoviesAdapter.shared_preferences_movies;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class RecentMoviesAdapter extends RecyclerView.Adapter<RecentMoviesAdapter.ViewHolder> {
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
    public RecentMoviesAdapter(List<Movie> movies) {
        mMovies = movies;
    }
    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public RecentMoviesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
    public void onBindViewHolder(@NonNull final RecentMoviesAdapter.ViewHolder holder, final int position) {
        // Get the data model based on position
        final Movie movie = mMovies.get(position);

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
        if (is_in_shared(movie.getId()+"")) {
            favorite.setImageResource(R.drawable.favorite);
            if (!MainActivity.favoriteArrayList.contains(movie)) {
                MainActivity.favoriteArrayList.add(mMovies.get(position));
            }
        }
        favorite.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            int id = mMovies.get(position).getId();
                                            String name = mMovies.get(position).getTitle();
                                            String profile_path = mMovies.get(position).getPoster_path();
                                            boolean isFavorite = mMovies.get(position).getFavorite();

                                            if (!is_in_shared(id+"")) {
                                                favorite.setImageResource(R.drawable.favorite);
                                                mMovies.get(position).setFavorite(true);
                                                //saveFavoriteMovies
                                                save_movie_to_fav(id);
                                                Toast.makeText(mContext, "movie is added to favorite", Toast.LENGTH_SHORT).show();
                                                isFavorite = mMovies.get(position).getFavorite();
                                                favoriteArrayList.add(movie);
                                            } else if (is_in_shared(id+"")) {
                                                favorite.setImageResource(R.drawable.unfavorite);
                                                if (delete_from_fav(id+"")){
                                                    Toast.makeText(mContext, "movie is deleted from favorite", Toast.LENGTH_SHORT).show();
                                                }
                                                mMovies.get(position).setFavorite(false);
                                                isFavorite = mMovies.get(position).getFavorite();
                                                //unsaveFavoriteMovie
                                                favoriteArrayList.remove(mMovies.get(position));
                                                Log.d("favoriteList", id + "--" + name + "  " + position);
                                                Log.d("favoriteList", favoriteArrayList.toString());
                                            }
                                        }
                                    }
        );
    }
    public void save_movie_to_fav(int id){
        SharedPreferences sharedPreferences;
        sharedPreferences=mContext.getSharedPreferences(shared_preferences_movies,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        String s =sharedPreferences.getString(movies_shared,"");
        s=s+id+",";
        editor.putString(movies_shared,s);
        editor.apply();
        Log.d("fav_movies", "save movie: "+s);
    }
    public boolean is_in_shared(String id){
        SharedPreferences sharedPreferences;
        sharedPreferences=mContext.getSharedPreferences(shared_preferences_movies,Context.MODE_PRIVATE);
        String s =sharedPreferences.getString(movies_shared,"");
        List<String> x= Arrays.asList(s.split(","));
        return x.contains(id);
    }
    public boolean delete_from_fav(String id){
        SharedPreferences sharedPreferences;
        sharedPreferences=mContext.getSharedPreferences(shared_preferences_movies,Context.MODE_PRIVATE);
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
    // Returns the total count of items in the list
    public int getItemCount() {
        return mMovies.size();
    }
    //filter search
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
