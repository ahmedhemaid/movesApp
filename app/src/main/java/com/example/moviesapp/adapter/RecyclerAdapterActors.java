package com.example.moviesapp.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviesapp.R;
import com.example.moviesapp.activites.ActorDetails;
import com.example.moviesapp.api.TheMovieDBAPI;
import com.example.moviesapp.basese.Actor;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class RecyclerAdapterActors extends RecyclerView.Adapter<RecyclerAdapterActors.ViewHolder> {
    TextView actorName;
    String mName="";
    ArrayList<String> names;
    private OnItemClickListener mListener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }
    // Store a member variable for the contacts
    private List<String>namesOfActor;
    private List<Actor> mActors;
    // Pass in the contact array into the constructor
    public RecyclerAdapterActors(List<Actor> movies) {
        mActors = movies;
    }
    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @NonNull
    @Override
    public RecyclerAdapterActors.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the custom layout
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rootView = inflater.inflate(R.layout.row_of_actors, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(rootView);
        // Return the completed view to render on screen
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull final RecyclerAdapterActors.ViewHolder holder, final int position) {
        // Get the data model based on position
        Actor actor = mActors.get(position);

        // Set item views based on your views and data model
        ImageView imageViewPicture;
        TextView textViewName;
        textViewName=holder.itemView.findViewById(R.id.text_view_actor_name);
        textViewName.setText(mActors.get(position).getName()+"");
        imageViewPicture=holder.itemView.findViewById(R.id.actor_profile);
        Picasso.get().load(mActors.get(position).getLinkOfImage()).into(imageViewPicture);
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
        imageViewPicture = holder.itemView.findViewById(R.id.actor_profile);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.itemView.getContext(), ActorDetails.class);
                intent.putExtra("idActor",mActors.get(position).getId());
                TheMovieDBAPI.idActorAPI=mActors.get(position).getId();
                holder.itemView.getContext().startActivity(intent);
            }
        });
        favorite = holder.itemView.findViewById(R.id.favorite_actor);
        favorite.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (mActors.get(position).isFavorite() ==false) {
                                                favorite.setImageResource(R.drawable.favorite);
                                                mActors.get(position).setFavorite(true);
                                            }
                                            else {
                                                favorite.setImageResource(R.drawable.unfavorite);
                                                mActors.get(position).setFavorite(false);
                                            }
                                        }
                                    }
        );
    }

    // Returns the total count of items in the list
    @Override

    public int getItemCount() {
            return mActors.size();
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
        namesOfActor=new ArrayList<>();
        namesOfActor.addAll(newList);
        notifyDataSetChanged();
    }
}
