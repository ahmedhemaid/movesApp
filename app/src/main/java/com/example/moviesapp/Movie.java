package com.example.moviesapp;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String name;
    private String description;
    private String dateOfMovie;
    private String linkOfImage;
    private Boolean isFavorite;
    public Movie(String name,String description){
        this.name=name;
        this.description=description;
    }
    public Movie(){}

    public void setDateOfMovie(String dateOfMovie) {
        this.dateOfMovie = dateOfMovie;
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

    public String getDateOfMovie() {
        return dateOfMovie;
    }

    public String getDescription() {
        return description;
    }

    public String getLinkOfImage() {
        return linkOfImage;
    }

    public String getName() {
        return name;
    }

    //data set
    public static List<Movie> generateDummyMovies() {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            movies.add(new Movie("lion king","movie description"));
        }
        return movies;
    }

}
