package com.example.firebase_login.Models;

public class Comment {

    private String username, comment;



    public Comment(String username, String comment) {
        this.username = username;
        this.comment = comment;
    }


    public Comment(){

    }


    public String getUsername() {

        return username;

    }

    public void setUsername(String username) {

        this.username = username;

    }

    public String getComment() {

        return comment;

    }

    public void setComment(String comment) {

        this.comment = comment;

    }
}
