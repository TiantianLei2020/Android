package com.tian.m3client_v1.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Movie {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo(name = "movie_name")
    public String movieName;
    @ColumnInfo(name = "release_date")
    public String releaseDate;
    @ColumnInfo(name = "add_date_time")
    public String addDate;

    public Movie(int uid, String movieName, String releaseDate, String addDate) {
        this.uid = uid;
        this.movieName = movieName;
        this.releaseDate = releaseDate;
        this.addDate = addDate;
    }

    @Ignore
    public Movie() {

    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }
}
