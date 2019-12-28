package com.example.moviesapp.basese;

import java.util.ArrayList;
import java.util.List;

public class Actor {
    private String name;
    private String description;
    private String dateOfActor;
    private String linkOfImage;
    private Boolean isFavorite=false;
    private int id;

    public Actor(String name){
        this.name=name;
    }
    public Actor(){}

    public void setDateOfActor(String dateOfActor) {
        this.dateOfActor = dateOfActor;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }

    public void setLinkOfImage(String linkOfImage) {
        this.linkOfImage = linkOfImage;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Boolean isFavorite() {
        return isFavorite;
    }

    public String getDateOfActor() {
        return dateOfActor;
    }

    public String getDescription() {
        return description;
    }

    public String getLinkOfImage() {
        return "https://image.tmdb.org/t/p/w500"+linkOfImage+"";
    }

    public String getName() {
        return name;
    }

    //data set
    public static List<Actor> generateDummyMovies() {
        List<Actor> actors = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            actors.add(new Actor("actor name"));
        }
        return actors;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}