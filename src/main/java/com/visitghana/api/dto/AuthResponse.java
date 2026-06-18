package com.visitghana.api.dto;

import lombok.Data;

@Data
public class AuthResponse {
    private Long userId;
    private String fullName;
    private String email;
    private String role;
    private String message;

    public AuthResponse(Long userId, String fullName,
                        String email, String role, String message) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.message = message;
    }
}