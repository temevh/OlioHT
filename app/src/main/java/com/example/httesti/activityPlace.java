package com.example.httesti;

import java.io.Serializable;

public class activityPlace implements Serializable {
    // relevant info of a spesific sport/activity location
    private Integer ID;
    private String admin = "N/A";
    private String email = "N/A";
    private String phoneNumber = "N/A";
    private String address = "N/A";
    private String addInfo ="N/A";
    private String placeType = "N/A";
    private String name = "N/A";
    private String url = "";


    // getters and setters for each variable
    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public Integer getID() {
        return ID;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public String getAddress() {
        return address;
    }

    public String getAdmin() {
        return admin;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPlaceType() {
        return placeType;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
