package com.example.jbonham81.week1;

import java.io.Serializable;

public class Movie implements Serializable {

    public static final String TAG = "------------MovieClass------------";

    public String movieTitle;
    public String movieRelease;
    public String movieRating;

    public Movie(String title, String release, String rating){
        movieTitle = title;
        movieRelease = release;
        movieRating = rating;
    }
    public String getMovieTitle() {
             return movieTitle == null ? "" : movieTitle;
    }
    public String getMovieRelease() {
        return movieRelease == null ? "" : movieRelease;
    }
    public String getMovieRating() {
        return movieRating == null ? "" : movieRating;
    }
    public String getMovie() {
        return movieTitle;
    }
}
