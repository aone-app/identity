package com.nerosoft.aone.identity.application;

import com.nerosoft.aone.identity.dto.*;
import org.springframework.scheduling.annotation.Async;

import javax.security.auth.login.CredentialException;
import java.util.concurrent.CompletableFuture;

@Async
public interface AuthApplicationService {
    CompletableFuture<AuthResponseDto> grant(AuthRequestDto request) throws CredentialException;

    CompletableFuture<AuthResponseDto> refresh(String refreshToken) throws CredentialException;
}
