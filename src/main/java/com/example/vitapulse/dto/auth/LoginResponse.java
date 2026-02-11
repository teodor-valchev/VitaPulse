package com.example.vitapulse.dto.auth;

public class LoginResponse {
    private String token;
    private String type = "Bearer";

    public LoginResponse(String token) {
        this.token = token;
    }

    public String getToken() { return token; }
    public String getType() { return type; }
}
