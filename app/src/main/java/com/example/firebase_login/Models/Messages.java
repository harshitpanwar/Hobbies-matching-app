package com.example.firebase_login.Models;

public class Messages {

    String uId, message,date,time;
    Long timestamp;



    public Messages(String uId, String message, Long timestamp, String date, String time) {
        this.uId = uId;
        this.message = message;
        this.timestamp = timestamp;
        this.date = date;
        this.time = time;

    }



    public Messages(String uId, String message) {
        this.uId = uId;
        this.message = message;
    }


    public Messages(){

    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
