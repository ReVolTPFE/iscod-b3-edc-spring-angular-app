package com.asteiner.edc.Controller;

import com.asteiner.edc.Entity.User;
import com.asteiner.edc.Service.AuthService;
import com.asteiner.edc.swaggerDocsDtos.LoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController
{
    @Autowired
    private AuthService authService;

    @Operation(summary = "User login", description = "Authenticates a user and returns its id if the credentials are correct")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully authenticated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(type = "integer", description = "User id", example = "1"))
            }),
            @ApiResponse(responseCode = "404", description = "User not found with these credentials", content = @Content)
    })
    @PostMapping("/login")
    public int login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User login details", required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginRequest.class)))
            @RequestBody User user) {
        String email = user.getEmail();
        String password = user.getPassword();

        return authService.login(email, password);
    }
}
