package org.ruben.password.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.ruben.password.entities.AppUser;

@ApplicationScoped
public class AppUserRepository implements PanacheRepository<AppUser> {
    public AppUser findByUsername(String username) {
        return find("user_name", username).firstResult();
    }
}
