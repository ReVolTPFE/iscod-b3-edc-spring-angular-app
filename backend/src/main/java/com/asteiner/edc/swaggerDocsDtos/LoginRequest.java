package com.asteiner.edc.swaggerDocsDtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request body for user login")
public class LoginRequest {
    @Schema(description = "Email of the user", example = "user@example.com")
    private String email;

    @Schema(description = "Password of the user", example = "password123")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}