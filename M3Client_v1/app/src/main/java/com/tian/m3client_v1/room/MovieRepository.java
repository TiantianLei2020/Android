package com.tian.m3client_v1.room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MovieRepository {
    private MovieDAO dao;
    private LiveData<List<Movie>> allMovies;
    private Movie movie;

    public MovieRepository(Application application){
        MovieDB movieDB = MovieDB.getInstance(application);
        dao = movieDB.movieDAO();
    }

//    public LiveData<List<Movie>> getAllMovies() {
//        allMovies = dao.getAll();
//        return allMovies;
//    }

    public void insert (final Movie movie){
        MovieDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(movie);
            }
        });
    }

    public void deleteAll() {
        MovieDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.deleteAll();
            }
        });
    }

    public void delete(final Movie movie) {
        MovieDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.delete(movie);
            }
        });
    }

    public Movie findByID(final int movieId) {
        MovieDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Movie runMovie = dao.findByID(movieId);
                setMovie(runMovie);
            }
        });
        return movie;
    }

    public void setMovie (Movie movie) {
        this.movie = movie;
    }
}
