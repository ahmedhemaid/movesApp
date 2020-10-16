package com.example.moviesapp.basese;

import java.util.ArrayList;
import java.util.List;

public class Actor {
    public ArrayList<Movie> famousMovies;
    private String name;
    private String description;
    private String birthday;
    private String linkOfImage;
    private String knownOfDepartment;
    private String gender;
    private String popularity;
    private String place_of_birth;
    private Boolean isFavorite=false;
    private int id;

    public Actor(String name){
        this.name=name;
    }
    public Actor(){}
    public Actor(int id,String name,String linkOfImage){
        this.name=name;
        this.id=id;
        this.linkOfImage=linkOfImage;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
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

    public String getBirthday() {
        return birthday;
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

    public String getKnownOfDepartment() {
        return knownOfDepartment;
    }

    public void setKnownOfDepartment(String knownOfDepartment) {
        this.knownOfDepartment = knownOfDepartment;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public void setPlace_of_birth(String place_of_birth) {
        this.place_of_birth = place_of_birth;
    }
}
