package com.tian.m3client_v1.entity;

public class Usertable {
    private Integer userId;
    private String userName;
    private String surname;
    private String gender;
    private String dob;
    private String stNumber;
    private String stName;
    private String userState;
    private Integer postode;

    public Usertable() {

    }

    public Usertable(Integer userId, String userName, String surname, String gender, String dob,
                     String stNumber, String stName, String userState, Integer postode) {
        this.userId = userId;
        this.userName = userName;
        this.surname = surname;
        this.gender = gender;
        this.dob = dob;
        this.stNumber = stNumber;
        this.stName = stName;
        this.userState = userState;
        this.postode = postode;
    }

    public Usertable(Integer userId, String userName, String stNumber, String stName, Integer postode) {
        this.userId = userId;
        this.userName = userName;
        this.stNumber = stNumber;
        this.stName = stName;
        this.postode = postode;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getStNumber() {
        return stNumber;
    }

    public void setStNumber(String stNumber) {
        this.stNumber = stNumber;
    }

    public String getStName() {
        return stName;
    }

    public void setStName(String stName) {
        this.stName = stName;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public Integer getPostode() {
        return postode;
    }

    public void setPostode(Integer postode) {
        this.postode = postode;
    }
}
