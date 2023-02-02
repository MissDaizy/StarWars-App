package com.diana_ukrainsky.starwars.data.model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.Comparator;
import java.util.List;

public class Movie {
    @SerializedName("title")
    private String title;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("opening_crawl")
    private String summary;
    @SerializedName("director")
    private String director;
    @SerializedName("characters")
    private List<String> characters;
    @SerializedName("producer")
    private String producer;
    @SerializedName("url")
    private String movieUrl;

    public Movie() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public List<String> getCharacters() {
        return characters;
    }

    public void setCharacters(List<String> characters) {
        this.characters = characters;
    }

    public String getMovieUrl() {
        return movieUrl;
    }

    public void setMovieUrl(String movieUrl) {
        this.movieUrl = movieUrl;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj!=null) {
            Movie movie=(Movie) obj;
            return title==movie.getTitle();
        }
        return false;
    }

    public static class SortByTitle implements Comparator<Movie> {
        // Used for sorting title
        public int compare(Movie m1, Movie m2) {
            return m1.getTitle().compareTo(m2.getTitle());
        }
    }
    public static class SortByYear implements Comparator<Movie> {
        // Used for sorting in ascending order of
        // roll number
        public int compare(Movie m1, Movie m2) {
            return m1.getReleaseDate().compareTo(m2.getReleaseDate()) ;
        }
    }
    public static class SortByDirector implements Comparator<Movie> {
        // Used for sorting title
        public int compare(Movie m1, Movie m2) {
            return m1.getDirector().compareTo(m2.getDirector());
        }
    }
}
