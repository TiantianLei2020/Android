package com.tian.m3client_v1.entity;


public class Memoirtable {
    private Integer memoirId;
    private String moviename;
    private String moviereleasedate;
    private String watchedDate;
    private String watchedTime;
    private String comment;
    private Double rating;
    private Cinematable cinemaId;
    private Usertable userId;
//    private String userId;
//    private String cinemaId;


    public Memoirtable() {

    }

    public Memoirtable(Integer memoirId, String moviename, String moviereleasedate, String watchedDate, String watchedTime, String comment, Double rating, Cinematable cinemaId, Usertable userId) {
        this.memoirId = memoirId;
        this.moviename = moviename;
        this.moviereleasedate = moviereleasedate;
        this.watchedDate = watchedDate;
        this.watchedTime = watchedTime;
        this.comment = comment;
        this.rating = rating;
        this.cinemaId = cinemaId;
        this.userId = userId;
    }
    //    public Memoirtable(Integer memoirId, String moviename, String moviereleasedate,
//                       String watchedDate, String watchedTime, String comment, Double rating, String userId, String cinemaId) {
//        this.memoirId = memoirId;
//        this.moviename = moviename;
//        this.moviereleasedate = moviereleasedate;
//        this.watchedDate = watchedDate;
//        this.watchedTime = watchedTime;
//        this.comment = comment;
//        this.rating = rating;
//        this.userId = userId;
//        this.cinemaId = cinemaId;
//    }

    public Memoirtable(String moviename, String moviereleasedate, Double rating) {
        this.moviename = moviename;
        this.moviereleasedate = moviereleasedate;
        this.rating = rating;
    }

    public Integer getMemoirId() {
        return memoirId;
    }

    public void setMemoirId(Integer memoirId) {
        this.memoirId = memoirId;
    }

    public String getMoviename() {
        return moviename;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    public String getMoviereleasedate() {
        return moviereleasedate;
    }

    public void setMoviereleasedate(String moviereleasedate) {
        this.moviereleasedate = moviereleasedate;
    }

    public String getWatchedDate() {
        return watchedDate;
    }

    public void setWatchedDate(String watchedDate) {
        this.watchedDate = watchedDate;
    }

    public String getWatchedTime() {
        return watchedTime;
    }

    public void setWatchedTime(String watchedTime) {
        this.watchedTime = watchedTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

//    public String getUserId() {
//        return userId;
//    }
//
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }
//
//    public String getCinemaId() {
//        return cinemaId;
//    }
//
//    public void setCinemaId(String cinemaId) {
//        this.cinemaId = cinemaId;
//    }


    public Cinematable getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Cinematable cinemaId) {
        this.cinemaId = cinemaId;
    }

    public Usertable getUserId() {
        return userId;
    }

    public void setUserId(Usertable userId) {
        this.userId = userId;
    }
}
