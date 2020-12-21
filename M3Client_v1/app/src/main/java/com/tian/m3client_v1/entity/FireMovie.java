package com.tian.m3client_v1.entity;

public class FireMovie {

    private Integer mid;

    private String movName;

    private String movRelDate;

    private String movAddDateTime;

    private String imdbID;

    private String perId;

    public FireMovie(){}

    public FireMovie(Integer mid, String movName, String movRelDate, String movAddDateTime, String imdbID, String perId) {
        this.mid = mid;
        this.movName = movName;
        this.movRelDate = movRelDate;
        this.movAddDateTime = movAddDateTime;
        this.imdbID = imdbID;
        this.perId = perId;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getMovName() {
        return movName;
    }

    public void setMovName(String movName) {
        this.movName = movName;
    }

    public String getMovRelDate() {
        return movRelDate;
    }

    public void setMovRelDate(String movRelDate) {
        this.movRelDate = movRelDate;
    }

    public String getMovAddDateTime() {
        return movAddDateTime;
    }

    public void setMovAddDateTime(String movAddDateTime) {
        this.movAddDateTime = movAddDateTime;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getPerId() {
        return perId;
    }

    public void setPerId(String perId) {
        this.perId = perId;
    }
}
