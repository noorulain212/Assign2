package com.example.assign2;


public class Restaurant {
    private String name;
    private String location;
    private String phone;
    private String description;
    private String ratings;

    // Constructor, getters, and setters

    public Restaurant(){

    }

    public Restaurant(String name, String location, String phone, String description, String ratings) {
        this.name = name;
        this.location = location;
        this.phone = phone;
        this.description = description;
        this.ratings = ratings;

    }
    public String getName() {return name;}
    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }


}