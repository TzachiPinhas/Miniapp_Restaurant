package com.example.miniapp_restaurant.Models;

public class Review {
    private String name;
    private String overview;
    private String date;
    private float rating;

    public Review() {
    }

    public String getName() {
        return name;
    }

    public Review setName(String name) {
        this.name = name;
        return this;
    }


    public String getOverview() {
        return overview;
    }

    public Review setOverview(String overview) {
        this.overview = overview;
        return this;
    }

    public String getDate() {
        return date;
    }

    public Review setDate(String date) {
        this.date = date;
        return this;
    }

    public float getRating() {
        return rating;
    }

    public Review setRating(float rating) {
        this.rating = rating;
        return this;
    }
}
