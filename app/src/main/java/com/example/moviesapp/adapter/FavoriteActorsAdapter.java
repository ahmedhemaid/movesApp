package com.example.moviesapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviesapp.R;
import com.example.moviesapp.basese.Actor;

import java.util.List;

/**
 * Created by delaroy on 8/17/18.
 */

public class FavoriteActorsAdapter extends RecyclerView.Adapter<FavoriteActorsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Actor> actorList;


    public FavoriteActorsAdapter(Context mContext, List<Actor> actorList){
        this.mContext = mContext;
        this.actorList = actorList;
    }

    @Override
    public FavoriteActorsAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_of_actors_in_favorite, viewGroup, false);

        return new MyViewHolder(view);
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public void onBindViewHolder(final FavoriteActorsAdapter.MyViewHolder viewHolder, int i){


    }

    @Override
    public int getItemCount(){
        return actorList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title, userrating;
        public ImageView thumbnail;

        public MyViewHolder(View view){
            super(view);
            title = (TextView) view.findViewById(R.id.text_view_movie_name_fav);
            thumbnail = (ImageView) view.findViewById(R.id.image_view_movie);

            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int pos = getAdapterPosition();
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
}
