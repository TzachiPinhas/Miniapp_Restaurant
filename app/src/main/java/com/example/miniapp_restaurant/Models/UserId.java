package com.example.miniapp_restaurant.Models;

public class UserId {

    private String superapp;
    private String email;

    public UserId() {
    }

    public String getEmail() {
        return email;
    }

    public UserId setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getSuperapp() {
        return superapp;
    }

    public UserId setSuperapp(String superapp) {
        this.superapp = superapp;
        return this;
    }
}
