package com.diana_ukrainsky.starwars.repository;

import com.diana_ukrainsky.starwars.data.model.FilmsResult;
import com.diana_ukrainsky.starwars.data.model.Movie;
import com.diana_ukrainsky.starwars.data.remote.ApiService;
import com.diana_ukrainsky.starwars.data.remote.JsonApiMovies;

import io.reactivex.rxjava3.core.Observable;

public class Repository {
    private static Repository INSTANCE = null;

    private JsonApiMovies jsonApiMovies;
    public static Repository getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Repository();

        return INSTANCE;
    }
    private Repository( ) {
        this.jsonApiMovies = ApiService.getInstance().getJsonApiMovies();
    }

    public Observable<FilmsResult> getAllMovies(){
        return jsonApiMovies.getAllMovies();
    }
    public Observable<Movie> getMovieDetails(String id){
        return jsonApiMovies.getMovieDetails(id);
    }
    public Observable<Character> getCharacterDetails(String id){
        return jsonApiMovies.getCharacterDetails(id);
    }
}
