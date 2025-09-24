package com.nerosoft.aone.identity.listener;

import com.nerosoft.aone.identity.event.UserAuthSucceedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEventListener {
    @Async
    @EventListener
    public void handleTokenGrantedEvent(UserAuthSucceedEvent event) {
        // Handle the event (e.g., log it, send notification, etc.)
        System.out.println("Token granted event received: " + event);
    }
}
