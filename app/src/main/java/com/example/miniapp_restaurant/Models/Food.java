package com.example.miniapp_restaurant.Models;

public class Food {

    private String name;
    private int amount;
    private String type;
    private String expiryDate;

    public Food() {
    }

    public String getName() {
        return name;
    }

    public Food setName(String name) {
        this.name = name;
        return this;
    }

    public int getAmount() {
        return amount;
    }

    public Food setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public String getType() {
        return type;
    }

    public Food setType(String type) {
        this.type = type;
        return this;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public Food setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }
}
