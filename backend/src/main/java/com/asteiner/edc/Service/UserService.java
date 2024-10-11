package com.asteiner.edc.Service;

import com.asteiner.edc.Entity.User;
import com.asteiner.edc.Others.GetUserDto;

import java.util.List;

public interface UserService {
    int create(User user);

    List<GetUserDto> getUsers();
}
