package com.example.btl_mobile;

import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName("username")
    private String username;

    @SerializedName("passwordHash")
    private String passwordHash;

    // Constructor
    public Login(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
