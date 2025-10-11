package com.nerosoft.aone.identity.listener;

import com.nerosoft.aone.identity.domain.Token;
import com.nerosoft.aone.identity.event.TokenRefreshedEvent;
import com.nerosoft.aone.identity.event.UserAuthSucceedEvent;
import com.nerosoft.aone.identity.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenStorageEventListener {
    private final TokenRepository repository;

    @Autowired
    public TokenStorageEventListener(TokenRepository repository) {
        this.repository = repository;
    }

    @Async
    @EventListener
    public void handleTokenRefreshedEvent(TokenRefreshedEvent event) {
        // Handle the event (e.g., log it, send notification, etc.)
        System.out.println("Token refreshed event received: " + event);


    }

    @Async
    @EventListener
    public void handleUserAuthSucceedEvent(UserAuthSucceedEvent event) {
        // Handle the event (e.g., log it, send notification, etc.)
        System.out.println("Token granted event received: " + event);

        var issuedAt = Long.parseLong(event.getExtendedInfo().get("issuedAt"));
        var expiresAt = Long.parseLong(event.getExtendedInfo().get("expiresAt"));

        var token = Token.create("access_token", event.getExtendedInfo().get("key"), event.getSubject(), new Date(issuedAt),new Date(expiresAt));
        repository.save(token);
    }
}
