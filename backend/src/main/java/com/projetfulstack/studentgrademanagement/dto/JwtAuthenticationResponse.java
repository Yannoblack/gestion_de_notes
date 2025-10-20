package com.projetfulstack.studentgrademanagement.dto;

/**
 * JWT Authentication Response DTO
 * Data transfer object for JWT authentication responses
 * 
 * @author ProjetFullStack
 * @version 1.0.0
 */
public class JwtAuthenticationResponse {
    
    private String accessToken;
    private String tokenType = "Bearer";
    private Long userId;
    private String email;
    private String firstName;
    private String lastName;
    private String role;

    // Constructors
    public JwtAuthenticationResponse() {
    }

    public JwtAuthenticationResponse(String accessToken, Long userId, String email, 
                                   String firstName, String lastName, String role) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    // Getters and Setters
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
