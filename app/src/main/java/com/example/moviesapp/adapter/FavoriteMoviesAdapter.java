package com.example.moviesapp.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviesapp.R;
import com.example.moviesapp.basese.FavoriteMovies;
import com.example.moviesapp.basese.Movie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.moviesapp.adapter.ActionMoviesAdapter.movies_shared;

/**
 * Created by delaroy on 8/17/18.
 */

public class FavoriteMoviesAdapter extends RecyclerView.Adapter<FavoriteMoviesAdapter.MyViewHolder> {
    private OnItemClickListener mListener;
    private Context mContext;
    int mPosition;
    public static final String shared_preferences_movies = "FAVORITE_MOVIES";
    public static final String movies_shared="fav_movie";
    private List<Movie> movieList;
    List<FavoriteMovies> favoriteArrayList;
List<Movie>movies= PopularMoviesAdapter.mMovies;
    public FavoriteMoviesAdapter(Context mContext, List<Movie> movieList) {
        this.mContext = mContext;
        this.movieList = movieList;
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }

    @Override
    public FavoriteMoviesAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_of_movie_in_favorite, viewGroup, false);
                return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FavoriteMoviesAdapter.MyViewHolder viewHolder, final int position) {
        final ImageView favoriteIcon = viewHolder.itemView.findViewById(R.id.delete_icon_in_fav_movies);
        final ImageView imageViewPicture;
        TextView textViewName;
        mPosition=position;
        textViewName=viewHolder.itemView.findViewById(R.id.text_view_movie_name_fav);
        textViewName.setText(movieList.get(position).getTitle()+"");
        imageViewPicture=viewHolder.itemView.findViewById(R.id.image_view_movie);
        Picasso.get().load(movieList.get(position).getPoster_path()).into(imageViewPicture);
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getItemCount() {
        return movieList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView delete;
        public ImageView profile;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.text_view_movie_name_fav);
            delete = (ImageView) view.findViewById(R.id.delete_icon_in_fav_movies);
            profile=view.findViewById(R.id.image_view_movie);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            movieList.remove(getAdapterPosition());
                            delete_from_fav(movieList.get(mPosition).getId()+"");
                            notifyDataSetChanged();
                        }
                    });
                }
            });
        }
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

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }
}