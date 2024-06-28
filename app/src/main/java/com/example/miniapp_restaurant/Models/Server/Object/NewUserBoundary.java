package com.example.miniapp_restaurant.Models.Server.Object;

public class NewUserBoundary {
    private String email;
    private RoleEnum role;
    private String username;
    private String avatar;

    public NewUserBoundary() {
    }

    public NewUserBoundary(String email, RoleEnum role, String userName, String avatar) {
        super();
        this.email = email;
        this.role = role;
        this.username = userName;
        this.avatar = avatar;
    }


    public String getEmail() {
        return email;
    }

    public NewUserBoundary setEmail(String email) {
        this.email = email;
        return this;
    }

    public RoleEnum getRole() {
        return role;
    }

    public NewUserBoundary setRole(RoleEnum role) {
        this.role = role;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public NewUserBoundary setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public NewUserBoundary setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    @Override
    public String toString() {
        return "NewUserBoundary{" +
                "email='" + email + '\'' +
                ", role=" + role +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
