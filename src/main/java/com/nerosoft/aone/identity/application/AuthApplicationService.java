package com.nerosoft.aone.identity.application;

import com.nerosoft.aone.identity.dto.*;

import javax.security.auth.login.CredentialException;

public interface AuthApplicationService {
    AuthResponseDto grant(AuthRequestDto request) throws CredentialException;

    AuthResponseDto refresh(String refreshToken) throws CredentialException;
}
