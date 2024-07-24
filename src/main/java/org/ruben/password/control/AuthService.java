package org.ruben.password.control;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.ruben.password.entity.AppUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    @Inject
    AppUserRepository userRepository;

    public boolean authenticate(String username, String password) {
        LOGGER.info("Searching for {}.", username );
        AppUser userFound = userRepository.findByUsername(username);
        return userFound != null && userFound.password.equals(password);
    }

    @Transactional
    public void registerUser(AppUser newUser) {
        userRepository.persist(newUser);
    }
}
