package com.nerosoft.aone.identity.application;

import org.springframework.web.client.RestClientException;

public interface AuthProvider {
    /**
     * Authenticate the user using the provided authorization code.
     *
     * @param authCode The authorization code received from the OAuth provider.
     * @return An OAuthResult containing user information and tokens.
     * @throws RestClientException If there is an error during the authentication process.
     */
    OAuthResult authenticate(String authCode) throws RestClientException;
}
