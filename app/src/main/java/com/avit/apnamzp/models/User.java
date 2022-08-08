package com.avit.apnamzp.models;

public class User {
    private String name;
    private String phoneNo;
    private String password;
    private String fcmId;

    public User(String name) {
        this.name = name;
    }

    public User(String name, String phoneNo, String password) {
        this.name = name;
        this.phoneNo = phoneNo;
        this.password = password;
    }

    public User(String name, String phoneNo, String password, String fcmId) {
        this.name = name;
        this.phoneNo = phoneNo;
        this.password = password;
        this.fcmId = fcmId;
    }

    public User(String phoneNo, String fcmId) {
        this.phoneNo = phoneNo;
        this.fcmId = fcmId;
    }

    public String getFcmId() {
        return fcmId;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getPassword() {
        return password;
    }
}
