package com.example.miniapp_restaurant.Models;

import com.example.miniapp_restaurant.Models.Server.Object.CreatedBy;
import com.example.miniapp_restaurant.Models.Server.Object.Location;
import com.example.miniapp_restaurant.Models.Server.Object.ObjectBoundary;
import com.example.miniapp_restaurant.Models.Server.Object.ObjectId;
import com.example.miniapp_restaurant.Models.Server.Object.UserId;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Restaurant {
    private String restaurantID;
    private String restaurantEmail;
    private String restaurantName;
    private String restaurantAddress;
    private Location restaurantLocation;
    private String restaurantPhone;
    private List<Food> storage;
    private List<Order> orders;
    private List<Review> reviews;

    public Restaurant() {
    }

    public Restaurant(ObjectBoundary objectBoundary) {
        Gson gson = new Gson();
        Restaurant temp = gson.fromJson((String) objectBoundary.getObjectDetails().get("restaurant"), Restaurant.class);
        this.restaurantID = temp.getRestaurantID();
        this.restaurantEmail = temp.getRestaurantEmail();
        this.restaurantName = temp.getRestaurantName();
        this.restaurantAddress = temp.getRestaurantAddress();
        this.restaurantLocation = temp.getRestaurantLocation();
        this.restaurantPhone = temp.getRestaurantPhone();
        this.storage = temp.getStorage();
        this.orders = temp.getOrders();
        this.reviews = temp.getReviews();
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public Restaurant setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
        return this;
    }

    public String getRestaurantEmail() {
        return restaurantEmail;
    }

    public Restaurant setRestaurantEmail(String restaurantEmail) {
        this.restaurantEmail = restaurantEmail;
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

    public Location getRestaurantLocation() {
        return restaurantLocation;
    }

    public Restaurant setRestaurantLocation(Location restaurantLocation) {
        this.restaurantLocation = restaurantLocation;
        return this;
    }

    public String getRestaurantPhone() {
        return restaurantPhone;
    }

    public Restaurant setRestaurantPhone(String restaurantPhone) {
        this.restaurantPhone = restaurantPhone;
        return this;
    }

    public List<Food> getStorage() {
        return storage;
    }

    public Restaurant setStorage(List<Food> storage) {
        this.storage = storage;
        return this;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Restaurant setOrders(List<Order> orders) {
        this.orders = orders;
        return this;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public Restaurant setReviews(List<Review> reviews) {
        this.reviews = reviews;
        return this;
    }

    public ObjectBoundary toObjectBoundary(String email) {
        ObjectBoundary objectBoundary = new ObjectBoundary();
        objectBoundary.setType("Restaurant");
        objectBoundary.setAlias(this.restaurantName);

        objectBoundary.setLocation(new Location().setLat(0.0).setLng(0.0));
        objectBoundary.setActive(true);

        CreatedBy createdBy = new CreatedBy();
        UserId userId = new UserId();
        userId.setSuperapp("2024b.gal.said");
        userId.setEmail(email);
        createdBy.setUserId(userId);
        objectBoundary.setCreatedBy(createdBy);

        Gson gson = new Gson();
        Map<String, Object> objectDetails = Map.of("restaurant", gson.toJson(this, Restaurant.class));
        objectBoundary.setObjectDetails(objectDetails);

        return objectBoundary;
    }
}
