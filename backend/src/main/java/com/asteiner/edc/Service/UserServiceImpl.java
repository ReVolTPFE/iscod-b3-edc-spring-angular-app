package com.asteiner.edc.Service;

import com.asteiner.edc.Entity.User;
import com.asteiner.edc.Others.GetUserDto;
import com.asteiner.edc.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public int create(User user) {
        int userId = userRepository.save(user).getId();

        return userId;
    }

    @Override
    public List<GetUserDto> getUsers() {
        List<User> users = (List<User>) userRepository.findAll();

        List<GetUserDto> usersDto = new ArrayList<>();

        for (User user : users) {
            GetUserDto userDto = new GetUserDto();
            userDto.setId(user.getId());
            userDto.setUsername(user.getUsername());
            userDto.setEmail(user.getEmail());

            usersDto.add(userDto);
        }

        return usersDto;
    }
}
