package com.example.firebase_login.Models;

public class User {

    private String uid;
    private String name;
    private String email;
    private String imageurl;
    private String messageId;

    public User(){

    }

    public User(String uid, String name, String email, String imageurl, String messageId) {
        this.uid = uid;
        this.name = name;
        this.imageurl = imageurl;
        this.email = email;
        this.messageId = messageId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }



    public String getUid(String key) {
        return uid;
    }
}
