package com.example.moviesapp.api;


public class TheMovieDBAPI {
    public static int idActorAPI;
    private static String API_KEY = "4a7d2c349f439f8ac8d00d0bff4b7e32";
    private static String nameForSearch="suicide squad";
    public static String baseURL = "https://api.themoviedb.org/3/";
    public static String recentMoviesURL = baseURL + "movie/upcoming?api_key=" + API_KEY;
    public static String popularMoviesURL = baseURL + "movie/popular?api_key=" + API_KEY;
    public static String latestMoviesURL = baseURL + "movie/latest?api_key=" + API_KEY;
    public static String topRatedMoviesURL = baseURL + "movie/top_rated?api_key="+ API_KEY;
    public static String actorsDetailsURL=baseURL+"person/popular?api_key="+API_KEY+"&language=en-US&page=";
    public static String searchMovieURL=baseURL+"search/movie?query="+nameForSearch+"api_key="+API_KEY+"&language=en-US&page=1&include_adult=false";
}

