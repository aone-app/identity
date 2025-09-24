package com.nerosoft.aone.identity.event;

import org.springframework.context.ApplicationEvent;

public class UserAuthSucceedEvent extends ApplicationEvent {
    public UserAuthSucceedEvent(Object source) {
        super(source);
    }


}
