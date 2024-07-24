package org.ruben.password.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "app_user")
public class AppUser extends PanacheEntity {
    public String user_name;
    public String password;
    public String email;
}
