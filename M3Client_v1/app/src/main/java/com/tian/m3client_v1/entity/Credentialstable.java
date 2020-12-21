package com.tian.m3client_v1.entity;

import java.sql.Date;

public class Credentialstable {
    private Integer credentialsId;
    private String username;
    private String password;
    private String signDate;
    private Usertable userId;

    public Credentialstable () {

    }

    public Credentialstable(Integer credentialsId, String username,
                            String password, String signDate, Usertable userId) {
        this.credentialsId = credentialsId;
        this.username = username;
        this.password = password;
        this.signDate = signDate;
        this.userId = userId;
    }

    public Integer getCredentialsId() {
        return credentialsId;
    }

    public void setCredentialsId(Integer credentialsId) {
        this.credentialsId = credentialsId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public Usertable getUserId() {
        return userId;
    }

    public void setUserId(Usertable userId) {
        this.userId = userId;
    }
}
