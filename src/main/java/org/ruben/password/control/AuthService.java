package org.ruben.password.control;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import org.ruben.password.entity.AppUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    @Inject
    AppUserRepository userRepository;

    public boolean canAuthenticate(String username, String password) {
        LOGGER.info("Searching for {}.", username );
        AppUser userFound = userRepository.findByUsername(username);
        // Use BCrypt to verify the password against the stored hash
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), userFound.password);

        // Return whether the password matches
        return result.verified;
    }

    @Transactional
    public void registerUser(AppUser newUser) {
        // Check if username or email already exists
        if (userRepository.count("user_name", newUser.user_name) > 0) {
            throw new BadRequestException("Username is already taken");
        }
        if (userRepository.count("email", newUser.email) > 0) {
            throw new BadRequestException("Email is already registered");
        }
        newUser.password = BCrypt.withDefaults().hashToString(12, newUser.password.toCharArray());
        userRepository.persist(newUser);
    }
}
