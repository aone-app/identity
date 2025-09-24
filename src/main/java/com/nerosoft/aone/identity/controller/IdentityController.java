package com.nerosoft.aone.identity.controller;

import com.nerosoft.aone.identity.application.AuthApplicationService;
import com.nerosoft.aone.identity.dto.AuthRequestDto;
import com.nerosoft.aone.identity.dto.AuthResponseDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.CredentialException;
import java.util.concurrent.ExecutionException;

@RestController("/api/identity")
public class IdentityController {

    private final AuthApplicationService _service;

    public IdentityController(AuthApplicationService service) {
        _service = service;
    }

    /**
     * Grant a new token based on the provided authentication request.
     *
     * @param request The authentication request containing user credentials.
     * @return An AuthResponseDto containing the granted tokens and related information.
     */
    @PostMapping("/token/grant")
    public AuthResponseDto grantToken(@RequestBody AuthRequestDto request) {
        try {
            var future = _service.grant(request);
            return future.get();
        } catch (CredentialException | ExecutionException | InterruptedException e) {
            return null;
        }
    }

    /**
     * Refresh an existing token using the provided refresh token.
     *
     * @param token The refresh token to be used for obtaining a new access token.
     * @return An AuthResponseDto containing the new tokens and related information.
     */
    @PostMapping("/token/refresh")
    public AuthResponseDto refreshToken(@RequestParam String token) {
        try {
            var future = _service.refresh(token);
            return future.get();
        } catch (CredentialException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Revoke an existing token.
     */
    @PostMapping("/token/revoke")
    public void revokeToken() {
        // do nothing for now
    }
}
