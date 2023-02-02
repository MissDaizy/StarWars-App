package com.diana_ukrainsky.starwars.data.remote;

import com.diana_ukrainsky.starwars.data.model.FilmsResult;
import com.diana_ukrainsky.starwars.data.model.Movie;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JsonApiMovies {
    @GET("films")
    Observable<FilmsResult> getAllMovies();

    @GET("films/{movieId}")
    Observable<Movie> getMovieDetails(
            @Path("movieId") String id
    );
    @GET("films/{characterUrl}")
    Observable<Character> getCharacterDetails(
            @Path("characterUrl") String id
    );

}
