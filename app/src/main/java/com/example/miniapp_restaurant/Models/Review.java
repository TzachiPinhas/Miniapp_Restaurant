package com.example.miniapp_restaurant.Models;

import com.example.miniapp_restaurant.Models.Server.Object.ObjectBoundary;
import com.example.miniapp_restaurant.Models.Server.Object.ObjectId;
import com.example.miniapp_restaurant.Models.Server.Object.UserSession;
import com.google.gson.Gson;

import java.util.Map;

public class Review {
    private String name;
    private String overview;
    private String date;
    private float rating;

    public Review() {
    }


    public Review(String name, String overview, String date, float rating) {
        this.name = name;
        this.overview = overview;
        this.date = date;
        this.rating = rating;
    }

    public Review(ObjectBoundary objectBoundary) {
        Gson gson = new Gson();
        Review temp = gson.fromJson((String) objectBoundary.getObjectDetails().get("Review"), Review.class);
        this.name = temp.getName();
        this.overview = temp.getOverview();
        this.date = temp.getDate();
        this.rating = temp.getRating();


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
