package com.projetfulstack.studentgrademanagement.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Login Request DTO
 * Data transfer object for user login requests
 * 
 * @author ProjetFullStack
 * @version 1.0.0
 */
public class LoginRequest {
    
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    // Constructors
    public LoginRequest() {
    }

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
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
