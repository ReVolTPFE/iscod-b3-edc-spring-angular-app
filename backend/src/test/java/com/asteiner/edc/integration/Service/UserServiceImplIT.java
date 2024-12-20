package com.asteiner.edc.integration.Service;

import com.asteiner.edc.Entity.User;
import com.asteiner.edc.Others.GetUserDto;
import com.asteiner.edc.Service.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@ExtendWith(SpringExtension.class)
public class UserServiceImplIT {
    @Autowired
    private UserServiceImpl userService;

    @Test
    @DirtiesContext
    public void testCreate() {
        User user = new User();
        user.setUsername("Username");
        user.setEmail("user@example.com");
        user.setPassword("password");

        int userId = userService.create(user);

        assertEquals(1, userId);
    }

    @Test
    @DirtiesContext
    public void testGetUsers() {
        User user1 = new User();
        user1.setUsername("User1");
        user1.setEmail("user1@example.com");
        user1.setPassword("password");

        User user2 = new User();
        user2.setUsername("User2");
        user2.setEmail("user2@example.com");
        user2.setPassword("password");

        userService.create(user1);
        userService.create(user2);

        List<GetUserDto> users = userService.getUsers();

        Assertions.assertThat(users).hasSize(2);
        Assertions.assertThat(users.getFirst().getUsername()).isEqualTo("User1");
    }
}
