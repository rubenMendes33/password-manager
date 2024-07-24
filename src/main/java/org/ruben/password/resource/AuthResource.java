package org.ruben.password.resource;

import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ruben.password.entities.AppUser;
import org.ruben.password.repository.AppUserRepository;
import org.ruben.password.service.AuthService;

import java.util.HashMap;
import java.util.Map;


@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    AuthService authService;

    @POST
    @Path("/login")
    public Response login(AppUser appUser) {
        AppUser foundUser = authService.authenticate(appUser.user_name);
        if (foundUser != null && foundUser.password.equals(appUser.password)) {
            String token = Jwt.issuer("example.com")
                    .upn(foundUser.user_name)
                    .groups("User")
                    .sign();
            return Response.ok().header("Authorization", "Bearer " + token).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
