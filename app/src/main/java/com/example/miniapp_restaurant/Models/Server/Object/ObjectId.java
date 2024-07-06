package com.example.miniapp_restaurant.Models.Server.Object;

public class ObjectId {
    private String superApp;
    private String id;

    public ObjectId() {
    }

    public ObjectId(String superApp, String id) {
        this.superApp = superApp;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public ObjectId setId(String id) {
        this.id = id;
        return this;
    }

    public String getSuperApp() {
        return superApp;
    }

    public ObjectId setSuperApp(String superApp) {
        this.superApp = superApp;
        return this;
    }


    @Override
    public String toString() {
        return "ObjectId{" +
                "superApp='" + superApp + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
