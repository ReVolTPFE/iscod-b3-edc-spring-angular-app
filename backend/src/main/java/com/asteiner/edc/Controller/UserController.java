package com.asteiner.edc.Controller;

import com.asteiner.edc.Entity.User;
import com.asteiner.edc.Others.GetUserDto;
import com.asteiner.edc.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController
{
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    @ResponseStatus(code = HttpStatus.CREATED)
    public int createUser(@RequestBody User user) {
        return userService.create(user);
    }

    @GetMapping("")
    @ResponseStatus(code = HttpStatus.OK)
    public List<GetUserDto> getUsers() {
        return userService.getUsers();
    }
}
