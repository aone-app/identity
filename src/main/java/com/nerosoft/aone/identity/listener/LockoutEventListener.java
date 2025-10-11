package com.nerosoft.aone.identity.listener;

import com.nerosoft.aone.identity.event.UserAuthFailedEvent;
import com.nerosoft.aone.identity.event.UserAuthSucceedEvent;
import com.nerosoft.aone.identity.repository.UserRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

/*
 * Listener for handling user lockout based on authentication events.
 */
@Component
public class LockoutEventListener {

    private final ApplicationContext applicationContext;

    public LockoutEventListener(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Async
    @EventListener
    public void handleUserAuthSucceedEvent(UserAuthSucceedEvent event) {
        // Handle the event (e.g., log it, send notification, etc.)
        System.out.println("Token granted event received: " + event);

        //var context = FacesContextUtils.getRequiredWebApplicationContext(FacesContext.getCurrentInstance());

        var repository = applicationContext.getBean(UserRepository.class);

        var optional = repository.findById(event.getSubject());
        if (optional.isEmpty()) {
            return;
        }

        var user = optional.get();

        if (user.getLockoutEnd() != null && user.getLockoutEnd().before(new Date())) {
            user.setAccessFailedCount(0);
            user.setLockoutEnd(null);
            repository.save(user);
        }
    }

    @Async
    @EventListener
    public void handleUserAuthFailedEvent(UserAuthFailedEvent event) {
        // Handle the event (e.g., log it, send notification, etc.)
        System.out.println("Token grant failed event received: " + event);

        var repository = applicationContext.getBean(UserRepository.class);

        var optional = repository.findByUsername(event.getUsername());
        if (optional.isEmpty()) {
            return;
        }

        var user = optional.get();

        int accessFailedCount = user.getAccessFailedCount() + 1;
        user.setAccessFailedCount(accessFailedCount);

        if (accessFailedCount >= 5) {
            // Lock the user account for 15 minutes
            Date lockoutEnd = new Date(System.currentTimeMillis() + 15 * 60 * 1000);
            user.setLockoutEnd(lockoutEnd);
        }

        repository.save(user);
    }
}
