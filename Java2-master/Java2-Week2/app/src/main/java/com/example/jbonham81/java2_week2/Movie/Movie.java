package com.example.jbonham81.java2_week2.Movie;


import java.io.Serializable;

public class Movie implements Serializable {

    public static final String TAG = "-------------- Movie --------------";
    public static final long SerialVersionUID = 1234567898;

    public String movieTitle;
    public String movieYear;
    public String movieDirector;
    public String movieWriter;
    public String movieGenre;

    public Movie(String _title, String _year, String _director, String _writer, String _genre){
        movieTitle = _title;
        movieYear = _year;
        movieDirector = _director;
        movieWriter = _writer;
        movieGenre = _genre;

    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMovieYear() {
        return movieYear;
    }

    public String getMovieDirector() {
        return movieDirector;
    }

    public String getMovieWriter() {
        return movieWriter;
    }

    public String getMovieGenre() {
        return movieGenre;
    }

    public void setMovieTitle() {
        this.movieTitle =  movieTitle;
    }

    public void setMovieYear() {
        this.movieYear =  movieYear;
    }

    public void setMovieDirector() {
        this.movieDirector =  movieDirector;
    }

    public void setMovieWriter() {
        this.movieWriter =  movieWriter;
    }

    public void setMovieGenre() {
        this.movieGenre =  movieGenre;
    }

}
