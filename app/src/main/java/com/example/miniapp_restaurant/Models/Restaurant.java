package com.example.miniapp_restaurant.Models;

import java.util.ArrayList;

public class Restaurant {

    private String restaurantID;
    private String restaurantName;
    private String restaurantAddress;
    private String restaurantPhone;
    private ArrayList<FoodItem> leftoverItems;


    public Restaurant() {
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public Restaurant setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
        return this;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public Restaurant setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
        return this;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public Restaurant setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
        return this;
    }

    public String getRestaurantPhone() {
        return restaurantPhone;
    }

    public Restaurant setRestaurantPhone(String restaurantPhone) {
        this.restaurantPhone = restaurantPhone;
        return this;
    }

    public ArrayList<FoodItem> getLeftoverItems() {
        return leftoverItems;
    }

    public Restaurant setLeftoverItems(ArrayList<FoodItem> leftoverItems) {
        this.leftoverItems = leftoverItems;
        return this;
    }
}



