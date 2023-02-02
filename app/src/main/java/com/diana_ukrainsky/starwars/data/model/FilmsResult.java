package com.diana_ukrainsky.starwars.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FilmsResult {
    @SerializedName("count")
    private int count;
    @SerializedName("next")
    private int next;
    @SerializedName("previous")
    private int previous;
    @SerializedName("results")
    private List<Movie> results;

    public FilmsResult() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public int getPrevious() {
        return previous;
    }

    public void setPrevious(int previous) {
        this.previous = previous;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
