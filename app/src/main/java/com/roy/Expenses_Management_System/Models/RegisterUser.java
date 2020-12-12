package com.roy.Expenses_Management_System.Models;

import com.google.firebase.database.ServerValue;

import java.sql.Timestamp;

public class RegisterUser {

    private String User_name,Mobile_No,User_key;
    private Object timeStamp;

    public RegisterUser(String user_name, String mobile_No) {
        User_name = user_name;
        Mobile_No = mobile_No;
        timeStamp = ServerValue.TIMESTAMP;
    }

    public String getUser_name() {
        return User_name;
    }

    public String getMobile_No() {
        return Mobile_No;
    }


    public Object getTimeStamp() {
        return timeStamp;
    }

    public String getUser_key() {
        return User_key;
    }

    public void setUser_name(String user_name) {
        User_name = user_name;
    }

    public void setMobile_No(String mobile_No) {
        Mobile_No = mobile_No;
    }


    public void setUser_key(String user_key) {
        User_key = user_key;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }
}
