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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by delaroy on 8/17/18.
 */

public class FavoriteMoviesAdapter extends RecyclerView.Adapter<FavoriteMoviesAdapter.MyViewHolder> {
    private OnItemClickListener mListener;
    private Context mContext;
    private List<Movie> movieList;
    List<FavoriteMovies> favoriteArrayList;
List<Movie>movies=RecyclerAdapterMovies.mMovies;
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
        loadData();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FavoriteMoviesAdapter.MyViewHolder viewHolder, final int position) {
        final ImageView favoriteIcon = viewHolder.itemView.findViewById(R.id.delete_icon_in_fav_movies);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView delete;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.text_view_movie_name_fav);
            delete = (ImageView) view.findViewById(R.id.delete_icon_in_fav_movies);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int pos = getAdapterPosition();
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            movieList.remove(pos);
                            notifyDataSetChanged();
                                }
                    });
                    /* if (pos != RecyclerView.NO_POSITION){
                        Movie clickedDataItem = movieList.get(pos);
                        Intent intent = new Intent(mContext, DetailActivity.class);
                        intent.putExtra("movies", clickedDataItem );
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                        Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getOriginalTitle(), Toast.LENGTH_SHORT).show();
                    }*/
                }
            });
        }
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
        Log.d("shared", favoriteArrayList.size() + "");

    }

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }
}