package com.example.miniapp_restaurant.Models.Server.Command;

import com.example.miniapp_restaurant.Models.Server.Object.ObjectId;

public class InvokedBy {
    private UserId userId;

    public InvokedBy() {
    }

    public InvokedBy(String superapp, String email) {
        this.userId = new UserId(superapp, email);
    }

    public InvokedBy(UserId userId) {
        super();
        this.userId = userId;
    }

    public UserId getUserId() {
        return userId;
    }

    public InvokedBy setUserId(UserId userId) {
        this.userId = userId;
        return this;
    }
}
