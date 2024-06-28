package com.example.miniapp_restaurant.Models.Server.Object;

public class CreatedBy {
    private UserId userId;

    public CreatedBy() {
    }

    public CreatedBy(String superapp, String email) {
        this.userId = new UserId(superapp, email);
    }


    public UserId getUserId() {
        return userId;
    }

    public CreatedBy setUserId(UserId userId) {
        this.userId = userId;
        return this;
    }
}
