package com.avit.apnamzp.models;

public class User {
    private String name;
    private String phoneNo;
    private String password;

    public User(String name, String phoneNo, String password) {
        this.name = name;
        this.phoneNo = phoneNo;
        this.password = password;
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
