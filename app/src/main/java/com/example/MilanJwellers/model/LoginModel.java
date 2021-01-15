package com.example.MilanJwellers.model;

public class LoginModel {
    String userName;
    String password;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    String userID;

    public LoginModel(String userName, String password,String userID) {
        this.userName = userName;
        this.password = password;
        this.userID = userID;
    }

    public LoginModel() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
