package com.example.miniapp_restaurant.Models.Server.Object;

public class ObjectId {
    private String superapp;
    private String id;

    public ObjectId() {
    }

    public ObjectId(String superapp, String id) {
        this.superapp = superapp;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public ObjectId setId(String id) {
        this.id = id;
        return this;
    }

    public String getSuperapp() {
        return superapp;
    }

    public ObjectId setSuperapp(String superapp) {
        this.superapp = superapp;
        return this;
    }


    @Override
    public String toString() {
        return "ObjectId{" +
                "superApp='" + superapp + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
