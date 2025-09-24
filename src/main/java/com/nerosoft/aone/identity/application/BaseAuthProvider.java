package com.nerosoft.aone.identity.application;

import org.springframework.core.env.Environment;

public abstract class BaseAuthProvider implements AuthProvider {
    public BaseAuthProvider(Environment environment, String provider) {
        clientId = environment.getProperty("oauth." + provider + ".client-id");
        clientSecret = environment.getProperty("oauth." + provider + ".client-secret");
        redirectUri = environment.getProperty("oauth.redirect-uri");
    }

    protected final String clientId;
    protected final String clientSecret;
    protected final String redirectUri;
}
