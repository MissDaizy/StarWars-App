package com.diana_ukrainsky.starwars.ui.movie_details;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.diana_ukrainsky.common.Constants;
import com.diana_ukrainsky.starwars.data.model.Movie;
import com.diana_ukrainsky.starwars.repository.Repository;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailsViewModel extends ViewModel {

    private MutableLiveData<Movie> movieDetailsLiveData;
    private CompositeDisposable disposables;
    private Repository repository;

    public MovieDetailsViewModel() {
        this.repository = Repository.getInstance();

        init();
    }

    private void init() {
        movieDetailsLiveData = new MutableLiveData<>();
        disposables=new CompositeDisposable();
    }

    public MutableLiveData<Movie> getMovieDetailsLiveData() {
        return movieDetailsLiveData;
    }

    public void getMenuItemDetails(String id) {
        repository.getMovieDetails(id)
                .debounce(400, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Movie>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Movie contactDetails) {
                        movieDetailsLiveData.setValue(contactDetails);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(Constants.LOG, "getMovieDetails error: " + e.getMessage());

                    }

                    @Override
                    public void onComplete() {
                        // Nothing to do here

                    }
                });

    }
    public void onEventMovieDetails(MovieDetailsEvent movieDetailsEvent, Object object) {
        switch (movieDetailsEvent) {
            case GET_MOVIE_DETAILS:
                Movie movie = (Movie) object;
                getMenuItemDetails(movie.getMovieUrl());
                break;

        }
    }
}
