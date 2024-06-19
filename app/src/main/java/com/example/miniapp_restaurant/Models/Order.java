package com.example.miniapp_restaurant.Models;

import java.util.List;

public class Order {
    private String orderId;
    private String associationName;
    private List<String> items;
    private String timeStamp;
    private String status;

    public Order() {
    }

    public String getOrderId() {
        return orderId;
    }

    public Order setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getAssociationName() {
        return associationName;
    }

    public Order setAssociationName(String associationName) {
        this.associationName = associationName;
        return this;
    }

    public List<String> getItems() {
        return items;
    }

    public Order setItems(List<String> items) {
        this.items = items;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Order setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public Order setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }
}
