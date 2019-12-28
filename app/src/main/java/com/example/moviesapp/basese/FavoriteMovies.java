package com.example.moviesapp.basese;

public class FavoriteMovies {
    private int id;
    private int cardPosition;
    private String name;
    private String profile_path;
    private boolean isFavorite;
    public FavoriteMovies(){
    }
    public FavoriteMovies(int id,int cardPosition,String name,String profile_path,boolean isFavorite){
        this.id=id;
        this.cardPosition=cardPosition;
        this.profile_path=profile_path;
        this.name=name;
        this.isFavorite=isFavorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCardPosition() {
        return cardPosition;
    }

    public void setCardPosition(int cardPosition) {
        this.cardPosition = cardPosition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
