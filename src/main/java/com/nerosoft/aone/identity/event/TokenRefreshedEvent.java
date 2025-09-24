package com.nerosoft.aone.identity.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Event triggered when a token is refreshed.
 */
@Getter
public class TokenRefreshedEvent extends ApplicationEvent {
    public TokenRefreshedEvent(Object source, String originToken) {
        super(source);
        this.originToken = originToken;
    }

    private final String originToken;
}
