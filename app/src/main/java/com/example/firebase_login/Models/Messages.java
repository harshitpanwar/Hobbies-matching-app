package com.example.firebase_login.Models;

public class Messages {

    String uId, message;
    Long timestamp;

    public Messages(String uId, String message, Long timestamp) {
        this.uId = uId;
        this.message = message;
        this.timestamp = timestamp;
    }



    public Messages(String uId, String message) {
        this.uId = uId;
        this.message = message;
    }


    public Messages(){

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
