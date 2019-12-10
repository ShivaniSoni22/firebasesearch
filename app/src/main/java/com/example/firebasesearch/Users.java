package com.example.firebasesearch;

public class Users {

    public  String image, name, status;


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Users(String image, String name, String status) {
        this.image = image;
        this.name = name;
        this.status = status;
    }

    public Users(){

    }
}
