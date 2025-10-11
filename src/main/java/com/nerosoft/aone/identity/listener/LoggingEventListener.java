package com.nerosoft.aone.identity.listener;

import com.nerosoft.aone.identity.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class LoggingEventListener {
    @Async
    @EventListener
    public void handleUserAuthSucceedEvent(UserAuthSucceedEvent event) {
        // Handle the event (e.g., log it, send notification, etc.)
        System.out.println("Token granted event received: " + event);

        if(Objects.equals(event.getProvider(), "refresh_token")){
            return;
        } else {
            System.out.println("Authentication provider: " + event.getProvider());
        }
    }
}
