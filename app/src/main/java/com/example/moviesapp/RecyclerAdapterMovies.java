package com.example.moviesapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class RecyclerAdapterMovies extends RecyclerView.Adapter<RecyclerAdapterMovies.ViewHolder> {
    private OnItemClickListener mListener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }
    // Store a member variable for the contacts
    private List<String>namesOfMovies;
    private List<Movie> mMovies;
    // Pass in the contact array into the constructor
    public RecyclerAdapterMovies(List<Movie> movies) {
        mMovies = movies;
    }
    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public RecyclerAdapterMovies.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Inflate the custom layout
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rootView = inflater.inflate(R.layout.row_of_movie, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(rootView);
        // Return the completed view to render on screen
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterMovies.ViewHolder holder, int position) {

        // Get the data model based on position
        Movie movie = mMovies.get(position);

        // Set item views based on your views and data model
    }

    // Returns the total count of items in the list
    @Override

    public int getItemCount() {
        return mMovies.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        ImageView imageViewPicture;
        TextView textViewName;
        ImageView favorite;
        boolean isPlay=false;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View rootView) {

            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(rootView);

            imageViewPicture = rootView.findViewById(R.id.image_view_movie);
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
            textViewName = rootView.findViewById(R.id.text_view_movie_name);
            favorite = rootView.findViewById(R.id.favorite);
            favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                if (isPlay==false) {
                    favorite.setImageResource(R.drawable.favorite);
                    isPlay=true;
                }
                else {
                    favorite.setImageResource(R.drawable.unfavorite);
                    isPlay=false;
                }
                }
                }
            );
        }
    }
    public void updateList(List<String> newList){
        namesOfMovies=new ArrayList<>();
        namesOfMovies.addAll(newList);
        notifyDataSetChanged();
    }
}
