package com.tian.m3client_v1.room;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class MovieViewModel extends ViewModel {
    private MovieRepository cRepository;
    private MutableLiveData<List<Movie>> allMovies;

    public MovieViewModel (){
        allMovies = new MutableLiveData<>();
    }

//    public LiveData<List<Movie>> getAllMovies() {
//        return cRepository.getAllMovies();
//    }

    public void initializeVars (Application application) {
        cRepository = new MovieRepository(application);
    }

    public void insert(Movie movie) {
        cRepository.insert(movie);
    }

    public Movie findById(int movieId) {
        return cRepository.findByID(movieId);
    }
}
