package com.microservice.authservice;

import com.microservice.authservice.domain.dto.CreateUserRequest;
import com.microservice.authservice.service.UserService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class DatabaseInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final List<String> usernames = List.of(
            "ada.lovelace@nix.io",
            "alan.turing@nix.io",
            "dennis.ritchie@nix.io"
    );
    private final List<String> fullNames = List.of(
            "Ada Lovelace",
            "Alan Turing",
            "Dennis Ritchie"
    );
    private final List<String> roles = List.of(
            "USER_ADMIN",
            "AUTHOR_ADMIN",
            "BOOK_ADMIN"
    );
    private final String password = "Test12345_";

    private final UserService userService;

    public DatabaseInitializer(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        for (int i = 0; i < usernames.size(); ++i) {
            CreateUserRequest request = new CreateUserRequest();
            request.setUsername(usernames.get(i));
            request.setFullName(fullNames.get(i));
            request.setPassword(password);
            request.setRePassword(password);
            request.setAuthorities(Set.of(roles.get(i)));

            userService.create(request);
        }
    }

}