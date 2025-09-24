package com.nerosoft.aone.identity.dto;

import lombok.Data;

/**
 * This class is used to encapsulate the data required for authentication operations.
 */
@Data
public class AuthRequestDto {
    private String username;
    private String password;
    private String provider;
    private String requestId;
}
