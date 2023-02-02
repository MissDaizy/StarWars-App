package com.diana_ukrainsky.starwars.ui.movie_list;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.diana_ukrainsky.common.Constants;
import com.diana_ukrainsky.starwars.data.model.FilmsResult;
import com.diana_ukrainsky.starwars.data.model.Movie;
import com.diana_ukrainsky.starwars.repository.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class MovieListViewModel extends ViewModel {
    private MutableLiveData<List<Movie>> movieListLiveData;
    private MutableLiveData<List<Movie>> filteredMovieListLiveData;

    // Variable for hiding and showing the loading spinner
    private MutableLiveData<Boolean> loading;
    private MutableLiveData<String> currentSearchTextLiveData;
    private CompositeDisposable disposables;
    private Repository repository;
    private FilterType selectedFilter;
    private SortType selectedSort;


    public MovieListViewModel() {
        this.repository = Repository.getInstance();

        init();
    }

    private void init() {
        movieListLiveData = new MutableLiveData<>();
        filteredMovieListLiveData = new MutableLiveData<>();
        loading = new MutableLiveData<>();
        currentSearchTextLiveData = new MutableLiveData<>("");

        disposables = new CompositeDisposable();
        selectedFilter = FilterType.ALL;
        selectedSort=SortType.ASC;
    }


    public MutableLiveData<List<Movie>> getMovieListLiveData() {
        return movieListLiveData;
    }

    public MutableLiveData<List<Movie>> getFilteredMovieListLiveData() {
        return filteredMovieListLiveData;
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public MutableLiveData<String> getCurrentSearchTextLiveData() {
        return currentSearchTextLiveData;
    }

    public void getMovies() {
        repository.getAllMovies()
                .debounce(400, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(movies -> {
                    List<Movie> movieList=new ArrayList<>();
                    for (Movie movie:movies.getResults()) {
                        String[] parts = movie.getMovieUrl().split("/");
                        String lastWord = parts[parts.length - 1];
                        movie.setMovieUrl(lastWord);
                        movieList.add(movie);
                    }
                    return movieList;
                })
                .subscribe(new Observer<List<Movie>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        loading.setValue(true);
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<Movie> movies) {
                        loading.setValue(false);
                        movieListLiveData.setValue(movies);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(Constants.LOG, "getMovies error: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        // Nothing to do here
                    }
                });
    }


    public void onEventMovieList(MovieListEvent event, Object object) {
        switch (event) {
            //TODO: FILTER

            case FILTER_LIST: {
                if(object instanceof FilterType)
                    selectedFilter = (FilterType) object;
                else if(object instanceof SortType)
                    selectedSort = (SortType)object;
                filterList();
                break;
            }
            case SEARCH: {
                String searchQuery=(String)object;
                searchMenuItems(searchQuery.toLowerCase());
                if(filteredMovieListLiveData.getValue()!=null) {
                    if(filteredMovieListLiveData.getValue().isEmpty())
                        loading.setValue(false);
                }
                break;
            }
        }
    }

    private void filterList() {
        List<Movie> filteredMenuItems;
        if (movieListLiveData.getValue() == null)
            return;
        else if (filteredMovieListLiveData.getValue() == null)
            filteredMenuItems = movieListLiveData.getValue();
        else
            filteredMenuItems = filteredMovieListLiveData.getValue();

        filterCases(filteredMenuItems);
    }
    private void filterCases(List<Movie> filteredContacts) {
        switch (selectedFilter) {
            case ALL:
                currentSearchTextLiveData.setValue("");
                filteredContacts =movieListLiveData.getValue();
                break;

            case YEAR:
                if(selectedSort.equals(SortType.ASC))
                    Collections.sort(filteredContacts,  new Movie.SortByYear());
                else
                    Collections.sort(filteredContacts, new Movie.SortByYear().reversed());

                break;

            case TITLE:
                if(selectedSort.equals(SortType.ASC))
                    Collections.sort(filteredContacts, new Movie.SortByTitle());
                else
                    Collections.sort(filteredContacts, new Movie.SortByTitle().reversed());

                break;


            case DIRECTOR:
                if(selectedSort.equals(SortType.ASC))
                    Collections.sort(filteredContacts, new Movie.SortByDirector());
                else
                    Collections.sort(filteredContacts, new Movie.SortByDirector().reversed());

                break;

        }
        filteredMovieListLiveData.setValue(filteredContacts);

    }
    public void disposeComposite() {
        disposables.dispose();
    }
    public void searchMenuItems(String searchQuery) {
        currentSearchTextLiveData.setValue(searchQuery);
        List<Movie> filteredMenuItems = new ArrayList<>();
        if (movieListLiveData.getValue() != null) {
            for (Movie movie : movieListLiveData.getValue()) {
                if (movie.getTitle().toLowerCase().contains(searchQuery)) {
                    filteredMenuItems.add(movie);
                }
            }
            filteredMovieListLiveData.setValue(filteredMenuItems);
        }
    }
}
