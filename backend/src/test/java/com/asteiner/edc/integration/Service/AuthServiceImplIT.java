package com.asteiner.edc.integration.Service;

import com.asteiner.edc.Entity.User;
import com.asteiner.edc.Service.AuthServiceImpl;
import com.asteiner.edc.Service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
@ExtendWith(SpringExtension.class)
public class AuthServiceImplIT {
    @Autowired
    private AuthServiceImpl authService;

    @Autowired
    private UserServiceImpl userService;

    @Test
    @DirtiesContext
    public void testLogin() {
        User user = new User();
        user.setUsername("Username");
        user.setEmail("user@example.com");
        user.setPassword("password");

        userService.create(user);

        String email = "user@example.com";
        String password = "password";

        int userId = authService.login(email, password);

        assertEquals(1, userId);
    }
}
