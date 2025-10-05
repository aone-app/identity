package com.nerosoft.aone.identity.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

public class UserAuthFailedEvent extends ApplicationEvent {
    public UserAuthFailedEvent(Object source) {
        super(source);

    }

    @Getter
    @Setter
    private String provider;

    @Getter
    @Setter
    private String username;
}
