package com.example.httesti;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    // User specific variables
    private String username;
    private String name;
    private Integer age;
    private Double height;
    private Double weight;
    private Double BMI;
    private String homeCity;
    private ArrayList<activityPlace> favourites;

    // getters and setters for each of the variables
    public User(String username){
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setFavourites(ArrayList<activityPlace> favourites) {
        this.favourites = favourites;
    }

    public void setHomeCity(String homeCity) {
        this.homeCity = homeCity;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setBMI() {
        this.BMI = this.weight/Math.pow(this.height,2);
    }

    public String getUsername() {
        return this.username;
    }

    public Integer getAge() {
        return this.age;
    }

    public Double getWeight() {
        return this.weight;
    }

    public Double getHeight() {
        return this.height;
    }

    public Double getBMI() {
        return this.BMI;
    }

    public ArrayList<activityPlace> getFavourites() {
        return this.favourites;
    }

    public String getName() {
        return this.name;
    }

    public String getHomeCity() {
        return this.homeCity;
    }
}
