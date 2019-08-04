package com.example.moviesapp;

public class MovieTrailer  {
    private String videoURL;
    public MovieTrailer(){
    }
    public MovieTrailer(String videoURL){
        this.videoURL=videoURL;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }
}
