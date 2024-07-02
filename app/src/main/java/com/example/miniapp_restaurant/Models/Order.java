package com.example.miniapp_restaurant.Models;

import com.example.miniapp_restaurant.Models.Server.Object.CreatedBy;
import com.example.miniapp_restaurant.Models.Server.Object.Location;
import com.example.miniapp_restaurant.Models.Server.Object.ObjectBoundary;
import com.example.miniapp_restaurant.Models.Server.Object.ObjectId;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Order {
    private ObjectId orderID;
    private String donatorEmail;
    private String donatorName;
    private String associationName;
    private Location donatorLocation;
    private String orderDate;
    private String orderTime;
    private List<Food> foods;
    private OrderStatus orderStatus;
    private WhoCarries whoCarries;

    public Order() {
    }

    public Order( String donatorEmail, String donatorName, String associationName, Location donatorLocation, String orderDate, String orderTime, List<Food> foods, OrderStatus orderStatus, WhoCarries whoCarries) {
        this.donatorEmail = donatorEmail;
        this.donatorName = donatorName;
        this.associationName = associationName;
        this.donatorLocation = donatorLocation;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.foods = foods;
        this.orderStatus = orderStatus;
        this.whoCarries = whoCarries;
    }

    public Order(ObjectBoundary objectBoundary) {
        Gson gson = new Gson();
        Order temp = gson.fromJson((String) objectBoundary.getObjectDetails().get("order"), Order.class);
        this.orderID = objectBoundary.getObjectId();
        this.donatorEmail = temp.getDonatorEmail();
        this.donatorName = temp.getDonatorName();
        this.donatorLocation = temp.getDonatorLocation();
        this.orderDate = temp.getOrderDate();
        this.orderTime = temp.getOrderTime();
        this.foods = temp.getFoods();
        this.orderStatus = temp.getOrderStatus();
        this.whoCarries = temp.getWhoCarries();
    }

    public static List<Order> convertObjectBoundaryList(List<ObjectBoundary> objectBoundaryList) {
        List<Order> orders = new ArrayList<>();
        for (ObjectBoundary objectBoundary : objectBoundaryList) {
            orders.add(new Order(objectBoundary));
        }
        return orders;
    }

    public String getDonatorEmail() {
        return donatorEmail;
    }

    public String getDonatorName() {
        return donatorName;
    }

    public Order setDonatorName(String donatorName) {
        this.donatorName = donatorName;
        return this;
    }

    public Location getDonatorLocation() {
        return donatorLocation;
    }

    public Order setDonatorLocation(Location donatorLocation) {
        this.donatorLocation = donatorLocation;
        return this;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public Order setOrderDate(String orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public Order setOrderTime(String orderTime) {
        this.orderTime = orderTime;
        return this;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public Order setFoods(List<Food> foods) {
        this.foods = foods;
        return this;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public Order setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public ObjectId getOrderID() {
        return orderID;
    }

    public Order setOrderID(ObjectId orderID) {
        this.orderID = orderID;
        return this;
    }

    public Order setDonatorEmail(String donatorEmail) {
        this.donatorEmail = donatorEmail;
        return this;
    }

    public String getAssociationName() {
        return associationName;
    }

    public Order setAssociationName(String associationName) {
        this.associationName = associationName;
        return this;
    }

    public WhoCarries getWhoCarries() {
        return whoCarries;
    }

    public Order setWhoCarries(WhoCarries whoCarries) {
        this.whoCarries = whoCarries;
        return this;
    }



    @Override
    public String toString() {
        return "Order{" +
                "orderID='" + donatorEmail + '\'' +
                ", donatorName='" + donatorName + '\'' +
                ", donatorLocation='" + donatorLocation + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", foods=" + foods +
                ", orderStatus=" + orderStatus +
                '}';
    }

    public ObjectBoundary toObjectBoundary(String email) {
        ObjectBoundary objectBoundary = new ObjectBoundary();
        objectBoundary.setObjectId(orderID);
        objectBoundary.setType("Order");
        objectBoundary.setAlias(donatorEmail);
        objectBoundary.setCreatedBy(new CreatedBy("2024b.gal.said", "ziv@gmail.com"));
        objectBoundary.setLocation(donatorLocation);
        objectBoundary.setActive(true);

        Gson gson = new Gson();
        Map<String, Object> orderMap = Map.of("order", gson.toJson(this, Order.class));
        objectBoundary.setObjectDetails(orderMap);

        return objectBoundary;
    }
}
