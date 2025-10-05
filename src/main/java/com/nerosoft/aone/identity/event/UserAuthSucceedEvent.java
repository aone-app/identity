package com.nerosoft.aone.identity.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.HashMap;
import java.util.Map;

public class UserAuthSucceedEvent extends ApplicationEvent {
    public UserAuthSucceedEvent(Object source) {
        super(source);
    }

    @Getter
    @Setter
    private String provider;

    @Getter
    @Setter
    private long subject;

    @Getter
    private Map<String, String> extendedInfo = new HashMap<>();
}
