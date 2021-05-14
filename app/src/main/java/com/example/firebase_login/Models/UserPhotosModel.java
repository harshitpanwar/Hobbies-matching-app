package com.example.firebase_login.Models;

public class UserPhotosModel {

    private String ImageUrl;


    public UserPhotosModel(String ImageUrl) {
        this.ImageUrl = ImageUrl;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String ImageUrl) {
        this.ImageUrl = ImageUrl;
    }
}
