package com.example.miniapp_restaurant.Models.Server.Object;

public class UserSession {
    private static UserSession instance;
    private String boundaryId;
    private String userEmail;
    private final String SUPERAPP= "2024b.gal.said";


    private UserSession() {
    }

    public static synchronized UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public String getSUPERAPP() {
        return SUPERAPP;
    }

    public void setBoundaryId(String boundaryId) {
        this.boundaryId = boundaryId;
    }

    public String getBoundaryId() {
        return boundaryId;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void clearSession() {
        this.boundaryId = null;
        this.userEmail = null;
    }
}
