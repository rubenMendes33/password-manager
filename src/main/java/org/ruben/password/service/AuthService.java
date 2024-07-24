package org.ruben.password.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.ruben.password.entities.AppUser;
import org.ruben.password.repository.AppUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    @Inject
    AppUserRepository userRepository;

    public AppUser authenticate(String username) {
        LOGGER.warn("Searching for {}.", username );
        return userRepository.findByUsername(username);
    }
}
