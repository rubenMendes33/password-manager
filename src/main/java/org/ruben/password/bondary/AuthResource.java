package org.ruben.password.bondary;

import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.ruben.password.control.AppUser;
import org.ruben.password.control.AuthService;


@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    AuthService authService;

    @POST
    @Path("/login")
    public Response login(AppUser appUser) {
        if (!authService.authenticate(appUser.user_name, appUser.password)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } else {
            String token = Jwt.issuer("example.com")
                    .upn(appUser.user_name)
                    .groups("User")
                    .sign();
            return Response.ok().header("Authorization", "Bearer " + token).build();
        }
    }

    @POST
    @Path("/register")
    public Response register(AppUser appUser) {
        authService.registerUser(appUser);
        return Response.ok().build();
    }
}
