package com.tian.m3client_v1.entity;


public class Cinematable {
    private Integer cinema_id;
    private String cinemaname;
    private Integer cinemaPostcode;

    public Cinematable(){

    }

    public Cinematable(Integer cinema_id, String cinemaname, Integer cinemaPostcode) {
        this.cinema_id = cinema_id;
        this.cinemaname = cinemaname;
        this.cinemaPostcode = cinemaPostcode;
    }

    public Integer getCinema_id() {
        return cinema_id;
    }

    public void setCinema_id(Integer cinema_id) {
        this.cinema_id = cinema_id;
    }

    public String getCinemaname() {
        return cinemaname;
    }

    public void setCinemaname(String cinemaname) {
        this.cinemaname = cinemaname;
    }

    public Integer getCinema_postcode() {
        return cinemaPostcode;
    }

    public void setCinema_postcode(Integer cinema_postcode) {
        this.cinemaPostcode = cinema_postcode;
    }
}
