package com.nerosoft.aone.identity.event;

import org.springframework.context.ApplicationEvent;

public class UserAuthFailedEvent extends ApplicationEvent {
    public UserAuthFailedEvent(Object source) {
        super(source);
    }
}
