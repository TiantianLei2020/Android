package com.tian.m3client_v1.entity;

import java.io.Serializable;

public class ItemModel implements Serializable {
    private String name;
    private String desc;
    private String imageSrc;
    private String orgUrl;
    private String releaseDate;
    private String watchDate;
    private String cinemaPostcode;
    private String comments;
    private String socre;
    private String IMDB;

    public ItemModel(String name, String IMDB) {
        this.name = name;
        this.IMDB = IMDB;
    }

    public ItemModel(String name, String desc, String imageSrc, String orgUrl) {
        this.name = name;
        this.desc = desc;
        this.imageSrc = imageSrc;
        this.orgUrl = orgUrl;
    }

    public ItemModel(String name, String imageSrc, String releaseDate, String watchDate,
                      String cinemaPostcode, String comments, String socre, String IMDB) {
        this.name = name;
        this.imageSrc = imageSrc;
        this.releaseDate = releaseDate;
        this.watchDate = watchDate;
        this.cinemaPostcode = cinemaPostcode;
        this.comments = comments;
        this.socre = socre;
        this.IMDB = IMDB;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public String getOrgUrl() {
        return orgUrl;
    }

    public void setOrgUrl(String orgUrl) {
        this.orgUrl = orgUrl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getWatchDate() {
        return watchDate;
    }

    public void setWatchDate(String watchDate) {
        this.watchDate = watchDate;
    }

    public String getCinemaPostcode() {
        return cinemaPostcode;
    }

    public void setCinemaPostcode(String cinemaPostcode) {
        this.cinemaPostcode = cinemaPostcode;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getSocre() {
        return socre;
    }

    public void setSocre(String socre) {
        this.socre = socre;
    }

    public String getIMDB() {
        return IMDB;
    }

    public void setIMDB(String IMDB) {
        this.IMDB = IMDB;
    }

}
