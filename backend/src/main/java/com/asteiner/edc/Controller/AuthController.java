package com.asteiner.edc.Controller;

import com.asteiner.edc.Entity.User;
import com.asteiner.edc.Service.AuthService;
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

    @PostMapping("/login")
    public int login(@RequestBody User user) {
        String email = user.getEmail();
        String password = user.getPassword();

        return authService.login(email, password);
    }
}
