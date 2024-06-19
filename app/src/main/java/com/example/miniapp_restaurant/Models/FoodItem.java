package com.example.miniapp_restaurant.Models;

public class FoodItem {

    private String name;
    private int amount;
    private String type;
    private String expiryDate;

    public FoodItem() {
    }

    public String getName() {
        return name;
    }

    public FoodItem setName(String name) {
        this.name = name;
        return this;
    }

    public int getAmount() {
        return amount;
    }

    public FoodItem setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public String getType() {
        return type;
    }

    public FoodItem setType(String type) {
        this.type = type;
        return this;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public FoodItem setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }
}
