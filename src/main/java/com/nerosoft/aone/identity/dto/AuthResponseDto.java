package com.nerosoft.aone.identity.dto;

import lombok.Data;

@Data
public class AuthResponseDto {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private long expiresIn;
    private long issuedAt;
    private String subject;
    private String username;
}

